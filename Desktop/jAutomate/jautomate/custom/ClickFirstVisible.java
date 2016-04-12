package custom;

import jautomate.ScriptRunner;

import java.awt.image.BufferedImage;
import java.util.Properties;

public class ClickFirstVisible
{
	ScriptRunner scriptRunner=null;
	
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		for(String commandParameter:commandParameters)
		{
			BufferedImage image=scriptRunner.loadImage(commandParameter);
			if(image!=null)
			{
				if(scriptRunner.getVizionEngine().mouseMove(image))
				{
					// Moved to that image
					scriptRunner.mouseLeftClick();
					return true;
				}
			}
			else
			{
				scriptParameters.put("Error", "Failed to load image");
				return false;
			}
		}
		// No image found
		return false;
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/03/03/clickfirstvisible";
	}

	public String getTooltip()
	{
		return "Clicks the first image that can be found";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}

	public String getCommand()
	{
		return "ClickFirstVisible \"<Image>\"";
	}

	public void setScriptRunner(ScriptRunner scriptRunner)
	{
		this.scriptRunner=scriptRunner;
	}
}
