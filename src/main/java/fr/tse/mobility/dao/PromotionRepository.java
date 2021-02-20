package fr.tse.mobility.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.tse.mobility.domain.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
	
}
