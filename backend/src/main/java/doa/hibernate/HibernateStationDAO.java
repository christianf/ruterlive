package doa.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import structure.Station;

import doa.StationDAO;

@Transactional
public class HibernateStationDAO implements StationDAO {
	private HibernateTemplate hibernateTemplate;

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Transactional(readOnly = false)
	public void addStation(Station station) {
		// TODO Auto-generated method stub

		hibernateTemplate.saveOrUpdate(station);
	}

	@Override
	public void removeStation(Station station) {
		// TODO Auto-generated method stub

	}

	@Override
	public Station updateStation(Station station) {
		/*
		 * private int id;
		 * 
		 * // Required private String stationName; private String district;
		 * 
		 * // Together unique, both required. private int stationId; private
		 * Set<Integer> direction = new HashSet<Integer>(); private Set<Integer>
		 * lines = new HashSet<Integer>();
		 * 
		 * private double[] coords;
		 */
		Station hibStation = (Station) getStation(station.getStationId())
				.get(0);
		if (!(station.getStationName().length() > hibStation.getStationName()
				.length()))
			hibStation.setStationName(station.getStationName());
		if (hibStation.getCoords() == null && station.getCoords() != null)
			hibStation.setCoords(station.getCoords());
		hibernateTemplate.update(hibStation);
		return hibStation;
	}

	@Override
	public void updateStationList(List<Station> stations) {
		// TODO Auto-generated method stub
		for (Station station : stations)
			addStation(station);

	}

	@Override
	public List<Station> getStation(int stationId) {
		// TODO Auto-generated method stub

		return (List<Station>) hibernateTemplate.find(
				"from Station where StationId = ?", stationId);

	}

	@Override
	public boolean hasStation(int stationId) {
		return !hibernateTemplate.find("from Station where StationId = ?",
				stationId).isEmpty();
	}

	@Override
	public List<Station> searchForStation(String searchString) {

		List<Station> stations = null;
		searchString = "%" + searchString + "%";

		stations = (List<Station>) hibernateTemplate.find(
				"from Station where lower(StationName) LIKE lower(?)",
				new String[] { searchString });
		return stations;
	}
}
