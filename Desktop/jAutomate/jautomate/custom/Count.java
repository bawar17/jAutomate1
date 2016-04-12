package custom;

import jautomate.ScriptRunner;

import java.awt.image.BufferedImage;
import java.util.Properties;

public class Count
{
	ScriptRunner scriptRunner=null;
	
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<1)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: Count Image");
			return false;
		}

		String filename=commandParameters[0].trim();

		BufferedImage image=scriptRunner.loadImage(filename);
		if(image!=null)
		{
			int count=scriptRunner.getVizionEngine().findImages(image).size();
			scriptParameters.put("Count", ""+count);
			return true;
		}
		else
		{
			scriptParameters.put("Error", "Failed to load image");
			return false;
		}
	}

	public String getTooltip()
	{
		return "Returns the number of times an image appears on screen";
	}

	public String[] getParameters()
	{
		return new String[]{"Error", "Count"};
	}

	public String getCommand()
	{
		return "Count \"<Image>\"";
	}

	public void setScriptRunner(ScriptRunner scriptRunner)
	{
		this.scriptRunner=scriptRunner;
	}
}
