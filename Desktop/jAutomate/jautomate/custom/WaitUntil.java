package custom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JOptionPane;

public class WaitUntil
{
	private static SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm:ss");

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: WaitUntil Time");
			return false;
		}

		String timeString=commandParameters[0];
		
		while(true)
		{
			String currentTimeString = simpleTime.format(new Date());
			if(timeString.equals(currentTimeString))
			{
				return true;
			}
			try
			{
				Thread.sleep(900);
			}
			catch (InterruptedException e)
			{
				return false;
			}
		}
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/08/08/waituntil";
	}

	public String getTooltip()
	{
		return "Waits until a certain time";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter time using the format: HH:mm:ss");
		if (text != null)
		{
			return "WaitUntil " + text;
		}
		return null;
	}
}
