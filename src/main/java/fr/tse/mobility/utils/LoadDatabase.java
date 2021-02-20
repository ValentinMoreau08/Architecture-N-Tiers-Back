package fr.tse.mobility.utils;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import fr.tse.mobility.dao.AppUserRepository;
import fr.tse.mobility.dao.PromotionRepository;
import fr.tse.mobility.dao.RoleRepository;
import fr.tse.mobility.dao.StudentRepository;
import fr.tse.mobility.dao.TeacherRepository;
import fr.tse.mobility.dao.TravelRepository;
import fr.tse.mobility.domain.AppUser;
import fr.tse.mobility.domain.Promotion;
import fr.tse.mobility.domain.Role;
import fr.tse.mobility.domain.Student;
import fr.tse.mobility.domain.Teacher;
import fr.tse.mobility.domain.Travel;

@Configuration // Can create @Bean method
public class LoadDatabase {

	private Promotion fise1;
	private Promotion fise2;
	private Promotion fise3;
	private Student student1;
	private Student student2;
	private Teacher teacher1;
	private Teacher teacher2;
	private Role administratorRole;
	private Role userRole;
	private AppUser admin1;
	private AppUser admin2;
	private AppUser user1;
	private AppUser user2;
	private Travel travel1;
	private Travel travel2;
	private Travel travel3;
	
	@Bean	// Method that will create beam disponible in spring environnement
	@Profile("!test")
	CommandLineRunner initDatabase(StudentRepository studentRepository, TeacherRepository teacherRepository, PromotionRepository promotionRepository, AppUserRepository appUserRepository, RoleRepository roleRepository, TravelRepository travelRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				if(!needInitialize(studentRepository, teacherRepository, promotionRepository, appUserRepository, roleRepository, travelRepository)) return;
				initPromotions(promotionRepository);
				initStudents(studentRepository, promotionRepository);
				initTeachers(teacherRepository);
				initRoles(roleRepository);
				initAppUsers(appUserRepository, roleRepository, studentRepository, teacherRepository);
				initTravels(travelRepository, studentRepository);
			}
		};
	}
	
	public boolean needInitialize(StudentRepository studentRepository, TeacherRepository teacherRepository, PromotionRepository promotionRepository, AppUserRepository appUserRepository, RoleRepository roleRepository, TravelRepository travelRepository) {
		if(studentRepository.findAll().size() != 0) return false;
		if(teacherRepository.findAll().size() != 0) return false;
		if(promotionRepository.findAll().size() != 0) return false;
		if(appUserRepository.findAll().size() != 0) return false;
		if(roleRepository.findAll().size() != 0) return false;
		if(travelRepository.findAll().size() != 0) return false;
		return true;
	}
	
	public void initPromotions(PromotionRepository promotionRepository) {
		fise1 = new Promotion("FISE1");
		fise2 = new Promotion("FISE2");
		fise3 = new Promotion("FISE3");
		promotionRepository.save(fise1);
		promotionRepository.save(fise2);
		promotionRepository.save(fise3);
	}
	
	@Transactional
	public void initStudents(StudentRepository studentRepository, PromotionRepository promotionRepository) {
		fise1 = promotionRepository.save(fise1);
		fise2 = promotionRepository.save(fise2);
		student1 = new Student("Dupont", "Jean", fise1);
		student2 = new Student("Grand", "Paul", fise2);
		fise1.addStudent(student1);
		fise2.addStudent(student2);
		studentRepository.save(student1);
		studentRepository.save(student2);
	}
	
	public void initTeachers(TeacherRepository teacherRepository) {
		teacher1 = new Teacher("Depes", "Michel");
		teacher2 = new Teacher("Odfned", "Jacques");
		teacherRepository.save(teacher1);
		teacherRepository.save(teacher2);
	}
	
	public void initRoles(RoleRepository roleRepository) {
		administratorRole = new Role(2L, "Administrator");
		userRole = new Role(1L, "User");
		roleRepository.save(administratorRole);
		roleRepository.save(userRole);
	}
	
	@Transactional
	public void initAppUsers(AppUserRepository appUserRepository, RoleRepository roleRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
		student1 = studentRepository.save(student1);
		student2 = studentRepository.save(student2);
		teacher1 = teacherRepository.save(teacher1);
		teacher2 = teacherRepository.save(teacher2);
		administratorRole = roleRepository.findById(administratorRole.getId()).orElse(null);
		userRole = roleRepository.findById(userRole.getId()).orElse(null);
		admin1 = new AppUser("d.michel", "123", administratorRole, teacher1);
		admin2 = new AppUser("o.jacques", "1234", administratorRole, teacher2);
		user1 =  new AppUser("d.jean", "12345", userRole, student1);
		user2 =  new AppUser("g.paul", "123456", userRole, student2);
		
		student1.setAppUser(user1);
		student2.setAppUser(user2);
		teacher1.setAppUser(admin1);
		teacher2.setAppUser(admin2);
		appUserRepository.save(admin1);
		appUserRepository.save(admin2);
		appUserRepository.save(user1);
		appUserRepository.save(user2);
	}
	
	@Transactional
	public void initTravels(TravelRepository travelRepository, StudentRepository studentRepository) {
		student1 = studentRepository.save(student1);
		student2 = studentRepository.save(student2);
		
		travel1 = new Travel("Espagne", "Barcelone", "Flawless", new Date(110, 3, 12), new Date(110, 5, 20), student1);
		travel2 = new Travel("Allemagne", "Berlin", "Keirn", new Date(121, 1, 1), new Date(121, 12, 31), student1);
		travel3 = new Travel("Allemagne", "Haust", "Heins", new Date(121, 2, 2), new Date(121, 5, 20), student2);
		travelRepository.save(travel1);
		travelRepository.save(travel2);
		travelRepository.save(travel3);
		student1.addTravel(travel1);
		student1.addTravel(travel2);
		student1.addTravel(travel3);
	}
	
	
	
}
