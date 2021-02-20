package fr.tse.mobility.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.tse.mobility.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
}
