package xpug.kata.birthday_greetings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FileEmployeeRepository implements EmployeeRepository {

	private final String employeesFilePath;

	public FileEmployeeRepository(String employeesFilePath) {
		if (filePathExists(employeesFilePath)) {
			this.employeesFilePath = employeesFilePath;
		} else
			throw new EmployeesRepositoryAccessException();
	}

	private boolean filePathExists(String employeesFilePath) {
		File f = new File(employeesFilePath);
		return f.exists();
	}

	public List<Employee> getEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(employeesFilePath));
			String line = in.readLine(); // skip header

			while ((line = in.readLine()) != null) {
				Employee employee = obtainEmployee(line);
				employees.add(employee);
			}
		} catch (IOException ioException) {
			throw new EmployeesRepositoryAccessException();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				throw new EmployeesRepositoryAccessException();
			}
		}

		return employees;
	}
	
	private Employee obtainEmployee(String line) {
		try {
			String[] employeeData = line.split(", ");
			return new Employee(employeeData[1], employeeData[0],
					employeeData[2], employeeData[3]);
		} catch (ParseException parseException) {
			throw new EmployeesRepositoryAccessException();
		}
	}

}
