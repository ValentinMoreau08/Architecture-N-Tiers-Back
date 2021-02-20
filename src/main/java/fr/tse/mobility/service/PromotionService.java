package fr.tse.mobility.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import fr.tse.mobility.domain.Promotion;
import fr.tse.mobility.domain.Travel;
import fr.tse.mobility.exception.PromotionNotFoundException;
import fr.tse.mobility.exception.TravelNotFoundException;

public interface PromotionService {

	public List<Promotion> getAllPromotions();

	public Promotion getPromotionById(Long id) throws PromotionNotFoundException;
		
}
