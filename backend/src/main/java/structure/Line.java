package structure;

import java.util.List;

public class Line {
	private int id;
	
	//Together unique, both required.
	private int number; //First value of the tripId or number of line.
	private int direction; //1 or 2 depending on direction.
	
	private List<Depature> depatures; //List of depature for line.
	
	
}
