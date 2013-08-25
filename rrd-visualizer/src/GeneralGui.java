import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class GeneralGui {

	public JFrame mainframe;
	public JPanel mainpanel;
	
	
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
		randomView.addActionListener(new RandomViewListener());
		datePickerTest.addActionListener(new DatePickerTestListener());
		modemenu.add(randomView);
		modemenu.add(datePickerTest);
		
		mainframe.setJMenuBar(menuBar);
		mainframe.setSize(300, 300);
		
		mainframe.setVisible(true);
	}
	
	class RandomViewListener implements ActionListener {
		public void actionPerformed(ActionEvent ae){
			RandomWeatherMap rwm = new RandomWeatherMap();
			mainpanel = rwm.createMainPanel();
			mainframe.setContentPane(mainpanel);
			mainframe.pack();
		}
	}
	
	class DatePickerTestListener implements ActionListener {
		public void actionPerformed(ActionEvent ae){
			
			SliderRRDTest sRRDt = new SliderRRDTest();
			mainpanel = sRRDt.createMainPanel();
			mainframe.setContentPane(mainpanel);
			mainframe.pack();
		}
	}
}
