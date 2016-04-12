package custom.selenium;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class OpenBrowser
{
	private WebDriver webDriver=null;

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: OpenBrowser Browser(firefox, chrome, internetexplorer or htmlunit)");
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
				String browser=commandParameters[0].trim();
				String osName = System.getProperty("os.name");
				boolean isWin = osName.startsWith("Windows");
				if("firefox".equalsIgnoreCase(browser))
				{
					webDriver=new FirefoxDriver();
					return true;
				}
				else if("htmlunit".equalsIgnoreCase(browser))
				{
					webDriver=new HtmlUnitDriver();
					return true;
				}
				else if("chrome".equalsIgnoreCase(browser))
				{
					String driverName=getSeleniumDriverName();
					if(driverName!=null)
					{
						File driverFile=new File(getPath(), driverName);
						String driverPath=driverFile.getAbsolutePath();
						System.setProperty("webdriver.chrome.driver", driverPath);
						webDriver=new ChromeDriver();
						return true;
					}
				}
				else if("internetexplorer".equalsIgnoreCase(browser) && isWin)
				{
					File driverFile=new File(getPath(), "bin/IEDriverServer.exe");
					String driverPath=driverFile.getAbsolutePath();
					System.setProperty("webdriver.ie.driver", driverPath);
					webDriver=new InternetExplorerDriver();
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
	
	private String getSeleniumDriverName()
	{
		String osName = System.getProperty("os.name");
		boolean isWin = osName.startsWith("Windows");
		boolean isOSX = osName.startsWith("Mac");
		boolean isLinux = osName.indexOf("nux")>=0;
		if(isWin)
		{
			return "bin/chromedriver.exe";
		}
		else if(isOSX)
		{
			return "bin/chromedriver_mac";
		}
		else if(isLinux)
		{
			return "bin/chromedriver_linux";
		}
		else
		{
			return null;
		}
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		Object[] options = { "Chrome", "Firefox", "InternetExplorer", "htmlunit" };
		String text = (String) JOptionPane.showInputDialog(null, "Mode:", "Select browser to start", JOptionPane.PLAIN_MESSAGE, null, options, "chrome");
		if (text != null)
		{
			return "OpenBrowser \"" + text + "\"";
		}
		else
		{
			return null;
		}
	}

	public String getHelp()
	{
		return "http://jautomate.com/2014/12/23/openbrowser";
	}

	public String getTooltip()
	{
		return "Opens a browser window";
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}

	public WebDriver getWebDriver()
	{
		return webDriver;
	}

	private String getPath()
	{
		String path = OpenBrowser.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = path;
		try
		{
			decodedPath = URLDecoder.decode(path, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			return null;
		}

		String absolutePath = decodedPath.substring(0, decodedPath.lastIndexOf("/")) + "/";
		return absolutePath;
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
