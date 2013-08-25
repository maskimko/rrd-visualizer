import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
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
	private static long previousMoment;
	private static Date startTime, endTime;
	
	public JPanel createMainPanel(){
		mainPanel = new JPanel(new BorderLayout());
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(System.currentTimeMillis());
		endTime = date.getTime();
		 dateEnd = new JDateChooser(date.getTime());
		imagePanel = new ImagePanel("6 floor", 0 ,0);
		 date.roll(date.WEEK_OF_YEAR, -1);
		startTime = date.getTime();
		dateStart = new JDateChooser(date.getTime());
		
		dateStart.addPropertyChangeListener(new StartDateChangeListener());
		dateEnd.addPropertyChangeListener(new EndDateChangeListener());
		
		
				moment = createSliderFromDate(dateStart.getDate(), dateEnd.getDate());
			
		
		 JLabel dateStartLabel = new JLabel("Start date");
		 JLabel dateEndLabel = new JLabel("End date");
		
		dateChoose = new JPanel(new FlowLayout());
		dateChoose.add(dateStartLabel);
		dateChoose.add( dateStart);
		dateChoose.add(moment);
		dateChoose.add(dateEnd);
		dateChoose.add(dateEndLabel);
		mainPanel.add(BorderLayout.NORTH, dateChoose);
		mainPanel.add(BorderLayout.CENTER, imagePanel);
		JButton printDate = new JButton("Print date");
		printDate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Calendar startDt = dateStart.getCalendar();
				Calendar endDt =  dateEnd.getCalendar();
				if (endDt.after(startDt)) {
					System.out.println("Start date " + startDt.getDisplayName(Calendar.DATE, Calendar.SHORT, Locale.US));
					System.out.println("End date" + endDt);
				} else {
					System.out.println("Attention: you swap dates");
				}
			}
		});
		mainPanel.add(BorderLayout.SOUTH, printDate);
		return mainPanel;
	}
	
	private JSlider createSliderFromDate(Date start, Date end) {

		
			JSlider sldr = createSliderFromTime(start.getTime(), end.getTime());
		return sldr;
	}
	
	private JSlider createSliderFromTime(long start, long end) {
		 JSlider sldr = null;
		if (end - start < 0) {
			System.out.println("Swaped arguments. Trying to repair");
			swapDates(dateStart, dateEnd);
		}
		//Calculate time difference in minutes
		 int timeDiff = (int) Math.abs(dateStart.getDate().getTime() - dateEnd.getDate().getTime()) / 1000 / 60;
		 
		sldr = new JSlider(JSlider.HORIZONTAL, 0, timeDiff, timeDiff);
		sldr.addChangeListener(new MomentListener());
		
		return sldr;
	}
	
	private void swapDates(JDateChooser start, JDateChooser end){
		Date swapDate = start.getDate();
		dateStart.setDate(end.getDate());
		dateEnd.setDate(swapDate);
	}
	
	
	
	class MomentListener implements ChangeListener {
		
		 
		
		public void stateChanged(ChangeEvent ce){
			long dateValueInMillis = dateStart.getDate().getTime() + moment.getValue() * 60 * 1000;
			if (dateValueInMillis != previousMoment) {
			previousMoment = dateValueInMillis;	
			
			Calendar currentCal = Calendar.getInstance();
			currentCal.setTimeInMillis(dateValueInMillis);
			System.out.println("Current value " + currentCal.getTime());
			System.out.println("Slider value " +moment.getValue());
			}
		}
	}
	
	class StartDateChangeListener implements PropertyChangeListener {
		
		
		public void propertyChange(PropertyChangeEvent pce){
			JDateChooser jdc = (JDateChooser) pce.getSource();
			if (!startTime.equals(jdc.getDate())){
				
				int timeDiff = (int) (dateEnd.getDate().getTime() - jdc.getDate().getTime()) / 1000 / 60;
				if (timeDiff < 0){
					timeDiff = Math.abs(timeDiff);
					swapDates(dateStart, dateEnd);
				}
				moment.setMaximum(timeDiff);
			}
			
		}
	}
class EndDateChangeListener implements PropertyChangeListener {
		
		
		public void propertyChange(PropertyChangeEvent pce){
			JDateChooser jdc = (JDateChooser) pce.getSource();
			if (!endTime.equals(jdc.getDate())){
				
				int timeDiff = (int) (jdc.getDate().getTime() - dateStart.getDate().getTime()) / 1000 / 60;
				if (timeDiff < 0){
					timeDiff = Math.abs(timeDiff);
					swapDates(dateStart, dateEnd);
				}
				moment.setMaximum(timeDiff);
			}
			
		}
	}
	
	/*class DateChangeListener implements PropertyChangeListener{
		int timeDiff;
		
		public void propertyChange(PropertyChangeEvent pce){
			if ("date".equals(pce.getPropertyName())){
				JDateChooser jdc = (JDateChooser) pce.getSource();
				boolean isDateSelectedByUser = false;
				try {
					Field dateSelectedField = JDateChooser.class.getDeclaredField("dateSelected");
					dateSelectedField.setAccessible(true);
					isDateSelectedByUser = dateSelectedField.getBoolean(jdc);
				}catch (Exception ignoreOrNot){}
				
				if (isDateSelectedByUser) {
					//TODO reinitialize JSlider
					System.out.println("Date has been changed!");
					//moment = createSliderFromDate(dateStart.getDate(), dateEnd.getDate());
					if (jdc.equals(dateEnd)) {
						 timeDiff = (int) (jdc.getDate().getTime() - dateStart.getDate().getTime()) / 1000 / 60;
					} else if (jdc.equals(dateStart)) {
						 timeDiff = (int) (dateEnd.getDate().getTime() - jdc.getDate().getTime()) / 1000 / 60;
					} else {
						System.out.println("Cannot catch JDateChooser object");
					}
					if (timeDiff < 0) {
						swapDates(dateStart, dateEnd);
						timeDiff = Math.abs(timeDiff);
					}
					moment.setMinimum(0);
					moment.setMaximum(timeDiff);
					
				}
				
				try {
					Field dateSelectedField = JDateChooser.class.getDeclaredField("dateSelected");
					dateSelectedField.setAccessible(true);
					dateSelectedField.setBoolean(jdc, false);
				} catch (Exception ignoreOrNot){}
			} 
		}
	}*/

}

