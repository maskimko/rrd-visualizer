package Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyMandel {

	private static int max = 64;
	private Color[] colors;
	private double viewX = 0, viewY = 0;
	private double zoom = 1.0;
	private int widthOfPanel, heightOfPanel;
	private boolean drag, toDrag, rect = true, oldRect = true;
	private JPanel main;
	private int mouseX, mouseY, dragX, dragY, oldX, oldY;

	private void init() {
		colors = new Color[256];
		float h = 0;
		float c = 0;
		drag = false;
		toDrag = false;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				h = 2 * i;
				if (h > 15) {
					h = 31 - h;
				}
				c = j * 2;
				if (c > 15) {
					c = 31 - c; 
				}
				h = (float) h / 16;
				c = (float) c / 16;
				colors[i * 16 + j] = new Color(Color.HSBtoRGB(h, 0.5f, c));
			}
		}
	}

	public void go() {
		JFrame window = new JFrame("Mandelbrot set");
		main = new JPanel() {
			@Override
			public void paint(Graphics g) {

				if (drag) {
					g.setColor(Color.BLACK);
					g.setXORMode(Color.WHITE);
					if (oldRect) {
						int x = Math.min(mouseX, oldX);
						int y  = Math.min(mouseY, oldY);
						int w = Math.abs(mouseX- oldX);
						int h = Math.abs(mouseY - oldY);
						g.drawRect(x, y, w, h);
					} else {
						g.drawLine(mouseX, mouseY,  oldX, oldY);
					}
					
					if (rect) {
						int x = Math.min(mouseX, dragX);
						int y  = Math.min(mouseY, dragY);
						int w = Math.abs(mouseX- dragX);
						int h = Math.abs(mouseY - dragY);
						g.drawRect(x, y, w, h);
					} else {
						g.drawLine(mouseX, mouseY, dragX, dragY);
					}
					
					
					oldX = dragX;
					oldY = dragY;
					oldRect = rect;
					drag = false;
					return;
				}
				
			

				widthOfPanel = getSize().width;
				heightOfPanel = getSize().height;
				for (int i = 0; i < widthOfPanel; i++) {
					for (int j = 0; j < heightOfPanel; j++) {
						double r = zoom / Math.min(widthOfPanel, heightOfPanel);
						double dx = 2.5 * (i * r + viewX) - 2.0;
						double dy = 1.25 - 2.5 * (j * r + viewY);
						int value = mandel(dx, dy);
						g.setColor(colors[value % colors.length]);
						g.drawLine(i, j, i, j);
					}
				}
			}

			public void update(Graphics g) {
				paint(g);
			}
		};
		window.addKeyListener(new ListenTheKeyboard());
		main.addMouseListener(new MyMouseListener());
		main.addMouseMotionListener(new MyMouseMotionListener());
		

		window.setSize(new Dimension(400, 400));
		window.setContentPane(main);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	private int mandel(double px, double py) {
		double zx = 0, zy = 0, zy2 = 0, zx2 = 0;
		int value = 0;
		while (value < max && zx2 + zy2 < 4) {
			zy = 2 * zy * zx + py;
			zx = zx2 - zy2 + px;
			zx2 = zx * zx;
			zy2 = zy * zy;
			value++;
		}
		return value == max ? 0 : value;
	}

	public static void main(String[] args) {
		MyMandel mm = new MyMandel();
		mm.init();
		mm.go();
	}

	class MyMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
			Point relPnt = e.getPoint();
			if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
				if ((e.getModifiers() & InputEvent.SHIFT_MASK) != 0) {
					viewX += zoom * (mouseX -relPnt.x) / Math.min(widthOfPanel, heightOfPanel);
					viewY += zoom * (mouseY -relPnt.y) / Math.min(widthOfPanel, heightOfPanel);
				} else if (relPnt.x != mouseX && relPnt.y != mouseY) {
					viewX += zoom * Math.min(mouseX, relPnt.x)
							/ Math.min(widthOfPanel, heightOfPanel);
					viewY += zoom * Math.min(mouseY, relPnt.y)
							/ Math.min(widthOfPanel, heightOfPanel);
					zoom *= Math.max((double) Math.abs(mouseX - relPnt.x)
							/ widthOfPanel,
							(double) Math.abs(mouseY - relPnt.y)
									/ heightOfPanel);
					drag = false;
				}
				
				main.repaint();
			} else if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
				max += max / 4;
			
				main.repaint();
			}
			toDrag = false;
		}

		public void mousePressed(MouseEvent e) {
			mouseX = dragX = oldX = e.getX();
			mouseY = dragY = oldY = e.getY();
			toDrag = true;
		}

	}

	class MyMouseMotionListener implements MouseMotionListener {
		public void mouseMoved(MouseEvent e) {
		}

		public void mouseDragged(MouseEvent e) {
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
				dragX = e.getX();
				dragY = e.getY();
				drag = true;
				main.repaint();
			}
		}
	}
	
	
	class ListenTheKeyboard implements KeyListener {
		public void keyPressed(KeyEvent e){
			//System.out.println(e.getKeyCode());
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				max = 16;
				viewX = 0;
				viewY = 0;
				zoom = 1;
				main.repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_O){
				viewX -= 0.5 * zoom;
				viewY -= 0.5 * zoom;
				zoom *= 2.0;
			
				main.repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_SHIFT){
				if (rect) {
					rect = false;
					oldRect = true;
					if (toDrag){
						drag = true;
						main.repaint();
					}
				}
			}
		} 
		public void keyReleased(KeyEvent e){
			if (e.getKeyCode() == KeyEvent.VK_SHIFT){
				if (!rect) {
					rect = true;
					oldRect = false;
					if (toDrag) {
						drag = true;
						main.repaint();
					}
					
				}
			}
		}
		public void keyTyped(KeyEvent e) {} 
		
		

	}
}
