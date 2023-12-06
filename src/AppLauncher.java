import javax.swing.SwingUtilities;

public class AppLauncher {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
//				new WeatherApp().setVisible(true);
				System.out.println(WeatherAppAPI.getLocationData("Tehran"));
			}
		});

	}

}
