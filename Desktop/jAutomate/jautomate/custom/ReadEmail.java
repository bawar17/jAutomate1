package custom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMultipart;

/**
 * Reads one new email at a time (except the first time that loads all messages)
 */
public class ReadEmail
{
	private static SimpleDateFormat simpleDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static HashSet<String> messageStore=new HashSet<String>();
	
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<4)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: ReadEmail Host Port User Password");
			return false;
		}

		String host=commandParameters[0];
		String port=commandParameters[1];
		final String user=commandParameters[2];
		final String pw=commandParameters[3];
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
	    javax.mail.Store store = (javax.mail.Store)session.getStore("pop3");
	    store.connect(host, user, pw);
	    Folder inbox = store.getFolder("INBOX");
	    inbox.open(Folder.READ_WRITE);
	 
	    // Get the list of inbox messages
	    Message[] messages = inbox.getMessages();
	    
	    if(messageStore.size()==0)
	    {
	    	// Fill message store
	    	for(Message message:messages)
	    	{
	    		Date sentDate=message.getSentDate();
	    		String dateString = simpleDateTime.format(sentDate);
	    		messageStore.add(dateString);
	    	}
  			scriptParameters.put("Information", "Messages stored");
	    	return true;
	    }

    	for(Message message:messages)
    	{
    		String dateString = simpleDateTime.format(message.getSentDate());
    		if(!messageStore.contains(dateString))
    		{
    			try
    			{
      			// New email
  	    		messageStore.add(dateString);
    				scriptParameters.put("EmailSubject", message.getSubject());
    				scriptParameters.put("EmailContent", getContent(message));
    		    inbox.close(true);
    		    store.close();
    				return true;
    			}
    			catch (Exception e3)
    			{
    				scriptParameters.put("Error", "Falied to get message: "+e3.toString());
    		    inbox.close(true);
    		    store.close();
    				return false;
    			}
    		}
    	}
			scriptParameters.put("Information", "No unread emails");
	    inbox.close(true);
	    store.close();
	  }
		catch (Exception e2)
		{
			scriptParameters.put("Error", "Falied to read message: "+e2.toString());
			return false;
		}
		return true;
	}

	public static String getContent(Message message)
	{
		try
		{
			Object content = message.getContent();
			if (content instanceof MimeMultipart)
			{
				MimeMultipart multipart = (MimeMultipart) content;
				if (multipart.getCount() > 0)
				{
					BodyPart part = multipart.getBodyPart(0);
					content = part.getContent();
				}
			}
			if (content != null)
			{
				return content.toString();
			}
		}
		catch (Exception e)
		{
		}
		return null;
	}

	public String getHelp()
	{
		return "http://jautomate.com/2014/09/21/reademail";
	}

	public String getTooltip()
	{
		return "Reads one new email at a time";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "Information", "EmailSubject", "EmailContent"};
	}
}
