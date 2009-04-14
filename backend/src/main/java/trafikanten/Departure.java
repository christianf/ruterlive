package trafikanten;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Departure implements Comparable<Departure> {
	// private String tripId;
	private Time scheduled;
	private Time expected;
	private int stationId;
	// private boolean isReal;
	private int stopPosition;
	private Daytype dayType;
	
	public enum Daytype {
		WEEKDAY, SATURDAY, SUNDAY
	}
	
	public Departure(String scheduled, String expected, String stationId,
			String stopPosition) {
		super();
		this.stopPosition = Integer.parseInt(stopPosition);

		SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-M-dd'T'KK:mm:ss.SSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
		try {
			this.scheduled = new Time(dateFormat.parse(scheduled).getTime());
			this.expected = new Time(dateFormat.parse(expected).getTime());
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(dateFormat.parse(scheduled).getTime());
			switch (calendar.get(Calendar.DAY_OF_WEEK)) {
				case 0: dayType = Daytype.SUNDAY; break;
				case 1: 
				case 2:  
				case 3:  
				case 4:  
				case 5: dayType = Daytype.WEEKDAY; break; 
				case 6: dayType = Daytype.SATURDAY; break;
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.stationId = Integer.parseInt(stationId.substring(3));
	}

	/**
	 * @return the scheduled
	 */
	public Date getScheduled() {
		return scheduled;
	}

	/**
	 * @return the expected
	 */
	public Date getExpected() {
		return expected;
	}

	/**
	 * @param expected
	 *            the expected to set
	 */
	public void setExpected(Time expected) {
		this.expected = expected;
	}

	/**
	 * @return the stationId
	 */
	public int getStationId() {
		return stationId;
	}

	/**
	 * @return the stopPosition
	 */
	public int getStopPosition() {
		return stopPosition;
	}

	public boolean isReal() {
		return scheduled.compareTo(expected) == 0;
	}

	public String getOnTime()
	{
		int comparison = expected.compareTo(scheduled);
		if(comparison == 0) return "#98FB98";
		else if(comparison < 0) return "#FFE4B5";
		return "#FF82A0";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dayType == null) ? 0 : dayType.hashCode());
		result = prime * result
				+ ((scheduled == null) ? 0 : scheduled.hashCode());
		result = prime * result + stationId;
		result = prime * result + stopPosition;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departure other = (Departure) obj;
		if (dayType == null) {
			if (other.dayType != null)
				return false;
		} else if (!dayType.equals(other.dayType))
			return false;
		if (scheduled == null) {
			if (other.scheduled != null)
				return false;
		} else if (!scheduled.equals(other.scheduled))
			return false;
		if (stationId != other.stationId)
			return false;
		if (stopPosition != other.stopPosition)
			return false;
		return true;
	}

	@Override
	public int compareTo(Departure o) {
		return expected.compareTo(o.getExpected());
	}
}
