package fr.tse.mobility.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.tse.mobility.domain.Student;
import fr.tse.mobility.domain.Travel;
import fr.tse.mobility.domain.dto.CreateTravelDTO;
import fr.tse.mobility.exception.ExceptionResponse;
import fr.tse.mobility.exception.StudentNotFoundException;
import fr.tse.mobility.exception.TravelNotFoundException;
import fr.tse.mobility.service.StudentService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.DELETE})
public class StudentController {

	@Autowired StudentService studentService;
	
	@GetMapping("/students")
	public List<Student> getAllStudents(){
		return this.studentService.getAllStudents();
	}
	
	@GetMapping("/students/{id}")
	public Student getStudentById(@PathVariable Long id){
		return this.studentService.getStudentById(id);
	}
	
	@GetMapping("/students/{id}/travels")
	public Collection<Travel> getStudentTravels(@PathVariable Long id){
		return this.studentService.getTravelsByStudentId(id);
	}
	
	@GetMapping("/students/{studentId}/travels/{travelId}")
	public Travel getStudentTravelById(@PathVariable Long studentId, @PathVariable Long travelId){
		return this.studentService.getStudentTravelById(studentId, travelId);
	}
	
	@DeleteMapping("/students/{studentId}/travels/{travelId}")
	public void deleteStudentTravel(@PathVariable Long studentId, @PathVariable Long travelId){
		this.studentService.deleteStudentTravel(studentId, travelId);
	}
	
	@PostMapping(value="/students/{id}/travels")
	public Travel addStudentTravel(@PathVariable Long id, @Valid @RequestBody CreateTravelDTO createTravelDTO) {
		return this.studentService.addStudentTravel(id, createTravelDTO);
	}
	
	@PatchMapping(value="/students/{studentId}/travels/{travelId}")
	public Travel modifyStudentTravel(@PathVariable Long studentId, @PathVariable Long travelId, @Valid @RequestBody CreateTravelDTO createTravelDTO) {
		return this.studentService.modifyStudentTravel(studentId, travelId, createTravelDTO);
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
