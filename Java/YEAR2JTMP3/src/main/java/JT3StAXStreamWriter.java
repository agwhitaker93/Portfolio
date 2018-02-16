import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

public class JT3StAXStreamWriter
{
	private String fileLocation;
	private File temp;
	
	public JT3StAXStreamWriter(String term, URL url) throws Exception
	{
		JT3GeonamesStreamReader streamReader = new JT3GeonamesStreamReader(url);
		temp = File.createTempFile("temp", ".xml");
		fileLocation = temp.getAbsolutePath();
		Map<String, String> elementsMap = new HashMap<String, String>();
		elementsMap.put("date", new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date()));
		elementsMap.put("term", term);
		try
		{
			String geoNameID = streamReader.getWeatherUrl();
			if (!geoNameID.equals("null"))
			{
				elementsMap.put("found", "true");
				elementsMap.put("geoNameID", geoNameID);
			}
			
			else
				elementsMap.put("found", "false");
		}
		catch (Exception e)
		{
		}
		
		writeXML(elementsMap, temp);
	}
	
	private void writeXML(Map<String, String> elementsMap, File file)
	{
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
		try
		{
			XMLStreamWriter streamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(file), "UTF-8");
			streamWriter.writeStartDocument("UTF-8", "1.0");
			streamWriter.writeCharacters("\n");
			streamWriter.writeStartElement("weatherSearches");
			
			streamWriter.writeCharacters("\n\t");
			streamWriter.writeStartElement("search");
			streamWriter.writeAttribute("date", elementsMap.get("date"));
			
			streamWriter.writeCharacters("\n\t\t");
			streamWriter.writeStartElement("term");
			streamWriter.writeCharacters(elementsMap.get("term"));
			streamWriter.writeEndElement();
			
			streamWriter.writeCharacters("\n\t\t");
			streamWriter.writeStartElement("found");
			streamWriter.writeCharacters(elementsMap.get("found"));
			streamWriter.writeEndElement();
			
			if (elementsMap.get("found").equalsIgnoreCase("TRUE"))
			{
				streamWriter.writeCharacters("\n\t\t");
				streamWriter.writeStartElement("geoNamesID");
				streamWriter.writeCharacters(elementsMap.get("geoNameID"));
				streamWriter.writeEndElement();
			}
			
			streamWriter.writeCharacters("\n\t");
			streamWriter.writeEndElement();
			streamWriter.writeCharacters("\n");
			streamWriter.writeEndElement();
			streamWriter.writeEndDocument();
			streamWriter.flush();
			streamWriter.close();
			
		}
		catch (Exception e)
		{
		}
	}
	
	public String getFileLocation()
	{
		return fileLocation;
	}
}
