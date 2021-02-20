package fr.tse.mobility.exception;

public class TravelNotFoundException extends RuntimeException {

	public TravelNotFoundException(Long id) {
		super("Travel with id " + id + " not found");
	}
	public TravelNotFoundException(Long studentId, Long travelId) {
		super("Travel with id " + travelId + " not found for student with id " + studentId);
	}
	
}
