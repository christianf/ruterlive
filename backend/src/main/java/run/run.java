package run;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import structure.Station;
import trafikanten.Line;

import doa.StationDAO;
import doa.hibernate.HibernateStationDAO;

import xmlparsers.StationListParser;
import xmlparsers.TrafikantenLiveParser;

public class run {
	public static void main(String args[]) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
		StationDAO hibernateStationDOA = (StationDAO)context.getBean("stationDAO");
		updateStations(hibernateStationDOA);	
	}
	
	private static void updateStations(StationDAO stationDAO) {
		if (true) {

			StationListParser parser = null;

			try {
				parser = new StationListParser();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			stationDAO.updateStationList(parser.getStations());
		}
	}
}
