package xpug.kata.birthday_greetings;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;


public class AcceptanceTest {

	private BirthdayService service;
	private static final int NONSTANDARD_PORT = 3003;
	SimpleSmtpServer server;
	private GreetingsMessageSender greetingsMessageSender;
	private EmployeeRepository employeeRepository;
	private Iterator emailIterator;

	@Before
	public void setUp() throws Exception {
		
		final int SMTP_PORT = 25;
		final String SENDER = "sender@here.com";
		final String SMTP_HOST = "localhost";

		final String employeesDataFile = "repositories/employee_data.txt";

		greetingsMessageSender = new FakeGreetingsEmailSender(SMTP_HOST, SMTP_PORT, SENDER);

		try {
			employeeRepository = new FileEmployeeRepository(employeesDataFile);
			service = new BirthdayService(greetingsMessageSender,employeeRepository);
		} catch (EmployeesRepositoryAccessException e) {
			
			System.err
					.println("EmployeesRepositoryAccessException creating employeeRepository from '"
							+ employeesDataFile + "'");
			throw e;
		}
	}

	@Test
	public void baseScenario() throws Exception {
		startBirthdayServiceFor("employee_data.txt", "2008/10/08");
		
		expectNumberOfEmailSentIs(1);
		expectEmailWithSubject_andBody_sentTo("Happy Birthday!", "Happy Birthday, dear John!", "john.doe@foobar.com");
	}

	private void expectEmailWithSubject_andBody_sentTo(String subject, String body, String recipient) {
		SmtpMessage message = (SmtpMessage) emailIterator.next();
		assertEquals(body, message.getBody());
		assertEquals(subject, message.getHeaderValue("Subject"));
		assertEquals(recipient, message.getHeaderValue("To"));		
	}

	private void expectNumberOfEmailSentIs(int expected) {
		assertEquals(expected, server.getReceivedEmailSize());
	}

	@SuppressWarnings("unchecked")
	private void startBirthdayServiceFor(String employeeFileName, String date) throws Exception {
		BirthdayService service = new BirthdayService(greetingsMessageSender, employeeRepository);
		EmailService mail = new SMTPMailService("localhost", NONSTANDARD_PORT);
		service.sendGreetings(new OurDate(date));
		emailIterator = server.getReceivedEmail();
	}


}
