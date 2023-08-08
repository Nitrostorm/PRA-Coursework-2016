/**
 * @author Ruzzel Pescozo
 * @author Jeff Cayaban
 * @author Maria Alexandra Padilla
 * @author Anna Banasik
 */
package majorCoursework.search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import api.jaws.Jaws;
import api.jaws.Shark;
import majorCoursework.favourites.Favourites;
import majorCoursework.search.Statistics;

/**
 * The Class Search, This is a big class, this is where we search for sharks, show statistics,
 * shark of the day feature is shown here too, as well as the favourite shark.
 */
public class Search extends JFrame {

	/** 
	 * The Jaws, Favourites, SharkOfTheDay, SearchFunction classes being initialised
	 * for references
	 */
	private Jaws jaws = new Jaws("7477xYH9nFRElJuM", "JYOFqUtwpLvcyGyJ", false);
	private Favourites fav = new Favourites("off", this);
	private SharkOfTheDay sotd = new SharkOfTheDay();
	private SearchFunction sf = new SearchFunction();


	/** The Java Swing Components to create the structure of the Search frame. */
	private JPanel mainPanel, searchPanel, resultsPanel, titlePanel, comboPanel, 
				   tRangePanel, genderPanel, stagesPanel,locPanel, logoPanel, btnPanel, 
				   copyrightPanel, resultsAndScrollPanel, statBtnPanel;
	private JLabel menuLabel, tRangeLabel, genderLabel, stagesLabel, locLabel, imageLabel, 
				   copyrightLabel;
	private JScrollPane resultScrollPane;
	private JComboBox<String> tRangeCombo, genderCombo, stagesCombo, locCombo;
	private JButton searchButton, statButton;

	/** The locations arrayList for use in the comboBox for locations. */
	private ArrayList<String> locations = jaws.getTagLocations();
	
	/** The Strings for use in SearchFunction.*/
	private String selectRange, selectGender, selectStage, selectLocation;
	

	/**
	 * Instantiates a new search.
	 *
	 * @param onORoff this determines whether we use it as a Frame or for reference
	 *        of the methods.
	 */
	public Search(String onORoff) {
		if (onORoff == "off") {
		}

		else {
			setSize(1200, 600);
			setMinimumSize(new Dimension(945, 700));
			setLayout(new BorderLayout()); // main frame's layout
			setTitle("Search");
			sotd.createSharkOfTheDay();
			createComponents();
			createButtonSearchFunction();
			statBtnListener();
			JCBcreateListener(tRangeCombo, genderCombo, stagesCombo, locCombo);
		}

	}

	/**
	 * Creates the components of the Search frame.
	 */
	private void createComponents() {

		/* MAIN PANEL */
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		/* SEARCH PANEL */
		searchPanel = new JPanel(new BorderLayout());
		searchPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		searchPanel.setPreferredSize(new Dimension(300, 400));

		/* TITLE PANEL */
		titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
		menuLabel = new JLabel("Shark Tracker");
		titlePanel.add(menuLabel, BorderLayout.WEST);
		titlePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);
		searchPanel.add(titlePanel, BorderLayout.NORTH);

		/* COMBO PANEL */
		comboPanel = new JPanel();
		LayoutManager layout = new BoxLayout(comboPanel, BoxLayout.Y_AXIS);
		comboPanel.setLayout(layout);

		/* TRACKING RANGE */
		tRangePanel = new JPanel(new BorderLayout());
		tRangePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		tRangeLabel = new JLabel("Tracking Range");
		tRangePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);
		tRangePanel.add(tRangeLabel, BorderLayout.NORTH);

		String ranges[] = { "Choose", "Last 24 Hours", "Last Week", "Last Month" };
		tRangeCombo = new JComboBox<String>(ranges);
		tRangeCombo.setEditable(false);
		tRangePanel.add(tRangeCombo, BorderLayout.CENTER);
		comboPanel.add(tRangePanel);

		/* GENDER */
		genderPanel = new JPanel(new BorderLayout());
		genderPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		genderLabel = new JLabel("Gender");
		genderPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);
		genderPanel.add(genderLabel, BorderLayout.NORTH);

		String genders[] = { "Choose", "Male", "Female", "All" };
		genderCombo = new JComboBox<String>(genders);
		genderCombo.setEditable(false);
		genderPanel.add(genderCombo, BorderLayout.CENTER);
		comboPanel.add(genderPanel);

		/* STAGES OF LIFE */
		stagesPanel = new JPanel(new BorderLayout());
		stagesPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		stagesLabel = new JLabel("Stages of Life");
		stagesPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);
		stagesPanel.add(stagesLabel, BorderLayout.NORTH);

		String stages[] = { "Choose", "Mature", "Immature", "Undetermined", "All" };
		stagesCombo = new JComboBox<String>(stages);

		stagesCombo.setEditable(false);
		stagesPanel.add(stagesCombo, BorderLayout.CENTER);
		comboPanel.add(stagesPanel);

		/* TAG LOCATION */
		locPanel = new JPanel(new BorderLayout());
		locPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		locLabel = new JLabel("Tag Location");
		locPanel.add(locLabel, BorderLayout.NORTH);
		locPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);
		locCombo = new JComboBox<String>();
		locCombo.addItem("Choose");

		for (int i = 0; i < locations.size(); i++) {
			locCombo.addItem(locations.get(i));
		}

		locCombo.addItem("All");
		locCombo.setEditable(false);
		locPanel.add(locCombo, BorderLayout.CENTER);
		comboPanel.add(locPanel);

		/* SEARCH BUTTON */
		btnPanel = new JPanel(new BorderLayout());
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 7));
		searchButton = new JButton("Search");
		btnPanel.add(searchButton, BorderLayout.CENTER);
		comboPanel.add(btnPanel);

		searchPanel.add(comboPanel, BorderLayout.CENTER);

		/* STATISTICS BUTTON */
		statBtnPanel = new JPanel(new BorderLayout());
		statBtnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 7));
		statButton = new JButton("Statistics");
		statBtnPanel.add(statButton, BorderLayout.CENTER);
		comboPanel.add(statBtnPanel, BorderLayout.CENTER);

		/* LOGO */
		ImageIcon icon = new ImageIcon("library/images/SharkTrackerIcon.png");
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImage);

		imageLabel = new JLabel("", icon, JLabel.CENTER);
		logoPanel = new JPanel(new BorderLayout());
		logoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		logoPanel.add(imageLabel, BorderLayout.CENTER);
		searchPanel.add(logoPanel, BorderLayout.SOUTH);

		/* RESULTS OF SEARCH PANEL */

		resultsAndScrollPanel = new JPanel(new BorderLayout());

		resultsPanel = new JPanel();
		LayoutManager resultsLayout = new BoxLayout(resultsPanel, BoxLayout.Y_AXIS);
		resultsPanel.setLayout(resultsLayout);

		resultsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		resultScrollPane = new JScrollPane(resultsPanel);
		resultsAndScrollPanel.add(resultScrollPane, BorderLayout.CENTER);

		mainPanel.add(resultsAndScrollPanel, BorderLayout.CENTER);
		mainPanel.add(searchPanel, BorderLayout.WEST);
		mainPanel.add(sotd.getSharkOfTheDay(), BorderLayout.NORTH);

		this.add(mainPanel, BorderLayout.CENTER);

		/* COPYRIGHT */
		copyrightPanel = new JPanel(new BorderLayout());
		copyrightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		copyrightLabel = new JLabel(jaws.getAcknowledgement());
		copyrightPanel.add(copyrightLabel, BorderLayout.WEST);

		this.add(copyrightPanel, BorderLayout.SOUTH);
	}

	/**
	 * Creates the Listener for searchButton, the button calls another method.
	 */
	private void createButtonSearchFunction() {

		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				searchResults();
			}
		});
	}

	/**
	 * Creates an actionListener for the statistics button in the search frame.
	 */
	private void statBtnListener() {
		statButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Statistics stat = new Statistics(selectRange);
				stat.setVisible(true);

			}

		});
	}

	/**
	 * JCBcreateListener method, creates the listeners for the 4 JComboBoxes
	 *
	 * @param range: the range JCB
	 * @param gender: the gender JCB
	 * @param stages: the stages JCB 
	 * @param location: the location JCB
	 */
	private void JCBcreateListener(JComboBox<String> range, JComboBox<String> gender, JComboBox<String> stages,
			JComboBox<String> location) {

		range.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectRange = (String) range.getSelectedItem();
			}
		});

		gender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectGender = (String) gender.getSelectedItem();
			}

		});

		stages.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectStage = (String) stages.getSelectedItem();
			}

		});

		location.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectLocation = (String) location.getSelectedItem();
			}

		});
	}

	/**
	 * Search results, this is the method that the SearchButton calls.
	 * @see Search#createButtonSearchFunction()
	 * 
	 * we use the methods from favourites to obtain the followed sharks
	 * @see Favourites#readFile()
	 */
	private void searchResults() {

		/*
		 * MultiClickThreshhold set to 5000 (5 seconds) will prevent a series of windows
		 * from opening in that duration, 
		 */
		searchButton.setMultiClickThreshhold(5000);
		resultsPanel.removeAll();
		fav.readFile();
		HashMap<String, String> namesAndPing = new HashMap<String, String>();
		ArrayList<String> followedSharkNames = new ArrayList<String>();

		/*
		 * we first make sure that the HashMap and ArrayList is empty before sorting
		 */
		namesAndPing.clear();
		followedSharkNames.clear();
		followedSharkNames = fav.getFollowedSharks();
		try {
			/*
			 * The search function is initiated and uses the selected options from the
			 * 4 Jcombo boxes
			 */
			sf = new SearchFunction();
			/**
			 * for reference to the method
			 * @see SearchFunction#addFunction(Search, String, String, String, String)
			 */
			sf.addFunction(this, selectRange, selectGender, selectStage, selectLocation);
		}

		catch (Exception e) {
			/*
			 * If either of the 4 combo boxes don't have a selected option
			 * an error will appear
			 */
			JPanel errorPanel = new JPanel();
			errorPanel.add(new JLabel("Please ensure you have chosen an option for all dropdown lists"));
			resultsPanel.add(errorPanel);
			resultsPanel.revalidate();
			resultsPanel.repaint();
		}

		namesAndPing = sf.getNameAndTimes();

		if (sf.getFinalList().isEmpty() == true) {
			/*
			 * This is just to show the user if their query yielded no results
			 */
			JPanel emptyPanel = new JPanel();
			emptyPanel.add(new JLabel(
					"<html><center>There are no results, please try again.<center><br><center>Also, make sure that you have chosen an option for all four dropdown lists.<center></html>"));
			resultsPanel.add(emptyPanel);
			resultsPanel.revalidate();
			resultsPanel.repaint();
		}

		else {
			/*
			 * This for loop utilises the jaws api for the sharks,
			 * it creates identical layouts for every shark, obtaining and presenting
			 * their corresponding information using their get methods
			 */
			for (Shark shark : sf.getFinalList()) {

				ResultsPanel singleResultPanel = new ResultsPanel();

				/*
				 * ---------CHANGING THE FOLLOW BUTTON'S TEXT ACCORDINGLY-----
				 */
				JButton follow = new JButton();
				if (followedSharkNames.contains(shark.getName()) == true) {
					follow.setText("Following");
					fav.addFollowButton(follow, shark);
				}

				if (!followedSharkNames.contains(shark.getName()) == true) {
					follow.setText("Follow");
					fav.addFollowButton(follow, shark);
				}

				/*
				 * the setters for description JTextArea make them not editable 
				 * and nicer to look at
				 */
				JTextArea description = new JTextArea(shark.getDescription());
				description.setOpaque(false);
				description.setEditable(false);
				description.setWrapStyleWord(true);
				description.setLineWrap(true);

				/*
				 * Every piece of needed information on the shark obtained are then 
				 * simultaneously added to the singleResultPanel JPanel in this order
				 */
				singleResultPanel.getNamePane().add(new JLabel(shark.getName()));
				singleResultPanel.getGenderPane().add(new JLabel(shark.getGender()));
				singleResultPanel.getStageOfLifePane().add(new JLabel(shark.getStageOfLife()));
				singleResultPanel.getSpeciesPane().add(new JLabel(shark.getSpecies()));
				singleResultPanel.getLengthPane().add(new JLabel(shark.getLength()));
				singleResultPanel.getWeightPane().add(new JLabel(shark.getWeight()));
				singleResultPanel.getDescriptionPane().add(description, BorderLayout.CENTER);
				singleResultPanel.getPingPane().add(new JLabel("Last ping: " + namesAndPing.get(shark.getName()) + " at " + shark.getTagLocation()),BorderLayout.WEST);
				singleResultPanel.getPingPane().add(follow, BorderLayout.EAST);

				resultsPanel.add(singleResultPanel.getSingleResultPane());
				
				/*
				 * revalidate and repaint will make us able to update the results panel
				 */
				resultsPanel.revalidate();
				resultsPanel.repaint();

			}
		}
	}

	/**
	 * FavouritesShark method this is used by the Favourites class.
	 *                 When we click on a shark that we followed in the favourites window
	 *                 It will use this method to display that shark using the Search window
	 *                 @see Favourites#jlListener(String)
	 *
	 * @param s the s
	 * @param ping the ping
	 */
	public void favouritesShark(Shark s, String ping) {
		/*
		 * we clean the results panel and read the followed sharks and place them into
		 * our temporary arrayList called "followedSharkNames
		 */
		resultsPanel.removeAll();
		fav.readFile();

		ArrayList<String> followedSharkNames = new ArrayList<String>();
		followedSharkNames = fav.getFollowedSharks();

		ResultsPanel singleResultPanel = new ResultsPanel();

		JButton follow = new JButton();
		if (followedSharkNames.contains(s.getName()) == true) {
			follow.setText("Following");
			fav.addFollowButton(follow, s);
		}

		if (!followedSharkNames.contains(s.getName()) == true) {
			follow.setText("Follow");
			fav.addFollowButton(follow, s);
		}
		JTextArea description = new JTextArea(s.getDescription());
		description.setOpaque(false);
		description.setEditable(false);
		description.setWrapStyleWord(true);
		description.setLineWrap(true);

		singleResultPanel.getNamePane().add(new JLabel(s.getName()));
		singleResultPanel.getGenderPane().add(new JLabel(s.getGender()));
		singleResultPanel.getStageOfLifePane().add(new JLabel(s.getStageOfLife()));
		singleResultPanel.getSpeciesPane().add(new JLabel(s.getSpecies()));
		singleResultPanel.getLengthPane().add(new JLabel(s.getLength()));
		singleResultPanel.getWeightPane().add(new JLabel(s.getWeight()));
		singleResultPanel.getDescriptionPane().add(description, BorderLayout.CENTER);
		
		/*
		 * everything within this method is similar to the searchResults method in this class
		 * except for displaying the ping below. 
		 */
		/**
		 * There's a possibility that the shark hasn't pinged in over a month so I added a case for it
		 * and the ping of the shark is calculated so even if the shark's ping changes the next day,
		 * viewing the followed shark will always show the latest ping.
		 */
		if (ping == null) {
			singleResultPanel.getPingPane().add(new JLabel("This shark hasn't been located for over a month, no ping is available"));
		} else {
			singleResultPanel.getPingPane().add(new JLabel("Last ping: " + ping + " at " + s.getTagLocation()),BorderLayout.WEST);
		}
		singleResultPanel.getPingPane().add(follow, BorderLayout.EAST);

		resultsPanel.add(singleResultPanel.getSingleResultPane());
		resultsPanel.revalidate();
		resultsPanel.repaint();
	}
}
