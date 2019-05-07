package xpug.kata.birthday_greetings;

import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
