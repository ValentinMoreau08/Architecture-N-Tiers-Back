package fr.tse.mobility.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import fr.tse.mobility.dao.AppUserRepository;
import fr.tse.mobility.domain.AppUser;
import fr.tse.mobility.service.AppUserService;
import fr.tse.mobility.service.impl.AppUserServiceImpl;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class LoginControllerTest {

	private @Autowired MockMvc mvc;
	private @Autowired AppUserRepository appUserRepository;
	private @Autowired AppUserService appUserService;
	
	@Test
	public void login() throws Exception {
		AppUser appUser = appUserRepository.findAll().get(0);
		
		mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\": \""+ appUser.getUsername() + "\",\"password\": \"" + appUser.getPassword() + "\"}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.username", is(appUser.getUsername())))
			.andExpect(jsonPath("$.password", is(appUser.getPassword())))
			.andExpect(jsonPath("$.role.label", is(appUser.getRole().getLabel())));
		
		mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\": \""+ "15s6df4sdg" + "\",\"password\": \"" + "sdgdsg165sg5" + "\"}"))
			.andExpect(status().isUnauthorized())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.error", is("LoginOrPasswordIncorrectException")))
			.andExpect(jsonPath("$.message", is("Login or Password incorrect")))
			.andExpect(jsonPath("$.path", is("/login")));

	}

}
