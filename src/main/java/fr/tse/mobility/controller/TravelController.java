package fr.tse.mobility.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.tse.mobility.domain.Travel;
import fr.tse.mobility.exception.ExceptionResponse;
import fr.tse.mobility.exception.StudentNotFoundException;
import fr.tse.mobility.exception.TravelNotFoundException;
import fr.tse.mobility.service.StudentService;
import fr.tse.mobility.service.TravelService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.DELETE})
public class TravelController {

	@Autowired TravelService travelService;
	@Autowired StudentService studentService;
	
	/*@GetMapping("/travels")
	public List<Travel> getAllTravels(){
		return this.travelService.getAllTravels();
	}*/
	
	@GetMapping("/travels/{id}")
	public Travel getTravelsById(@PathVariable Long id){
		return this.travelService.getTravelById(id);
	}
	
	/*@GetMapping("/travels")
	public List<Travel> getFilteredTravels(@RequestParam MultiValueMap<String, String> filters){
		System.out.println("["+filters.size()+"]Filters:");
        filters.get("filter").forEach(System.out::println);
		return travelService.getFilteredTravels(filters);
	}*/

	@GetMapping("/travels")
	public List<Travel> getFilteredTravels(
			@And({
				@Spec(path = "country", params = "country", spec = Equal.class),
				@Spec(path = "city", params = "city", spec = Equal.class),
				@Spec(path = "company", params = "company", spec = Equal.class),
				@Spec(path = "student.promotion.id", params = "promotion", spec = Equal.class)
			})
			Specification<Travel> travelSpec) {
		
		return this.travelService.getFilteredTravels(travelSpec);
	}
	
	
	
	
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(StudentNotFoundException.class)
	public ExceptionResponse handleError(HttpServletRequest req, StudentNotFoundException ex) {
		return new ExceptionResponse(req, ex);
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(TravelNotFoundException.class)
	public ExceptionResponse handleError(HttpServletRequest req, TravelNotFoundException ex) {
		return new ExceptionResponse(req, ex);
	}
	
}
