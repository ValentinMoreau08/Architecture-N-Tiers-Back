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
import fr.tse.mobility.service.StudentService;
import fr.tse.mobility.service.impl.StudentServiceImpl;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class StudentControllerTest {

	private @Autowired MockMvc mvc;
	private @Autowired StudentService studentService;
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
	public void findAllStudentsTest() throws Exception {
		mvc.perform(get("/students").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].firstname", is("studentFirstName1")))
			.andExpect(jsonPath("$[0].lastname", is("studentName1")))
			.andExpect(jsonPath("$[1].firstname", is("studentFirstName2")))
			.andExpect(jsonPath("$[1].lastname", is("studentName2")));
	}
	
	@Test
	public void getStudentByIdTest() throws Exception {
		Student student = studentService.getAllStudents().get(0);
		
		mvc.perform(get("/students/"+student.getId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.firstname", is(student.getFirstname())))
			.andExpect(jsonPath("$.lastname", is(student.getLastname())));
		
		mvc.perform(get("/students/"+"6668523").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.error", is("StudentNotFoundException")))
			.andExpect(jsonPath("$.message", is("Student with id 6668523 not found")))
			.andExpect(jsonPath("$.path", is("/students/6668523")));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void getStudentTravelsTest() throws Exception {
		Student student = studentService.getAllStudents().get(0);
		
		/*mvc.perform(get("/students/"+student.getId()+"/travels").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].country", is("countryTest1")))
			.andExpect(jsonPath("$[0].city", is("cityTest1")))
			.andExpect(jsonPath("$[0].company", is("companyTest1")))
			.andExpect(jsonPath("$[0].startDate", is("2010-04-11T22:00:00.000+00:00")))
			.andExpect(jsonPath("$[0].endDate", is("2010-06-19T22:00:00.000+00:00")));*/

		
		MvcResult mvcResult = mvc.perform(get("/students/"+student.getId()+"/travels").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn();
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		
		String response = mvcResult.getResponse().getContentAsString();
		
		String country1 = JsonPath.parse(response).read("$[0].country");
		String city1 = JsonPath.parse(response).read("$[0].city");
		String company1 = JsonPath.parse(response).read("$[0].company");
		Date startDate1 = formatter1.parse(JsonPath.parse(response).read("$[0].startDate"));
		Date endDate1 = formatter1.parse(JsonPath.parse(response).read("$[0].endDate"));
		
		String country2 = JsonPath.parse(response).read("$[1].country");
		String city2 = JsonPath.parse(response).read("$[1].city");
		String company2 = JsonPath.parse(response).read("$[1].company");
		Date startDate2 = formatter1.parse(JsonPath.parse(response).read("$[1].startDate"));
		Date endDate2 = formatter1.parse(JsonPath.parse(response).read("$[1].endDate"));
		
		//System.out.println("StringStartDate="+JsonPath.parse(response).read("$[0].startDate"));
		//System.out.println("StartDate="+startDate1.toString());
		
		Assertions.assertTrue(travelListContains(student.getTravels(), country1, city1, company1, startDate1, endDate1));
		Assertions.assertTrue(travelListContains(student.getTravels(), country2, city2, company2, startDate2, endDate2));
		
		
		mvc.perform(get("/students/"+"6668523"+"/travels").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.error", is("StudentNotFoundException")))
			.andExpect(jsonPath("$.message", is("Student with id 6668523 not found")))
			.andExpect(jsonPath("$.path", is("/students/6668523/travels")));
	}
	
	@Test
	public void getStudentTravelByIdTest() throws Exception {
		Travel travel = travelRepository.findAll().get(0);
		
		/*mvc.perform(get("/students/"+travel.getPerson().getId()+"/travels/"+travel.getId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.country", is("countryTest1")))
			.andExpect(jsonPath("$.city", is("cityTest1")))
			.andExpect(jsonPath("$.company", is("companyTest1")))
			.andExpect(jsonPath("$.startDate", is("2010-04-11T22:00:00.000+00:00")))
			.andExpect(jsonPath("$.endDate", is("2010-06-19T22:00:00.000+00:00")));*/
		
		mvc.perform(get("/students/"+travel.getStudent().getId()+"/travels/"+travel.getId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.country", is(travel.getCountry())))
			.andExpect(jsonPath("$.city", is(travel.getCity())))
			.andExpect(jsonPath("$.company", is(travel.getCompany())))
			//.andExpect(jsonPath("$.startDate", is("2010-04-11T22:00:00.000+00:00")))
			//.andExpect(jsonPath("$.endDate", is("2010-06-19T22:00:00.000+00:00")))
			;
		
		mvc.perform(get("/students/"+travel.getStudent().getId()+"/travels/"+"77778").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.error", is("TravelNotFoundException")))
			.andExpect(jsonPath("$.message", is("Travel with id " + 77778 + " not found for student with id "+travel.getStudent().getId())))
			.andExpect(jsonPath("$.path", is("/students/"+travel.getStudent().getId()+"/travels/"+"77778")));
		
		mvc.perform(get("/students/"+"6668523"+"/travels/"+travel.getId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.error", is("StudentNotFoundException")))
			.andExpect(jsonPath("$.message", is("Student with id 6668523 not found")))
			.andExpect(jsonPath("$.path", is("/students/6668523/travels/"+travel.getId())));
	}
	
	@Test
	@Transactional
	public void addStudentTravelTest() throws Exception {
		Student student = studentService.getAllStudents().get(0);
		student = studentRepository.save(student);
		int prevSize = student.getTravels().size();
		
		String content = "{"
				+ "\"country\": \"countryTest\","
				+ "\"city\": \"cityTest\","
				+ "\"company\": \"companyTest\","
				+ "\"startDate\": \"2010-04-11T22:00:00.000+00:00\","
				+ "\"endDate\": \"2010-06-19T22:00:00.000+00:00\""
				+ "}";
		
		mvc.perform(post("/students/"+student.getId()+"/travels").contentType(MediaType.APPLICATION_JSON).content(content))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.country", is("countryTest")))
			.andExpect(jsonPath("$.city", is("cityTest")))
			.andExpect(jsonPath("$.company", is("companyTest")))
			.andExpect(jsonPath("$.startDate", is("2010-04-11T22:00:00.000+00:00")))
			.andExpect(jsonPath("$.endDate", is("2010-06-19T22:00:00.000+00:00")));
		
		mvc.perform(post("/students/"+"6668523"+"/travels").contentType(MediaType.APPLICATION_JSON).content(content))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.error", is("StudentNotFoundException")))
			.andExpect(jsonPath("$.message", is("Student with id 6668523 not found")))
			.andExpect(jsonPath("$.path", is("/students/6668523/travels")));
		
		Assertions.assertEquals(prevSize + 1, student.getTravels().size());
		
		//studentServiceImpl.deleteStudentTravel(student.getId(), null);
		
		//Assertions.assertEquals(prevSize, student.getTravels().size());
	}
	
}
