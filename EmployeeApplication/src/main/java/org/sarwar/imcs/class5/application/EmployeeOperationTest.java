package org.sarwar.imcs.class5.application;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

import org.sarwar.imcs.class5.application.message.AppMessage;
import org.sarwar.imcs.class5.application.message.Message;
import org.sarwar.imcs.class5.collection.dto.Address;
import org.sarwar.imcs.class5.collection.dto.Employee;
import org.sarwar.imcs.class5.collection.dto.PhoneNumber;
import org.sarwar.imcs.class5.collection.service.EmployeeArrayOperations;
import org.sarwar.imcs.class5.collection.service.EmployeeCollectionInterface;
import org.sarwar.imcs.class5.collection.service.EmployeeCollectionOperations;
import org.sarwar.imcs.class5.collection.service.EmployeeJDBCOperations;
import org.sarwar.imcs.class5.collection.service.EmployeeOperations;
import org.sarwar.imcs.class5.collection.util.AddressUtil;
import org.sarwar.imcs.class5.collection.util.DateNotValidException;
import org.sarwar.imcs.class5.collection.util.EmployeeUtil;
import org.sarwar.imcs.class5.collection.util.PhoneNumberUtil;
import org.sarwar.imcs.class5.collection.util.UserInputUtil;

public class EmployeeOperationTest {
	public static EmployeeOperations empl;

	public static void main(String[] args) throws DateNotValidException, SQLException {
		System.out.println(AppMessage.WELCOME_MSG);
		boolean loopCondition = true;
		while (loopCondition) {
			int operation = UserInputUtil.getIntegerFromUser(AppMessage.OPTIONS);
			switch(operation){
			case 1:
				empl = EmployeeArrayOperations.getInstance();
				doOperations();
				break;
			case 2:
				empl = EmployeeCollectionOperations.getInstance();
				doOperations();
				break;
			case 3:
				empl = EmployeeJDBCOperations.getInstance();
				doOperations();
				break;
			case 4:
				loopCondition = false;
				break;
			default:
				System.out.println(Message.INVALID_MSG);
			}
		}

	}

	public static void doOperations() throws DateNotValidException, SQLException {
		boolean loopCondition = true;
		while (loopCondition) {
			int operation = UserInputUtil.getIntegerFromUser(AppMessage.MENU_FOR_OPERATION);
			switch (operation) {
			case 1:
				System.out.println(AppMessage.EMPLOYEE_CREATION_WELCOME_MSG);
				empl.addEmployee(EmployeeUtil.createEmployee(empl.getEmployeeIds()));
				break;
			case 2:
				readEmployee();
				break;
			case 3:
				updateEmployee();
				break;
			case 4:
				deleteEmployee();
				break;
			case 5:
				loopCondition = false;
				break;
			default:
				System.out.println(Message.INVALID_MSG);
				break;

			}
		}

	}

	private static void deleteEmployee() throws SQLException {
		if (!checkValidity()) {
			return;
		}
		int employeeId = UserInputUtil.getIntegerFromUser(Message.ID_MSG);
		if (empl.deleteEmployee(employeeId)) {
			System.out.println("You have successfully Deleted Employee " + employeeId);
		} else
			System.out.println(AppMessage.EMPLOYEE_NOT_FOUND);

	}

	private static boolean checkValidity() {
		if (empl.getNumberOfEmployees() == 0) {
			System.out.println(AppMessage.EMPTY_DATABASE_MSG);
			return false;
		}
		return true;
	}

	private static void updateEmployee() throws DateNotValidException, SQLException {
		if (!checkValidity()) {
			return;
		}
		boolean loopCondition = true;
		while (loopCondition) {
			int operation = UserInputUtil.getIntegerFromUser(AppMessage.UPDATE_WELCOME_MSG);
			switch (operation) {
			case 1:
				updateEmployeeInformation();
				break;
			case 2:
				updateEmployeeAddress();
				break;
			case 3:
				updateEmployeePhoneNumber();
				break;
			case 4:
				updateEmployeeSalary();
				break;
			case 5:
				updateEmployeeEndDate();
				break;
			case 6:
				loopCondition = false;
				break;
			}
		}
	}

	private static void updateEmployeeEndDate() throws DateNotValidException, SQLException {
		LocalDate endDate = null;
		int employeeId = UserInputUtil.getIntegerFromUser(Message.ID_MSG);
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String endDateInput = UserInputUtil.getStringFromUser(Message.END_DATE_MSG);
		try {
			endDate = LocalDate.parse(endDateInput, dateFormat);
		} catch (DateTimeParseException e) {
			System.out.println(Message.DATE_ERROR_MSG);
			updateEmployeeEndDate();
		}
		if (empl.updateEmployeeEndDate(employeeId, endDate)) {
			System.out.println("You have Successfully Updated Employment Period of Employee " + employeeId);
		} else
			System.out.println(AppMessage.EMPLOYEE_NOT_FOUND);
	}

	private static void updateEmployeeSalary() throws SQLException {
		int employeeId = UserInputUtil.getIntegerFromUser(Message.ID_MSG);
		Double salary = UserInputUtil.getDoubleFromUser(Message.SALARY_MSG);
		if (empl.updateEmployeeSalary(employeeId, salary)) {
			System.out.println("You have Successfully Updated Phone Number of Employee " + employeeId);
		} else
			System.out.println(AppMessage.EMPLOYEE_NOT_FOUND);
	}

	private static void updateEmployeePhoneNumber() throws SQLException {
		int employeeId = UserInputUtil.getIntegerFromUser(Message.ID_MSG);
		PhoneNumber phoneNumber = PhoneNumberUtil.createPhoneNumber();
		if (empl.updateEmployeePhoneNumber(employeeId, phoneNumber)) {
			System.out.println("You have Successfully Upda2" + "ted Phone Number of Employee " + employeeId);
		} else
			System.out.println(AppMessage.EMPLOYEE_NOT_FOUND);

	}

	private static void updateEmployeeAddress() throws SQLException {
		int employeeId = UserInputUtil.getIntegerFromUser(Message.ID_MSG);
		Address address = AddressUtil.createAddress();
		if (empl.updateEmployeeAddress(employeeId, address)) {
			System.out.println("You have Successfully Updated Address of Employee " + employeeId);
		} else
			System.out.println(AppMessage.EMPLOYEE_NOT_FOUND);

	}

	private static void updateEmployeeInformation() throws DateNotValidException, SQLException {
		empl.addEmployee(EmployeeUtil.createEmployee(empl.getEmployeeIds()));
	}

	private static void readEmployee() throws SQLException, DateNotValidException {
		if (!checkValidity()) {
			return;
		}
		boolean loopCondition = true;
		while (loopCondition) {
			int operation = UserInputUtil.getIntegerFromUser(AppMessage.READ_WELCOME_MSG + AppMessage.EXTENDED_READ_MSG);
			switch (operation) {
			case 1:
				printEmployeeInformation();
				break;
			case 2:
				printEmployeeInformationByPhoneNumber();
				break;
			case 3:
				printEmployeeAddress();
				break;
			case 4:
				printEmployeePhoneNumber();
				break;
			case 5:
				printEmployeeGender();
				break;
			case 6:
				printEmployeeSalary();
				break;
			case 7:
				printAllUniqueEmployees();
				break;
			case 8:
				printEmployeesBySortedName();
				break;
			case 9:
				printEmployeesBySortedSalary();
				break;
			default:
				System.out.println(Message.INVALID_MSG);
				break;
			case 10:
				loopCondition = false;
				break;
			}
		}
	}

	private static void printEmployeesBySortedName() throws SQLException, DateNotValidException {
		List<Employee> employees = (List<Employee>)((EmployeeCollectionInterface)empl)
				.getEmployeeBySortedName();
		for(Employee e: employees){
			System.out.println(e);
		}
	}

	private static void printEmployeesBySortedSalary() throws SQLException, DateNotValidException {
		List<Employee> employees = (List<Employee>)((EmployeeCollectionInterface)empl)
				.getEmployeeBySortedSalary();
		for(Employee e: employees){
			System.out.println(e);
		}
		
	}

	private static void printAllUniqueEmployees() throws SQLException {
		Set<String> employeeNames = (Set<String>)((EmployeeCollectionInterface)empl)
				.getUniqueNames();
		for(String name: employeeNames){
			System.out.println(name);
		}
		
	}

	private static Employee getEmployee() throws SQLException, DateNotValidException {
		int employeeId = UserInputUtil.getIntegerFromUser(Message.ID_MSG);
		return empl.getEmployeeById(employeeId);
	}

	private static void printEmployeeSalary() throws SQLException, DateNotValidException {
		Employee e = getEmployee();
		if (e != null) {
			System.out.println(
					"Salary: " + e.getGrossSalary() + "\nGorss Salary: " + e.getGrossSalary() + "\nHRA: " + e.getHRA());
		} else
			System.out.println(AppMessage.EMPLOYEE_NOT_FOUND);

	}

	private static void printEmployeeGender() throws SQLException, DateNotValidException {
		Employee e = getEmployee();
		if (e != null) {
			System.out.println(e.getGender());
		} else
			System.out.println(AppMessage.EMPLOYEE_NOT_FOUND);
	}

	private static void printEmployeePhoneNumber() throws SQLException, DateNotValidException {
		Employee e = getEmployee();
		if (e != null) {
			List<PhoneNumber> phoneNumbers = e.getPhoneNumbers();
			for (PhoneNumber ph : phoneNumbers) {
				System.out.println(ph);
			}
		} else
			System.out.println(AppMessage.EMPLOYEE_NOT_FOUND);
	}

	private static void printEmployeeAddress() throws SQLException, DateNotValidException {
		Employee e = getEmployee();
		if (e != null) {
			System.out.println(e.getAddress());
		} else
			System.out.println(AppMessage.EMPLOYEE_NOT_FOUND);
	}

	private static void printEmployeeInformationByPhoneNumber() throws SQLException, DateNotValidException {
		String number = UserInputUtil.getStringFromUser(Message.PHONE_NUMBER_MSG);
		Employee e = empl.getEmployeeByPhoneNumber(number);
		System.out.println(e);
	}

	private static void printEmployeeInformation() throws SQLException, DateNotValidException {
		Employee e = getEmployee();
		if (e != null) {
			System.out.println(e);
		} else
			System.out.println(AppMessage.EMPLOYEE_NOT_FOUND);
	}

}
