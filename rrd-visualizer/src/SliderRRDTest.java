import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.toedter.calendar.JDateChooser;

public class SliderRRDTest {

	public JPanel mainPanel;
	public ImagePanel imagePanel;
	private JPanel dateChoose;
	private JDateChooser dateStart, dateEnd;
	private JSlider moment;
	private static long previousMoment, previousStart, previousEnd;

	public JPanel createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(System.currentTimeMillis());

		dateEnd = new JDateChooser(date.getTime());
		dateEnd.setLocale(Locale.US);
		imagePanel = new ImagePanel("6 floor", 0, 0);
		date.roll(Calendar.WEEK_OF_YEAR, -1);

		dateStart = new JDateChooser(date.getTime());
		dateStart.setLocale(Locale.US);
		date = null;
		previousStart = dateStart.getDate().getTime();
		previousEnd = dateEnd.getDate().getTime();
		dateStart.addPropertyChangeListener(new StartDateChangeListener());
		dateEnd.addPropertyChangeListener(new EndDateChangeListener());

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
		mainPanel.add(BorderLayout.CENTER, imagePanel);
		JButton printDate = new JButton("Print date");
		printDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Calendar startDt = dateStart.getCalendar();
				Calendar endDt = dateEnd.getCalendar();
				if (endDt.after(startDt)) {
					System.out.println("Start date: "
							+ startDt.getDisplayName(Calendar.DAY_OF_WEEK,
									Calendar.LONG, Locale.US)
							+ " "
							+ startDt.get(Calendar.DATE)
							+ " "
							+ startDt.getDisplayName(Calendar.MONTH,
									Calendar.LONG, Locale.US) + " "
							+ startDt.get(Calendar.YEAR));
					System.out.println("End date: "
							+ endDt.getDisplayName(Calendar.DAY_OF_WEEK,
									Calendar.LONG, Locale.US)
							+ " "
							+ endDt.get(Calendar.DATE)
							+ " "
							+ endDt.getDisplayName(Calendar.MONTH,
									Calendar.LONG, Locale.US) + " "
							+ endDt.get(Calendar.YEAR));
				} else {
					System.out.println("Attention: you swap dates");
				}
			}
		});
		mainPanel.add(BorderLayout.SOUTH, printDate);
		return mainPanel;
	}

	private void updateSlider() {
		
		moment.setMinimum(0);
		moment.setMaximum(getDateDiff());

	}

	private int getDateDiff(){
		return  Math.abs((int) ((dateEnd.getDate().getTime() - dateStart
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

	class MomentListener implements ChangeListener {

		public void stateChanged(ChangeEvent ce) {
			long dateValueInMillis = moment.getValue();
			System.out.println("Moment value: " + dateValueInMillis);
			dateValueInMillis = dateValueInMillis * 60 * 1000;
			dateValueInMillis += dateStart.getDate().getTime();
			System.out.println("Slider position: " + moment.getValue());
			if (dateValueInMillis != previousMoment) {
			previousMoment = dateValueInMillis;

			Calendar currentCal = Calendar.getInstance();
			currentCal.setTimeInMillis(dateValueInMillis);
			System.out.println("Slider time value " + dateValueInMillis);
			System.out.println("Current value " + currentCal.getTime());
			}
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
				updateSlider();

			}
		}

	}
}
