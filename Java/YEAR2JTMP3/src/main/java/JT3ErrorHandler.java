import java.io.PrintStream;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Code from http://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html#gcnsr
 *
 */
public class JT3ErrorHandler implements ErrorHandler
{
	private PrintStream out;
	
	JT3ErrorHandler(PrintStream out)
	{
		this.out = out;
	}
	
	private String getParseExceptionInfo(SAXParseException spe)
	{
		String systemId = spe.getSystemId();
		
		if (systemId == null)
		{
			systemId = "null";
		}
		
		String info = "URI=" + systemId + " Line="
				+ spe.getLineNumber() + ": " + spe.getMessage();
		
		return info;
	}

	@Override
	public void error(SAXParseException arg0) throws SAXException
	{
		String message = "Error: " + getParseExceptionInfo(arg0);
		throw new SAXException(message);
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException
	{
		String message = "Fatal Error: " + getParseExceptionInfo(arg0);
		throw new SAXException(message);
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException
	{
		out.println("Warning: " + getParseExceptionInfo(arg0));
	}
	
}
