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
public class LoadTestDatabase {

	private Promotion testPromotion1;
	private Promotion testPromotion2;
	private Promotion testPromotion3;
	private Student testStudent1;
	private Student testStudent2;
	private Teacher testTeacher1;
	private Teacher testTeacher2;
	private Role administratorRole;
	private Role userRole;
	private AppUser admin1;
	private AppUser user1;
	private Travel testTravel1;
	private Travel testTravel2;
	
	@Bean	// Method that will create beam disponible in spring environnement
	@Profile("test")
	CommandLineRunner initDatabase(StudentRepository studentRepository, TeacherRepository teacherRepository, PromotionRepository promotionRepository, AppUserRepository appUserRepository, RoleRepository roleRepository, TravelRepository travelRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				clear(studentRepository, teacherRepository, promotionRepository, appUserRepository, roleRepository, travelRepository);
				initPromotions(promotionRepository);
				initStudents(studentRepository, promotionRepository);
				initTeachers(teacherRepository);
				initRoles(roleRepository);
				initAppUsers(appUserRepository, roleRepository, studentRepository, teacherRepository);
				initTravels(travelRepository, studentRepository);
			}
		};
	}
	
	public void clear(StudentRepository studentRepository, TeacherRepository teacherRepository, PromotionRepository promotionRepository, AppUserRepository appUserRepository, RoleRepository roleRepository, TravelRepository travelRepository) {
		travelRepository.deleteAll();
		appUserRepository.deleteAll();
		studentRepository.deleteAll();
		teacherRepository.deleteAll();
		promotionRepository.deleteAll();
		roleRepository.deleteAll();
	}
	
	public void initPromotions(PromotionRepository promotionRepository) {
		testPromotion1 = new Promotion("promotion1");
		testPromotion2 = new Promotion("promotion2");
		testPromotion3 = new Promotion("promotion3");
		promotionRepository.save(testPromotion1);
		promotionRepository.save(testPromotion2);
		promotionRepository.save(testPromotion3);
	}
	
	@Transactional
	public void initStudents(StudentRepository studentRepository, PromotionRepository promotionRepository) {
		testPromotion1 = promotionRepository.save(testPromotion1);
		testPromotion2 = promotionRepository.save(testPromotion2);
		testStudent1 = new Student("studentName1", "studentFirstName1", testPromotion1);
		testStudent2 = new Student("studentName2", "studentFirstName2", testPromotion2);
		testPromotion1.addStudent(testStudent1);
		testPromotion2.addStudent(testStudent2);
		studentRepository.save(testStudent1);
		studentRepository.save(testStudent2);
	}
	
	public void initTeachers(TeacherRepository teacherRepository) {
		testTeacher1 = new Teacher("teacherName1", "teacherFirstName1");
		testTeacher2 = new Teacher("teacherName2", "teacherFirstName2");
		teacherRepository.save(testTeacher1);
		teacherRepository.save(testTeacher2);
	}
	
	public void initRoles(RoleRepository roleRepository) {
		administratorRole = new Role(2L, "Administrator");
		userRole = new Role(1L, "User");
		roleRepository.save(administratorRole);
		roleRepository.save(userRole);
	}
	
	@Transactional
	public void initAppUsers(AppUserRepository appUserRepository, RoleRepository roleRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
		testStudent1 = studentRepository.findById(testStudent1.getId()).orElse(null);
		testTeacher1 = teacherRepository.findById(testTeacher1.getId()).orElse(null);
		administratorRole = roleRepository.findById(administratorRole.getId()).orElse(null);
		userRole = roleRepository.findById(userRole.getId()).orElse(null);
		admin1 = new AppUser("appUserLogin1", "appUserPassword1", administratorRole, testStudent1);
		user1 =  new AppUser("appUserLogin2", "appUserPassword2", userRole, testTeacher1);
		testStudent1.setAppUser(admin1);
		testTeacher1.setAppUser(user1);
		appUserRepository.save(admin1);
		appUserRepository.save(user1);
	}
	
	@SuppressWarnings("deprecation")
	@Transactional
	public void initTravels(TravelRepository travelRepository, StudentRepository studentRepository) {
		testStudent1 = studentRepository.save(testStudent1);
		
		testTravel1 = new Travel("countryTest1", "cityTest1", "companyTest1", new Date(110, 3, 5), new Date(110, 5, 3), testStudent1);
		testTravel2 = new Travel("countryTest2", "cityTest2", "companyTest2", new Date(110, 4, 10), new Date(110, 6, 14), testStudent1);
		testStudent1.addTravel(testTravel1);
		testStudent1.addTravel(testTravel2);
		travelRepository.save(testTravel1);
		travelRepository.save(testTravel2);
		
	}
	
	
	
}
