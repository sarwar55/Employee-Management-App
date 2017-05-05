package org.sarwar.imcs.class5.collection.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Employee {
	private int id;
	private String name;
	private Gender gender;
	private Address address;
	private List<PhoneNumber> phoneNumbers;
	private Double salary;
	private EmploymentPeriod employmentPeriod;

	public Employee(int id, String name, Gender gender, Address address, Double salary,
			EmploymentPeriod employmentPeriod) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.address = address;
		this.phoneNumbers = new ArrayList<>();
		this.salary = salary;
		this.employmentPeriod = employmentPeriod;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void addPhoneNumbers(PhoneNumber phoneNumber) {
		this.phoneNumbers.add(phoneNumber);
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Double getHRA() {
		return this.getSalary() * 0.20;
	}

	public Double getGrossSalary() {
		return this.getHRA() + this.getSalary();
	}

	public EmploymentPeriod getEmploymentPeriod() {
		return employmentPeriod;
	}

	public void setEmploymentPeriod(EmploymentPeriod employmentPeriod) {
		this.employmentPeriod = employmentPeriod;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
