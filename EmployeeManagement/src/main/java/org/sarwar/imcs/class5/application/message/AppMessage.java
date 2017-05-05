package org.sarwar.imcs.class5.application.message;

public final class AppMessage {
	public static final String WELCOME_MSG = "Welcome to our Employee Management App!! ";
	public static final String OPTIONS = "Which Operations you Want to perform!!"
			+ "\nPRESS 1: To Perform Array Operations."
			+ "\nPRESS 2: To Perform Collections Operations."
			+ "\nPRESS 3: To Perform DB Operations."
			+ "\nPRESS 4: To EXIT";
			
	public static final String MENU_FOR_OPERATION = "\nPlease Press: \n1: To CREATE Employee."
			+ "\n2: To READ Employee Information." 
			+ "\n3: To UPDATE Employee Information."
			+ "\n4: To DELETE Employee." + "\n5: To EXIT.";
	public static final String EMPLOYEE_CREATION_WELCOME_MSG = "\nYou are "
			+ "now in the Employee Creation Module.";
	public static final String READ_WELCOME_MSG = "\nPlease Press: "
			+ "\n1: To Print Complete Employee Information."
			+ "\n2: To Print Employee Information by providing Employee Phone Number." 
			+ "\n3: To Print Employee Address."
			+ "\n4: To Print Employee Phone Numbers."
			+ "\n5: To Print Employee Gender."
			+ "\n6: To Print Employee Salary.";
	public static final String EXTENDED_READ_MSG = "\n7: Get all Unique Employee Names."
			+ "\n8: Get Employees By Sorted Names"
			+ "\n9: Get Employees By Sorted Salary."
			+ "\n10: To EXIT.";
	public static final String EMPTY_DATABASE_MSG = "You Have no Employee in the database."
			+ " Please Add Employee to the database first!!";
	public static final String EMPLOYEE_NOT_FOUND = "Employee Not Found!!";
	public static final String UPDATE_WELCOME_MSG ="\nPlease Press: "
			+ "\n1: To UPDATE Complete Employee Information."
			+ "\n2: To UPDATE Employee Address." 
			+ "\n3: To UPDATE Employee Phone Number."
			+ "\n4: To UPDATE Employee Salary."
			+ "\n5: To UPDATE Employee End Date."
			+ "\n6: To EXIT.";

}
