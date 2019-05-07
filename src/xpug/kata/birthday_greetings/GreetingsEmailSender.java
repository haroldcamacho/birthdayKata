package xpug.kata.birthday_greetings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GreetingsEmailSender implements GreetingsMessageSender {
	private final String smtpHost; 
	private final int smtpPort;
	private final String sender;
	
	public GreetingsEmailSender(String smtpHost, int smtpPort, String sender) {
		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
		this.sender = sender;
	}

	public void send(GreetingsMessage greetingsMessage) 
	{
		Message msgReadyToSend = prepareMessageReadyToSend(greetingsMessage);
		
		sendMessage(msgReadyToSend);
	}

	// made protected for testing :-(
	protected void sendMessage(Message msg) {
		try {
			Transport.send(msg);
		} catch (MessagingException messagingException) {
			throw new GreetingsMessageSenderException(GreetingsMessageSenderException.SENDING_PROBLEM);
		}
	}
	
	protected Message prepareMessageReadyToSend(GreetingsMessage greetingsMessage){
		
		Session session = createMailSession();
		
		InternetAddress address = createInternetAddress(greetingsMessage.getRecipient());
		
		Message msg = constructMessage(greetingsMessage, session, address);
		
		return msg;
		
	}
	
	private Message constructMessage(GreetingsMessage greetingsMessage, Session session, InternetAddress address) {
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(sender));
			msg.setRecipient(Message.RecipientType.TO, address);
			msg.setSubject(greetingsMessage.getSubject());
			msg.setText(greetingsMessage.getBody());
			return msg;
		} catch (MessagingException messagingException) {
			throw new GreetingsMessageSenderException(GreetingsMessageSenderException.CREATION_PROBLEM);
		}
	}
	
	private InternetAddress createInternetAddress(String recipient)
	{
		try {
			return new InternetAddress(recipient);
		} catch (AddressException addressException) {
			throw new GreetingsMessageSenderException(GreetingsMessageSenderException.ADDRESS_PROBLEM);
		}
	}
	
	private Session createMailSession()
	{
		java.util.Properties props = new java.util.Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", "" + smtpPort);
		Session session = Session.getDefaultInstance(props, null);
		return session;
	}
}
