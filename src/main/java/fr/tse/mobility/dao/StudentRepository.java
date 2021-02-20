package fr.tse.mobility.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.tse.mobility.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	
}
