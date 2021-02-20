package fr.tse.mobility.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class AppUser {

	private @Id @GeneratedValue Long id;
	private @Column(unique=true) String username;
	private String password;
	
	private @ManyToOne Role role;
	
	@JsonIgnoreProperties("appUser")
	@OneToOne
	private Person person;
	
	public AppUser() {}

	public AppUser(String username, String password, Role role, Person person) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.person = person;
	}

	public Long getId() {return id;}	
	public void setId(Long id) {this.id = id;}
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public Person getPerson() {return person;}
	public void setPerson(Person person) {this.person = person;}
	public Role getRole() {return role;}
	public void setRole(Role role) {this.role = role;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUser other = (AppUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} /*else if (!person.equals(other.person))
			return false;*/
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}
	
}
