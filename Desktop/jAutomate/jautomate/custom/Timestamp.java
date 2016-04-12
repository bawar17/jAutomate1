package custom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Timestamp
{
	private static SimpleDateFormat simpleDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		String currentDateString = simpleDateTime.format(new Date());
		scriptParameters.put("Timestamp", ""+currentDateString);
		return true;
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/03/03/timestamp";
	}

	public String getTooltip()
	{
		return "Creates a timestamp";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "Timestamp"};
	}
}
