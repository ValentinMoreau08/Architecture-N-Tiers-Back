package fr.tse.mobility.dao;

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

import fr.tse.mobility.domain.Promotion;
import fr.tse.mobility.domain.Student;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class StudentRepositoryTest {

	private @Autowired StudentRepository studentRepository;
	private @Autowired PromotionRepository promotionRepository;
	
	@Test
	public void findAllTest() {
		List<Student> students = studentRepository.findAll();
		Assertions.assertEquals(students.size(), 2);
		
		Assertions.assertTrue(studentListContains(students, "studentName1", "studentFirstName1"));
		Assertions.assertTrue(studentListContains(students, "studentName2", "studentFirstName2"));
	}
	
	private boolean studentListContains(List<Student> students, String lastname, String firstname) {
		Iterator<Student> studentIterator = students.iterator();
		while(studentIterator.hasNext()) {
			Student student = studentIterator.next();
			if(student.getLastname().equals(lastname) && student.getFirstname().equals(firstname)) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	@Transactional
	public void saveAndDeleteTest() {
		List<Student> students = studentRepository.findAll();
		int prevStudentSize = students.size();
		
		Promotion promotion = promotionRepository.findAll().get(0);
		int prevPromotionSize = promotion.getStudents().size();
		
		Student student = new Student("studentName3", "studentFirstName3", promotion);
		promotion.addStudent(student);
		student = studentRepository.save(student);
		
		students = studentRepository.findAll();
		Assertions.assertEquals(prevStudentSize+ 1, students.size());
		Assertions.assertTrue(students.contains(student));
		
		Promotion newPromotion = promotionRepository.findById(promotion.getId()).orElse(null);
		Assertions.assertEquals(prevPromotionSize + 1, newPromotion.getStudents().size());
		Assertions.assertTrue(newPromotion.getStudents().contains(student));
		
		promotion.removeStudent(student);
		studentRepository.delete(student);
		
		students = studentRepository.findAll();
		Assertions.assertEquals(prevStudentSize, students.size());
		Assertions.assertFalse(students.contains(student));
		
		newPromotion = promotionRepository.findById(promotion.getId()).orElse(null);
		Assertions.assertEquals(prevPromotionSize, newPromotion.getStudents().size());
		Assertions.assertFalse(newPromotion.getStudents().contains(student));
	}
	
}
