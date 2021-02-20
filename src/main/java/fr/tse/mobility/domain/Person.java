package fr.tse.mobility.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

	private @Id @GeneratedValue Long id;
	private String lastname;
	private String firstname;
	
	@JsonIgnoreProperties("person")
	@OneToOne(mappedBy = "person")
	private AppUser appUser;
	
	public Person() {}

	public Person(String lastname, String firstname) {
		this.lastname = lastname;
		this.firstname = firstname;
	}

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	public String getLastname() {return lastname;}
	public void setLastname(String lastname) {this.lastname = lastname;}
	public String getFirstname() {return firstname;}
	public void setFirstname(String firstname) {this.firstname = firstname;}
	public AppUser getAppUser() {return appUser;}
	public void setAppUser(AppUser appUser) {this.appUser = appUser;}
	public void removeAppUser() {this.appUser = null;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (appUser == null) {
			if (other.appUser != null)
				return false;
		} /*else if (!appUser.equals(other.appUser))
			return false;*/
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		return true;
	}

	
	
}
