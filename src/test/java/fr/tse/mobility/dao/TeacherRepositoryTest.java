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

import fr.tse.mobility.domain.Teacher;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class TeacherRepositoryTest {

	private @Autowired TeacherRepository teacherRepository;
	
	@Test
	public void findAllTest() {
		List<Teacher> teachers = teacherRepository.findAll();
		Assertions.assertEquals(teachers.size(), 2);
		
		Assertions.assertTrue(teacherListContains(teachers, "teacherName1", "teacherFirstName1"));
		Assertions.assertTrue(teacherListContains(teachers, "teacherName2", "teacherFirstName2"));
	}
	
	private boolean teacherListContains(List<Teacher> teachers, String lastname, String firstname) {
		Iterator<Teacher> teacherIterator = teachers.iterator();
		while(teacherIterator.hasNext()) {
			Teacher teacher = teacherIterator.next();
			if(teacher.getLastname().equals(lastname) && teacher.getFirstname().equals(firstname)) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void saveAndDeleteTest() {
		List<Teacher> teachers = teacherRepository.findAll();
		int prevSize = teachers.size();
		
		Teacher teacher = new Teacher("teacherName3", "teacherFirstName3");
		teacher = teacherRepository.save(teacher);
		
		teachers = teacherRepository.findAll();
		Assertions.assertEquals(prevSize+ 1, teachers.size());
		Assertions.assertTrue(teachers.contains(teacher));
		
		teacherRepository.delete(teacher);
		
		teachers = teacherRepository.findAll();
		Assertions.assertEquals(prevSize, teachers.size());
		Assertions.assertFalse(teachers.contains(teacher));
	}
	
}
