/******************************************************************************
*
* ReadSystemProperty - JAutomate plug-in for reading the Java system
*                      properties.
*
* Copyright 2015 CAG Senseus AB (http://www.cag.se)
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*******************************************************************************
* Author: Mathias Nord (mathias.nord@cag.se)
******************************************************************************/

package custom;

import java.util.Properties;
import javax.swing.JOptionPane;

public class ReadSystemProperty
{
	public Boolean executeCommand(String[] commandParameters, Properties scriptParameters)
	{
		if (commandParameters.length != 1)
		{
			scriptParameters.put("Error", "Wrong number of parameters. Usage: ReadSystemProperty PropertyName");
			return false;
		}
		try
		{
			scriptParameters.put("PropertyValue", System.getProperty(commandParameters[0], ""));
			return true;
		}
		catch (Exception e)
		{
			scriptParameters.put("Error", "UnexpectedException: " + e.toString());
			return false;
		}
	}
	
	public String getTooltip()
	{
		return "Returns the value of the specified system property";
	}
	
	public String[] getParameters()
	{
		return new String[]{ "Error", "PropertyValue" };
	}
	
	public String getCommand()
	{
		String text = JOptionPane.showInputDialog(null, "Enter property name");
		if (text != null)
		{
			return "ReadSystemProperty " + text;
		}
		else
		{
			return null;
		}
	}
}
