package org.sarwar.imcs.class5.collection.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sarwar.imcs.class5.collection.dto.Address;
import org.sarwar.imcs.class5.collection.dto.Employee;
import org.sarwar.imcs.class5.collection.dto.EmploymentPeriod;
import org.sarwar.imcs.class5.collection.dto.Gender;
import org.sarwar.imcs.class5.collection.dto.PhoneNumber;
import org.sarwar.imcs.class5.collection.util.DateNotValidException;

public class EmployeeCollectionOperations implements EmployeeCollectionInterface {
	private Map<Integer, Employee> employees;
	private volatile static EmployeeCollectionOperations COLLECTION_INSTANCE;

	private EmployeeCollectionOperations() {
		this.employees = new HashMap<>();
	}

	public static EmployeeCollectionOperations getInstance() {
		if (COLLECTION_INSTANCE == null) {
			synchronized (EmployeeCollectionOperations.class) {
				if (COLLECTION_INSTANCE == null) {
					COLLECTION_INSTANCE = new EmployeeCollectionOperations();
				}
			}
		}
		return COLLECTION_INSTANCE;
	}

	@Override
	public void addEmployee(Employee e) {
		this.employees.put(e.getId(), e);
	}

	@Override
	public boolean deleteEmployee(int employeeId) {
		if (employees.containsKey(employeeId)) {
			employees.remove(employeeId);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateEmployeeAddress(int employeeId, Address address) {
		if (employees.containsKey(employeeId)) {
			employees.get(employeeId).setAddress(address);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateEmployeePhoneNumber(int employeeId, PhoneNumber ph) {
		if (employees.containsKey(employeeId)) {
			employees.get(employeeId).addPhoneNumbers(ph);
			ph.setOwner(employees.get(employeeId));
			return true;
		}
		return false;
	}

	@Override
	public boolean updateEmployeeSalary(int employeeId, Double salary) {
		if (employees.containsKey(employeeId)) {
			employees.get(employeeId).setSalary(salary);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateEmployeeEndDate(int employeeId, LocalDate endDate) throws DateNotValidException {
		if (employees.containsKey(employeeId)) {
			employees.get(employeeId).getEmploymentPeriod().setEndDate(endDate);
			return true;
		}
		return false;
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		if (employees.containsKey(employeeId)) {
			return employees.get(employeeId);
		}
		return null;
	}

	@Override
	public Employee getEmployeeByPhoneNumber(String number) {
		for (Employee e : employees.values()) {
			List<PhoneNumber> ph = e.getPhoneNumbers();
			for (PhoneNumber phoneNumber : ph) {
				if (phoneNumber.getNumber().equals(number)) {
					return e;
				}
			}
		}
		return null;
	}

	@Override
	public List<PhoneNumber> getEmployeePhoneNumber(int employeeId) {
		if (employees.containsKey(employeeId)) {
			return employees.get(employeeId).getPhoneNumbers();
		}
		return null;
	}

	@Override
	public Address getEmployeeAddress(int employeeId) {
		if (employees.containsKey(employeeId)) {
			return employees.get(employeeId).getAddress();
		}
		return null;
	}

	@Override
	public Gender getEmployeeGender(int employeeId) {
		if (employees.containsKey(employeeId)) {
			return employees.get(employeeId).getGender();
		}
		return null;
	}

	@Override
	public EmploymentPeriod getEmployeeEmploymentPeriod(int employeeId) {
		if (employees.containsKey(employeeId)) {
			return employees.get(employeeId).getEmploymentPeriod();
		}
		return null;
	}

	@Override
	public int getNumberOfEmployees() {
		return employees.size();
	}

	@Override
	public Employee[] getEmployees() {
		List<Employee> employee = new ArrayList<>();
		for (Employee e : employees.values())
			employee.add(e);
		return employee.toArray(new Employee[employee.size()]);
	}

	@Override
	public List<Integer> getEmployeeIds() {
		List<Integer> employeeId = new ArrayList<>();
		for (Integer e : employees.keySet())
			employeeId.add(e);
		return employeeId;
	}

	@Override
	public Set<String> getUniqueNames() {
		Set<String> employeeNames = new HashSet<>();
		for (Employee e : employees.values())
			employeeNames.add(e.getName());
		return employeeNames;
	}

	@Override
	public List<Employee> getEmployeeBySortedName() {
		List<Employee> employee = new ArrayList<>();
		for (Employee e : employees.values())
			employee.add(e);
		Collections.sort(employee, new Comparator<Employee>() {
			@Override
			public int compare(Employee e1, Employee e2) {
				return e1.getName().compareTo(e2.getName());
			}
		});
		return employee;
	}

	@Override
	public List<Employee> getEmployeeBySortedSalary() {
		List<Employee> employee = new ArrayList<>();
		for (Employee e : employees.values())
			employee.add(e);
		Collections.sort(employee, new Comparator<Employee>() {
			@Override
			public int compare(Employee e1, Employee e2) {
				return e1.getSalary().compareTo(e2.getSalary());
			}
		});
		return employee;
	}

}
