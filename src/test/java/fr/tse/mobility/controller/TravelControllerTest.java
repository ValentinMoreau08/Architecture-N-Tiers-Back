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
import fr.tse.mobility.domain.Student;
import fr.tse.mobility.domain.Travel;
import fr.tse.mobility.service.TravelService;
import fr.tse.mobility.service.impl.StudentServiceImpl;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class TravelControllerTest {

	private @Autowired MockMvc mvc;
	private @Autowired TravelService travelService;
	private @Autowired TravelRepository travelRepository;
	private @Autowired StudentRepository studentRepository;
	
	private boolean travelListContains(Collection<Travel> travels, String country, String city, String company, Date startDate, Date endDate) {
		Iterator<Travel> travelIterator = travels.iterator();
		while(travelIterator.hasNext()) {
			Travel travel = travelIterator.next();
			//System.out.println("TravelStartDate="+travel.getStartDate().toString());
			//System.out.println("dist="+travel.getStartDate().compareTo(startDate));
			if(		travel.getCountry().equals(country)
					&& travel.getCity().equals(city)
					&& travel.getCompany().equals(company)
					//&& travel.getStartDate().compareTo(startDate) == 0
					//&& travel.getEndDate().compareTo(endDate) == 0
					) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void getAlTravelsTest() throws Exception {
		mvc.perform(get("/travels").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void getTravelByIdTest() throws Exception {
		Travel travel = travelService.getAllTravels().get(0);
		
		mvc.perform(get("/travels/"+travel.getId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.country", is(travel.getCountry())))
			.andExpect(jsonPath("$.city", is(travel.getCity())))
			.andExpect(jsonPath("$.company", is(travel.getCompany())));
		
		mvc.perform(get("/travels/"+"6668523").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.error", is("TravelNotFoundException")))
			.andExpect(jsonPath("$.message", is("Travel with id 6668523 not found")))
			.andExpect(jsonPath("$.path", is("/travels/6668523")));
	}
	
}
