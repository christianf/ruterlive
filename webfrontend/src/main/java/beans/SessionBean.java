package beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.component.html.HtmlInputText;

import org.richfaces.component.html.HtmlDataTable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import doa.StationDAO;

import structure.Station;
import trafikanten.Line;
import xmlparsers.SearchStation;
import xmlparsers.TrafikantenLiveParser;

public class SessionBean {
	private Station selectedStation;
	private HtmlDataTable stationTable;
	private HtmlInputText searchInput;

	private String stationName;

	public boolean getRenderSelect() {
		if (stationName == null)
			return false;
		return true;
	}

	/**
	 * @param selectedStation
	 *            the selectedStation to set
	 */
	public String selectStation() {
		selectedStation = (Station) stationTable.getRowData();
		return "showStation";
	}

	public String search() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:applicationContext.xml");
		StationDAO hibernateStationDOA = (StationDAO) context
				.getBean("stationDAO");
		try {
			selectedStation = hibernateStationDOA.getStation(Integer
					.parseInt(stationName)).get(0);
			if (selectedStation != null)
				return "showStation";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "search";
	}
/*
	public List<Station> getStations() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:applicationContext.xml");
		StationDAO hibernateStationDOA = (StationDAO) context
				.getBean("stationDAO");
		if (stationName.length() < 2)
			return null;

		return hibernateStationDOA.searchForStation(stationName);
	}
*/
	public List<Station> getStations() {
		try {
			SearchStation search = new SearchStation(stationName);
			return search.getStations();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @return the searchInput
	 */
	public HtmlInputText getSearchInput() {
		return searchInput;
	}

	/**
	 * @param searchInput
	 *            the searchInput to set
	 */
	public void setSearchInput(HtmlInputText searchInput) {
		this.searchInput = searchInput;
	}

	public Station getSelectedStation() {
		return selectedStation;
	}

	public void setSelectedStation(Station selectedStation) {
		this.selectedStation = selectedStation;
	}

	/**
	 * @param stationTable
	 *            the stationTable to set
	 */
	public void setStationTable(HtmlDataTable stationTable) {
		this.stationTable = stationTable;
	}

	/**
	 * @return the stationTable
	 */
	public HtmlDataTable getStationTable() {
		return stationTable;
	}

	public List<Line> getLines() {
		List<Line> lines = updateDepatures(selectedStation);
		if(lines == null)
			return new ArrayList<Line>();
		return lines;
	}

	/**
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * @param stationName
	 *            the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public List<Line> updateDepatures(Station station) {
		if(station == null) return null;
		TrafikantenLiveParser parser = null;
		try {
			parser = new TrafikantenLiveParser(station);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Line> lines = parser.getLines();
		Collections.sort(lines);
		for (Line line : lines)
			Collections.sort(line.getDepartures());
		station.addLine(lines); // FIXME: Move to a higher level, so it
		// allways runs!
		return lines;
	}
	
	public String onTime()
	{
		return "#FF0000";
	}
}
