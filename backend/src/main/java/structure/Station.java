package structure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import doa.StationDAO;

import trafikanten.Line;
import xmlparsers.TrafikantenLiveParser;

/*
 <ID>2</ID>
 <fromid>03010013</fromid>
 <StopName xsi:type="xsd:string">Jernbanetorget (foran Oslo S)</StopName>
 <District xsi:type="xsd:string">Oslo</District>
 <XCoordinate xsi:type="xsd:int">597864</XCoordinate>
 <YCoordinate xsi:type="xsd:int">6642854</YCoordinate>
 */
public class Station {
	private int id;

	// Required
	private String stationName;
	private String district;

	// Together unique, both required.
	private int stationId;
	private Set<Integer> direction = new HashSet<Integer>();
	private Set<Integer> lines = new HashSet<Integer>();

	private double[] coords;

	// private int type; //Not in use
	/**
	 * @param stationId
	 *            the stationId to set
	 */
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	/**
	 * @return the stationId
	 */
	public int getStationId() {
		return stationId;
	}

	/**
	 * @param district
	 *            the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param stationName
	 *            the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	/**
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(Set<Integer> direction) {
		this.direction = direction;
	}

	/**
	 * @return the direction
	 */
	public Set<Integer> getDirection() {
		return direction;
	}

	/**
	 * @param lines
	 *            the lines to set
	 */
	public void setLines(Set<Integer> lines) {
		this.lines = lines;
	}

	/**
	 * @return the lines
	 */
	public Set<Integer> getLines() {
		return lines;
	}

	/**
	 * @param coords
	 *            the coords to set
	 */
	public void setCoords(double[] coords) {
		this.coords = coords;
	}

	/**
	 * @return the coords
	 */
	public double[] getCoords() {
		return coords;
	}

	public List<Integer> getLinesList() {
		List<Integer> linesList = new ArrayList<Integer>();
		linesList.addAll(lines);
		Collections.sort(linesList);
		return linesList;
	}

	/**
	 * 
	 * @return int representation of public transportation type. 0 for bus, 1
	 *         for tram, 2 for underground and 3 for bus + tram
	 */
	public int getType() {
		boolean bus = false, tram = false, underground = false;

		for (int l : lines)
			if (l >= 20)
				bus = true;
			else if (l >= 10)
				tram = true;
			else
				underground = true;
		if (bus && tram)
			return 3;
		else if (bus)
			return 0;
		else if (tram)
			return 1;
		else if (underground)
			return 2;
		return 0;
	}

	public void addLine(List<Line> depatures) {
		for (Line d : depatures)
			lines.add(d.getLineId());
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:applicationContext.xml");
		StationDAO hibernateStationDOA = (StationDAO) context
				.getBean("stationDAO");
		hibernateStationDOA.addStation(this);
	}

	public String toString() {
		if (coords != null)
			return stationId + " " + stationName + " " + coords[0] + " "
					+ coords[1];
		return stationId + " " + stationName;
	}
}
