package custom;

import java.util.Properties;

import javax.swing.JOptionPane;

public class Round
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<2)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: Round Value NoDecimals");
			return false;
		}

		String param=commandParameters[0];
		String noDecimals=commandParameters[1];
		String result=String.format("%."+noDecimals+"f", Double.parseDouble(param));
		String replaced=result.replace(',', '.');
		scriptParameters.put("Result", replaced);
		return true;
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/03/03/round";
	}

	public String getTooltip()
	{
		return "Rounds a value to a certain number of decimals";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "Result"};
	}

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter value to round");
		if (text != null)
		{
			return "Round " + text;
		}
		else
		{
			return null;
		}
	}
}
