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

import fr.tse.mobility.domain.AppUser;
import fr.tse.mobility.domain.Role;
import fr.tse.mobility.domain.Student;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class AppUserRepositoryTest {

	private @Autowired AppUserRepository appUserRepository;
	private @Autowired StudentRepository studentRepository;
	private @Autowired RoleRepository roleRepository;
	
	@Test
	public void findAllTest() {
		List<AppUser> appUsers = appUserRepository.findAll();
		Assertions.assertEquals(appUsers.size(), 2);
		
		Assertions.assertTrue(appUserListContains(appUsers, "appUserLogin1", "appUserPassword1"));
		Assertions.assertTrue(appUserListContains(appUsers, "appUserLogin2", "appUserPassword2"));
	}
	
	private boolean appUserListContains(List<AppUser> appUsers, String username, String password) {
		Iterator<AppUser> appUserIterator = appUsers.iterator();
		while(appUserIterator.hasNext()) {
			AppUser appUser = appUserIterator.next();
			if(appUser.getUsername().equals(username) && appUser.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	@Transactional
	public void saveAndDeleteTest() {
		Student student = studentRepository.findAll().get(0);
		Role role = roleRepository.findAll().get(0);
		
		List<AppUser> appUsers = appUserRepository.findAll();
		int prevAppUserSize = appUsers.size();
		
		AppUser appUser = new AppUser("appUserName3", "appUserFirstName3", role, student);
		appUser = appUserRepository.save(appUser);
		student.setAppUser(appUser);
		
		appUsers = appUserRepository.findAll();
		Assertions.assertEquals(prevAppUserSize+ 1, appUsers.size());
		Assertions.assertTrue(appUsers.contains(appUser));
		
		Student newStudent = studentRepository.findById(student.getId()).orElse(null);
		Assertions.assertEquals(appUser, newStudent.getAppUser());
		
		student.removeAppUser();
		appUserRepository.delete(appUser);
		
		appUsers = appUserRepository.findAll();
		Assertions.assertEquals(prevAppUserSize, appUsers.size());
		Assertions.assertFalse(appUsers.contains(appUser));
		
		newStudent = studentRepository.findById(student.getId()).orElse(null);
		Assertions.assertNull(newStudent.getAppUser());
	}
	
	@Test
	public void findByLoginAndPasswordTest() {
		AppUser appUser = appUserRepository.findAll().get(0);
		
		AppUser newAppUser = appUserRepository.findByUsernameAndPassword(appUser.getUsername(), appUser.getPassword()).orElse(null);
		Assertions.assertEquals(appUser, newAppUser);
	}
	
}
