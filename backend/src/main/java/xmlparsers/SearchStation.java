package xmlparsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import doa.StationDAO;

import structure.Station;
import tools.CoordinateConversion;

public class SearchStation extends DefaultHandler {
	private List<Station> stations = new ArrayList<Station>();
	private Station station = new Station();
	private String temp = "";
	private String xcoord = "";
	private String ycoord = "";
	private String current = "";

	public SearchStation(String searchString) throws SAXException, IOException {
		// TODO Auto-generated constructor stub
		super();
		String searchPage = new String(
				"http://www5.trafikanten.no/txml/?type=1&stopname=");
		XMLReader xr = XMLReaderFactory.createXMLReader();

		xr.setContentHandler(this);
		xr.setErrorHandler(this);

		xr.parse(searchPage + searchString); // FIXME: Check if xml is out of
		// date.
	}

	public List<Station> getStations() {
		return stations;
	}

	/*
	 * <fromid>03010013</fromid> <StopName xsi:type="xsd:string">Jernbanetorget
	 * (foran Oslo S)</StopName> <District xsi:type="xsd:string">Oslo</District>
	 * <XCoordinate xsi:type="xsd:int">597864</XCoordinate> <YCoordinate
	 * xsi:type="xsd:int">6642854</YCoordinate> <AirDistance
	 * xsi:type="xsd:int">0</AirDistance>(non-Javadoc)
	 */

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {

		if (qName.equals("StopMatch")) {
			station = new Station();
		} else if (qName.equals("fromid"))
			current = "fromId";
		else if (qName.equals("StopName"))
			current = "StopName";
		else if (qName.equals("District"))
			current = "District";
		else if (qName.equals("XCoordinate"))
			current = "XCoordinate";
		else if (qName.equals("YCoordinate"))
			current = "YCoordinate";
		else
			current = "";
		temp = "";

	}

	public void endElement(String uri, String name, String qName) {
		if (qName.equals("StopMatch")) {
			StationDAO hibernateStationDOA = (StationDAO) new ClassPathXmlApplicationContext(
					"classpath*:applicationContext.xml").getBean("stationDAO");
			List<Station> hibStation = hibernateStationDOA.getStation(station.getStationId());
			if(hibStation.isEmpty())
				hibernateStationDOA.addStation(station);
			else
				station = hibernateStationDOA.updateStation(station);
			stations.add(station);
		} else if (qName.equals("fromid"))
			station.setStationId(Integer.parseInt(temp));
		else if (qName.equals("StopName")) {
			station.setStationName(temp);
		} else if (qName.equals("District")) {
			station.setDistrict(temp);
		} else if (qName.equals("XCoordinate")) {
			xcoord = temp;
		} else if (qName.equals("YCoordinate")) {
			ycoord = temp;
			if (!xcoord.equals("0") || !ycoord.equals("0"))
				station.setCoords(CoordinateConversion.utm2LatLon(0, "", Double
						.parseDouble(xcoord), Double.parseDouble(ycoord)));
		}
	}

	public void characters(char ch[], int start, int length) {
		temp += new String(ch, start, length);
	}

}
