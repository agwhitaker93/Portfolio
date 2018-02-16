
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;

public class JT3Frame2
{
	private JFrame frmGetWeather;
	private JButton forecastButton;
	private JTextField textField;
	private JLabel lblLocation;
	private JTextArea textArea;
	private static JT3Handler2 handler;
	private static SAXParser saxParser;
	private static JT3GeonamesHandler geonameHandler;
	private static XMLReader xmlReader;
	private JPanel weatherPanel;
	private JLabel weatherLabel;
	private JLabel temp;
	private JLabel wind;
	private JLabel humidity;
	private JLabel pressure;
	private JLabel visibility;
	private JLabel tempLabel;
	private JLabel windLabel;
	private JLabel humidityLabel;
	private JLabel pressureLabel;
	private JLabel visibilityLabel;
	
	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		saxParser = factory.newSAXParser();
		xmlReader = saxParser.getXMLReader();
		geonameHandler = new JT3GeonamesHandler();
		handler = new JT3Handler2();
		xmlReader.setErrorHandler(new JT3ErrorHandler(System.err));
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					JT3Frame2 window = new JT3Frame2();
					window.frmGetWeather.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public JT3Frame2() throws Exception
	{
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws Exception
	 */
	private void initialize() throws Exception
	{
		frmGetWeather = new JFrame();
		frmGetWeather.setResizable(false);
		frmGetWeather.setTitle("Get BBC Weather");
		frmGetWeather.setBounds(100, 100, 450, 370);
		frmGetWeather.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGetWeather.getContentPane().setLayout(null);
		
		forecastButton = new JButton("Forecast");
		forecastButton.setBounds(338, 21, 96, 23);
		frmGetWeather.getContentPane().add(forecastButton);
		ActionListener listener = new ButtonListener();
		forecastButton.addActionListener(listener);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(12, 25, 316, 19);
		frmGetWeather.getContentPane().add(textField);
		
		lblLocation = new JLabel("Location");
		lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocation.setBounds(12, 11, 316, 14);
		frmGetWeather.getContentPane().add(lblLocation);
		
		textArea = new JTextArea();
		textArea.setBounds(12, 223, 422, 86);
		frmGetWeather.getContentPane().add(textArea);
		
		weatherPanel = new JPanel();
		weatherPanel.setBounds(12, 55, 96, 96);
		frmGetWeather.getContentPane().add(weatherPanel);
		weatherPanel.setLayout(new BorderLayout(0, 0));
		
		weatherLabel = new JLabel("");
		weatherPanel.add(weatherLabel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBounds(118, 55, 280, 157);
		frmGetWeather.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]
		{ 56, 56, 56, 56, 56, 0 };
		gbl_panel.rowHeights = new int[]
		{ 74, 43, 0 };
		gbl_panel.columnWeights = new double[]
		{ 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[]
		{ 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		
		temp = new JLabel("Temp");
		GridBagConstraints gbc_temp = new GridBagConstraints();
		gbc_temp.insets = new Insets(0, 0, 5, 5);
		gbc_temp.gridx = 0;
		gbc_temp.gridy = 0;
		panel.add(temp, gbc_temp);
		temp.setIcon(new ImageIcon(getClass().getResource("/resources/extra/Temperature.png")));
		temp.setHorizontalTextPosition(SwingConstants.CENTER);
		temp.setVerticalTextPosition(JLabel.BOTTOM);
		
		humidity = new JLabel("Humidity");
		GridBagConstraints gbc_humidity = new GridBagConstraints();
		gbc_humidity.insets = new Insets(0, 0, 5, 5);
		gbc_humidity.gridx = 1;
		gbc_humidity.gridy = 0;
		panel.add(humidity, gbc_humidity);
		humidity.setIcon(new ImageIcon(getClass().getResource("/resources/extra/Humidity.png")));
		humidity.setHorizontalTextPosition(JLabel.CENTER);
		humidity.setVerticalTextPosition(JLabel.BOTTOM);
		
		pressure = new JLabel("Pressure");
		GridBagConstraints gbc_pressure = new GridBagConstraints();
		gbc_pressure.insets = new Insets(0, 0, 5, 5);
		gbc_pressure.gridx = 2;
		gbc_pressure.gridy = 0;
		panel.add(pressure, gbc_pressure);
		pressure.setIcon(new ImageIcon(getClass().getResource("/resources/extra/Barometer.png")));
		pressure.setHorizontalTextPosition(JLabel.CENTER);
		pressure.setVerticalTextPosition(JLabel.BOTTOM);
		
		visibility = new JLabel("Visibility");
		GridBagConstraints gbc_visibility = new GridBagConstraints();
		gbc_visibility.insets = new Insets(0, 0, 5, 5);
		gbc_visibility.gridx = 3;
		gbc_visibility.gridy = 0;
		panel.add(visibility, gbc_visibility);
		visibility.setIcon(new ImageIcon(getClass().getResource("/resources/extra/Visible.png")));
		visibility.setHorizontalTextPosition(JLabel.CENTER);
		visibility.setVerticalTextPosition(JLabel.BOTTOM);
		
		wind = new JLabel("Wind");
		GridBagConstraints gbc_wind = new GridBagConstraints();
		gbc_wind.insets = new Insets(0, 0, 5, 0);
		gbc_wind.gridx = 4;
		gbc_wind.gridy = 0;
		panel.add(wind, gbc_wind);
		wind.setIcon(new ImageIcon(getClass().getResource("/resources/extra/Windsock.png")));
		wind.setHorizontalTextPosition(JLabel.CENTER);
		wind.setVerticalTextPosition(JLabel.BOTTOM);
		
		tempLabel = new JLabel("");
		tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_tempLabel = new GridBagConstraints();
		gbc_tempLabel.anchor = GridBagConstraints.NORTH;
		gbc_tempLabel.insets = new Insets(0, 0, 0, 5);
		gbc_tempLabel.gridx = 0;
		gbc_tempLabel.gridy = 1;
		panel.add(tempLabel, gbc_tempLabel);
		
		humidityLabel = new JLabel("");
		humidityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_humidityLabel = new GridBagConstraints();
		gbc_humidityLabel.anchor = GridBagConstraints.NORTH;
		gbc_humidityLabel.insets = new Insets(0, 0, 0, 5);
		gbc_humidityLabel.gridx = 1;
		gbc_humidityLabel.gridy = 1;
		panel.add(humidityLabel, gbc_humidityLabel);
		
		pressureLabel = new JLabel("");
		pressureLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_pressureLabel = new GridBagConstraints();
		gbc_pressureLabel.anchor = GridBagConstraints.NORTH;
		gbc_pressureLabel.insets = new Insets(0, 0, 0, 5);
		gbc_pressureLabel.gridx = 2;
		gbc_pressureLabel.gridy = 1;
		panel.add(pressureLabel, gbc_pressureLabel);
		
		visibilityLabel = new JLabel("");
		visibilityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_visibilityLabel = new GridBagConstraints();
		gbc_visibilityLabel.anchor = GridBagConstraints.NORTH;
		gbc_visibilityLabel.insets = new Insets(0, 0, 0, 5);
		gbc_visibilityLabel.gridx = 3;
		gbc_visibilityLabel.gridy = 1;
		panel.add(visibilityLabel, gbc_visibilityLabel);
		
		windLabel = new JLabel("");
		windLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_windLabel = new GridBagConstraints();
		gbc_windLabel.anchor = GridBagConstraints.NORTH;
		gbc_windLabel.gridx = 4;
		gbc_windLabel.gridy = 1;
		panel.add(windLabel, gbc_windLabel);
		
		JMenuBar menuBar = new JMenuBar();
		frmGetWeather.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("About");
		menuBar.add(mnNewMenu);
		JMenuItem mntmNewMenuItem = new JMenuItem("Weather Icons from icons8.com");
		ActionListener openIcons8 = new MenuListener();
		mntmNewMenuItem.addActionListener(openIcons8);
		mnNewMenu.add(mntmNewMenuItem);
	}
	
	private class ButtonListener implements ActionListener
	{
		/**
		 * When pressed, the button will get the location from the text field, inject it into a URL to get a geonameID, which is then
		 * injected into another URL to get the weather from the BBC website
		 */
		public void actionPerformed(ActionEvent e)
		{
			String location = textField.getText();
			String geonamesUrl = "http://api.geonames.org/search?q=" + location + "&maxRows=1&lang=en&username=eeu211";
			
			if (!geonamesUrl.equals(""))
			{
				try
				{
					xmlReader.setContentHandler(geonameHandler);
					xmlReader.parse(geonamesUrl);
					String bbcUrl = "http://open.live.bbc.co.uk/weather/feeds/en/" + geonameHandler.getWeatherUrl()
							+ "/observations.rss";
							
					try
					{
						xmlReader.setContentHandler(handler);
						xmlReader.parse(bbcUrl);
						String weatherDetails = handler.getWeatherDetails();
						textArea.setText(weatherDetails);
						SetIcon(weatherDetails);
						ExtractDescription(handler.getWeatherDescription());
					}
					catch (Exception e1)
					{
						System.out.println("Error caught: " + e1);
						textArea.setText("Error detected. Location not found");
						SetIcon("");
						ClearLabels("error");
						
						if (bbcUrl.equals("http://open.live.bbc.co.uk/weather/feeds/en/null/observations.rss"))
							textArea.append(" on geonames.org");
							
						else
							textArea.append(" on bbc.co.uk");
					}
				}
				catch (Exception e1)
				{
					System.out.println("Error caught: " + e1);
					textArea.setText("Error detected. Location not found");
					SetIcon("");
					ClearLabels("error");
				}
			}
		}
	}
	
	private class MenuListener implements ActionListener
	{
		/**
		 * Opens a browser window to the source of the icons used in the program
		 */
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			try
			{
				java.awt.Desktop.getDesktop().browse(new URI("https://icons8.com/"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the weather icon based on the returned weather details
	 * @param weather The details to pick an icon based on
	 */
	private void SetIcon(String weather)
	{
		String[] arr = weather.split("\\W+");
		
		for (String ss : arr)
		{
			switch (ss)
			{
				case "Cloud":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("/resources/Clouds.png")));
					return;
					
				case "Cloudy":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("/resources/PartlyCloudy.png")));
					return;
					
				case "Fog":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("/resources/FogDay.png")));
					return;
					
				case "Rain":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("/resources/ModerateRain.png")));
					return;
					
				case "Snow":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("/resources/Snow.png")));
					return;
					
				case "Sleet":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("/resources/Sleet.png")));
					return;
					
				case "Storm":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("/resources/Storm.png")));
					return;
					
				case "Sunny":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("/resources/Sun.png")));
					return;
					
				case "Clear":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("/resources/Dry.png")));
					return;
					
				case "Mist":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("/resources/FogDay.png")));
					return;
					
				default:
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("/resources/TVOff.png")));
					break;
			}
		}
	}
	
	/**
	 * Extracts the specific details about the areas weather
	 * @param weatherDescription The description string taken from the xml file
	 */
	private void ExtractDescription(String weatherDescription)
	{
		String[] arr = weatherDescription.split("\\W+");
		
		for (int i = 0; i < arr.length; i++)
		{
			switch (arr[i])
			{
				case "Temperature":
					tempLabel.setText(arr[++i] + "°C");
					break;
					
				case "Direction":
					windLabel.setText("<html>" + arr[++i]);
					while (!arr[i + 1].equalsIgnoreCase("WIND"))
					{
						windLabel.setText(windLabel.getText() + " " + arr[++i]);
					}
					i+=2;
					while (!arr[i+1].equalsIgnoreCase("HUMIDITY"))
					{
						windLabel.setText(windLabel.getText() + "<br>" + arr[++i] + "</html>");
					}
					break;
					
				case "Humidity":
					humidityLabel.setText(arr[++i] + "%");
					break;
					
				case "Pressure":
					pressureLabel.setText("<html>" + arr[++i] + "<br>" + arr[++i] + "</html>");
					break;
					
				case "Visibility":
					visibilityLabel.setText(arr[++i]);
					if (arr.length > i + 1)
						visibilityLabel.setText("<html>" + visibilityLabel.getText() + "<br>" + arr[++i] + "</html>");
					break;
			}
		}
	}
	
	/**
	 * Clears all of the labels
	 * @param msg message to be displayed on defined labels
	 */
	private void ClearLabels(String msg)
	{
		tempLabel.setText(msg);
		windLabel.setText(msg);
		humidityLabel.setText(msg);
		pressureLabel.setText(msg);
		visibilityLabel.setText(msg);
	}
}
