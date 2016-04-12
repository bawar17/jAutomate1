package custom;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class IncreaseDate
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<3)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: IncreaseDate Date DateFormat NoDays");
			return false;
		}

		String dateString=commandParameters[0];
		String dateFormatStr=commandParameters[1];
		String noDaysStr=commandParameters[2];

		try
		{
			int noDays=Integer.parseInt(noDaysStr);
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
			String date=increaseDate(dateString, dateFormat, noDays);
			if(date==null)
			{
				scriptParameters.put("Error", "Failed to parse the date given the DateFormat provided");
				return false;
			}
			scriptParameters.put("IncreasedDate", date);
			return true;
		}
		catch(Exception e)
		{
			scriptParameters.put("Error", "Failed to increase date: "+e.toString());
			return false;
		}
	}

	/**
	 * @param date
	 * @param dateFormat
	 * @return The next date after date
	 */
	public static String increaseDate(String date, SimpleDateFormat dateFormat, int noDays)
	{
		return increaseDate(date, dateFormat, Calendar.DAY_OF_YEAR, noDays);
	}

	public static String increaseTime(String time, SimpleDateFormat dateFormat, int noHours)
	{
		return increaseDate(time, dateFormat, Calendar.HOUR_OF_DAY, noHours);
	}

	/**
	 * Increase date or time
	 * 
	 * @param date
	 * @param dateFormat
	 * @param field For example Calendar.DAY_OF_YEAR or Calendar.HOUR
	 * @param no No fields
	 * @return
	 */
	public static String increaseDate(String date, SimpleDateFormat dateFormat, int field, int no)
	{
		if (date == null || "".equals(date))
			return null;

		try
		{
			Date d = dateFormat.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.add(field, no);
			return dateFormat.format(calendar.getTime());
		}
		catch (Exception e)
		{
			// Cannot parse this string
			return null;
		}
	}

	public String getHelp()
	{
		return "http://jautomate.com/2014/11/09/increasedate";
	}

	public String getTooltip()
	{
		return "Increases or decreases a date";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "IncreasedDate"};
	}
}
