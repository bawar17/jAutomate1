package custom;

import java.util.Properties;

public class SubRegion
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if(commandParameters.length<4)
		{
			scriptParameters.put("Error", "Missing parameter in command. Usage: SubRegion NoColumns NoRows Column Row");
			return false;
		}

		String regionX=scriptParameters.getProperty("RegionX");
		String regionY=scriptParameters.getProperty("RegionY");
		String regionWidth=scriptParameters.getProperty("RegionWidth");
		String regionHeight=scriptParameters.getProperty("RegionHeight");
		
		if(regionX==null || regionY==null || regionWidth==null | regionHeight==null)
		{
			// No region
			return false;
		}
		
		if(commandParameters.length!=4)
		{
			return false;
		}

		int noColumns=Integer.parseInt(commandParameters[0]);
		int noRows=Integer.parseInt(commandParameters[1]);
		int column=Integer.parseInt(commandParameters[2]);
		int row=Integer.parseInt(commandParameters[3]);

		int subRegionWidth=Integer.parseInt(regionWidth)/noColumns;
		int subRegionHeight=Integer.parseInt(regionHeight)/noRows;

		int subRegionX=Integer.parseInt(regionX)+subRegionWidth*(column-1);
		int subRegionY=Integer.parseInt(regionY)+subRegionHeight*(row-1);

		int subRegionCenterX=subRegionWidth*(column-1)+subRegionWidth/2;
		int subRegionCenterY=subRegionHeight*(row-1)+subRegionHeight/2;
		
		scriptParameters.put("SubRegion", ""+subRegionX+" "+subRegionY+" "+subRegionWidth+" "+subRegionHeight);
		scriptParameters.put("SubRegionCenterX", ""+subRegionCenterX);
		scriptParameters.put("SubRegionCenterY", ""+subRegionCenterY);
		return true;
	}
	
	public String getHelp()
	{
		return "http://jautomate.com/2014/03/03/subregion";
	}

	public String getTooltip()
	{
		return "Creates a smaller region of the current region";
	}

	public String[] getParameters()
	{
		return new String[]{"Error"};
	}
}
