package custom.selenium;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;

public class GetUrl
{
	private WebDriver webDriver=null;

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: GetUrl URL");
			return false;
		}

		String url=commandParameters[0].trim();

		if(webDriver==null)
		{
			scriptParameters.put("Error", "No browser open");
			return false;
		}

		try
		{
			webDriver.get(url);
			return true;
		}
		catch(Exception e)
		{
			scriptParameters.put("Error", "Exception: "+e.toString());
			return false;
		}
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "Response"};
	}

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter URL", "http://www.");
		if (text != null)
		{
			return "GetUrl \"" + addProtocol(text) + "\"";
		}
		else
		{
			return null;
		}
	}

	private static String addProtocol(String text)
	{
		if (text.startsWith("www."))
		{
			// Add protocol
			return "http://" + text;
		}
		else
		{
			return text;
		}
	}

	public String getHelp()
	{
		return "http://jautomate.com/2014/12/23/geturl";
	}

	public String getTooltip()
	{
		return "Opens a website";
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}
}
