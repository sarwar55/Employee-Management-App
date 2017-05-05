package org.sarwar.imcs.class5.collection.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.sarwar.imcs.class5.collection.dto.Employee;
import org.sarwar.imcs.class5.collection.util.DateNotValidException;

public interface EmployeeCollectionInterface extends EmployeeOperations{
	public Set<String> getUniqueNames() throws SQLException;
	public List<Employee> getEmployeeBySortedName() throws SQLException, DateNotValidException;
	public List<Employee> getEmployeeBySortedSalary() throws SQLException, DateNotValidException;
}
