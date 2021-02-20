package fr.tse.mobility.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.tse.mobility.domain.Promotion;
import fr.tse.mobility.exception.ExceptionResponse;
import fr.tse.mobility.exception.PromotionNotFoundException;
import fr.tse.mobility.service.PromotionService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.DELETE})
public class PromotionController {

	@Autowired PromotionService promotionService;
	
	@GetMapping("/promotions")
	public List<Promotion> getAllPromotions(){
		return this.promotionService.getAllPromotions();
	}
	
	@GetMapping("/promotions/{id}")
	public Promotion getPromotionById(@PathVariable Long id){
		return this.promotionService.getPromotionById(id);
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(PromotionNotFoundException.class)
	public ExceptionResponse handleError(HttpServletRequest req, PromotionNotFoundException ex) {
		return new ExceptionResponse(req, ex);
	}
	
}
