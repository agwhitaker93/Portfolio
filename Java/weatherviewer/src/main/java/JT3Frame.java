import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class JT3Frame
{
	private JFrame frmGetWeather;
	private JButton forecastButton;
	private JTextField textField;
	private JLabel label;
	private JTextArea textArea;
	private static JT3Handler handler;
	private static SAXParser saxParser;
	private JPanel weatherPanel;
	private JLabel weatherLabel;
	
	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		saxParser = factory.newSAXParser();
		handler = new JT3Handler();
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					JT3Frame window = new JT3Frame();
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
	public JT3Frame() throws Exception
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
		frmGetWeather.setBounds(100, 100, 450, 333);
		frmGetWeather.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGetWeather.getContentPane().setLayout(null);
		
		forecastButton = new JButton("Forecast");
		forecastButton.setBounds(340, 80, 94, 46);
		frmGetWeather.getContentPane().add(forecastButton);
		ActionListener listener = new ButtonListener();
		forecastButton.addActionListener(listener);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(12, 25, 422, 19);
		frmGetWeather.getContentPane().add(textField);
		textField.setText("http://open.live.bbc.co.uk/weather/feeds/en/2656397/observations.rss");
		
		label = new JLabel("URL");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(207, 11, 30, 14);
		frmGetWeather.getContentPane().add(label);
		
		textArea = new JTextArea();
		textArea.setBounds(12, 162, 422, 98);
		frmGetWeather.getContentPane().add(textArea);
		
		weatherPanel = new JPanel();
		weatherPanel.setBounds(12, 55, 96, 96);
		frmGetWeather.getContentPane().add(weatherPanel);
		weatherPanel.setLayout(new BorderLayout(0, 0));
		
		weatherLabel = new JLabel("");
		weatherPanel.add(weatherLabel, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frmGetWeather.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("About");
		menuBar.add(mnNewMenu);
		JMenuItem mntmNewMenuItem = new JMenuItem("Weather Icons from icons8.com");
		mnNewMenu.add(mntmNewMenuItem);
	}
	
	public class ButtonListener implements ActionListener
	{
		/**
		 * When pressed, the button will get the URL provided and parse it with the sax parser, then add the weather details to the text area
		 */
		public void actionPerformed(ActionEvent e)
		{
			String url = textField.getText();
			
			if (!url.equals(""))
			{
				try
				{
					saxParser.parse(url, handler);
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				
				textArea.setText(handler.getWeatherDetails());
				SetIcon(handler.getWeatherDetails());
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
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("Clouds.png")));
					return;
					
				case "Fog":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("FogDay.png")));
					return;
					
				case "Rain":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("ModerateRain.png")));
					return;
					
				case "Snow":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("Snow.png")));
					return;
					
				case "Sleet":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("Sleet.png")));
					return;
					
				case "Storm":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("Storm.png")));
					return;
					
				case "Sunny":
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("Sun.png")));
					return;
					
				default:
					weatherLabel.setIcon(new ImageIcon(getClass().getResource("Sun.png")));
					break;
			}
		}
	}
}
