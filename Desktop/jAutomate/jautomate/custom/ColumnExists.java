package custom;

import jautomate.CSVReader;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ColumnExists
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<3)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: ColumnExists FilePath Column Value");
			return false;
		}

		String filename=commandParameters[0].trim();
		String column=commandParameters[1].trim();
		String value=commandParameters[2].trim();

		CSVReader csvReader;
		try
		{
			csvReader = new CSVReader(addRootFolder(filename, scriptParameters));
		}
		catch (IOException e)
		{
			return false;
		}
		while (csvReader.hasNext())
		{
			Properties csvParameters=null;
			try
			{
				csvParameters = csvReader.next();
				String columnValue=csvParameters.getProperty(column);
				if(columnValue==null)
				{
					// Invalid column
					return false;
				}
				if(value.equalsIgnoreCase(columnValue.trim()))
				{
					return true;
				}
			}
			catch (Exception ex)
			{
				return false;
			}
		}
		return false;
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/03/03/columnexists";
	}

	public String getTooltip()
	{
		return "Checks if a column value exits";
	}
	
	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	/**
	 * Adds the RootFolder parameter to filepath if defined
	 * @param filepath
	 * @param scriptParameters
	 * @return A path to filepath
	 */
	private static String addRootFolder(String filepath, Properties scriptParameters)
	{
		if((new File(filepath)).isAbsolute())
		{
			// Do not add a root folder to an absolute path
			return filepath;
		}
		String rootFolder=scriptParameters.getProperty("RootFolder");
		if(rootFolder==null)
		{
			return filepath;
		}
		File file=new File(rootFolder, filepath);
		return file.getPath();
	}
}
