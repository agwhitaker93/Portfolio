
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author FleaNovus
 *
 */
public class JT3Handler extends DefaultHandler
{
	int rssCount, channelCount, itemCount, titleCount;
	private String weatherDetails;
	
	/**
	 * Initializes all variables to default values at start of the program
	 */
	public void startDocument() throws SAXException
	{
		rssCount = 0;
		channelCount = 0;
		itemCount = 0;
		titleCount = 0;
	}
	
	/**
	 * increments the integer variables to locate the required tag
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
	{
		if (qName.equalsIgnoreCase("RSS"))
		{
			rssCount++;
		}
		
		else if (qName.equalsIgnoreCase("CHANNEL"))
		{
			channelCount++;
		}
		
		else if (qName.equalsIgnoreCase("ITEM"))
		{
			itemCount++;
		}
		
		else if (qName.equalsIgnoreCase("TITLE"))
		{
			titleCount++;
		}
	}
	
	/**
	 * decrements the integer variables to locate the required tag
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if (qName.equalsIgnoreCase("RSS"))
		{
			rssCount--;
		}
		
		else if (qName.equalsIgnoreCase("CHANNEL"))
		{
			channelCount--;
		}
		
		else if (qName.equalsIgnoreCase("ITEM"))
		{
			itemCount--;
		}
		
		else if (qName.equalsIgnoreCase("TITLE"))
		{
			titleCount--;
		}
	}
	
	/**
	 * gets data from the required tag once found
	 */
	public void characters(char ch[], int start, int length) throws SAXException
	{
		if (rssCount > 0 && channelCount > 0 && itemCount > 0 && titleCount > 0)
		{
			weatherDetails = (new String(ch, start, length));
			System.out.println(weatherDetails);
		}
	}
	
	/**
	 * helper method to get weather details
	 * @return weather details
	 */
	public String getWeatherDetails()
	{
		return weatherDetails;
	}
}
