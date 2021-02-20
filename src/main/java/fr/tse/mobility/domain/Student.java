package fr.tse.mobility.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Student extends Person {

	@JsonIgnoreProperties("students")
	private @ManyToOne Promotion promotion;

	@JsonIgnoreProperties("student")
	@OneToMany(mappedBy="student", fetch=FetchType.EAGER)
	private Set<Travel> travels;
	
	public Student() {
		this.travels = new HashSet<Travel>();
	}

	public Student(String name, String firstname, Promotion promotion) {
		super(name, firstname);
		this.promotion = promotion;
		this.travels = new HashSet<Travel>();
	}

	public Promotion getPromotion() {return promotion;}
	public void setPromotion(Promotion promotion) {this.promotion = promotion;}
	public Set<Travel> getTravels() {return travels;}
	public void setTravels(Set<Travel> travels) {this.travels = travels;}
	public void addTravel(Travel travel) {this.travels.add(travel);}
	public void removeTravel(Travel travel) {this.travels.remove(travel);}

	@Override
	public boolean equals(Object obj) {
		if(!super.equals(obj))
			return false;
		Student other = (Student) obj;
		if (promotion == null) {
			if (other.promotion != null)
				return false;
		} else if (!promotion.equals(other.promotion))
			return false;
		if (travels == null) {
			if (other.travels != null)
				return false;
		} else if (travels.size() != other.travels.size())
			return false;
		return true;
	}
	
	
	
}
