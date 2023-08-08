/**
 * This is the SharkMap class which is called to generate the map of all the user's followed sharks.
 * 
 * @author Jeff Cayaban
 * @author Maria Alexandra Padilla
 * @author Ruzzel Pescozo
 * @author Anna Banasik
 * 
 */

package majorCoursework.favourites;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import api.jaws.Jaws;

public class SharkMap extends JFrame {

	/** To allow us to retrieve the locations of each followed shark using its associated methods. */
	private Jaws jaws = new Jaws("7477xYH9nFRElJuM", "JYOFqUtwpLvcyGyJ", true);
	
	/** 
	 * sharkLat - Stores the shark's latitude.
	 * sharkLon - Stores the shark's longitude.
	 * finalLine - Stores the String required for the URL from the Google Static Map API.
	 * 
	 * */
	private String sharkLat, sharkLon, finalLine;
	
	/** Will store the image from the ImageIcon. */
	private JLabel imageLabel;
	
	/** Will store the URL of the Google Static Maps API*/
	private BufferedImage image;
	
	/** Will store the scaled image directly from the given URL*/
	private ImageIcon ic;
	
	/**
	 * Will set up the frame required for the map of the followed sharks.
	 */
	
	public SharkMap() {
		super("Shark Map - User's Favourite Sharks");
		setMinimumSize(new Dimension(800, 630));
		setResizable(true);
		setLayout(new BorderLayout());
		
		setVisible(true);

	}
	
	/**
	 * Will generate the map of the user's followed sharks using the Google Static Map API.
	 * 
	 * @param sharkNames The list of the user's followed sharks.
	 * @see jaws#getLastLocation()
	 */
	
	public void generateMap(ArrayList<String> sharkNames) {
		
		finalLine = "";
		
		for(int i = 0; i < sharkNames.size(); i++) {
	
			// Stores the Latitude and Longitude values for a shark.
			sharkLat = String.valueOf(jaws.getLastLocation(sharkNames.get(i)).getLatitude());
			sharkLon = String.valueOf(jaws.getLastLocation(sharkNames.get(i)).getLongitude());

			// Will generate the line needed for the URL which will help generate the map.
			finalLine = finalLine + "&markers=size:mid%7Ccolor:0xff0000%7Clabel:%7C" + sharkLat + "," + sharkLon;
		}

		try {
			// Reads the URL and stores the image of the map.
			image = ImageIO.read(new URL("http://maps.googleapis.com/maps/api/staticmap?center=King's+College+London&zoom=1&scale=2&size=600x600&maptype=terrain&key=AIzaSyDtUBsMX-EIT5IG3Hrwxa-z-7l3Cz3TPPQ&format=jpg&visual_refresh=true" + finalLine));
			ic = new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
			imageLabel = new JLabel(ic);
	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Adds the map to the JPanel.
		this.add(imageLabel, BorderLayout.CENTER);
		
	}

}
