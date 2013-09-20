import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Racks.Rack;
import Racks.RackAddable;
import Racks.RackCollection;
import Racks.RackTempProperty;
import Racks.TemperatureLayer;

import com.toedter.calendar.JDateChooser;

public class SliderRRDTest implements RackAddable{

	public JPanel mainPanel;
	public ImagePanel imagePanel;
	//private BufferedImage temperatureLayer;
	private JPanel dateChoose;
	private JPanel infoPanel, buttonPanel;
	private JTextArea infoText;
	private JScrollPane scrollInfoText, scrollImage;
	private JDateChooser dateStart, dateEnd;
	private JSlider moment;
	private Calendar currentCal;
	private long previousMoment, previousStart, previousEnd;
	//private File rrdFile = null;
	private RackCollection rackColl = new RackCollection();
	

	public JPanel createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(System.currentTimeMillis());

		dateEnd = new JDateChooser(date.getTime());
		dateEnd.setLocale(Locale.US);
		imagePanel = new ImagePanel("6 floor", 0, 0);
		scrollImage = new JScrollPane(imagePanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		date.roll(Calendar.WEEK_OF_YEAR, -1);

		dateStart = new JDateChooser(date.getTime());
		dateStart.setLocale(Locale.US);
		date = null;
		previousStart = dateStart.getDate().getTime();
		previousEnd = dateEnd.getDate().getTime();
		dateStart.addPropertyChangeListener(new StartDateChangeListener());
		dateEnd.addPropertyChangeListener(new EndDateChangeListener());
		currentCal = dateEnd.getCalendar();
		moment = createSliderFromDate(dateStart.getDate(), dateEnd.getDate());
		
		JLabel dateStartLabel = new JLabel("Start date");
		JLabel dateEndLabel = new JLabel("End date");

		dateChoose = new JPanel(new FlowLayout());
		dateChoose.add(dateStartLabel);
		dateChoose.add(dateStart);
		dateChoose.add(moment);
		dateChoose.add(dateEnd);
		dateChoose.add(dateEndLabel);
		mainPanel.add(BorderLayout.NORTH, dateChoose);
		mainPanel.add(BorderLayout.CENTER, scrollImage);
		JButton printDate = new JButton("Print date");
		JButton clear = new JButton("Clear");
		printDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Calendar startDt = dateStart.getCalendar();
				Calendar endDt = dateEnd.getCalendar();
				Calendar curDt = getCurrentCal(moment);
				String startDtStr, endDtStr, curDtStr;
				if (endDt.after(startDt)) {
					startDtStr = "Start date: "
							+ startDt.getDisplayName(Calendar.DAY_OF_WEEK,
									Calendar.LONG, Locale.US)
							+ " "
							+ startDt.get(Calendar.DATE)
							+ " "
							+ startDt.getDisplayName(Calendar.MONTH,
									Calendar.LONG, Locale.US) + " "
							+ startDt.get(Calendar.YEAR);
					endDtStr = "End date: "
							+ endDt.getDisplayName(Calendar.DAY_OF_WEEK,
									Calendar.LONG, Locale.US)
							+ " "
							+ endDt.get(Calendar.DATE)
							+ " "
							+ endDt.getDisplayName(Calendar.MONTH,
									Calendar.LONG, Locale.US) + " "
							+ endDt.get(Calendar.YEAR);
					curDtStr = "Current date: "
							+ curDt.getDisplayName(Calendar.DAY_OF_WEEK,
									Calendar.LONG, Locale.US)
							+ " "
							+ curDt.get(Calendar.DATE)
							+ " "
							+ curDt.getDisplayName(Calendar.MONTH,
									Calendar.LONG, Locale.US) + " "
							+ curDt.get(Calendar.YEAR);
					System.out.println(startDtStr);
					System.out.println(endDtStr);
					System.out.println(curDtStr);
					infoText.append(curDtStr + "\n");
				} else {
					System.out.println("Attention: you swap dates");
				}
			}
		});
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent clearText) {
				infoText.setText("");
			}
		});
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		infoText = new JTextArea(7, 80);
		infoText.setEditable(false);
		scrollInfoText = new JScrollPane(infoText);
		buttonPanel.add(printDate);
		buttonPanel.add(clear);
		infoPanel.add(buttonPanel);
		infoPanel.add(scrollInfoText);
		mainPanel.add(BorderLayout.SOUTH, infoPanel);
		return mainPanel;
	}

	
	private void updateSlider() {

		moment.setMinimum(0);
		moment.setMaximum(getDateDiff());

	}

	/*
	public void setRRDFile(File rrdf) throws IOException {
		if (rrdf.canRead()) {
			this.rrdFile = rrdf;
			infoText.append("File " + rrdFile.getAbsolutePath() + " has been loaded successfully!\n");
		} else {
			infoText.setCaretColor(new Color(0xff0000));
			infoText.append("Cannot open file " + rrdf.getAbsolutePath() + "\n");
			infoText.setCaretColor(new Color(0xffffff));
			throw new IOException("Cannot open file " + rrdf.getAbsolutePath());
		}
	}
	*/

	
	
	public void addRack(Rack rack){
		rackColl.add(rack);
		String rackInfo = "Coordinates " + rack.getCenterX() + ":" + rack.getCenterY() + " width " + rack.getWidth() + " height " + rack.getHeight();
		infoText.append("Added rack " + rackInfo + "\n");
	}
	
	private int getDateDiff() {
		return Math.abs((int) ((dateEnd.getDate().getTime() - dateStart
				.getDate().getTime()) / 1000 / 60));
	}

	private JSlider createSliderFromDate(Date start, Date end) {

		return createSliderFromTime(start.getTime(), end.getTime());

	}

	private synchronized JSlider createSliderFromTime(long start, long end) {
		JSlider sldr = null;
		if (end - start < 0) {
			System.out.println("Swaped arguments. Trying to repair");
			swapDates(dateStart, dateEnd);
		}
		// Calculate time difference in minutes
		int timeDiff = getDateDiff();

		sldr = new JSlider(JSlider.HORIZONTAL, 0, timeDiff, timeDiff);
		sldr.addChangeListener(new MomentListener());

		return sldr;
	}

	private BufferedImage getLayer(String rackPropDescr){
		BufferedImage temperatureLayer = TemperatureLayer.getLayer(imagePanel.getWidth(), imagePanel.getHeight(), rackColl.getRackProperty(rackPropDescr), rackColl, currentCal);
		return temperatureLayer;
	}
	
	private void swapDates(JDateChooser start, JDateChooser end) {
		Date swapDate = start.getDate();
		dateStart.setDate(end.getDate());
		dateEnd.setDate(swapDate);
	}

	private synchronized void swapDates() {
		Date ds, de;
		ds = dateStart.getDate();
		de = dateEnd.getDate();
		if (de.before(ds)) {
			swapDates(dateStart, dateEnd);
		}

	}

	public Calendar getStartTime(){
		return dateStart.getCalendar();
	}
	
	public Calendar getEndTime(){
		return dateEnd.getCalendar();
	}
	
	
	private void  updateRackProperties(){
		infoText.append("Please, wait while getting new data from racks...  ");
		Iterator<Rack> rackIt = rackColl.iterator();
		while (rackIt.hasNext()){
			rackIt.next().updateProperties(dateStart.getCalendar(), dateEnd.getCalendar());
		}
		infoText.append(" ... finished!\n");
	}
	
	private Calendar getCurrentCal(JSlider js) {
		long dateValueInMillis = js.getValue();
		System.out.println("Moment value: " + dateValueInMillis);
		dateValueInMillis = dateValueInMillis * 60 * 1000;
		dateValueInMillis += dateStart.getDate().getTime();
		System.out.println("Slider position: " + js.getValue());
		System.out.println("Slider time value " + dateValueInMillis);
		Calendar currentCal = Calendar.getInstance();
		if (dateValueInMillis != previousMoment) {
			previousMoment = dateValueInMillis;

			currentCal.setTimeInMillis(dateValueInMillis);

		} else {
			currentCal.setTimeInMillis(previousMoment);
		}
		return currentCal;
	}

	class MomentListener implements ChangeListener {

		public void stateChanged(ChangeEvent ce) {
			currentCal = getCurrentCal((JSlider) ce.getSource());
			System.out.println("Current value " + currentCal.getTime());
			
			Iterator<Rack> rcIter = rackColl.iterator();
			while(rcIter.hasNext()){
				rcIter.next().paintRadiation(RackTempProperty.rackTempDescription, currentCal, imagePanel.getGraphics());
			}
			
			//imagePanel.setBufferedImage(RackTempProperty.rackTempDescription, getLayer("Rack Temperature"));
			//imagePanel.add2Image(rackColl);
			//imagePanel.repaint();
		
		}
	}

	class StartDateChangeListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent pce) {
			if (previousStart != dateStart.getDate().getTime()) {
				previousStart = dateStart.getDate().getTime();
				swapDates();
				System.out.println("Start time in millis: "
						+ dateStart.getDate().getTime());
				System.out.println("Human readable format: "
						+ dateStart.getDate().toString());
				updateRackProperties();
				updateSlider();
				
			}
		}
	}

	class EndDateChangeListener implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent pce) {
			if (previousEnd != dateEnd.getDate().getTime()) {
				previousEnd = dateEnd.getDate().getTime();
				swapDates();
				System.out.println("End time in millis: "
						+ dateEnd.getDate().getTime());
				System.out.println("Human readable format: "
						+ dateEnd.getDate().toString());
				updateRackProperties();
				updateSlider();
				
			}
		}

	}
}
