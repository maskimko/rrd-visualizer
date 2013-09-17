package Racks;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class RackCreatorGUI  {

private JFrame rackMaker = null;
	
	public void showMenu(){
		rackMaker = new JFrame("Rack Maker");
		/*rackMaker.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});*/
		rackMaker.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel params = new JPanel();
		params.setLayout(new BoxLayout(params, BoxLayout.Y_AXIS));
		JPanel namePane = new JPanel();
		namePane.setLayout(new BoxLayout(namePane, BoxLayout.X_AXIS));
		namePane.setBorder(BorderFactory.createTitledBorder("Name"));
		JPanel nameLabelBox = new JPanel();
		nameLabelBox.setLayout(new BoxLayout(nameLabelBox, BoxLayout.X_AXIS));
		nameLabelBox.setPreferredSize(new Dimension(200, 20));
		JPanel nameValueBox = new JPanel();
		nameValueBox.setLayout(new BoxLayout(nameValueBox, BoxLayout.X_AXIS));
		nameValueBox.setPreferredSize(new Dimension(200, 20));
		JLabel nameLb = new JLabel("Rack name");
		JTextField nameField = new JTextField("Rack name");
		nameLabelBox.add(nameLb);
		namePane.add(nameLabelBox);
		nameValueBox.add(nameField);
		namePane.add(nameValueBox);
		params.add(namePane);
		JPanel coordAndSize = new JPanel();
		coordAndSize.setLayout(new BoxLayout(coordAndSize, BoxLayout.Y_AXIS));
		coordAndSize.setBorder(BorderFactory.createTitledBorder("Coordinates and Size"));
		
		JPanel coordPane = new JPanel();
		coordPane.setLayout(new BoxLayout(coordPane, BoxLayout.X_AXIS));
		JPanel coordLabelBox = new JPanel();
		coordLabelBox.setLayout(new BoxLayout(coordLabelBox, BoxLayout.X_AXIS));
		coordLabelBox.setPreferredSize(new Dimension(200, 20));
		JPanel coordValueBox = new JPanel();
		coordValueBox.setLayout(new BoxLayout(coordValueBox, BoxLayout.X_AXIS));
		coordValueBox.setPreferredSize(new Dimension(200, 20));
		coordPane.add(coordLabelBox);
		coordPane.add(coordValueBox);
		coordAndSize.add(coordPane);
		JPanel sizePane = new JPanel();
		sizePane.setLayout(new BoxLayout(sizePane, BoxLayout.X_AXIS));
		JPanel sizeLabelBox = new JPanel();
		sizeLabelBox.setLayout(new BoxLayout(sizeLabelBox, BoxLayout.X_AXIS));
		sizeLabelBox.setPreferredSize(new Dimension(200, 20));
		JPanel sizeValueBox = new JPanel();
		sizeValueBox.setLayout(new BoxLayout(sizeValueBox, BoxLayout.X_AXIS));
		sizeValueBox.setPreferredSize(new Dimension(200, 20));
		sizePane.add(sizeLabelBox);
		sizePane.add(sizeValueBox);
		coordAndSize.add(sizePane);
		JLabel coordLb = new JLabel("X coordinate, Y coordinate");
		JLabel sizeLb = new JLabel("Width, Height");
		JTextField widthField = new JTextField("Rack width");
		JTextField heightField = new JTextField("Rack height");
		JTextField xField = new JTextField("X coordinate");
		JTextField yField = new JTextField("Y coordinate");
		coordLabelBox.add(coordLb);
		coordValueBox.add(xField);
		coordValueBox.add(Box.createRigidArea(new Dimension(10,0)));
		coordValueBox.add(yField);
		sizeLabelBox.add(sizeLb);
		sizeValueBox.add(widthField);
		sizeValueBox.add(Box.createRigidArea(new Dimension(10, 0)));
		sizeValueBox.add(heightField);
		params.add(coordAndSize);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.add(Box.createHorizontalGlue());
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				rackMaker.setVisible(false);
			}
		});
		JButton ok = new JButton("Ok");
		buttonPane.add(cancel);
		buttonPane.add(Box.createRigidArea(new Dimension(10,0)));
		buttonPane.add(ok);
		params.add(buttonPane);
		
		
		rackMaker.setContentPane(params);
		rackMaker.pack();
		rackMaker.setVisible(true);
	}
}
