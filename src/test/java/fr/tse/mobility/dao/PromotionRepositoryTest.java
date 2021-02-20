package fr.tse.mobility.dao;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fr.tse.mobility.domain.Promotion;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class PromotionRepositoryTest {

	private @Autowired PromotionRepository promotionRepository;
	
	@Test
	public void findAllTest() {
		List<Promotion> promotions = promotionRepository.findAll();
		Assertions.assertEquals(promotions.size(), 3);
		
		Assertions.assertTrue(promotionListContains(promotions, "promotion1"));
		Assertions.assertTrue(promotionListContains(promotions, "promotion2"));
		Assertions.assertTrue(promotionListContains(promotions, "promotion3"));
	}
	
	private boolean promotionListContains(List<Promotion> promotions, String label) {
		Iterator<Promotion> promotionIterator = promotions.iterator();
		while(promotionIterator.hasNext()) {
			Promotion promo = promotionIterator.next();
			if(promo.getLabel().equals(label)) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void saveAndDeleteTest() {
		List<Promotion> promotions = promotionRepository.findAll();
		int prevSize = promotions.size();
		
		Promotion promotion = new Promotion("promotion4");
		promotion = promotionRepository.save(promotion);
		
		promotions = promotionRepository.findAll();
		Assertions.assertEquals(prevSize+ 1, promotions.size());
		Assertions.assertTrue(promotions.contains(promotion));
		
		promotionRepository.delete(promotion);
		
		promotions = promotionRepository.findAll();
		Assertions.assertEquals(prevSize, promotions.size());
		Assertions.assertFalse(promotions.contains(promotion));
	}
	
}
