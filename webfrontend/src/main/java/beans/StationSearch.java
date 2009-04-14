package beans;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import structure.Station;

import doa.StationDAO;

public class StationSearch {

	public StationSearch() {

	}

	public List<Station> getStationsForAutocomplete(Object o) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:applicationContext.xml");
		String station = (String) o;
		StationDAO hibernateStationDOA = (StationDAO) context
				.getBean("stationDAO");
		return hibernateStationDOA.searchForStation(station);
	}
}
