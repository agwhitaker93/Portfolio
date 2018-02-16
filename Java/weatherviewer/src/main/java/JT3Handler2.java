
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author FleaNovus
 *
 */
public class JT3Handler2 extends DefaultHandler
{
	int itemCount, titleCount, descriptionCount;
	private String weatherDetails;
	private String weatherDescription;
	
	/**
	 * Initializes all variables to default values at start of the program
	 */
	public void startDocument() throws SAXException
	{
		itemCount = 0;
		titleCount = 0;
		descriptionCount = 0;
	}

	/**
	 * increments the integer variables to locate the required tag
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
	{
		if (qName.equalsIgnoreCase("ITEM"))
		{
			itemCount++;
		}
		
		else if (qName.equalsIgnoreCase("TITLE"))
		{
			titleCount++;
		}
		
		else if (qName.equalsIgnoreCase("DESCRIPTION"))
		{
			descriptionCount++;
		}
	}

	/**
	 * decrements the integer variables to locate the required tag
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if (qName.equalsIgnoreCase("ITEM"))
		{
			itemCount--;
		}
		
		else if (qName.equalsIgnoreCase("TITLE"))
		{
			titleCount--;
		}
		
		else if (qName.equalsIgnoreCase("DESCRIPTION"))
		{
			descriptionCount--;
		}
	}

	/**
	 * gets data from the required tags once found
	 */
	public void characters(char ch[], int start, int length) throws SAXException
	{
		if (itemCount > 0)
		{
			if (titleCount > 0)
			{
				weatherDetails = new String(ch, start, length);
			}
			
			else if (descriptionCount > 0)
			{
				weatherDescription = new String(ch, start, length);
			}
		}
	}

	/**
	 * helper method to get weather details
	 * @return weather details
	 */
	public String getWeatherDetails()
	{
		String details = weatherDetails;
		weatherDetails = "null";
		return details;
	}

	/**
	 * helper method to get weather description
	 * @return weather description
	 */
	public String getWeatherDescription()
	{
		return weatherDescription;
	}
}
