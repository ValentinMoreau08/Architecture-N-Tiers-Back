package fr.tse.mobility.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.tse.mobility.dao.AppUserRepository;
import fr.tse.mobility.domain.AppUser;
import fr.tse.mobility.domain.dto.LoginDTO;
import fr.tse.mobility.exception.LoginOrPasswordIncorrectException;
import fr.tse.mobility.service.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService{

	private @Autowired AppUserRepository appUserRepository;

	/*@Override
	public AppUser getAppUserByLogin(String login) {
		return appUserRepository.findByLogin(login).orElse(null);
	}*/
	
	@Override
	public AppUser login(LoginDTO loginDTO) throws LoginOrPasswordIncorrectException {
		AppUser appUser = appUserRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword()).orElse(null);
		if(appUser == null) {
			throw new LoginOrPasswordIncorrectException();
		} else {
			return appUser;
		}
	}

	

	
}
