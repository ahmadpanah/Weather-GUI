import java.awt.Cursor;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*; 

public class WeatherApp extends JFrame {
	public WeatherApp() {
		// Setup GUI and Add a Title
		super("برنامه هواشناسی");
		
		// Config Gui end program it has been closed
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Set the Size of Our GUI (in Pixels)
		setSize(450,650);
		
		// Load Our Application at the Center of Screen
		setLocationRelativeTo(null);
		
		// set manually position Our Components
		setLayout(null);
		
		// Prevent any Resize
		setResizable(false);
		
		addGuiComponents();
	}

	private void addGuiComponents() {

		// Search Field
		JTextField searchTextField = new JTextField();
		
		// Set Location and Size
		searchTextField.setBounds(15,15,350,45);
		
		// Change Font and Size
		searchTextField.setFont(new Font("Dialog" , Font.PLAIN, 24));
		
		add(searchTextField);
		
		// Search Button
		JButton searchButton = new JButton(loadImage("src/assets/search.png"));
		
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		searchButton.setBounds(375, 14, 45, 45);
		add(searchButton);
		
		JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png")); 
		weatherConditionImage.setBounds(0,120,450,300);
		add(weatherConditionImage);
		
		JLabel temperatureText = new JLabel ("10 C");
		temperatureText.setBounds(0, 350, 450, 54);
		temperatureText.setFont(new Font("Dialog" , Font.BOLD , 48));
		
		temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
		add(temperatureText);
		
		JLabel weatherConditionDesc = new JLabel("Cloudy");
		weatherConditionDesc.setBounds(0,405,450,36);
		weatherConditionDesc.setFont(new Font("Dialog" , Font.PLAIN , 32));
		weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
		add(weatherConditionDesc);
		
		JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
		humidityImage.setBounds(15, 500, 74, 69);
		add(humidityImage);
		
		JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
		humidityText.setBounds(90, 500, 85, 55);
		humidityText.setFont(new Font("Dialog" , Font.PLAIN , 16));
		add(humidityText);
		
		// Wind Speed 
		JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
		windSpeedImage.setBounds(230, 500, 74, 66);
		add(windSpeedImage);
		
		JLabel windSpeedText = new JLabel("<html><b>WindSpeed</b> 15Km/h</html>");
		windSpeedText.setBounds(320, 500, 85, 55);
		windSpeedText.setFont(new Font("Dialog" , Font.PLAIN , 16));
		add(windSpeedText);
		
		
		
	}
	private ImageIcon loadImage(String resourcePath) {
		try {
			BufferedImage image = ImageIO.read(new File(resourcePath));
			return new ImageIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Could not find resource file");
		return null;
	}
}
