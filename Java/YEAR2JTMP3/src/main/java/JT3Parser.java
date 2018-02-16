import javax.xml.parsers.*;

/**
 * 
 * @author FleaNovus
 *
 */
public class JT3Parser
{
	public static void main(String[]args) throws Exception
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		
		JT3Handler handler = new JT3Handler();
		
		saxParser.parse("http://open.live.bbc.co.uk/weather/feeds/en/2656397/observations.rss", handler);
	}
}
