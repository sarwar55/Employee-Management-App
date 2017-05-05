package org.sarwar.imcs.class5.collection.util;

import java.util.List;

import org.sarwar.imcs.class5.application.message.Message;
import org.sarwar.imcs.class5.collection.dto.Address;
import org.sarwar.imcs.class5.collection.dto.Employee;
import org.sarwar.imcs.class5.collection.dto.EmploymentPeriod;
import org.sarwar.imcs.class5.collection.dto.Gender;
import org.sarwar.imcs.class5.collection.dto.PhoneNumber;

public class EmployeeUtil {
	public static Employee createEmployee(List<Integer> employeeIds) 
			throws DateNotValidException{
		Employee e = null;
		int employeeId = UserInputUtil.getIntegerFromUser(Message.ID_MSG);
		if(employeeIds.contains(employeeId)){
			System.out.println(Message.ERROR_ID_MSG);
			createEmployee(employeeIds);
		}
		String employeeName = UserInputUtil.getStringFromUser(Message.FULL_NAME_MSG);
		Gender gender = GenderUtil.createGender();
		Address address= AddressUtil.createAddress();
		Double salary = UserInputUtil.getDoubleFromUser(Message.SALARY_MSG);
		EmploymentPeriod employmentPeriod = EmploymentPeriodUtil.createEmploymentPeriod();
		e = new Employee(employeeId, employeeName, gender, 
				address, salary, employmentPeriod);
		addPhoneNumber(e);
		System.out.println("You successfully Added Employee " + employeeId + 
				" to the database");
		return e;
	}
	public static void addPhoneNumber(Employee e) {
			int operation = UserInputUtil.getIntegerFromUser(Message.PHONE_NUMBER_MSG);
			switch (operation) {
			case 1:
				PhoneNumber phoneNumber= PhoneNumberUtil.createPhoneNumber();
				e.addPhoneNumbers(phoneNumber);
				phoneNumber.setOwner(e);
				addPhoneNumber(e);
				break;
			default:
				System.out.println(Message.INVALID_MSG);
				addPhoneNumber(e);
				break;
			case 2:
				break;
		}
	}
}
