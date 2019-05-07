package xpug.kata.birthday_greetings;

public class GreetingsMessage {

		private final String subject;
		private final String body;
		private final String recipient;
		

		public GreetingsMessage(String subject, String body, String recipient) {
			super();
			this.subject = subject;
			this.body = body;
			this.recipient = recipient;
		}

		public GreetingsMessage(Employee employee) {
			this.recipient = employee.getEmail();
			this.subject = "Happy Birthday!";
			this.body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.getFirstName());
		}
		
		public final String getSubject() {
			return subject;
		}

		public final String getBody() {
			return body;
		}

		public final String getRecipient() {
			return recipient;
		}
		
		@Override
		public String toString() {
			return "GreetingsMessage [subject=" + subject + ", body=" + body
					+ ", recipient=" + recipient + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((body == null) ? 0 : body.hashCode());
			result = prime * result
					+ ((recipient == null) ? 0 : recipient.hashCode());
			result = prime * result + ((subject == null) ? 0 : subject.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GreetingsMessage other = (GreetingsMessage) obj;
			if (body == null) {
				if (other.body != null)
					return false;
			} else if (!body.equals(other.body))
				return false;
			if (recipient == null) {
				if (other.recipient != null)
					return false;
			} else if (!recipient.equals(other.recipient))
				return false;
			if (subject == null) {
				if (other.subject != null)
					return false;
			} else if (!subject.equals(other.subject))
				return false;
			return true;
		}

}
