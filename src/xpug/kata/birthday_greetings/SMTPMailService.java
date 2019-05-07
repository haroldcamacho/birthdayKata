package xpug.kata.birthday_greetings;

import static java.util.Arrays.asList;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SMTPMailService implements EmailService {

	private String smtpHost;
	private int smtpPort;

	public SMTPMailService(String smtpHost, int smtpPort) {
		this.smtpHost = smtpHost;
		// TODO Auto-generated constructor stub
		this.smtpPort = smtpPort;
	}
	
	/* (non-Javadoc)
	 * @see xpug.kata.birthday_greetings.EmailService#sendMessage(java.lang.String, xpug.kata.birthday_greetings.Employee)
	 */
	@Override
	public void sendMessage(String sender, Employee employee)
			throws AddressException, MessagingException {
		
		String receiver = employee.getEmail();
		String body = "Happy Birthday, dear %NAME%!".replace("%NAME%",
				employee.getFirstName());
		String subject = "Happy Birthday!";
		
		System.out.println("Email sent to: " + asList(sender, subject, body, receiver));

		// Create a mail session
		java.util.Properties props = new java.util.Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", "" + smtpPort);
		Session session = Session.getDefaultInstance(props, null);

		// Construct the message
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(sender));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
				receiver));
		msg.setSubject(subject);
		msg.setText(body);

		// Send the message
		Transport.send(msg);
	}


}
