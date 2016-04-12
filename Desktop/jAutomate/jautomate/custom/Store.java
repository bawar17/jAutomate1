package custom;

import java.util.Properties;

public class Store
{
	private static Properties store=new Properties();

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: Store Parameter [Value]");
			return false;
		}
		
		String parameter = commandParameters[0];

		if(commandParameters.length==1)
		{
			// Get a value
			String value=store.getProperty(parameter, "Unknown parameter");
			scriptParameters.put(parameter, value);
		}
		else if(commandParameters.length>=2)
		{
			// Set a value
			String value = commandParameters[1];
			store.setProperty(parameter, value);
		}
		return true;
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/03/03/store";
	}

	public String getTooltip()
	{
		return "Store or retrieve a global parameter value";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}
}
