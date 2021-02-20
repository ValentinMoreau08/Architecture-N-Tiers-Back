package fr.tse.mobility.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.tse.mobility.dao.PromotionRepository;
import fr.tse.mobility.domain.Promotion;
import fr.tse.mobility.exception.PromotionNotFoundException;
import fr.tse.mobility.exception.StudentNotFoundException;
import fr.tse.mobility.service.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService {

	private @Autowired PromotionRepository promotionRepository;

	@Override
	public List<Promotion> getAllPromotions() {
		return promotionRepository.findAll();
	}
	
	@Override
	public Promotion getPromotionById(Long id) throws StudentNotFoundException{
		try {
			return this.promotionRepository.findById(id).orElseThrow();
		} catch (NoSuchElementException e) {
			throw new PromotionNotFoundException(id);
		}
	}
	
}
