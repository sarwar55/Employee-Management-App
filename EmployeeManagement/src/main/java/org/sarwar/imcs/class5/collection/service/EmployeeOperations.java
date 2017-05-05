package org.sarwar.imcs.class5.collection.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.sarwar.imcs.class5.collection.dto.Address;
import org.sarwar.imcs.class5.collection.dto.Employee;
import org.sarwar.imcs.class5.collection.dto.EmploymentPeriod;
import org.sarwar.imcs.class5.collection.dto.Gender;
import org.sarwar.imcs.class5.collection.dto.PhoneNumber;
import org.sarwar.imcs.class5.collection.util.DateNotValidException;

public interface EmployeeOperations {
	public void addEmployee(Employee e) throws SQLException;
	public boolean deleteEmployee(int employeeId) throws SQLException;
	public boolean updateEmployeeAddress(int employeeId, Address address) throws SQLException;
	public boolean updateEmployeePhoneNumber(int employeeId, PhoneNumber ph) throws SQLException;
	public boolean updateEmployeeSalary(int employeeId, Double Salary) throws SQLException;
	public boolean updateEmployeeEndDate(int employeeId, 
			LocalDate endDate) throws DateNotValidException, SQLException;
	public Employee getEmployeeById(int employeeId) throws SQLException, DateNotValidException;
	public Employee getEmployeeByPhoneNumber(String number) throws SQLException, DateNotValidException;
	public List<PhoneNumber> getEmployeePhoneNumber(int employeeId) throws SQLException, DateNotValidException;
	public Address getEmployeeAddress(int employeeId) throws SQLException;
	public Gender getEmployeeGender(int employeeId) throws SQLException;
	public EmploymentPeriod getEmployeeEmploymentPeriod(int employeeId) throws SQLException, DateNotValidException;
	public int getNumberOfEmployees();
	public Employee[] getEmployees() throws SQLException, DateNotValidException;
	public List<Integer> getEmployeeIds();
	
}
