package fr.tse.mobility.service;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.tse.mobility.dao.StudentRepository;
import fr.tse.mobility.dao.TravelRepository;
import fr.tse.mobility.domain.Student;
import fr.tse.mobility.domain.Travel;
import fr.tse.mobility.domain.dto.CreateTravelDTO;
import fr.tse.mobility.service.impl.StudentServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class TravelServiceTest {

	private @Autowired TravelService travelService;
	private @Autowired StudentRepository studentRepository;
	private @Autowired TravelRepository travelRepository;

	@Test
	public void getAllTravelsTest() {
		List<Travel> travels = travelService.getAllTravels();
		Assertions.assertEquals(2, travels.size());
		
		Assertions.assertTrue(travelListContains(travels, "countryTest1", "cityTest1", "companyTest1", new Date(110, 3, 5), new Date(110, 5, 3)));
		Assertions.assertTrue(travelListContains(travels, "countryTest2", "cityTest2", "companyTest2", new Date(110, 4, 10), new Date(110, 6, 14)));
		
		Assertions.assertFalse(travelListContains(travels, "countryTest666", "cityTest666", "companyTest666", new Date(110, 4, 10), new Date(110, 6, 14)));
	}
	
	private boolean travelListContains(List<Travel> travels, String country, String city, String company, Date startDate, Date endDate) {
		Iterator<Travel> travelIterator = travels.iterator();
		while(travelIterator.hasNext()) {
			Travel travel = travelIterator.next();
			if(		travel.getCountry().equals(country)
					&& travel.getCity().equals(city)
					&& travel.getCompany().equals(company)
					&& travel.getStartDate().compareTo(startDate) == 0
					&& travel.getEndDate().compareTo(endDate) == 0) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void getTravelByIdTest() {
		Travel travel = travelService.getAllTravels().get(0);
		
		Travel newTravel = travelService.getTravelById(travel.getId());
		
		Assertions.assertNotEquals(null, newTravel);
		
		Assertions.assertEquals(travel, newTravel);
		Assertions.assertEquals(travel, newTravel);
	}
	
}
