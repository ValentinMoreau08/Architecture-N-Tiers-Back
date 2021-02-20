package fr.tse.mobility.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Travel {

	private @Id @GeneratedValue Long id;
	private String country;
	private String city;
	private String company;
	private @Temporal(TemporalType.TIMESTAMP) Date startDate;
	private @Temporal(TemporalType.TIMESTAMP) Date endDate;
	
	@JsonIgnoreProperties("travels")
	private @ManyToOne Student student;
	
	public Travel() {}

	public Travel(String country, String city, String company, Date startDate, Date endDate, Student student) {
		this.country = country;
		this.city = city;
		this.company = company;
		this.startDate = startDate;
		this.endDate = endDate;
		this.student = student;
	}

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	public String getCountry() {return country;}
	public void setCountry(String country) {this.country = country;}
	public String getCity() {return city;}
	public void setCity(String city) {this.city = city;}
	public String getCompany() {return company;}
	public void setCompany(String company) {this.company = company;}
	public Date getStartDate() {return startDate;}
	public void setStartDate(Date startDate) {this.startDate = startDate;}
	public Date getEndDate() {return endDate;}
	public void setEndDate(Date endDate) {this.endDate = endDate;}
	public Person getStudent() {return student;}
	public void setStudent(Student student) {this.student = student;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Travel other = (Travel) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	
	
}
