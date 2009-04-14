package structure;

import java.util.List;

public class Depature {
	private int id;
	
	private Station station;

	/*
	 * Number defines the sequence of departure for a line in a day. Every kind of day have their own
	 * range of departures and thus numbers. 
	 */
	
	private Line line;
	private int number; //Middle value of tripId. The id number of the trip, unique per kind. Is not sequential and might change per day.
	
	private int type; //Last value of tripId. Bus, tram or underground.
	private enum kind { weekday, saturday, sunday, special } //Different data for weekday, saturday and sunday. Special not supported.
}