package org.sarwar.imcs.class5.collection.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.sarwar.imcs.class5.collection.dto.Address;
import org.sarwar.imcs.class5.collection.dto.Employee;
import org.sarwar.imcs.class5.collection.dto.EmploymentPeriod;
import org.sarwar.imcs.class5.collection.dto.Gender;
import org.sarwar.imcs.class5.collection.dto.PhoneNumber;
import org.sarwar.imcs.class5.collection.util.DateNotValidException;

public class EmployeeJDBCOperations implements EmployeeCollectionInterface {
	private static final String URL = "jdbc:mysql://localhost:" 
				+ "3306/employeedb?autoReconnect=true&useSSL=false";
	private volatile static EmployeeJDBCOperations JDBC_INSTANCE;
	private List<Integer> employeeIds;

	private EmployeeJDBCOperations() throws SQLException {
		employeeIds = new ArrayList<>();
		final String SQL = "SELECT empId from employee";
		try (Connection connection = DriverManager.getConnection(URL, "root", "root");
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SQL);) {
			while (resultSet.next()) {
				int employeeId = resultSet.getInt("empId");
				employeeIds.add(employeeId);
			}
			System.out.println("Your Employee DataBase is Ready For Operation.");
			System.out.println("You Have " + employeeIds.size() + " Employees "
					+ "in your DataBase!!");
		}
	}

	public static EmployeeJDBCOperations getInstance() throws SQLException {
		if (JDBC_INSTANCE == null) {
			synchronized (EmployeeJDBCOperations.class) {
				// double checking Singleton instance
				if (JDBC_INSTANCE == null) {
					JDBC_INSTANCE = new EmployeeJDBCOperations();
				}
			}
		}
		return JDBC_INSTANCE;
	}

	@Override
	public void addEmployee(Employee e) throws SQLException {
		try (Connection connection = DriverManager.getConnection(URL, "root", "root")) {
			connection.setAutoCommit(false);
			insertEmployee(connection, e);
			insertPhoneNumber(connection, e);
			insertAddress(connection, e);
			insertEmploymentPeriod(connection, e);
			connection.commit();
		}
	}

	private void insertEmployee(Connection connection, Employee e) 
						throws SQLException {
		final String UPATE_STRING = "INSERT INTO employee (empId, name, gender, salary)" 
								+ " VALUES (?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int employeeId = e.getId();
		String name = e.getName();
		Gender gender = e.getGender();
		double salary = e.getSalary();
		try {
			preparedStatement = connection.prepareStatement(UPATE_STRING);
			preparedStatement.setInt(1, employeeId);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, gender.toString());
			preparedStatement.setDouble(4, salary);
			preparedStatement.executeUpdate();
			employeeIds.add(employeeId);
		} finally{
			preparedStatement.close();
		}
	}

	private void insertEmploymentPeriod(Connection connection, Employee e) 
								throws SQLException {
		System.out.println(e.getEmploymentPeriod());
		final String UPATE_STRING = "INSERT INTO employmentperiod (empId, startDate, endDate, isActive)" 
				+ " VALUES (?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int employeeId = e.getId();
		LocalDate startDate = e.getEmploymentPeriod().getStartDate();
		LocalDate endDate = e.getEmploymentPeriod().getEndDate();
		int isActive = -1;
		if(e.getEmploymentPeriod().isActive()){
			isActive = 1;
		} else isActive = 0;
		try {
			preparedStatement = connection.prepareStatement(UPATE_STRING);
			preparedStatement.setInt(1, employeeId);
			preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
			if(endDate != null) 
				preparedStatement.setDate(3, java.sql.Date.valueOf(endDate));
			else preparedStatement.setDate(3, null);
			preparedStatement.setInt(4, isActive);
			preparedStatement.executeUpdate();
		} finally{
			preparedStatement.close();
		}
	}

	private void insertAddress(Connection connection, Employee e) 
					throws SQLException {
		final String UPATE_STRING = "INSERT INTO address (empId, city, country, postalcode)" 
								+ " VALUES (?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int employeeId = e.getId();
		String city = e.getAddress().getCity();
		String country = e.getAddress().getCountry();
		int postal = e.getAddress().getPostalCode();
		try {
			preparedStatement = connection.prepareStatement(UPATE_STRING);
			preparedStatement.setInt(1, employeeId);
			preparedStatement.setString(2, city);
			preparedStatement.setString(3, country);
			preparedStatement.setInt(4, postal);
			preparedStatement.executeUpdate();
		} finally{
			preparedStatement.close();
		}
	}

	private void insertPhoneNumber(Connection connection, Employee e) 
							throws SQLException {
		final String UPATE_STRING = "INSERT INTO phonenumber (empId, areaCode, number)" 
				+ " VALUES (?,?,?)";
		int size = e.getPhoneNumbers().size();
		int employeeId = e.getId();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(UPATE_STRING);
			for (int i = 0; i < size; i++) {
				String areaCode = e.getPhoneNumbers().get(i).getAreaCode();
				String number = e.getPhoneNumbers().get(i).getNumber();
				preparedStatement.setInt(1, employeeId);
				preparedStatement.setString(2, areaCode);
				preparedStatement.setString(3, number);
				preparedStatement.executeUpdate();
			}
		} finally{
			preparedStatement.close();
		}

	}

	@Override
	public boolean deleteEmployee(int employeeId) throws SQLException {
		if(!employeeIds.contains(employeeId)) return false;
		final String DELETE_SQL = "DELETE employee WHERE empId = ?";
		try (Connection connection = 
				DriverManager.getConnection(URL, "root", "root");
				PreparedStatement preparedStatement = 
				connection.prepareStatement(DELETE_SQL)) {
			preparedStatement.setInt(1, employeeId);
			preparedStatement.executeUpdate();
			connection.commit();
			return true;
		}
	}

	@Override
	public boolean updateEmployeeAddress(int employeeId, Address address) 
							throws SQLException {
		if(!employeeIds.contains(employeeId)) return false;
		final String UPATE_STRING = "UPDATE address SET  city =?, country = ?,"
				+ " postalcode = ? WHERE empId = ?";
		String city = address.getCity();
		String country = address.getCountry();
		int postal = address.getPostalCode();
		try (Connection connection = 
				DriverManager.getConnection(URL, "root", "root");
				PreparedStatement preparedStatement = 
						connection.prepareStatement(UPATE_STRING)) {
			preparedStatement.setString(1, city);
			preparedStatement.setString(2, country);
			preparedStatement.setInt(3, postal);
			preparedStatement.setInt(4, employeeId);
			preparedStatement.executeUpdate();
			connection.commit();
			return true;
		}
	}

	@Override
	public boolean updateEmployeePhoneNumber(int employeeId, PhoneNumber ph) 
							throws SQLException {
		if(!employeeIds.contains(employeeId)) return false;
		final String UPATE_STRING = "UPDATE phonenumber SET  areaCode =?, number = ?"
				+ " WHERE empId = ?";
		String areaCode = ph.getAreaCode();
		String number = ph.getNumber();
		try (Connection connection = 
				DriverManager.getConnection(URL, "root", "root");
				PreparedStatement preparedStatement = 
						connection.prepareStatement(UPATE_STRING)) {
			preparedStatement.setString(1, areaCode);
			preparedStatement.setString(2, number);
			preparedStatement.setInt(3, employeeId);
			preparedStatement.executeUpdate();
			connection.commit();
			return true;
		}
	}

	@Override
	public boolean updateEmployeeSalary(int employeeId, Double salary) 
						throws SQLException {
		if(!employeeIds.contains(employeeId)) return false;
		final String UPATE_STRING = "UPDATE employee SET salary =?"
				+ " WHERE empId = ?";
		try (Connection connection = 
				DriverManager.getConnection(URL, "root", "root");
				PreparedStatement preparedStatement = 
						connection.prepareStatement(UPATE_STRING)){
			preparedStatement.setDouble(1, salary);
			preparedStatement.setInt(2, employeeId);
			preparedStatement.executeUpdate();
			connection.commit();
			return true;
		}
	}

	@Override
	public boolean updateEmployeeEndDate(int employeeId, LocalDate endDate) 
			throws DateNotValidException, SQLException {
		if(!employeeIds.contains(employeeId)) return false;
		final String UPATE_STRING = "UPDATE employmentperiod SET endDate = ?, "
				+ "isactive = ? WHERE empId = ?)";
		int isActive = -1;
		if(endDate.isAfter(LocalDate.now())) isActive = 1;
		else isActive = 0;
		try (Connection connection = 
				DriverManager.getConnection(URL, "root", "root");
				PreparedStatement preparedStatement = 
						connection.prepareStatement(UPATE_STRING)){
			preparedStatement.setDate(1, java.sql.Date.valueOf(endDate));
			preparedStatement.setInt(2, isActive);
			preparedStatement.setInt(3, employeeId);
			preparedStatement.executeUpdate();
			connection.commit();
			return true;
		}
	}

	@Override
	public Employee getEmployeeById(int employeeId) 
								throws SQLException, DateNotValidException {
		if (!employeeIds.contains(employeeId))
			return null;
		String name = null;
		Double salary = null;
		final String SQL = "SELECT * from employee WHERE empId = ?";
		List<PhoneNumber> phoneNumbers = getEmployeePhoneNumber(employeeId);
		Address address = getEmployeeAddress(employeeId);
		EmploymentPeriod employmentPeriod = getEmployeeEmploymentPeriod(employeeId);
		Gender gender = getEmployeeGender(employeeId);
		try (Connection connection = DriverManager.getConnection(URL, "root", "root");
				PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
			preparedStatement.setInt(1, employeeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				name = resultSet.getString("name");
				salary = resultSet.getDouble("salary");
			}
		}
		Employee e = new Employee(employeeId, name, gender, address, salary, employmentPeriod);
		for (PhoneNumber phoneNumber : phoneNumbers) {
			e.addPhoneNumbers(phoneNumber);
			phoneNumber.setOwner(e);
		}
		return e;
	}

	@Override
	public Employee getEmployeeByPhoneNumber(String number) throws SQLException, DateNotValidException {
		if (employeeIds.size() == 0)
			return null;
		final String SQL = "SELECT empId from phonenumber where number = ?";
		try (Connection connection = DriverManager.getConnection(URL, "root", "root");
				PreparedStatement prepareStatement = connection.prepareStatement(SQL);) {
			prepareStatement.setString(1, number);
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				int employeeId = resultSet.getInt("empId");
				return getEmployeeById(employeeId);
			}
		}
		return null;
	}

	@Override
	public List<PhoneNumber> getEmployeePhoneNumber(int employeeId) 
					throws SQLException, DateNotValidException {
		if(!employeeIds.contains(employeeId)) return null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<PhoneNumber> phoneNumbers = new ArrayList<>();
		final String SQL = "SELECT * from phonenumber WHERE empId = ?";
		try (Connection connection = DriverManager.getConnection(URL, "root", "root");) {
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setInt(1, employeeId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String areaCode = resultSet.getString("areaCode");
				String number = resultSet.getString("number");
				PhoneNumber phoneNumber = new PhoneNumber(areaCode, number);
				phoneNumbers.add(phoneNumber);
			}
		}
		return phoneNumbers;
	}

	@Override
	public Address getEmployeeAddress(int employeeId) throws SQLException {
		if(!employeeIds.contains(employeeId)) return null;
		final String SQL = "SELECT * from address WHERE empId = ?";
		try (Connection connection = DriverManager.getConnection(URL, "root", "root");
				PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
			preparedStatement.setInt(1, employeeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String city = resultSet.getString("city");
				String country = resultSet.getString("country");
				int postalCode = resultSet.getInt("postalcode");
				return new Address(city, country, postalCode);
			}
		}
		return null;
	}

	@Override
	public Gender getEmployeeGender(int employeeId) throws SQLException {
		if(!employeeIds.contains(employeeId)) return null;
		final String SQL = "SELECT gender from employee WHERE empId = ?";
		try (Connection connection = DriverManager.getConnection(URL, "root", "root");
				PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
			preparedStatement.setInt(1, employeeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String gender = resultSet.getString("gender");
				if(gender.equals("Male")) return Gender.MALE;
				else return Gender.FEMALE;
			}
		}
		return null;
	}

	@Override
	public EmploymentPeriod getEmployeeEmploymentPeriod(int employeeId) 
			throws SQLException, DateNotValidException {
		if(!employeeIds.contains(employeeId)) return null;
		final String SQL = "SELECT * from employmentperiod WHERE empId = ?";
		try (Connection connection = DriverManager.getConnection(URL, "root", "root");
				PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
			preparedStatement.setInt(1, employeeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
				LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
				return new EmploymentPeriod(startDate, endDate);
			}
		}
		return null;
	}

	@Override
	public int getNumberOfEmployees() {
		return employeeIds.size();
	}

	@Override
	public Employee[] getEmployees() throws SQLException, DateNotValidException {
		if(employeeIds.size() == 0) return null;
		List<Employee> employee = new ArrayList<>();
		final String SQL = "SELECT empId from employee";
		try (Connection connection = DriverManager.getConnection(URL, "root", "root");
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SQL);) {
			while (resultSet.next()) {
				int employeeId = resultSet.getInt("empId");
				Employee e = getEmployeeById(employeeId);
				employee.add(e);
			}
			return employee.toArray(new Employee[employee.size()]);
		}
	}

	@Override
	public List<Integer> getEmployeeIds() {
		return employeeIds;
	}

	@Override
	public Set<String> getUniqueNames() throws SQLException {
		if(employeeIds.size() == 0) return null;
		Set<String> employeeNames = new HashSet<>();
		final String SQL = "SELECT name from employee";
		try (Connection connection = DriverManager.getConnection(URL, "root", "root");
				Statement preparedStatement = connection.createStatement();
				ResultSet resultSet = preparedStatement.executeQuery(SQL);) {
			while (resultSet.next()) {
				employeeNames.add(resultSet.getString("name"));
			}
		}
		return employeeNames;
	}

	@Override
	public List<Employee> getEmployeeBySortedName() 
			throws SQLException, DateNotValidException {
		if(employeeIds.size() == 0) return null;
		List<Employee> employee = new ArrayList<>();
		final String SQL = "SELECT * from employee order by name desc";
		try (Connection connection = DriverManager.getConnection(URL, "root", "root");
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SQL);) {
			while (resultSet.next()) {
				int employeeId = resultSet.getInt("empId");
				Employee e = getEmployeeById(employeeId);
				employee.add(e);
			}
		return employee;
		}
	}

	@Override
	public List<Employee> getEmployeeBySortedSalary() 
			throws SQLException, DateNotValidException {
		if(employeeIds.size() == 0) return null;
		List<Employee> employee = new ArrayList<>();
		final String SQL = "SELECT * from employee order by salary desc";
		try (Connection connection = DriverManager.getConnection(URL, "root", "root");
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SQL);) {
			while (resultSet.next()) {
				int employeeId = resultSet.getInt("empId");
				Employee e = getEmployeeById(employeeId);
				employee.add(e);
			}
		return employee;
		}
	}

}
