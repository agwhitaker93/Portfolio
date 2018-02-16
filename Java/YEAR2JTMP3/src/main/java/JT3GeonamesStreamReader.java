import java.io.InputStream;
import java.net.URL;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class JT3GeonamesStreamReader
{
	private Boolean bGeoname;
	private String weatherUrl;
	
	public JT3GeonamesStreamReader(URL url)
	{
		bGeoname = false;
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		try
		{
			InputStream in = url.openStream();
			XMLStreamReader streamReader = xmlInputFactory.createXMLStreamReader(in);
			int event = streamReader.getEventType();
			while (true)
			{
				switch (event)
				{
					case XMLStreamConstants.START_ELEMENT:
						if (streamReader.getLocalName().equalsIgnoreCase("GEONAMEID"))
						{
							bGeoname = true;
						}
						break;
						
					case XMLStreamConstants.CHARACTERS:
						if (bGeoname)
						{
							weatherUrl = streamReader.getText();
						}
						break;
						
					case XMLStreamConstants.END_ELEMENT:
						if (streamReader.getLocalName().equalsIgnoreCase("GEONAMEID"))
							return;
						break;
				}
				if (!streamReader.hasNext())
					break;
					
				event = streamReader.next();
			}
		}
		catch (Exception e1)
		{
			System.out.println("Error: " + e1);
		}
	}
	
	public String getWeatherUrl()
	{
		return weatherUrl;
	}
}
