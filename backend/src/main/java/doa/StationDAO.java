package doa;

import java.util.List;

import structure.Station;

public interface StationDAO {
	
	public void updateStationList(List<Station> stations);
	public void addStation(Station station);
	public void removeStation(Station station);
	public Station updateStation(Station station);
	public List<Station> getStation(int stationId);
	public List<Station> searchForStation(String searchString);
	boolean hasStation(int stationId);
}
