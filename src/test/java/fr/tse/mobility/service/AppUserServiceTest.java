package fr.tse.mobility.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fr.tse.mobility.dao.AppUserRepository;
import fr.tse.mobility.domain.AppUser;
import fr.tse.mobility.domain.dto.LoginDTO;
import fr.tse.mobility.service.impl.AppUserServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class AppUserServiceTest {

	private @Autowired AppUserRepository appUserRepository;
	private @Autowired AppUserService appUserService;

	@Test
	public void loginTest() {
		AppUser appUser = appUserRepository.findAll().get(0);
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(appUser.getUsername());
		loginDTO.setPassword(appUser.getPassword());
		AppUser newAppUser = appUserService.login(loginDTO);

		Assertions.assertEquals(appUser, newAppUser);
	}
	
}
