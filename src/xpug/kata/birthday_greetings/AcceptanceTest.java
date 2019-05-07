package xpug.kata.birthday_greetings;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;


public class AcceptanceTest {

	private BirthdayService service;
	private GreetingsMessageSender greetingsMessageSender;
	private EmployeeRepository employeeRepository;

	@Before
	public void setUp() throws Exception {
		
		final int SMTP_PORT = 25;
		final String SENDER = "sender@here.com";
		final String SMTP_HOST = "localhost";

		final String employeesDataFile = "repositories/employee_data.txt";

		greetingsMessageSender = new FakeGreetingsEmailSender(SMTP_HOST,
				SMTP_PORT, SENDER);

		try {
			employeeRepository = new FileEmployeeRepository(employeesDataFile);
			service = new BirthdayService(greetingsMessageSender,employeeRepository);
		} catch (EmployeesRepositoryAccessException e) {
			/* Esto lo puse para comprobar porque estaba fallando la lectura desde el fichero
			 * repositorio de empleados cuando comence las pruebas con Maven. Quizas mejor quitarlo en un test
			 */
			System.err
					.println("EmployeesRepositoryAccessException creating employeeRepository from '"
							+ employeesDataFile + "'");
			throw e;
		}
	}

	@Test
	public void baseScenario() throws Exception {
		OurDate baseDate = new OurDate("2012/10/08");
		List<GreetingsMessage> greetingMessagesSent;
		
		service.sendGreetings(baseDate);

		greetingMessagesSent = ((FakeGreetingsEmailSender) greetingsMessageSender)
				.getGreetingsMessagesSent();
		
		
		/* He cambiado la forma de hacer el test:
		 * Ahora en lugar de comprobar datos dede Message (javax.mail), comprobamos la info
		 * basandonos en un GreetingMessage que es parte de nuestro core 
		 */

		assertEquals("message not sent?", 1, greetingMessagesSent.size());
		GreetingsMessage greetingMessage = greetingMessagesSent.get(0);

		assertEquals("Happy Birthday!", greetingMessage.getSubject());
		assertEquals("Happy Birthday, dear John!", greetingMessage.getBody());
		assertEquals("john.doe@foobar.com", greetingMessage.getRecipient());

	}

	@Test
	public void willNotSendEmailsWhenNobodysBirthday() throws Exception {
		OurDate nobodysBirthdayDate = new OurDate("2008/01/01");
		List<GreetingsMessage> greetingsMessagesSent;
		int numberSendedGreetingMessages;
		int expectedNumberSendedGreetingMessages = 0;
		
		
		service.sendGreetings(nobodysBirthdayDate);
		greetingsMessagesSent = ((FakeGreetingsEmailSender) greetingsMessageSender)
				.getGreetingsMessagesSent();
		numberSendedGreetingMessages = greetingsMessagesSent.size();

		assertEquals("what? messages?", expectedNumberSendedGreetingMessages, numberSendedGreetingMessages);
	}
	

}
