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
public class StudentServiceTest {

	private @Autowired StudentService studentService;
	private @Autowired StudentRepository studentRepository;
	private @Autowired TravelRepository travelRepository;

	@Test
	public void getAllStudentsTest() {
		List<Student> students = studentService.getAllStudents();
		Assertions.assertEquals(2, students.size());
		
		Assertions.assertTrue(studentListContains(students, "studentName1", "studentFirstName1"));
		Assertions.assertTrue(studentListContains(students, "studentName2", "studentFirstName2"));
		
		Assertions.assertFalse(studentListContains(students, "firstName666", "lastName666"));
	}
	
	private boolean studentListContains(List<Student> students, String lastname, String firstname) {
		Iterator<Student> studentIterator = students.iterator();
		while(studentIterator.hasNext()) {
			Student student = studentIterator.next();
			if(		student.getFirstname().equals(firstname)
					&& student.getLastname().equals(lastname)) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void getStudentByIdTest() {
		Student student = studentService.getAllStudents().get(0);
		
		Student newStudent = studentService.getStudentById(student.getId());
		
		Assertions.assertNotEquals(null, newStudent);
		
		Assertions.assertEquals(student.getFirstname(), newStudent.getFirstname());
		Assertions.assertEquals(student.getLastname(), newStudent.getLastname());
	}
	
	@Test
	public void getTravelsByStudentIdTest() {
		Student student = studentService.getAllStudents().get(0);
		Assertions.assertNotEquals(0, student.getTravels().size());
		
		Collection<Travel> travels = studentService.getTravelsByStudentId(student.getId());
		
		Assertions.assertNotEquals(null, travels);
		Assertions.assertEquals(student.getTravels().size(), travels.size());
		
		Iterator<Travel> travelIterator = student.getTravels().iterator();
		while(travelIterator.hasNext()) {
			Assertions.assertTrue(travelListContains(travels, travelIterator.next()));
		}
		
	}
	
	private boolean travelListContains(Collection<Travel> travels, Travel travel) {
		return this.travelListContains(travels, travel.getCountry(), travel.getCity(), travel.getCompany(), travel.getStartDate(), travel.getEndDate());
	}
	
	private boolean travelListContains(Collection<Travel> travels, String country, String city, String company, Date startDate, Date endDate) {
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
	public void getStudentTravelByIdTest() {
		Travel travel = travelRepository.findAll().get(0);
		
		Travel newTravel = this.studentService.getStudentTravelById(travel.getStudent().getId(), travel.getId());
		
		Assertions.assertEquals(travel, newTravel);
	}
	
	@Test
	@Transactional
	public void deleteStudentTravelTest() {
		Student student = this.studentRepository.findAll().get(0);
		student = this.studentRepository.save(student);
		
		Travel travel = new Travel("countryTest", "cityTest", "companyTest", new Date(113, 4, 10), new Date(115, 6, 21), student);
		
		student.addTravel(travel);
		travelRepository.save(travel);
		
		Assertions.assertTrue(student.getTravels().contains(travel));
		
		this.studentService.deleteStudentTravel(student.getId(), travel.getId());
		
		Assertions.assertFalse(student.getTravels().contains(travel));
	}
	
	
	@Test
	@Transactional
	public void addStudentTravelTest() {
		Student student = studentService.getAllStudents().get(0);
		student = studentRepository.save(student);
		
		Collection<Travel> travels = studentService.getTravelsByStudentId(student.getId());
		int prevSize = travels.size();
		
		Assertions.assertEquals(prevSize, student.getTravels().size());
		
		CreateTravelDTO createTravelDTO = new CreateTravelDTO();
		createTravelDTO.setCity("cityTest");
		createTravelDTO.setCompany("companyTest");
		createTravelDTO.setCountry("countryTest");
		createTravelDTO.setStartDate(new Date(110, 4, 10));
		createTravelDTO.setEndDate(new Date(110, 6, 21));
		
		Travel travel = studentService.addStudentTravel(student.getId(), createTravelDTO);
		
		travels = studentService.getTravelsByStudentId(student.getId());
		
		Assertions.assertEquals(prevSize + 1, travels.size());
		Assertions.assertEquals(travels.size(), student.getTravels().size());
		
		Assertions.assertTrue(travels.contains(travel));
		
		student.removeTravel(travel);
		travelRepository.delete(travel);
		
		travels = studentService.getTravelsByStudentId(student.getId());
		
		Assertions.assertEquals(prevSize, travels.size());
		Assertions.assertEquals(travels.size(), student.getTravels().size());
		

		
	}
	
}
