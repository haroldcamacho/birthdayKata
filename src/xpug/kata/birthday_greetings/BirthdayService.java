package xpug.kata.birthday_greetings;

import java.util.List;

public class BirthdayService {
	private final GreetingsMessageSender greetingsMessageSender;
	private final EmployeeRepository employeeRepository;

	public BirthdayService(GreetingsMessageSender greetingsMessageSender, EmployeeRepository employeeRepository) {
		this.greetingsMessageSender = greetingsMessageSender;
		this.employeeRepository = employeeRepository;
	}

	public void sendGreetings(OurDate ourDate) {
		List<Employee> employees = employeeRepository.getEmployees();
		sendGreetingsTo(employees, ourDate);
	}

	private void sendGreetingsTo(List<Employee> employees, OurDate ourDate) {
		for (Employee employee : employees) {
			if (employee.isBirthday(ourDate)) {
				GreetingsMessage greetingsMessage = new GreetingsMessage(employee);
				greetingsMessageSender.send(greetingsMessage);
			}
		}
	}

}
