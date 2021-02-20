package fr.tse.mobility.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.tse.mobility.domain.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	//Optional<AppUser> findByLogin(String login);
	Optional<AppUser> findByUsernameAndPassword(String username, String password);
}
