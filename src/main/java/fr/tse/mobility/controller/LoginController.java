package fr.tse.mobility.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fr.tse.mobility.domain.AppUser;
import fr.tse.mobility.domain.dto.LoginDTO;
import fr.tse.mobility.exception.ExceptionResponse;
import fr.tse.mobility.exception.LoginOrPasswordIncorrectException;
import fr.tse.mobility.service.AppUserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PATCH })
public class LoginController {

	@Autowired AppUserService appUserService;

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public AppUser login(@Valid @RequestBody LoginDTO loginDTO) {
		return this.appUserService.login(loginDTO);
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(LoginOrPasswordIncorrectException.class)
	public ExceptionResponse handleError(HttpServletRequest req, LoginOrPasswordIncorrectException ex) {
		return new ExceptionResponse(req, ex);
	}

}
