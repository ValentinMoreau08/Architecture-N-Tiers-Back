package fr.tse.mobility.service;

import java.util.Collection;
import java.util.List;

import fr.tse.mobility.domain.Student;
import fr.tse.mobility.domain.Travel;
import fr.tse.mobility.domain.dto.CreateTravelDTO;
import fr.tse.mobility.exception.StudentNotFoundException;
import fr.tse.mobility.exception.TravelNotFoundException;

public interface StudentService {

	public List<Student> getAllStudents();

	public Student getStudentById(Long id) throws StudentNotFoundException;
	
	public Collection<Travel> getTravelsByStudentId(Long id) throws StudentNotFoundException;
	
	public Travel getStudentTravelById(Long studentId, Long travelId) throws StudentNotFoundException, TravelNotFoundException;
	
	public void deleteStudentTravel(Long studentId, Long travelId) throws StudentNotFoundException, TravelNotFoundException;
	
	public Travel addStudentTravel(Long studentId, CreateTravelDTO createTravelDTO) throws StudentNotFoundException;
	
	public Travel modifyStudentTravel(Long studentId, Long travelId, CreateTravelDTO createTravelDTO) throws StudentNotFoundException, TravelNotFoundException;
	
}
