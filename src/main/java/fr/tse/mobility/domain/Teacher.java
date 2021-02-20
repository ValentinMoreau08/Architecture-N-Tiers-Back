package fr.tse.mobility.domain;

import javax.persistence.Entity;

@Entity
public class Teacher extends Person {
	
	public Teacher() {}

	public Teacher(String name, String firstname) {
		super(name, firstname);
	}

	@Override
	public boolean equals(Object obj) {
		if(!super.equals(obj))
			return false;
		Teacher other = (Teacher) obj;
		return true;
	}
	
}
