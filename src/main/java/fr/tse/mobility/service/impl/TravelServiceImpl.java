package fr.tse.mobility.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import fr.tse.mobility.dao.StudentRepository;
import fr.tse.mobility.dao.TravelRepository;
import fr.tse.mobility.domain.Travel;
import fr.tse.mobility.exception.StudentNotFoundException;
import fr.tse.mobility.exception.TravelNotFoundException;
import fr.tse.mobility.service.TravelService;

@Service
public class TravelServiceImpl implements TravelService {

	private @Autowired StudentRepository studentRepository;
	private @Autowired TravelRepository travelRepository;

	@Override
	public List<Travel> getAllTravels() {
		return this.travelRepository.findAll();
	}
	
	@Override
	public Travel getTravelById(Long id) throws StudentNotFoundException{
		try {
			return this.travelRepository.findById(id).orElseThrow();
		} catch (NoSuchElementException e) {
			throw new TravelNotFoundException(id);
		}
	}

	@Override
	public List<Travel> getFilteredTravels(Specification<Travel> travelSpec) {
		return this.travelRepository.findAll(travelSpec);
	}

	
}
