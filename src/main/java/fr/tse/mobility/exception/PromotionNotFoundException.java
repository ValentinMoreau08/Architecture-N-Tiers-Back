package fr.tse.mobility.exception;

public class PromotionNotFoundException extends RuntimeException {

	public PromotionNotFoundException(Long id) {
		super("Promotion with id " + id + " not found");
	}
	
}
