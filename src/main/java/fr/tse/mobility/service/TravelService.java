package fr.tse.mobility.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import fr.tse.mobility.domain.Travel;
import fr.tse.mobility.exception.TravelNotFoundException;

public interface TravelService {

	public List<Travel> getAllTravels();

	public Travel getTravelById(Long id) throws TravelNotFoundException;
	
	public List<Travel> getFilteredTravels(Specification<Travel> travelSpec);
		
}
