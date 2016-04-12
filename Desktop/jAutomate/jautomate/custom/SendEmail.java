package custom;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Sends an email from a JAutomate script
 * @author Jörgen Damberg, Claremont
 */
public class SendEmail
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<8)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: SendEmail Host Port User Password FromAddress ToAddress Subject Message [CCAddress]");
			return false;
		}

		String host=commandParameters[0];
		String port=commandParameters[1];
		final String user=commandParameters[2];
		final String pw=commandParameters[3];
		String fromAddress=commandParameters[4];
		String toAddress=commandParameters[5];
		String subject = commandParameters[6];
		String message = commandParameters[7];
		String ccAddress=null;
		if(commandParameters.length>8)
		{
			ccAddress=commandParameters[8];
		}

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(user, pw);
			}
		});

		try
		{
			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(fromAddress));
			mimeMessage.setContent(message, "text/html");
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
			if(ccAddress!=null)
			{
				mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccAddress));
			}
			mimeMessage.setSubject(subject);
			mimeMessage.setSentDate(new Date());
			Transport.send(mimeMessage);
		}
		catch (Exception e2)
		{
			scriptParameters.put("Error", "Falied to send message: "+e2.toString());
			return false;
		}
		return true;
	}

	public String getHelp()
	{
		return "http://jautomate.com/2014/04/16/sendemail";
	}

	public String getTooltip()
	{
		return "Sends an email";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}
}
