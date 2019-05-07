package xpug.kata.birthday_greetings;

import static spark.Spark.*;

import java.util.Iterator;

import org.junit.Before;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

import spark.Request;
import spark.Route;

public class Main {
	public static void main(String[] args) {
		get("/", (request, response) -> sendGreetingsView());
		post("/sendGreetings", (request, response) -> {
			SimpleSmtpServer server;
			server = SimpleSmtpServer.start(1081);
			String from = request.queryParams("from");
			String subject = request.queryParams("subject");
			String body = request.queryParams("body");
			GreetingsMessage message = new GreetingsMessage(subject, body, from);
			BirthdayService service = new BirthdayService(null, null);
			EmailService mail = new SMTPMailService("localhost", 1081);
			service.sendGreetings(new OurDate("2008/10/08"));
			server.stop();
		});
	}



	private static String sendGreetingsView() {
		return "<html>"
				+ "<body>"
				+ 	 "<form method='post' action='/sendGreetings'>" 
				+	 	"<div><label>From:</label>"
				+ 		"<input type='text' name='from'></div>"
				+	 	"<div><label>Subject:</label>"
				+ 		"<input type='text' name='subject'></div>"
				+	 	"<div><label>Body:</label>"
				+ 		"<input type='text' name='body'></div>"
				+ 		"<div><input type='submit' value='Send To All'></div>"
				+		"</form>"
				+ "</body>"
				+ "</html>";
	}
}
