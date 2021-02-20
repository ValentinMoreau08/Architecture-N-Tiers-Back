package fr.tse.mobility.service;

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
public class PromotionServiceTest {

	private @Autowired PromotionService promotionService;

	@Test
	public void getAllStudentsTest() {
		List<Promotion> promotions = promotionService.getAllPromotions();
		Assertions.assertEquals(3, promotions.size());

		Assertions.assertTrue(promotionListContains(promotions, "promotion1"));
		Assertions.assertTrue(promotionListContains(promotions, "promotion2"));
		Assertions.assertTrue(promotionListContains(promotions, "promotion3"));
		
		Assertions.assertFalse(promotionListContains(promotions, "promotion666"));
	}
	
	private boolean promotionListContains(List<Promotion> promotions, String label) {
		Iterator<Promotion> promotionIterator = promotions.iterator();
		while(promotionIterator.hasNext()) {
			Promotion promotion = promotionIterator.next();
			if(		promotion.getLabel().equals(label)) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void getPromotionByIdTest() {
		Promotion promotion = promotionService.getAllPromotions().get(0);
		
		Promotion newPromotion = promotionService.getPromotionById(promotion.getId());
		
		Assertions.assertNotEquals(null, newPromotion);
		
		Assertions.assertEquals(promotion.getLabel(), newPromotion.getLabel());
		Assertions.assertEquals(promotion.getLabel(), newPromotion.getLabel());
	}
		
}
