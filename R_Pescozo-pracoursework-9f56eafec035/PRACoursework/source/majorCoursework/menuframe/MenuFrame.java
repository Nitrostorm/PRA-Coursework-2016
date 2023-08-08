/**
 * @author Ruzzel Pescozo
 * @author Jeff Cayaban
 * @author Maria Alexandra Padilla
 * @author Anna Banasik
 */
package majorCoursework.menuframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;


import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import majorCoursework.favourites.Favourites;
import majorCoursework.search.Search;

/**
 * The Class MenuFrame, this is the First GUI that the user will see, it has the main options
 * and this is the root of the program.
 */
public class MenuFrame extends JFrame {

	// Java Class references
	private Favourites favouritesWindow;
	private Search searchWindow;
	private Quiz quizWindow;
	private UserProfile user;
	
	// The Java Swing components
	private JPanel logoPanel, buttonPanel, mainPanel;
	private JButton search, quiz;
	private JLabel sharkIcon;
	private ImageIcon sharkTrackerImage;
	
	// The menu bar for registering and logging in.
	private JMenuBar menuBar;
	private JMenu register, login;
	
	/*
	 * The favourites JButton is static
	 * because we only need one instance of favourites for each user, default or registered
	 */
	private static JButton favourites;
	
	/** 
	 * The current user is a static string as there is only one current user of 
	 * the program at any one time, this string is static in all classes
	 * that use it. 
	 */
	private static String currentUser = null;
	
	/**
	 * Instantiates a new menu frame.
	 *
	 * @param onOrOff this string determines if we use the class as a frame or for reference
	 *        to the methods
	 */
	public MenuFrame(String onOrOff) {

		super("Shark Tracker");
		if (onOrOff == "on") {
			setSize(350, 400);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setMinimumSize(new Dimension(350, 400));

			setupMainPanel();
			setMenuBar();
			setupIcon();
			setupButtons();
			addRegisterandLoginFunction();

			pack();
			setVisible(true);
		}
	}

	/**
	 * Sets up the main panel of which the logo and the buttons are placed onto.
	 */
	
	public void setupMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		this.setContentPane(mainPanel);
	}

	/**
	 * Will setup the register and login options at the top of the window.
	 */
	
	public void setMenuBar() {
		menuBar = new JMenuBar();
		register = new JMenu("Register");
		login = new JMenu("Log In");

		menuBar.add(register);
		menuBar.add(Box.createHorizontalStrut(220)); // to add 200 pixels space in between
		menuBar.add(login);
		menuBar.setOpaque(false);

		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Will add the application logo into the menu frame.
	 */

	public void setupIcon() {

		try {
			ImageIcon icon = new ImageIcon("library/images/SharkTrackerIcon.png");
			Image img = icon.getImage();

			//This line below obtains the image and sets the size
			sharkTrackerImage = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
			sharkIcon = new JLabel(sharkTrackerImage);

			logoPanel = new JPanel();
			logoPanel.add(sharkIcon);
			mainPanel.add(logoPanel, BorderLayout.CENTER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Will set up the JButtons and their respective ActionListeners in the menu frame.
	 */
	
	public void setupButtons() {
		buttonPanel = new JPanel(new BorderLayout());

		search = new JButton("Search");
		favourites = new JButton("Favourites");
		quiz = new JButton("Quiz");

		buttonPanel.add(search, BorderLayout.NORTH);
		buttonPanel.add(favourites, BorderLayout.CENTER);
		buttonPanel.add(quiz, BorderLayout.SOUTH);

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		/*
		 * This part of the code is exclusive to the 
		 * default user, it checks if the default user has favourites
		 * and if not, it will not be enabled 
		 */
		if (checkFile() == false) {
			favourites.setEnabled(false);
		} else {
			favourites.setEnabled(true);
		}

		/*-----SETTING UP THE ACTIONLISTENERS FOR THE THREE BUTTONS-----*/
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				search.setMultiClickThreshhold(5000);
				searchWindow = new Search("on");
				searchWindow.setResizable(false);
				searchWindow.setVisible(true);
			}

		});

		favourites.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				favourites.setMultiClickThreshhold(5000);
				favouritesWindow = new Favourites("on", searchWindow);
				favouritesWindow.readFile();

				favouritesWindow.displayList();
				favouritesWindow.setVisible(true);
			}
		});
		
		quiz.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				quiz.setMultiClickThreshhold(5000);
				quizWindow = new Quiz();
				quizWindow.setVisible(true);
				
			}
			
		});
	}

	/**
	 * Adds the register and login function, this has a reference to the userProfile class.
	 */
	
	public void addRegisterandLoginFunction() {
		register.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				user = new UserProfile("on");
				user.registerUser();
				user.setVisible(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}

		});

		login.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				user = new UserProfile("on");
				user.loginUser();
				user.setVisible(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}

		});
	}

	/**
	 * Checks the list of favourite sharks for any stored data.
	 *
	 * @return true, if successful on reading the file and there's a shark on the list.
	 */
	
	public boolean checkFile() {
		
		if (currentUser == null) {
			try {		
				FileReader in = new FileReader("source/data/ListOfFavSharks.txt");
				BufferedReader br = new BufferedReader(in);

				try {
					if (br.readLine() != null) {
						br.close();
						return true;
					} else {
						br.close();
						return false;
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			return false;
		}
		
		return true;
	}

	/**
	 * Sets the current user. This method is used by the UserProfile class to transfer the information 
	 * on the current user to the menuFrame. The other classes will use
	 * the UserProfile's getter method for the current user but it is a different case
	 * for the MenuFrame as it is open right from the start.
	 *
	 * @param String user will be the user who logged in.
	 * 
	 */
	public void setCurrentUser(String user) {
		currentUser = user;
	}

	/**
	 * Update favourites.
	 *
	 * @param this method is used by various classes in the whole program to update the button
	 *        making it available or not depending on how many sharks the current user has followed
	 */
	public void updateFavourites(boolean b) {
		if (b == false) {
			favourites.setEnabled(false);
		} else {
			favourites.setEnabled(true);
		}
	}
}