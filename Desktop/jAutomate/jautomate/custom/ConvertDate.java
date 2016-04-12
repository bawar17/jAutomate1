package custom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ConvertDate
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<3)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: ConvertDate Date FromDateFormat ToDateFormat");
			return false;
		}

		String dateString=commandParameters[0];
		String fromDateFormatStr=commandParameters[1];
		String toDateFormatStr=commandParameters[2];

		try
		{
			SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromDateFormatStr);
			SimpleDateFormat toDateFormat = new SimpleDateFormat(toDateFormatStr);
			Date date=fromDateFormat.parse(dateString);
			String convertedDateString = toDateFormat.format(date);
			scriptParameters.put("ConvertedDate", convertedDateString);
			return true;
		}
		catch(Exception e)
		{
			scriptParameters.put("Error", "Failed to convert date: "+e.toString());
			return false;
		}
	}

	public String getHelp()
	{
		return "http://jautomate.com/2014/06/15/convertdate";
	}

	public String getTooltip()
	{
		return "Converts a date from one format to another";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "ConvertedDate"};
	}
}
