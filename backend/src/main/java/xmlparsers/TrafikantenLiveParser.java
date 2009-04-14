package xmlparsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import structure.Station;
import trafikanten.Departure;
import trafikanten.Line;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class TrafikantenLiveParser extends DefaultHandler {

	// Change
	// URL
	// to
	// something
	// correct.

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
	private static Set<Integer> subwayStations = new HashSet<Integer>(Arrays
			.asList(3011930, 3012220, 3011030, 3012365, 2190070, 2190080,
					2190090, 3010011, 3010020, 3010031, 3010200, 3010360,
					3010370, 3010600, 3010610, 3010770, 3010780, 3010950,
					3011010, 3011020, 3011110, 3011120, 3011130, 3011140,
					3011200, 3011210, 3011220, 3011310, 3011320, 3011330,
					3011400, 3011410, 3011430, 3011440, 3011450, 3011510,
					3011520, 3011530, 3011540, 3011610, 3011620, 3011630,
					3011710, 3011720, 3011730, 3011810, 3011910, 3011920,
					3011940, 3012000, 3012010, 3012020, 3012030, 3012040,
					3012100, 3012120, 3012130, 3012210, 3012230, 3012240,
					3012260, 3012270, 3012280, 3012305, 3012310, 3012315,
					3012320, 3012325, 3012330, 3012340, 3012345, 3012350,
					3012355, 3012360, 3012370, 3012375, 3012380, 3012385,
					3012390, 3012410, 3012420, 3012430, 3012450, 3012460,
					3012560, 3012565, 3012572, 3012630));
	private List<Line> lines = new ArrayList<Line>();
	private String temp = "";

	private String tripID, dISID, lineID, tripStatus, stopPosition,
			scheduledDISDepartureTime, expectedDISDepartureTime,
			destinationStop;

	public TrafikantenLiveParser(Station station) throws SAXException,
			IOException {
		// TODO Auto-generated constructor stub
		super();

		XMLReader xr = XMLReaderFactory.createXMLReader();

		xr.setContentHandler(this);
		xr.setErrorHandler(this);

		if (subwayStations.contains(station.getStationId()))
			xr
					.parse("http://www.sis.trafikanten.no:8088/xmlrtpi/dis/request?DISID=SN$"
							+ station.getStationId());
		else
			xr
					.parse("http://www.sis.trafikanten.no/xmlrtpi/dis/request?DISID=SN$"
							+ station.getStationId());
	}

	public List<Line> getLines() {
		return lines;
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {
		if (qName.equals("DISDeviation")) {
			tripID = null;
			dISID = null;
			lineID = null;
			tripStatus = null;
			stopPosition = null;
			scheduledDISDepartureTime = null;
			expectedDISDepartureTime = null;
			destinationStop = null;
		}
		temp = "";

	}

	// TripID,DISID,LineID,TripStatus,StopPosition,ScheduledDISDepartureTime,ExpectedDISDepartureTime
	public void endElement(String uri, String name, String qName) {
		if (qName.equals("DISDeviation")) {

			Line newLine = new Line(lineID, destinationStop);

			Departure newDeparture = new Departure(scheduledDISDepartureTime,
					expectedDISDepartureTime, dISID, stopPosition);

			if (!lines.contains(newLine))
				lines.add(newLine);

			for (Line line : lines)
				if (line.equals(newLine))
					line.getDepartures().add(newDeparture);

		} else if (qName.equals("TripID"))
			tripID = temp;
		else if (qName.equals("DISID"))
			dISID = temp;
		else if (qName.equals("LineID"))
			lineID = temp;
		else if (qName.equals("TripStatus"))
			tripStatus = temp;
		else if (qName.equals("StopPosition"))
			stopPosition = temp;
		else if (qName.equals("ScheduledDISDepartureTime"))
			scheduledDISDepartureTime = temp;
		else if (qName.equals("ExpectedDISDepartureTime"))
			expectedDISDepartureTime = temp;
		else if (qName.equals("DestinationStop"))
			destinationStop = temp;
	}

	public void characters(char ch[], int start, int length) {
		temp += new String(ch, start, length);
	}

}
