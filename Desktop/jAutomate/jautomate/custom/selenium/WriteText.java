package custom.selenium;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WriteText
{
	private WebDriver webDriver=null;

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: WriteText Text");
			return false;
		}


		String text=commandParameters[0].trim();

		if(webDriver==null)
		{
			scriptParameters.put("Error", "No browser open");
			return false;
		}

		try
		{
			WebElement currentElement = webDriver.switchTo().activeElement();
			if(currentElement!=null)
			{
				currentElement.sendKeys(replaceTags(text));
				return true;
			}
			else
			{
				scriptParameters.put("Error", "No active element");
				return false;
			}
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
		String text = JOptionPane.showInputDialog(null, "Enter text");
		if (text != null)
		{
			return "WriteText \"" + text + "\"";
		}
		else
		{
			return null;
		}
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/12/23/writetext";
	}

	public String getTooltip()
	{
		return "Write a text to the text field in focus";
	}

	public void setWebDriver(WebDriver webDriver)
	{
		this.webDriver=webDriver;
	}

	private String replaceTags(String text)
	{
		text = replaceAllStrings(text, "[ENTER]", Keys.RETURN.toString());
		text = replaceAllStrings(text, "[TAB]", Keys.TAB.toString());
		text = replaceAllStrings(text, "[DELETE]", Keys.DELETE.toString());
		text = replaceAllStrings(text, "[ESCAPE]", Keys.ESCAPE.toString());
		text = replaceAllStrings(text, "[BACKSPACE]", Keys.BACK_SPACE.toString());
		text = replaceAllStrings(text, "[UP]", Keys.UP.toString());
		text = replaceAllStrings(text, "[DOWN]", Keys.DOWN.toString());
		text = replaceAllStrings(text, "[LEFT]", Keys.LEFT.toString());
		text = replaceAllStrings(text, "[RIGHT]", Keys.RIGHT.toString());
		text = replaceAllStrings(text, "[PAGE_UP]", Keys.PAGE_UP.toString());
		text = replaceAllStrings(text, "[PAGE_DOWN]", Keys.PAGE_DOWN.toString());
		text = replaceAllStrings(text, "[HOME]", Keys.HOME.toString());
		text = replaceAllStrings(text, "[END]", Keys.END.toString());
		text = replaceAllStrings(text, "[F1]", Keys.F1.toString());
		text = replaceAllStrings(text, "[F2]", Keys.F2.toString());
		text = replaceAllStrings(text, "[F3]", Keys.F3.toString());
		text = replaceAllStrings(text, "[F4]", Keys.F4.toString());
		text = replaceAllStrings(text, "[F5]", Keys.F5.toString());
		text = replaceAllStrings(text, "[F6]", Keys.F6.toString());
		text = replaceAllStrings(text, "[F7]", Keys.F7.toString());
		text = replaceAllStrings(text, "[F8]", Keys.F8.toString());
		text = replaceAllStrings(text, "[F9]", Keys.F9.toString());
		text = replaceAllStrings(text, "[F10]", Keys.F10.toString());
		text = replaceAllStrings(text, "[F11]", Keys.F11.toString());
		text = replaceAllStrings(text, "[F12]", Keys.F12.toString());
		return text;
	}

	/**
	 * Like replaceString but replaces all occurrences (at most 1000)
	 * @param text
	 * @param from
	 * @param to
	 * @return
	 */
	private static String replaceAllStrings(String text, String from, String to)
	{
		String notReplaced = text;
		for (int i = 0; i < 1000; i++)
		{
			String replaced = replaceString(notReplaced, from, to);
			if (replaced == notReplaced)
			{
				// From was not found - finished
				return replaced;
			}
			notReplaced = replaced;
		}
		return notReplaced;
	}

	/**
	 * Replaces from with to in text (only one)
	 * @param text
	 * @param from
	 * @param to
	 * @return
	 */
	private static String replaceString(String text, String from, String to)
	{
		int fromPos = text.indexOf(from);
		if (fromPos != -1)
		{
			int len = from.length();
			int toPos = fromPos + len - 1;
			return replaceString(text, to, fromPos, toPos);
		}
		// Not found
		return text;
	}

	private static String replaceString(String text, String insert, int start, int end)
	{
		String firstPart = text.substring(0, start);
		String lastPart = "";
		if (text.length() > end + 1)
			lastPart = text.substring(end + 1);
		return firstPart + insert + lastPart;
	}
}
