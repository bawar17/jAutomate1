package custom;

import java.util.Properties;
import java.util.Random;

public class SetRandom
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<2)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: SetRandom Parameter Value1 [Value2]");
			return false;
		}

		String parameter=commandParameters[0];

		Random generator = new Random(System.currentTimeMillis());
		int randomNumber = generator.nextInt(commandParameters.length-1);
		String parameterValue=commandParameters[randomNumber+1];

		scriptParameters.put(parameter, parameterValue);
		return true;
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/06/20/setrandom";
	}

	public String getTooltip()
	{
		return "Sets a parameter randomly from a number of possible values";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}
}
