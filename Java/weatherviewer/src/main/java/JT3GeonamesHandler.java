
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author FleaNovus
 *
 */
public class JT3GeonamesHandler extends DefaultHandler
{
	Boolean geonameIDFound;
	private String weatherURL;
	
	/**
	 * geonameIDFound boolean initialized to false
	 */
	public void startDocument() throws SAXException
	{
		geonameIDFound = false;
	}
	
	/**
	 * Searches for geonameID in the xml file
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
	{
		if (qName.equalsIgnoreCase("GEONAMEID"))
		{
			geonameIDFound = true;
		}
	}
	
	/**
	 * Resets the geonameIDFound boolean once past it
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if (qName.equalsIgnoreCase("GEONAMEID"))
		{
			geonameIDFound = false;
		}
	}
	
	/**
	 * extracts the geonameID from the xml file
	 */
	public void characters(char ch[], int start, int length) throws SAXException
	{
		if (geonameIDFound)
		{
			weatherURL = new String(ch, start, length);
		}
	}
	
	/**
	 * Helper method to retrieve the geonameID
	 * @return geonameID from xml file
	 */
	public String getWeatherUrl()
	{
		String url = weatherURL;
		weatherURL = "null";
		return url;
	}
}
