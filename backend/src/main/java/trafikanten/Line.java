package trafikanten;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Line implements Comparable<Line> {
	private int lineId;
	private String destinationStop;
	private List<Departure> departures;

	public Line(String lineId, String destinationStop) {
		this.destinationStop = destinationStop;
		this.lineId = Integer.parseInt(lineId);
		departures = new LinkedList<Departure>();
	}

	/**
	 * @return the departures
	 */
	public List<Departure> getDepartures() {
		return departures;
	}

	/**
	 * @param departures
	 *            the departures to set
	 */
	public void setDepartures(List<Departure> departures) {
		this.departures = departures;
	}

	/**
	 * @return the lineId
	 */
	public int getLineId() {
		return lineId;
	}

	/**
	 * @return the destinationStop
	 */
	public String getDestinationStop() {
		return destinationStop;
	}

	public int getType() {
		if (lineId >= 20) //Bus
			return 0;
		else if (lineId >= 10) //Tram
			return 1;
		return 2;	//Underground
	}

	@Override
	public int compareTo(Line o) {
		if (lineId == o.getLineId())
			return destinationStop.compareTo(o.getDestinationStop());
		return lineId - o.getLineId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destinationStop == null) ? 0 : destinationStop.hashCode());
		result = prime * result + lineId;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Line other = (Line) obj;
		if (destinationStop == null) {
			if (other.destinationStop != null)
				return false;
		} else if (!destinationStop.equals(other.destinationStop))
			return false;
		if (lineId != other.lineId)
			return false;
		return true;
	}

}
