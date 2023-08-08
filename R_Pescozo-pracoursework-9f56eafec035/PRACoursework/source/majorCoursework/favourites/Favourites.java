/**
 * @author Ruzzel Pescozo
 * @author Jeff Cayaban
 * @author Maria Alexandra Padilla
 * @author Anna Banasik
 */
package majorCoursework.favourites;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import api.jaws.Jaws;
import api.jaws.Location;
import api.jaws.Ping;
import api.jaws.Shark;
import majorCoursework.menuframe.MenuFrame;
import majorCoursework.menuframe.UserProfile;
import majorCoursework.search.Search;

/**
 * The Class Favourites, handles list of followed sharks, the shark map and viewing
 * the clicked followed shark in favourites window.
 */
public class Favourites extends JFrame{

	/** 
	 * The jaws, user profile and menu frame initialisation, "off" means it's only
	 * here for method usage.
	 */
	private Jaws jaws = new Jaws("7477xYH9nFRElJuM", "JYOFqUtwpLvcyGyJ", false);
	private UserProfile user = new UserProfile("off");
	private MenuFrame mf = new MenuFrame("off");
	
	/** 
	 * The Search class, this variable is used later on when Search wants to use
	 * favourites class
	 */
	private Search search;

	/** The Java swing fields */
	private JLabel heading;
	private JPanel topPanel, textPanel, display, bottomPanel;
	private JButton btnSharkMap;

	/** 
	 * The followed shark names ArrayList used for temporary storage for 
	 * writing, reading the file and following. 
	 */
	private ArrayList<String> followedSharkNames = new ArrayList<String>();
	
	/** 
	 * The array of shark names temporarily used 
	 * when splitting sharks when reading. 
	 */
	private String[] arrayOfSharkNames;
	
	/** 
	 * The current user is a static string as there is only one current user of 
	 * the program at any one time, this string is static in all classes
	 * that use it. 
	 */
	private static String currentUser;

	/** 
	 * The Constant kingsLoc, king's isn't a travelling house so it's latitude
	 * and longitude need to be static and final. 
	 */
	private static final Location kingsLoc = new Location(0.1161, 51.5119);

	/**
	 * Instantiates a new favourites.
	 *
	 * @param onORoff on = new favourites window, off = using for methods
	 * @param Search, used to add a reference of search, used for the event of the
	 *        user clicking on the followed shark to open a search window to display the shark.
	 */
	public Favourites(String onORoff, Search search) {
		currentUser = user.getCurrentUser();
		if (onORoff == "off") {
		}

		else {
			setLayout(new BorderLayout());
			setMinimumSize(new Dimension(500, 300));
			setTitle("Favourites");
			setComponents();
			readFile();
			mapListener();
			pack();
			setVisible(true);
		}

		this.search = search;
	}

	/**
	 * Sets the components of the JFrame.
	 */
	public void setComponents() {

		heading = new JLabel("Your following sharks are this far away from you right now:");
		topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
		topPanel.add(heading, BorderLayout.WEST);
		this.add(topPanel, BorderLayout.NORTH);

		display = new JPanel();
		LayoutManager layout = new BoxLayout(display, BoxLayout.Y_AXIS);
		display.setLayout(layout);
		display.setOpaque(true);
		display.setBackground(Color.WHITE);
		display.setMinimumSize(new Dimension(90, 280));

		textPanel = new JPanel(new BorderLayout());
		textPanel.add(display, BorderLayout.CENTER);
		textPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		this.add(textPanel, BorderLayout.CENTER);
		
		bottomPanel = new JPanel(new BorderLayout());
		btnSharkMap = new JButton("Shark Map");
		bottomPanel.add(btnSharkMap, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);

	}

	/**
	 * Adds the follow button.
	 *
	 * @param jb - JButton as an argument to be able to set many JButtons with listeners
	 *        without having to initialise them.
	 * 
	 * @param s - the shark itself is used as an argument so we can save or remove that shark
	 *        depending on the user's actions
	 *        
	 * @return the JButton with the listener so that we can use it
	 */
	public JButton addFollowButton(JButton jb, Shark s) {
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				
				/*
				 * This shows the user if the shark is being followed or not.
				 * also updates favourites button depending on the amount of followed sharks.
				 */
				if (jb.getText().equals("Follow")) {
					followedSharkNames.add(s.getName());
					jb.setText("Following");
					mf.updateFavourites(true);
					
				}
				else {
					followedSharkNames.remove(s.getName());
					jb.setText("Follow");
					if (followedSharkNames.isEmpty()){
						mf.updateFavourites(false);
					}
				}
				/*
				 * Writes to file in real time, fully adapting to the user's actions when 
				 * following or unfollowing.
				 * 
				 * There's an if statement depending on if it's a registered user or 
				 * default user (null)
				 */
				if (currentUser == null) {
					try {
						File favSharks = new File("source/data/ListOfFavSharks.txt");
						FileOutputStream fileOutStr = new FileOutputStream(favSharks);
						PrintWriter printwriter = new PrintWriter(fileOutStr);

						for (String name : followedSharkNames) {
							printwriter.println(name + ",");
						}

						printwriter.flush();
						printwriter.close();
						fileOutStr.close();
					}

					catch (Exception ex) {
						System.out.println("EXCEPTION");
					}
				} else {
					try {
						
						File favSharks = new File("source/data/"+currentUser + "ListOfFavSharks.txt");
						PrintWriter writer = new PrintWriter(favSharks);
						
						for(String name : followedSharkNames) {
							writer.println(name+",");
							System.out.println(name + ",");
						}
						
						writer.close();					
						
					}

					catch (IOException e1) {
						System.out.println("EXCEPTION");
					}
				}
			}
		});
		return jb;
	}

	/**
	 * readFile() method does what it says, but there's an if statement depending on the current
	 * user of the program.
	 */
	public void readFile() {
		if (currentUser == null) {
			try {
				File favSharks = new File("source/data/ListOfFavSharks.txt"); //if default user, it reads this file
				followedSharkNames.clear();
				List<String> listOfSharks = Files.readAllLines(favSharks.toPath());
				for (String shark : listOfSharks) {
					arrayOfSharkNames = shark.split(",");
					followedSharkNames.add(arrayOfSharkNames[0]);
				}
			}

			catch (Exception ex) {
				System.out.println("No Sharks Have Been Followed.");
			}
		} else {
			try {
				File favSharks = new File("source/data/"+currentUser + "ListOfFavSharks.txt");  //if not default, it reads the user's file 
				followedSharkNames.clear();
				List<String> listOfSharks = Files.readAllLines(favSharks.toPath());
				for (String shark : listOfSharks) {
					arrayOfSharkNames = shark.split(",");
					followedSharkNames.add(arrayOfSharkNames[0]);
				}
			}

			catch (Exception ex) {
				System.out.println("No Sharks Have Been Followed.");
			}
		}
	}

	/**
	 * Display list - this method works out the distances of the followed sharks as you click on
	 * favourites button on the menu frame.
	 * 
	 * This means that if you have been following the shark for a long time, you will see that the
	 * distances are updating because the shark doesn't always stay in the same place.
	 */
	public void displayList() {

		double latKing = kingsLoc.getLatitude();
		double lonKing = kingsLoc.getLongitude();

		/*
		 * The temporary array lists and hashmaps are there to sort the distances 
		 * in order of nearest to farthest.
		 */
		ArrayList<Double> distanceSorter = new ArrayList<Double>();
		ArrayList<String> sharkNames = new ArrayList<String>();
		HashMap<String, Double> sharkDistSorter = new HashMap<String, Double>();
		HashMap<String, Double> sharkDistSorted = new LinkedHashMap<String, Double>();

		for (String sharkName : followedSharkNames) {
			double lat = jaws.getLastLocation(sharkName).getLatitude();
			double lon = jaws.getLastLocation(sharkName).getLongitude();

			double distance = Math.round((calcDist(latKing, lat, lonKing, lon) * 100.0)) / 100.0;

			distanceSorter.add(distance);
			sharkDistSorter.put(sharkName, distance);  //this loop first collects sharks and distances
			sharkNames.add(sharkName);
		}
		Collections.sort(distanceSorter);   //puts distances in order
		
		/*
		 * This double loop will transfer the unsorted hashmap "sharkDistSorter"
		 * into sharkDistSorted.
		 * since distanceSorted has the distances in order, it will only input 
		 * the shark and distance into the new LinkedHashMap if
		 * the current distance being checked is equal to the sharkDistSorter.get(name)
		 * therefore the order of inputs to the linked hashmap is equal
		 */
		for (Double d : distanceSorter) {
			for (String name : sharkNames) {
				if (d.equals(sharkDistSorter.get(name))) { 
					sharkDistSorted.put(name, d);
				}
			}
		}
		for (String sharkName : sharkDistSorted.keySet()) {
			String lat = String.valueOf(jaws.getLastLocation(sharkName).getLatitude());
			String lon = String.valueOf(jaws.getLastLocation(sharkName).getLongitude());
			JLabel shark = new JLabel();
			if (sharknado(lon, lat) == true) {
				shark.setText(sharkName + ": " + sharkDistSorted.get(sharkName) + " KM."
						+ " - Involved in a Sharknado event!");
			} else {
				shark.setText(sharkName + ": " + sharkDistSorted.get(sharkName) + " KM.");
			}
			shark.setName(sharkName);

			shark.addMouseListener(jlListener(sharkName));

			display.add(shark);
			display.revalidate();
			display.repaint();
		}
		this.revalidate();
		this.repaint();
	}

	/**
	 * Jl listener.
	 *
	 * @param sharkN - the name of the shark
	 * @return the mouse listener
	 * 
	 * This method prepares a mouseClicked event for when the user clicks on a followed shark they
	 * want to check out. the name of the shark is used as a parameter to do this method.
	 * 
	 * Since the location of the shark changes, the ping also changes, which means that
	 * this method is adapted to that. 
	 * 
	 * (e.g you click on a shark you followed after a week, the ping  and location
	 *      will be it's most recent)
	 */
	public MouseListener jlListener(String sharkN) {
		MouseListener ac = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				JLabel tempLabel = (JLabel) e.getSource();
				String clickedSharkPing = null;
				ArrayList<Ping> dayPing = new ArrayList<Ping>();
				ArrayList<String> dayShark = new ArrayList<String>();
				ArrayList<String> pingOrdering = new ArrayList<String>();
				HashMap<String, String> pingShark = new HashMap<String, String>();
				dayPing = jaws.pastMonth();
				
				/*
				 * the first inputs to the pingordering arraylists wont have duplicates
				 * then we sort the orderings and reverse it so the latest time is first
				 */
				for (Ping p : dayPing) {
					if (!pingOrdering.contains(p.getTime())) {
						pingOrdering.add(p.getTime());
					}
					pingShark.put(p.getTime(), p.getName());
					dayShark.add(p.getName());
				}
				Collections.sort(pingOrdering);
				Collections.reverse(pingOrdering);
				/*
				 * For this loop, it will stop once the latest ping for the 
				 * shark is obtained.
				 */
				for (String ping : pingOrdering) {
					if (pingShark.get(ping).equals(sharkN)) {
						clickedSharkPing = ping;
						break;
					}
						}
				
				/*
				 * This makes sure that the shark being displayed is the
				 * clicked shark
				 */
				for (String nameOfShark : followedSharkNames) {
					if (nameOfShark.equals(tempLabel.getName())) {
						search = new Search("on");
						search.setVisible(true);
						search.favouritesShark(jaws.getShark(nameOfShark),clickedSharkPing);
						setVisible(false);
					}
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}

		};
		return ac;
	}

	/**
	 * calcDist method.
	 *
	 * @param kingsLatitude the latitude of king's college
	 * @param sharkLatitude the latitude of the shark
	 * @param kingsLongitude the longitude of king's college
	 * @param sharkLongitude the longitude of the shark
	 * @return the distance of the shark from king's college
	 * 
	 * This method is based on the Haversine formula which calculates the distance
	 * between two geographical points. In our case, the distance between 
	 * King's College and the shark itself
	 */
	public static double calcDist(double kingsLatitude, double sharkLatitude, double kingsLongitude, double sharkLongitude) {

		double radius = 6371;    //average radius of earth in KM

		double longDist = Math.toRadians(sharkLongitude - kingsLongitude);
		double latDist = Math.toRadians(sharkLatitude - kingsLatitude);

		double a = Math.sin(latDist / 2) * Math.sin(latDist / 2) + Math.cos(Math.toRadians(kingsLatitude))
				* Math.cos(Math.toRadians(sharkLatitude)) * Math.sin(longDist / 2) * Math.sin(longDist / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = radius * c;
		return d;

	}

	/**
	 * sharknado method.
	 *
	 * @param longitude the longitude of the shark
	 * @param latitude the latitude of the shark
	 * @return true, if successful
	 * 
	 * This method will help the program decide if the shark is in a Sharknado event or not. It uses the Google
	 * Geocoding API which will help determine whether the shark is on land or on water and thus whether in a 
	 * Sharknado event or not.
	 */
	public boolean sharknado(String longitude, String latitude) {
		URL googleApi;
		BufferedReader reader = null;

		List<String> list = new ArrayList<String>();

		String line;
		try {

			// Sets up the Google Geocoding API.
			googleApi = new URL("http://maps.google.com/maps/api/geocode/xml?address=" + latitude + "," + longitude
					+ "&sensor=false");
			URLConnection connection = googleApi.openConnection();
			connection.connect();
			reader = new BufferedReader(new InputStreamReader(googleApi.openStream()));

			
			// Will read every line on the URL.
			int i = 0;
			while ((line = reader.readLine()) != null && i < 3) {

				list.add(line);
			}

			// If the URL shows "ZERO_RESULTS" then the shark is on Water.
			if ((list.get(2)).equals(" <status>ZERO_RESULTS>/status>")) {
				return false;
			} 
			// If the URL shows "OK" then the shark is on Land.
			else if ((list.get(2)).equals(" <status>OK</status>")) {
				return true;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;

	}
	
	/**
	 * Map listener method is used for when the user wants to see a map of their currently
	 * following sharks, with their current locations pinpointed on the flat map.
	 */
	public void mapListener() {
		btnSharkMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SharkMap map = new SharkMap();
				map.generateMap(followedSharkNames);
				map.setVisible(true);

			}

		});
	}

	/**
	 * Gets the followed sharks.
	 *
	 * @return the followedSharkNames array list for use in other classes
	 */
	public ArrayList<String> getFollowedSharks() {
		return followedSharkNames;
	}

}
