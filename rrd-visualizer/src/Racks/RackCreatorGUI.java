package Racks;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class RackCreatorGUI {

	private JDialog rackMaker = null;
	private JTextField nameField, widthField, heightField, xField, yField;
	private JTextArea infoArea;
	private RackAddable customer;
	private JFrame owner;
	private DefaultListModel<RackProperty> listModel;
	private Rack rack;
	private TreeSet<RackProperty> rpSet = new TreeSet<RackProperty>();
	private JList<RackProperty> list;

	public RackCreatorGUI(RackAddable ra, JFrame jf) {
		customer = ra;
		owner = jf;
	}

	/**
	 * Returns object which uses RackCreatorGUI
	 * 
	 * @return RackAddable class which can add rack;
	 */
	public RackAddable getCustomer() {
		return customer;
	}

	public JFrame getOwner() {
		return owner;
	}

	public void showMenu() {
		rackMaker = new JDialog(owner, "Rack Maker");
		rackMaker.setModal(true);
		/*
		 * rackMaker.addWindowListener(new WindowAdapter(){ public void
		 * windowClosing(WindowEvent e){ System.exit(0); } });
		 */
		// rackMaker.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
		nameField = new JTextField("Rack name");
		nameLabelBox.add(nameLb);
		namePane.add(nameLabelBox);
		nameValueBox.add(nameField);
		namePane.add(nameValueBox);
		params.add(namePane);
		JPanel coordAndSize = new JPanel();
		coordAndSize.setLayout(new BoxLayout(coordAndSize, BoxLayout.Y_AXIS));
		coordAndSize.setBorder(BorderFactory
				.createTitledBorder("Coordinates and Size"));

		JPanel coordPane = new JPanel();
		coordPane.setLayout(new BoxLayout(coordPane, BoxLayout.X_AXIS));
		JPanel coordLabelBox = new JPanel();
		coordLabelBox.setLayout(new BoxLayout(coordLabelBox, BoxLayout.X_AXIS));
		coordLabelBox.setPreferredSize(new Dimension(200, 20));
		JPanel coordValueBox = new JPanel();
		coordValueBox.setLayout(new BoxLayout(coordValueBox, BoxLayout.X_AXIS));
		coordValueBox.setPreferredSize(new Dimension(200, 20));
		coordPane.add(coordLabelBox);
		// coordPane.add(new JSeparator(SwingConstants.VERTICAL));
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
		// sizePane.add(new JSeparator(SwingConstants.VERTICAL));
		sizePane.add(sizeValueBox);
		coordAndSize.add(sizePane);
		JLabel coordLb = new JLabel("X coordinate, Y coordinate");
		JLabel sizeLb = new JLabel("Width, Height");
		widthField = new JTextField("Rack width");
		heightField = new JTextField("Rack height");
		xField = new JTextField("X coordinate");
		yField = new JTextField("Y coordinate");
		coordLabelBox.add(coordLb);
		coordValueBox.add(xField);
		coordValueBox.add(Box.createRigidArea(new Dimension(10, 0)));
		coordValueBox.add(yField);
		sizeLabelBox.add(sizeLb);
		sizeValueBox.add(widthField);
		sizeValueBox.add(Box.createRigidArea(new Dimension(10, 0)));
		sizeValueBox.add(heightField);
		params.add(coordAndSize);

		JPanel propertyPanel = new JPanel();
		propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.X_AXIS));
		propertyPanel.setBorder(BorderFactory
				.createTitledBorder("Rack Properties"));
		JPanel propLabelBox = new JPanel();
		propLabelBox.setLayout(new BoxLayout(propLabelBox, BoxLayout.Y_AXIS));
		// propLabelBox.setAlignmentX(0.5f);
		JLabel propLb = new JLabel("Select Rack Property");
		JLabel propLb2 = new JLabel("to create");
		propLabelBox.add(propLb);
		propLabelBox.add(propLb2);
		propLabelBox.setPreferredSize(new Dimension(200, 20));
		JPanel propListBox = new JPanel();
		propListBox.setLayout(new BoxLayout(propListBox, BoxLayout.Y_AXIS));
		listModel = new DefaultListModel<RackProperty>();
		list = new JList<RackProperty>(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(3);
		JScrollPane jscrlpn = new JScrollPane(list);
		propListBox.add(jscrlpn);
		propListBox.setPreferredSize(new Dimension(200, 70));
		propertyPanel.add(propLabelBox);
		propertyPanel.add(propListBox);
		JPanel propButtonPanel = new JPanel();
		propButtonPanel.setLayout(new BoxLayout(propButtonPanel,
				BoxLayout.LINE_AXIS));
		propButtonPanel.add(Box.createHorizontalGlue());
		JButton cProp = new JButton("Create Property");
		cProp.addActionListener(new CreatePropertyListener());
		propButtonPanel.add(cProp);
		propListBox.add(propButtonPanel);
		params.add(propertyPanel);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.add(Box.createHorizontalGlue());
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				rackMaker.setVisible(false);
			}
		});
		JButton ok = new JButton("Ok");
		ok.addActionListener(new OKListener());
		buttonPane.add(cancel);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(ok);
		params.add(buttonPane);

		infoArea = new JTextArea(1, 50);
		infoArea.setEditable(false);
		params.add(infoArea);

		rackMaker.setContentPane(params);
		rackMaker.pack();
		rackMaker.setVisible(true);
	}

	private void resetFields() {
		nameField.setText("");
		xField.setText("");
		yField.setText("");
		widthField.setText("");
		heightField.setText("");
	}

	private Rack makeRack() throws NumberFormatException {
		RackProperty rackProperty = null;
		String name = nameField.getText();
		int x = Integer.parseInt(xField.getText());
		int y = Integer.parseInt(yField.getText());
		int width = Integer.parseInt(widthField.getText());
		int height = Integer.parseInt(heightField.getText());
		rack = new Rack(name, x, y, width, height, rackProperty);
		Iterator<RackProperty> rpIt = rpSet.iterator();
		while (rpIt.hasNext()){
			rack.addRackProperty(rpIt.next());
		}
		return rack;
	}

	class OKListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			Rack rc = null;
			try {
				rc = makeRack();
			} catch (NumberFormatException nfe) {
				infoArea.setText("Invalid input " + nfe.getMessage());
			}
			if (rc != null) {
				customer.addRack(rc);
				infoArea.setText("Rack " + rc.name
						+ " has been successfully added!");
				resetFields();
				rackMaker.setVisible(false);
			}
		}
	}

	class CreatePropertyListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			int rpSetSize = rpSet.size();
			RackTempPropCreatorGUI tPropCreator = new RackTempPropCreatorGUI(
					rpSet, owner, customer);
			tPropCreator.showMenu();
			if (rpSet.size() != rpSetSize) {
				RackProperty rp = rpSet.last();
				int index = list.getSelectedIndex();
				index++;
				listModel.insertElementAt(rp, index);
				list.ensureIndexIsVisible(index);
				infoArea.setText("Rack property " + rp.description
						+ " has been added");
			}
		}
	}
}
