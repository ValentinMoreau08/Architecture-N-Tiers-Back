package fr.tse.mobility.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.tse.mobility.domain.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
	
}
