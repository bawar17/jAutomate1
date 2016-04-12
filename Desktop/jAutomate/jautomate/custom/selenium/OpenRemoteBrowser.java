package custom.selenium;

import java.net.URL;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class OpenRemoteBrowser
{
	private WebDriver webDriver=null;

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<2)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: OpenRemoteBrowser URL Browser(firefox, chrome, internetexplorer or htmlunit)");
			return false;
		}

		if(isBrowserOpen())
		{
			// A browser is already open
			return true;
		}
		else
		{
			// Open a new browser
			try
			{
				String url=commandParameters[0].trim();
				String browser=commandParameters[1].trim();
				String osName = System.getProperty("os.name");
				boolean isWin = osName.startsWith("Windows");
				if("firefox".equalsIgnoreCase(browser))
				{
					webDriver = new RemoteWebDriver(new URL(url), DesiredCapabilities.firefox());
					return true;
				}
				else if("htmlunit".equalsIgnoreCase(browser))
				{
					webDriver = new RemoteWebDriver(new URL(url), DesiredCapabilities.htmlUnit());
					return true;
				}
				else if("chrome".equalsIgnoreCase(browser))
				{
					webDriver = new RemoteWebDriver(new URL(url), DesiredCapabilities.chrome());
					return true;
				}
				else if("internetexplorer".equalsIgnoreCase(browser) && isWin)
				{
					webDriver = new RemoteWebDriver(new URL(url), DesiredCapabilities.internetExplorer());
					return true;
				}
				scriptParameters.put("Error", "Unsupported browser - use firefox, chrome, internetexplorer or htmlunit");
				return false;
			}
			catch(Exception e)
			{
				scriptParameters.put("Error", "Exception: "+e.toString());
				return false;
			}
		}
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter URL", "http://www.");
		if (text != null)
		{
			Object[] options = { "Chrome", "Firefox", "InternetExplorer", "htmlunit" };
			String browser = (String) JOptionPane.showInputDialog(null, "Mode:", "Select browser to start", JOptionPane.PLAIN_MESSAGE, null, options, "chrome");
			if (browser != null)
			{
				return "OpenRemoteBrowser \"" + addProtocol(text) + "\" \"" + browser + "\"";
			}
		}
		return null;
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
		return "http://jautomate.com/2016/01/26/openremotebrowser/";
	}

	public String getTooltip()
	{
		return "Opens a remote browser";
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}

	public WebDriver getWebDriver()
	{
		return webDriver;
	}

	private boolean isBrowserOpen()
	{
		if(webDriver==null)
		{
			return false;
		}
		try
		{
			webDriver.getCurrentUrl();
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
}
