package org.sarwar.imcs.class5.collection.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sarwar.imcs.class5.collection.dto.Address;
import org.sarwar.imcs.class5.collection.dto.Employee;
import org.sarwar.imcs.class5.collection.dto.EmploymentPeriod;
import org.sarwar.imcs.class5.collection.dto.Gender;
import org.sarwar.imcs.class5.collection.dto.PhoneNumber;
import org.sarwar.imcs.class5.collection.util.DateNotValidException;

public class EmployeeArrayOperations implements EmployeeOperations{
	private Employee[] employees;
	private int numberOfEmployees = 0;
	private List<Integer> employeeIds;
	
	private volatile static EmployeeArrayOperations ARRAY_INSTANCE;
	
	private EmployeeArrayOperations() {
		this.employees = new Employee[1];
		employeeIds = new ArrayList<>();
	}
	
	 public static EmployeeArrayOperations getInstance(){
         if(ARRAY_INSTANCE == null){
            synchronized(EmployeeArrayOperations.class){
                if(ARRAY_INSTANCE == null){
                	ARRAY_INSTANCE = new EmployeeArrayOperations();
                }
            }
         }
         return ARRAY_INSTANCE;
     }
	
	private int getEmployeePosition(int employeeId){
		int employeePosition =-1;
		for (int i = 0; i < numberOfEmployees; i++) {
			if(employees[i].getId() == employeeId){
				employeePosition = i;
				break;
			}
		}
		return employeePosition;
	}

	public void addEmployee(Employee e){
		ensureCapacity();
		employees[numberOfEmployees++] = e;
		employeeIds.add(e.getId());
	}

	public boolean deleteEmployee(int employeeId){
		int employeePosition = getEmployeePosition(employeeId);
		if(employeePosition != -1){
			for(int i = employeePosition; i < numberOfEmployees - 1; i++){
				employees[i] = employees[i + 1];
			}
			employees[numberOfEmployees--] = null;
			employeeIds.remove(employeeId);
			return true;
		}
		return false;
	}
	
	public boolean updateEmployeeAddress(int employeeId, Address address){
		int employeePosition = getEmployeePosition(employeeId);
		if(employeePosition != -1){
			Employee e = employees[employeePosition];
			e.setAddress(address);
			return true;
		}
		return false;
	}

	public boolean updateEmployeePhoneNumber(int employeeId, PhoneNumber ph){
		int employeePosition = getEmployeePosition(employeeId);
		if(employeePosition != -1){
			Employee e = employees[employeePosition];
			e.addPhoneNumbers(ph);
			ph.setOwner(e);
			return true;
		}
		return false;
	}
	
	public boolean updateEmployeeSalary(int employeeId, Double salary){
		int employeePosition = getEmployeePosition(employeeId);
		if(employeePosition != -1){
			Employee e = employees[employeePosition];
			e.setSalary(salary);
			return true;
		}
		return false;
	}
	
	public boolean updateEmployeeEndDate(int employeeId, 
									LocalDate endDate) throws DateNotValidException{
		int employeePosition = getEmployeePosition(employeeId);
		if(employeePosition != -1){
			Employee e = employees[employeePosition];
			e.getEmploymentPeriod().setEndDate(endDate);
			return true;
		}
		return false;
	}
	
	public Employee getEmployeeById(int employeeId){
		int employeePosition = getEmployeePosition(employeeId);
		if(employeePosition != -1){
				return employees[employeePosition];
		}
		return null;
	}
	
	public Employee getEmployeeByPhoneNumber(String number){
		Employee e = null;
		for (int i = 0; i < numberOfEmployees; i++) {
			List<PhoneNumber> ph = employees[i].getPhoneNumbers();
			for(PhoneNumber phoneNumber : ph){
				if(phoneNumber.getNumber().equals(number)){
					return employees[i];
				}
			}
			
		}
		return e;
	}
	
	public List<PhoneNumber> getEmployeePhoneNumber(int employeeId){
		int employeePosition = getEmployeePosition(employeeId);
		if(employeePosition != -1){
				return employees[employeePosition].getPhoneNumbers();
		}
		return null;
	}
	
	public Address getEmployeeAddress(int employeeId){
		int employeePosition = getEmployeePosition(employeeId);
		if(employeePosition != -1){
				return employees[employeePosition].getAddress();
		}
		return null;
	}
	
	public Gender getEmployeeGender(int employeeId){
		int employeePosition = getEmployeePosition(employeeId);
		if(employeePosition != -1){
				return employees[employeePosition].getGender();
		}
		return null;
	}
	
	public EmploymentPeriod getEmployeeEmploymentPeriod(int employeeId){
		int employeePosition = getEmployeePosition(employeeId);
		if(employeePosition != -1){
				return employees[employeePosition].getEmploymentPeriod();
		}
		return null;
	}

	private void ensureCapacity() {
		int tempNumber = employees.length;
		if(numberOfEmployees == tempNumber){
			employees = Arrays.copyOf(employees, tempNumber * 2);
		}
		
	}
	
	public Employee[] getEmployees() {
		return employees;
	}

	public int getNumberOfEmployees() {
		return numberOfEmployees;
	}
	
	public List<Integer> getEmployeeIds() {
		return employeeIds;
	}
}
