import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class GeneralGui {

	public JFrame mainframe;
	public JPanel mainpanel;
	private JMenuItem open;
	private SliderRRDTest sRRDt = null;
	
	public static void main(String[] main){
		GeneralGui gg = new GeneralGui();
		gg.go();
	}
	
	public void go(){
		mainframe = new JFrame("Wheather map tester");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainpanel = (JPanel) mainframe.getContentPane();
		//mainpanel.setBackground(new Color(0x01000000, true));
		JMenuBar menuBar = new JMenuBar();
		JMenu filemenu = new JMenu("File");
		JMenu modemenu = new JMenu("Mode");
		menuBar.add(filemenu);
		menuBar.add(modemenu);
		JMenuItem randomView = new JMenuItem("Random view");
		JMenuItem datePickerTest = new JMenuItem("Test of Date Picker");
		JMenuItem exit = new JMenuItem("Exit");
		open = new JMenuItem("Open file");
		open.setEnabled(false);
		open.addActionListener(new OpenFileListener());
		randomView.addActionListener(new RandomViewListener());
		datePickerTest.addActionListener(new DatePickerTestListener());
		exit.addActionListener(new ExitListener());
		modemenu.add(randomView);
		modemenu.add(datePickerTest);
		filemenu.add(exit);
		filemenu.add(open);
		mainframe.setJMenuBar(menuBar);
		mainframe.setSize(300, 300);
		
		mainframe.setVisible(true);
	}
	
	class OpenFileListener implements ActionListener {
		private final JFileChooser jfc = new JFileChooser(); 
		public void actionPerformed(ActionEvent ae) {
			int retVal = jfc.showOpenDialog(mainframe);
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileFilter  rrdFFilter = new FileNameExtensionFilter("RRD file", "rrd");
			jfc.setFileFilter(rrdFFilter);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File rrdFile = jfc.getSelectedFile();
				if (sRRDt != null) {
					try {
					sRRDt.setRRDFile(rrdFile);
					} catch (IOException ioe) {
						System.err.println(ioe.getMessage());
					}
				} else {
					System.err.println("Error: Cannot get view with slider");
				}
			}
		}
	}
	
	class RandomViewListener implements ActionListener {
		public void actionPerformed(ActionEvent ae){
			RandomWeatherMap rwm = new RandomWeatherMap();
			mainpanel = rwm.createMainPanel();
			mainframe.setContentPane(mainpanel);
			mainframe.pack();
			open.setEnabled(false);
		}
	}
	
	class DatePickerTestListener implements ActionListener {
		public void actionPerformed(ActionEvent ae){
			
			sRRDt = new SliderRRDTest();
			mainpanel = sRRDt.createMainPanel();
			mainframe.setContentPane(mainpanel);
			mainframe.pack();
			open.setEnabled(true);
		}
	}
	
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent ae){
			mainframe.setVisible(false);
			System.exit(0);
		}
	}
}
