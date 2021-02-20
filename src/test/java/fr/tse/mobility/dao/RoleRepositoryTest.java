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

import fr.tse.mobility.domain.Role;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class RoleRepositoryTest {

	private @Autowired RoleRepository roleRepository;
	
	@Test
	public void findAllTest() {
		List<Role> roles = roleRepository.findAll();
		Assertions.assertEquals(roles.size(), 2);
		
		Assertions.assertTrue(roleListContains(roles, "Administrator"));
		Assertions.assertTrue(roleListContains(roles, "User"));
	}
	
	private boolean roleListContains(List<Role> roles, String label) {
		Iterator<Role> roleIterator = roles.iterator();
		while(roleIterator.hasNext()) {
			Role role = roleIterator.next();
			if(role.getLabel().equals(label)) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void saveAndDeleteTest() {
		List<Role> roles = roleRepository.findAll();
		int prevSize = roles.size();
		
		Role role = new Role(3L, "role3");
		role = roleRepository.save(role);
		
		roles = roleRepository.findAll();
		Assertions.assertEquals(prevSize+ 1, roles.size());
		Assertions.assertTrue(roles.contains(role));
		
		roleRepository.delete(role);
		
		roles = roleRepository.findAll();
		Assertions.assertEquals(prevSize, roles.size());
		Assertions.assertFalse(roles.contains(role));
	}
	
}
