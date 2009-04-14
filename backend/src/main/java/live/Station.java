package live;
/*
 * Bus & tram
 * http://www.sis.trafikanten.no/xmlrtpi/StopList.xml				List of all stops, not sure what it's good for.
 * http://www.sis.trafikanten.no/xmlrtpi/StopPointList.xml			All stations. More or less static, 1.1M.
 * http://www.sis.trafikanten.no/xmlrtpi/dis/request?DISID=SN$		+ stopId, bus and tram
 * 
 * Underground
 * http://www.sis.trafikanten.no:8088/xmlrtpi/StopList.xml
 * http://www.sis.trafikanten.no:8088/xmlrtpi/StopPointList.xml
 * http://www.sis.trafikanten.no:8088/xmlrtpi/dis/request?DISID=SN$	+ stopId, underground
 * 
 * http://www4.trafikanten.no/XML_TravelRequest.asp					asp for getting information.might use later.
 * http://www5.trafikanten.no/txml/?type=1&stopname=			 	+ stop name, will find all stops with name with different id's

 */
public class Station {
	private long id;
	
	private String stationName;
	private int stationId;
	
	public Station()
	{
		
	}

	/**
	 * @param stationId the stationId to set
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
	 * @param stationName the stationName to set
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
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
}
