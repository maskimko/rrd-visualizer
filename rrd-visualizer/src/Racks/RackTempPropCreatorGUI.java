package Racks;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RackTempPropCreatorGUI {

	private RackAddable customer;
	private RackProperty rp = null;
	private TreeSet<RackProperty> rpSet = null;
	private JFrame parent;
	private JComboBox<String> cfList;
	private JTextArea status;
	private JTextField filePath;
	private JDialog propJD;

	
	public RackTempPropCreatorGUI( TreeSet<RackProperty> rpSet, JFrame parent, RackAddable customer){
		this.rpSet = rpSet;
		this.parent = parent;
		this.customer = customer;
	}
	
	public void showMenu(){
		propJD = new JDialog(parent);
		propJD.setModal(true);
		JPanel propPane = new JPanel();
		propPane.setLayout(new BoxLayout(propPane, BoxLayout.Y_AXIS));
		JPanel filePanel = new JPanel();
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.LINE_AXIS));
		filePanel.setBorder(BorderFactory.createTitledBorder("RRD File"));
		JPanel fileLabelBox = new JPanel();
		fileLabelBox.setPreferredSize(new Dimension(150, 50));
		fileLabelBox.setLayout(new BoxLayout(fileLabelBox, BoxLayout.Y_AXIS));
		JLabel filePathLb = new JLabel("RRD File Path");
		fileLabelBox.add(filePathLb);
		fileLabelBox.add(Box.createVerticalGlue());
		filePanel.add(fileLabelBox);
		JPanel fileValueBox = new JPanel();
		fileValueBox.setLayout(new BoxLayout(fileValueBox, BoxLayout.Y_AXIS));
		fileValueBox.setPreferredSize(new Dimension(250, 50));
		filePath = new JTextField("Path to file");
		JButton openFile = new JButton("Open");
		openFile.addActionListener(new OpenFileListener());
		fileValueBox.add(filePath);
		JPanel fileValueButtonPane = new JPanel();
		fileValueButtonPane.setLayout(new BoxLayout(fileValueButtonPane, BoxLayout.X_AXIS));
		fileValueButtonPane.add(Box.createHorizontalGlue());
		fileValueButtonPane.add(openFile);
		fileValueBox.add(fileValueButtonPane);
		filePanel.add(fileValueBox);
		
		JPanel cfPane = new JPanel();
		cfPane.setBorder(BorderFactory.createTitledBorder("Consolidation function"));
		cfPane.setLayout(new BoxLayout(cfPane, BoxLayout.LINE_AXIS));
		JPanel cfLabelBox = new JPanel();
		JPanel cfListBox = new JPanel();
		cfLabelBox.setPreferredSize(new Dimension(150, 20));
		cfLabelBox.setLayout(new BoxLayout(cfLabelBox, BoxLayout.Y_AXIS));
		JLabel cfLb = new JLabel("Choose CF");
		cfLabelBox.add(cfLb);
		cfListBox.setLayout(new BoxLayout(cfListBox, BoxLayout.Y_AXIS));
		
		
		String[] cFunctions = new String[] {RackTempProperty.AVERAGE, RackTempProperty.MAX, RackTempProperty.MIN, RackTempProperty.LAST};
		cfList = new JComboBox<String>(cFunctions);
		cfList.setSelectedIndex(0);
		cfListBox.add(cfList);
		cfPane.add(cfLabelBox);
		cfPane.add(cfListBox);
		
		JPanel boxPane = new JPanel();
		boxPane.setLayout(new BoxLayout(boxPane, BoxLayout.LINE_AXIS));
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener());
		JButton ok = new JButton("Ok");
		ok.addActionListener(new OKListener());
		boxPane.add(Box.createHorizontalGlue());
		boxPane.add(cancel);
		boxPane.add(ok);
		
		
		status = new JTextArea(2,50);
		status.setEditable(false);
		
		
		propPane.add(filePanel);
		propPane.add(cfPane);
		propPane.add(boxPane);
		propPane.add(status);
		propJD.add(propPane);
		propJD.pack();
		propJD.setVisible(true);
	}
	
	class OpenFileListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileFilter rrdFileFilter = new FileNameExtensionFilter("Round-Robin Database", "rrd");
			fileChooser.setFileFilter(rrdFileFilter);
			int result = fileChooser.showOpenDialog(parent);
			if (result == JFileChooser.APPROVE_OPTION){
				File rrdFile = fileChooser.getSelectedFile();
				filePath.setText(rrdFile.getAbsolutePath());
				
				
			}
		}
	}
	
	class CancelListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			propJD.setVisible(false);
		}
	}
	
	class OKListener implements ActionListener{
		File rrdFile = null;
		public void actionPerformed(ActionEvent ae){
			
			try {
				rrdFile = new File(filePath.getText());
				
				if (rrdFile.exists()){
				if (!rrdFile.canRead()){
					status.setText("Warning! Cannot read this file!");
				} else {
				rp = new RackTempProperty(RadiationTemp.RadiationParametersTemp, rrdFile, (String) cfList.getSelectedItem(), customer.getStartTime(), customer.getEndTime());
				
				rpSet.add(rp);
				status.setText("Rack property " + rp.description + " has been added");
				propJD.setVisible(false);
				}} else {
					status.setText("There is no such file!");
				}
			} catch (NullPointerException npe) {
				status.setText(npe.getMessage());
			} catch (IllegalArgumentException iae){
				status.setText(iae.getMessage());
			} catch (IOException ioe){
				status.setText(ioe.getMessage());
			}
		}
	}
	
}
