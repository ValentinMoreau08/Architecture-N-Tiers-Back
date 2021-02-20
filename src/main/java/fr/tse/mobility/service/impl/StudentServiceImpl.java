package fr.tse.mobility.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.tse.mobility.dao.StudentRepository;
import fr.tse.mobility.dao.TravelRepository;
import fr.tse.mobility.domain.Student;
import fr.tse.mobility.domain.Travel;
import fr.tse.mobility.domain.dto.CreateTravelDTO;
import fr.tse.mobility.exception.StudentNotFoundException;
import fr.tse.mobility.exception.TravelNotFoundException;
import fr.tse.mobility.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{

	private @Autowired StudentRepository studentRepository;
	private @Autowired TravelRepository travelRepository;

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
	
	@Override
	public Student getStudentById(Long id) throws StudentNotFoundException{
		try {
			return this.studentRepository.findById(id).orElseThrow();
		} catch (NoSuchElementException e) {
			throw new StudentNotFoundException(id);
		}
	}

	@Override
	public Collection<Travel> getTravelsByStudentId(Long id) throws StudentNotFoundException{
		return this.getStudentById(id).getTravels();
	}
	
	@Override
	public Travel getStudentTravelById(Long studentId, Long travelId) throws StudentNotFoundException, TravelNotFoundException {
		Student student = this.getStudentById(studentId);
		
		Travel travel;
		try {
			travel =  this.travelRepository.findById(travelId).orElseThrow();
		} catch (NoSuchElementException e) {
			throw new TravelNotFoundException(studentId, travelId);
		}
		System.out.println("StudentId="+studentId+"||travel.getPerson().getId()="+travel.getStudent().getId());
		if(!travel.getStudent().getId().equals(studentId)) {
			throw new TravelNotFoundException(studentId, travelId);
		}
		
		return travel;
	}
	
	@Override
	@Transactional
	public void deleteStudentTravel(Long studentId, Long travelId) throws StudentNotFoundException, TravelNotFoundException {
		Student student = this.getStudentById(studentId);
		student = this.studentRepository.save(student);
		Travel travel = this.getStudentTravelById(studentId, travelId);
		
		student.removeTravel(travel);
		travelRepository.delete(travel);
		
	}

	@Override
	@Transactional
	public Travel addStudentTravel(Long studentId, CreateTravelDTO createTravelDTO) throws StudentNotFoundException {
		Student student = this.getStudentById(studentId);
		Travel travel = new Travel(	createTravelDTO.getCountry(),
									createTravelDTO.getCity(),
									createTravelDTO.getCompany(),
									createTravelDTO.getStartDate(),
									createTravelDTO.getEndDate(),
									student);
		student.addTravel(travel);
		travelRepository.save(travel);
		student = this.studentRepository.save(student);
		return travel;
	}

	@Override
	@Transactional
	public Travel modifyStudentTravel(Long studentId, Long travelId, CreateTravelDTO createTravelDTO) throws StudentNotFoundException, TravelNotFoundException {
		Travel travel = this.getStudentTravelById(studentId, travelId);
		travelRepository.save(travel);
		
		travel.setCountry(createTravelDTO.getCountry());
		travel.setCity(createTravelDTO.getCity());
		travel.setCompany(createTravelDTO.getCompany());
		travel.setStartDate(createTravelDTO.getStartDate());
		travel.setEndDate(createTravelDTO.getEndDate());
		
		return travel;
	}

	
	
}
