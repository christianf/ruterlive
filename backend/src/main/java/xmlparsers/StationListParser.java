package xmlparsers;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import structure.Station;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class StationListParser extends DefaultHandler {
	/*
	 * <StopPoint> <DISID>2160364:2</DISID> <PositionNumber>2</PositionNumber>
	 * <StopPointName>2</StopPointName> <StopName>Rogneskjær</StopName>
	 * </StopPoint> <StopPoint> <DISID>2160364:1</DISID>
	 * <PositionNumber>1</PositionNumber> <StopPointName>1</StopPointName>
	 * <StopName>Rogneskjær</StopName> </StopPoint> StopPoint>
	 * <DISID>11063144:44</DISID> <PositionNumber>44</PositionNumber>
	 * <StopPointName>44</StopPointName> <StopName>Brakahaug</StopName>
	 * </StopPoint> <StopPoint> <DISID>11063144:45</DISID>
	 * <PositionNumber>45</PositionNumber> <StopPointName>45</StopPointName>
	 * <StopName>Brakahaug</StopName> </StopPoint>
	 */

	private List<Station> stations = new ArrayList<Station>();
	private Station station = new Station();
	private String temp = "";
	private String current = "";

	public StationListParser() throws SAXException, IOException {
		// TODO Auto-generated constructor stub
		super();

		XMLReader xr = XMLReaderFactory.createXMLReader();

		xr.setContentHandler(this);
		xr.setErrorHandler(this);
		
		String stationXML[] = {"underground_station.xml","bus_tram_station.xml"};
		for(String xml:stationXML)
			xr.parse("target/classes/"+xml); // FIXME: Check if xml is out of date.
	}

	public List<Station> getStations() {
		return stations;
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {

		if (qName.equals("StopPoint") || qName.equals("Stop")) {
			station = new Station();
		}
		if (qName.equals("StopName"))
			current = "StopName";
		else if (qName.equals("DISID"))
			current = "DISID";
		else if (qName.equals("PositionNumber"))
			current = "PositionNumber";

		else
			current = "";
		temp = "";

	}

	public void endElement(String uri, String name, String qName) {
		if (qName.equals("StopPoint") || qName.equals("Stop")) {
			boolean addStation = true;
			for (Station s : stations)
				if (station.getStationId() == s.getStationId()) {
					s.getDirection().add(station.getDirection().iterator().next());
					addStation = false;
				}
			if (addStation)
				stations.add(station);
		} else if (qName.equals("StopName")) {
			station.setStationName(temp);
		} else if (qName.equals("DISID")) {
			if (temp.contains(":"))
				station.setStationId(Integer.parseInt(temp.substring(0, temp
						.indexOf(':'))));
			else
				station.setStationId(Integer.parseInt(temp));
		} else if (qName.equals("PositionNumber")) {
			station.getDirection().add((Integer.parseInt(temp)));
		}
	}

	public void characters(char ch[], int start, int length) {
		temp += new String(ch, start, length);
	}

}
