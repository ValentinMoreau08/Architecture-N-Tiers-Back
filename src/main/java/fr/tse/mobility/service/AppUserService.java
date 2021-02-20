package fr.tse.mobility.service;

import fr.tse.mobility.domain.AppUser;
import fr.tse.mobility.domain.dto.LoginDTO;
import fr.tse.mobility.exception.LoginOrPasswordIncorrectException;

public interface AppUserService {

	//public AppUser getAppUserByLogin(String login);
	
	public AppUser login(LoginDTO loginDTO) throws LoginOrPasswordIncorrectException;

}
