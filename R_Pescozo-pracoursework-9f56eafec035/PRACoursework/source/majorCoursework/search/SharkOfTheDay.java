/**
 * @author Ruzzel Pescozo
 * @author Jeff Cayaban
 * @author Maria Alexandra Padilla
 * @author Anna Banasik
 */
package majorCoursework.search;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import api.jaws.Jaws;
import api.jaws.Ping;

/**
 * The Class SharkOfTheDay, this handles the current shark of the day, the shark of the day
 * will only change if it's a new day.
 */
public class SharkOfTheDay {

	/** The jaws reference being called. */
	private Jaws jaws = new Jaws("7477xYH9nFRElJuM", "JYOFqUtwpLvcyGyJ", false);

	/** The shark of the day JLabel, this is where we put the shark of the day. */
	private JLabel sharkOfTheDayLabel;
	
	/** The panel that will include the link */
	private JPanel linkPanel;
	
	/** The clickable link. */
	private JButton urlButton;
	
	/** The url */
	private URI linkURI;
	
	/** 
	 * These ping lists and sets are used to sort the list of pings.
	 * It first goes through all pings in the past month into "allPings"
	 * then removes duplicates by transferring the pings to "noDuplicatePings"
	 * Finally placing this into an ArrayList pingTimes for use. 
	 */
	private ArrayList<Ping> allPings = new ArrayList<Ping>();
	
	/** The no duplicate pings. */
	private Set<String> noDuplicatePings = new HashSet<String>();
	
	/** The ping times. */
	private ArrayList<String> pingTimes = new ArrayList<String>();

	/** The shark time and names. */
	private Map<String, String> sharkTimeAndNames = new HashMap<String, String>();
	
	/** The shark of the day. */
	private String sharkOfTheDay;
	
	/** 
	 * The current date for the shark of the day (this will change depending on the recorded date
	 * and today's date. 
	 */
	private String currentDateSOTD;
	
	/**  The normal format of the date that I will use. */
	private DateFormat normalFormat = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");

	/**
	 * Instantiates a new shark of the day, if it's a new program it will save the current date
	 * but time will be set to 00:00:00 to prevent any collisions with time when comparing dates.
	 */
	public SharkOfTheDay() {
		getAllPings();
		readSharkOfTheDayFile();
		// get current date time with Calendar()
		if (currentDateSOTD == null) {
			Random randomNumber = new Random();
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			/*
			 * When there is no date, it will also create a new shark of the day.
			 */
			currentDateSOTD = normalFormat.format(cal.getTime());	
			int randomShark = randomNumber.nextInt(pingTimes.size());
			sharkOfTheDay = sharkTimeAndNames.get(pingTimes.get(randomShark));
		}
	}

	/**
	 * Gets the all pings.
	 *
	 * This method will obtain all the pings in the pastMonth,
	 * remove duplicates and then place them into an arrayList that i will use
	 * allPings -> noDuplicatePings -> pingTimes
	 */
	private void getAllPings() {
		
		allPings = jaws.pastMonth();
		for (Ping p : allPings) {
			String name = p.getName();
			String time = p.getTime();
			sharkTimeAndNames.put(time, name);
			noDuplicatePings.add(time);
		}
		pingTimes.addAll(noDuplicatePings);
		
	}

	/**
	 * Creates the shark of the day.
	 * 
	 * This creates a new Random to get a random number.
	 * This also obtains the current date and put into a string called "timeNow" using the format I made
	 * splitTimeNow is used to split the time so I can use the year, month, and date for comparing with
	 * splitCurrentDateSOTD which is the date of the current shark of the day.
	 */
	public void createSharkOfTheDay() {
		Random randomNumber = new Random();
		Calendar todaysDate = Calendar.getInstance();
		String timeNow = normalFormat.format(todaysDate.getTime());
		String[] splitTimeNow = timeNow.split("/");
		String[] splitCurrentDateSOTD = currentDateSOTD.split("/");
		
		/*
		 * For this if statement, there are 3 statements and either of them need to be valid to change
		 * the shark of the day into a new random one.
		 * the first of the 3 compares the years,
		 * the second compares the months,
		 * the third compares the day.
		 * 
		 * If the time now is larger in year, month or day compared to the current SOTD date.
		 * then it will create a new shark and replace the date with the new date.
		 */
		if (Integer.parseInt(splitTimeNow[0])>Integer.parseInt(splitCurrentDateSOTD[0])
			||	Integer.parseInt(splitTimeNow[1])>Integer.parseInt(splitCurrentDateSOTD[1])
				||	Integer.parseInt(splitTimeNow[2])>Integer.parseInt(splitCurrentDateSOTD[2])) {
			int randomShark = randomNumber.nextInt(pingTimes.size());
			todaysDate.set(Calendar.HOUR_OF_DAY, 0);
			todaysDate.set(Calendar.MINUTE, 0);
			todaysDate.set(Calendar.SECOND, 0);
			todaysDate.set(Calendar.MILLISECOND, 0);
			String newCurrentDate = normalFormat.format(todaysDate.getTime());
			sharkOfTheDay = sharkTimeAndNames.get(pingTimes.get(randomShark));
			saveSharkOfTheDay(sharkOfTheDay, newCurrentDate);
		} 
		/*
		 * This else statement means: dont replace anything, the application is opened again on the
		 * same day as the current shark of the day.
		 */
		else {
			saveSharkOfTheDay(sharkOfTheDay, currentDateSOTD);
			readSharkOfTheDayFile();
		}
	}

	/**
	 * Save shark of the day.
	 *
	 * @param sharkName: the name of the "shark of the day"
	 * @param date: the date that the shark of the day was created
	 */
	private void saveSharkOfTheDay(String sharkName, String date) {
		try {
			/*
			 * This will create/read the shark of the day file, 
			 * the inputs are separated with commas for use when reading.
			 * This will just save the current sharkOfTheDays name and date it was created
			 */
			
			FileWriter in = new FileWriter("source/data/SharkOfTheDay.txt");
			PrintWriter printwriter = new PrintWriter(in);

			printwriter.println(sharkName + "," + date + ",");

			printwriter.flush();
			printwriter.close();
			in.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Could not input to file / need to fix file");
		}

	}
	
	/**
	 * Reads shark of the day file.
	 */
	private void readSharkOfTheDayFile() {
		try {
			/*
			 * As you can see from the for loop, it uses arrays to obtain the
			 * current shark of the day and the date it became shark of the day.
			 * This is because of the s.split(",") we used the comma as the regex
			 */
			File sotd = new File("source/data/SharkOfTheDay.txt");
			List<String> shark = Files.readAllLines(sotd.toPath());
			String[] readingFile;
			for (String s : shark) {
				readingFile = s.split(",");
				sharkOfTheDay = readingFile[0];
				currentDateSOTD = readingFile[1];
			}

		} catch (Exception ex) {
			System.out.println("Could not read from file");
		}
	}

	/**
	 * Gets the shark of the day.
	 *
	 * @return the JPanel with all the details of the shark of the day
	 *  this will be used by the search class.
	 */
	public JPanel getSharkOfTheDay() {
		
		linkPanel = new JPanel();
		LayoutManager layout = new BoxLayout(linkPanel, BoxLayout.X_AXIS);
		linkPanel.setLayout(layout);
		
		String link = jaws.getVideo(sharkOfTheDay);
		
		try {
			linkURI = new URI(link);
			
			urlButton = new JButton("<html>"+link+"</html");
			urlButton.setHorizontalAlignment(SwingConstants.LEFT);
			urlButton.setOpaque(false);
			urlButton.setBorderPainted(false);
			urlButton.setBackground(Color.WHITE);
			
			urlButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().browse(linkURI);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
				}
				
			});
			
			
			sharkOfTheDayLabel = new JLabel("Shark of the day is: " + sharkOfTheDay + ". Click on the link for the video!: ");
			linkPanel.add(sharkOfTheDayLabel);
			linkPanel.add(urlButton);
			
		} catch (URISyntaxException e) {
			sharkOfTheDayLabel = new JLabel("Shark of the day is: " +sharkOfTheDay + ". click link for video: " + link);
			linkPanel.add(sharkOfTheDayLabel);
		}

		return linkPanel;
	}
}
