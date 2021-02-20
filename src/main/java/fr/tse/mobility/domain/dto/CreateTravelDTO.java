package fr.tse.mobility.domain.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateTravelDTO {

	
	@NotBlank(message = "Country is mandatory")
	private String country;
	@NotBlank(message = "City is mandatory")
	private String city;
	@NotBlank(message = "Compnay is mandatory")
	private String company;
	@NotNull(message = "Start Date is mandatory")
	private Date startDate;
	@NotNull(message = "End Date is mandatory")
	private Date endDate;
	
	public CreateTravelDTO() {}

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

}
