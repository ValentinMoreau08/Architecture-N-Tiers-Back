package fr.tse.mobility.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Promotion {

	private @Id @GeneratedValue Long id;
	private String label;
	
	@JsonIgnoreProperties("promotion")
	@OneToMany(mappedBy="promotion", fetch=FetchType.EAGER)
	private Set<Student> students;
	
	public Promotion() {
		this.students = new HashSet<Student>();
	}

	public Promotion(String label) {
		super();
		this.label = label;
		this.students = new HashSet<Student>();
	}

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	public String getLabel() {return label;}
	public void setLabel(String label) {this.label = label;}
	public Set<Student> getStudents() {return students;}
	public void setStudents(Set<Student> students) {this.students = students;}
	public void addStudent(Student student) {this.students.add(student);}
	public void removeStudent(Student student) {this.students.remove(student);}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Promotion other = (Promotion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (students == null) {
			if (other.students != null)
				return false;
		} else if (students.size() != other.students.size())
			return false;
		return true;
	}
	
}
