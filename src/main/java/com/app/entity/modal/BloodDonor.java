package com.app.entity.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "blood_donor_tbl")
public class BloodDonor extends BaseEntity {
	
	@Column(length = 30)
	private String name;

	@Column(length = 30)
	private String email;

	@Column(length = 10)
	private String contactNumber;

	@Enumerated(EnumType.STRING)
	private BloodGroup bloodGroup;

	@Column(length = 30)
	private String city;

	@Column(length = 30)
	private String state;

	public BloodDonor() {

	}

	public BloodDonor(String name, String email, String contactNumber, BloodGroup bloodGroup, String city,
			String state) {
		super();
		this.name = name;
		this.email = email;
		this.contactNumber = contactNumber;
		this.bloodGroup = bloodGroup;
		this.city = city;
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public BloodGroup getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(BloodGroup bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "BloodDonor [name=" + name + ", email=" + email + ", contactNumber=" + contactNumber + ", bloodGroup="
				+ bloodGroup + ", city=" + city + ", state=" + state + "]";
	}
}
