package fr.tse.mobility.dao;

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

import fr.tse.mobility.domain.Student;
import fr.tse.mobility.domain.Travel;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class TravelRepositoryTest {

	private @Autowired TravelRepository travelRepository;
	private @Autowired StudentRepository studentRepository;
	
	@Test
	public void findAllTest() {
		List<Travel> travels = travelRepository.findAll();
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
	@Transactional
	public void saveAndDeleteTest() {
		Student student = studentRepository.findAll().get(0);
		int prevStudentTravelSize = student.getTravels().size();
		
		List<Travel> travels = travelRepository.findAll();
		int prevTravelSize = travels.size();
		
		Travel travel = new Travel("countryTest2", "cityTest2", "companyTest2", new Date(110, 8, 14), new Date(110, 9, 23), student);
		travel = travelRepository.save(travel);
		student.addTravel(travel);
		
		travels = travelRepository.findAll();
		Assertions.assertEquals(prevTravelSize+ 1, travels.size());
		Assertions.assertTrue(travels.contains(travel));
		
		Student newStudent = studentRepository.findById(student.getId()).orElse(null);
		Assertions.assertEquals(prevStudentTravelSize + 1, newStudent.getTravels().size());
		Assertions.assertTrue(newStudent.getTravels().contains(travel));
		
		student.removeTravel(travel);
		travelRepository.delete(travel);
		
		travels = travelRepository.findAll();
		Assertions.assertEquals(prevTravelSize, travels.size());
		Assertions.assertFalse(travels.contains(travel));
		
		newStudent = studentRepository.findById(student.getId()).orElse(null);
		Assertions.assertEquals(prevStudentTravelSize , newStudent.getTravels().size());
		Assertions.assertFalse(newStudent.getTravels().contains(travel));
	}
	
}
