package xpug.kata.birthday_greetings;

import java.util.List;
import java.util.ArrayList;

import javax.mail.Message;


public class FakeGreetingsEmailSender extends GreetingsEmailSender{
	private List<Message> messagesSent;
	private List<GreetingsMessage> greetingsMessagesSent;
	
	


	public FakeGreetingsEmailSender(String smtpHost, int smtpPort,
			String sender) {
		super(smtpHost, smtpPort, sender);
		this.messagesSent = new ArrayList<Message>();
		this.greetingsMessagesSent = new ArrayList<GreetingsMessage>();
	}
	
	public void send(GreetingsMessage greetingsMessage) 
	{		
		greetingsMessagesSent.add(greetingsMessage);
		messagesSent.add(this.prepareMessageReadyToSend(greetingsMessage));
	}
	protected Message prepareMessageReadyToSend(GreetingsMessage greetingsMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<GreetingsMessage> getGreetingsMessagesSent() {
		return greetingsMessagesSent;
	}
}
