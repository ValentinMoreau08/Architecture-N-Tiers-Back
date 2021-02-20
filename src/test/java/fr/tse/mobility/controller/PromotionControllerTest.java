package fr.tse.mobility.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.jayway.jsonpath.JsonPath;

import fr.tse.mobility.dao.StudentRepository;
import fr.tse.mobility.dao.TravelRepository;
import fr.tse.mobility.domain.Promotion;
import fr.tse.mobility.domain.Student;
import fr.tse.mobility.domain.Travel;
import fr.tse.mobility.service.PromotionService;
import fr.tse.mobility.service.StudentService;
import fr.tse.mobility.service.impl.StudentServiceImpl;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class PromotionControllerTest {

	private @Autowired MockMvc mvc;
	private @Autowired PromotionService promotionService;
	private @Autowired StudentRepository studentRepository;
	
	private boolean promotionListContains(List<Promotion> promotions, String label) {
		Iterator<Promotion> promotionIterator = promotions.iterator();
		while(promotionIterator.hasNext()) {
			Promotion promotion = promotionIterator.next();
			if(		promotion.getLabel().equals(label)) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void findAllPromotionsTest() throws Exception {
		mvc.perform(get("/promotions").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].label", is("promotion1")));
		
		
	}
	
	@Test
	public void getPromotionByIdTest() throws Exception {
		Promotion promotion = promotionService.getAllPromotions().get(0);
		
		mvc.perform(get("/promotions/"+promotion.getId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.label", is(promotion.getLabel())));
		
		mvc.perform(get("/promotions/"+"6668523").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.error", is("PromotionNotFoundException")))
			.andExpect(jsonPath("$.message", is("Promotion with id 6668523 not found")))
			.andExpect(jsonPath("$.path", is("/promotions/6668523")));
	}
		
}
