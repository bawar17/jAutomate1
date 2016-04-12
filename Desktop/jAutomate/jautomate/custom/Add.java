package custom;

import java.util.Properties;

import javax.swing.JOptionPane;

public class Add
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: Add Value1 [Value2] ...");
			return false;
		}

		int sum=0;
		for(String commandParameter:commandParameters)
		{
			try
			{
				int value=Integer.parseInt(commandParameter);
				sum+=value;
			}
			catch(NumberFormatException e)
			{
				scriptParameters.put("Error", "Provide integer values only");
				return false;
			}
		}
		scriptParameters.put("Response", ""+sum);
		return true;
	}

	public String getHelp()
	{
		return "http://jautomate.com/2014/03/03/add";
	}

	public String getTooltip()
	{
		return "Adds all parameters";
	}
	
	public String[] getParameters()
	{
		return new String[]{"Error", "Response"};
	}

	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter values to add separated by space");
		if (text != null)
		{
			return "Add " + text;
		}
		return null;
	}
}
