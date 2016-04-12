package custom;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.swing.JFileChooser;

public class LoadText
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: LoadText FilePath");
			return false;
		}

		String filePath=commandParameters[0];
		String text=loadTextFile(filePath, scriptParameters);
		if(text==null)
		{
			scriptParameters.put("Error", "Failed to load text");
			return false;
		}
		String textReplaced = text.toString().replaceAll("\"", "\\\\\"");
		
		scriptParameters.put("LoadedText", textReplaced);
		return true;
	}

	/**
	 * Load the file and returns file content as a String
	 * @param filePath
	 * @return File as a String or null if failed
	 */
	private static String loadTextFile(String filePath, Properties scriptParameters)
	{
		File file = new File(filePath);
		if (file == null)
		{
			// File not found
			return null;
		}
		StringBuffer buf = new StringBuffer();
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(addRootFolder(filePath, scriptParameters)), "UTF8"));
			String str;
			int lineNo=0;
			while ((str = in.readLine()) != null)
			{
				if(lineNo>0)
				{
					buf.append('\n');
				}
				buf.append(str);
				lineNo++;
			}
			in.close();
			return buf.toString();
		}
		catch (Exception e)
		{
			// File not found
			return null;
		}
	}

	public String getHelp()
	{
		return "http://jautomate.com/2014/06/15/loadtext";
	}

	public String getTooltip()
	{
		return "Loads a text from file";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "LoadedText"};
	}

	public String getCommand()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select file to load");
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog((Frame)null) == JFileChooser.APPROVE_OPTION)
		{
			if (chooser.getSelectedFile() != null)
			{
				try
				{
					String name = chooser.getSelectedFile().getCanonicalPath();
					if (name != null && !"".equals(name.trim()))
					{
						return "LoadText " + "\"" + name + "\"";
					}
				}
				catch (Exception ex)
				{
				}
			}
		}
		return null;
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
