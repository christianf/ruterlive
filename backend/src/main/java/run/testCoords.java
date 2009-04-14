package run;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.Searcher;
import org.xml.sax.SAXException;

import structure.Station;
import tools.CoordinateConversion;
import xmlparsers.SearchStation;

public class testCoords {
	
	public static void main(String[] yarrs)
	{
		List<Station> stations = new ArrayList<Station>();
		try {
			SearchStation searcher = new SearchStation("tåsen");
			stations = searcher.getStations();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Station station:stations)
			System.out.println(station.toString());
	}
}
