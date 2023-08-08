/**
 * The User Profile class is where registering and logging in to the user's profile is dealt with.
 * 
 * @author Maria Alexandra Padilla
 * @author Jeff Cayaban
 * @author Ruzzel Pescozo
 * @author Anna Banasik
 * 
 */

package majorCoursework.menuframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class UserProfile extends JFrame {
	
	/**
	 * userPanel - The JPanel for the user profile which holds the label, textfield, and button.
	 */
	private JPanel userPanel;
	
	/**
	 * regNameLabel - The label for the register.
	 * loginNameLabel - The label for the login.
	 */
	private JLabel regNameLabel, loginNameLabel;
	
	/**
	 * regTextField - The text field that enables the user to type their name in to register.
	 * loginTextField - The text filed that enables the user to type their name in to login.
	 */
	private JTextField regTextField, loginTextField;
	
	/**
	 * regBtn - The button that should be clicked to register.
	 * loginBtn - The button that should be clicked to login.
	 */
	private JButton regBtn, loginBtn;
	
	/**
	 * currentUser - The name of the currentUser. The current user is a static string as there is only one
	 * current user of the program at any one time, this string is static in all classes that use it. 
	 */
	private static String currentUser;
	
	/**
	 * mf - This is a reference to the MenuFrame class. This variable is used later on when UserProfile
	 * wants to use the Menu Frame class.
	 */
	
	private static MenuFrame mf = new MenuFrame("off");
	
	/**
	 * Instantiates a new user profile (Registering or Logging In).
	 *
	 * @param onOrOff When it is "on", a new User Profile JFrame is created
	 */
	
	public UserProfile(String onOrOff){
		if (onOrOff == "on"){
		setMinimumSize(new Dimension(350,150));
		setResizable(false);
		setVisible(true);
		
		userPanel = new JPanel(new BorderLayout());
		pack();
		}
	}
	
	/**
	 * This method sets the JFrame's title to "Register" and adds the components (label, text field, and button)
	 * to the userPanel which is added to the UserProfile JFrame in order for the user to register his or her name. 
	 */
	public void registerUser(){
		
		this.setTitle("Register");
		
		regNameLabel  = new JLabel("Name:", SwingConstants.LEFT);
		regNameLabel.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));
		
		regTextField = new JTextField();
		
		regBtn = new JButton("Register");
		regBtn.addMouseListener(regBtnListener());
	
		userPanel.add(regNameLabel, BorderLayout.NORTH);
		userPanel.add(regTextField, BorderLayout.CENTER);
		userPanel.add(regBtn, BorderLayout.SOUTH);
		this.add(userPanel);	
	}
	
	/**
	 * This method sets the JFrame's title to "Log In" and adds the components (label, text field, and button)
	 * to the userPanel which is added to the UserProfile's JFrame in order for the user to log in using his or her name. 
	 */
	public void loginUser(){
		
		this.setTitle("Log In");
		
		loginNameLabel  = new JLabel("Name:", SwingConstants.LEFT);
		loginNameLabel.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));
		
		loginTextField = new JTextField();
		
		loginBtn = new JButton("Login");
		loginBtn.addMouseListener(loginBtnListener());
	
		userPanel.add(loginNameLabel, BorderLayout.NORTH);
		userPanel.add(loginTextField, BorderLayout.CENTER);
		userPanel.add(loginBtn, BorderLayout.SOUTH);
		this.add(userPanel);	
	}
	
	/**
	 * This method gets the text entered in the text field when registering.
	 *
	 * @return The registered name entered in the text field when registering.
	 */
	public String getRegText(){
		return regTextField.getText();
	}
	
	/**
	 * This method gets the text entered in the text field when logging in.
	 *
	 * @return The login name entered in the text field when logging in.
	 */
	public String getLoginText(){
		return loginTextField.getText();
	}
	
	
	/**
	 * This method closes the UserProfile frame.
	 */
	public void closeFrame(){
		this.dispose();
	}
	
	/**
	 * This method adds the MouseListener for the Register button where the MouseListener checks whether the user
	 * registering already exists in the User Register List. If the user's name is not on the list of registered users,
	 * the mouse listener will add the user's name to the list of registered user. Otherwise, an error is displayed.
	 * 
	 * @return 	The mouse listener that is triggered when the register button is clicked.
	 */
	public MouseListener regBtnListener(){
		
		MouseListener regUser = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				
				String userName = getRegText();
				
				if(checkUserRegisterFile() == true){
					try {
						readUserRegisterFile(userName);
						closeFrame();
					} 
					
					catch (IOException e1) {
						e1.printStackTrace();
					}

				}
				
				if(checkUserRegisterFile() == false){ 
					try {
						readUserRegisterFile(userName);
						closeFrame();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
		};
		
		return regUser;
		
	}

	/**
	 * This method adds the MouseListener for the Login button where checks whether the user logging in has registered or not.
	 * If the user has registered, he or she will be logged in and will be able to access his or her profile.
	 * If not, then an error message that the user hasn't registered yet will appear.
	 * Moreover, if the user doesn't have any existing Favourites file, one will be created upon logging in.
	 *
	 * @return 	The mouse listener that is triggered when the login button is clicked.
	 * 			
	 */
	public MouseListener loginBtnListener(){
		
		MouseListener loginUser = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				String userName = getLoginText();
				
				if(checkUserRegisterFile() == true){					
					try {
						readExistingUser(userName);
						
						if(existingUserFavFile() == false){
							createUserFavFile();
						}
						closeFrame();
					} 
					
					catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
		};
		
		return loginUser;
	}
	
	/**
	 * This method checks whether the User Register List file contains the names of registered users.
	 *
	 * @return True, if the User Register List file contains the names of registered users.
	 */
	public boolean checkUserRegisterFile() {
		try {

			FileReader in = new FileReader("source/data/UserRegisterList.txt");
			BufferedReader reader = new BufferedReader(in);

			try {
				if(reader.readLine() !=null) {
					reader.close();
					return true;
				}
				else {
					reader.close();
					return false;
				}
			} 
			
			catch (IOException e) {
				JOptionPane.showMessageDialog(null, "No file has been read.");
			}

		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * This method reads the User Register List file.
	 *
	 * @param name The name that is being searched in the User Register List file.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void readUserRegisterFile(String name) throws IOException {
		
		BufferedReader in = null;	
		List<String> list = new ArrayList<String>();
		
		//the User Register List file is read.
		try {

			FileReader inr = new FileReader("source/data/UserRegisterList.txt");
			BufferedReader reader = new BufferedReader(inr);
		    
		    String str;
		    while ((str = reader.readLine()) != null) { //the User Register List is read line by line until it reaches the end.
		    	list.add(str);						//the String that is read is stored to list ArrayList.
		    }
			
		    if(list.contains(name)){				//If the list contains the name of the user, an error message appears.
				JOptionPane.showMessageDialog(null, "This name already exist.", "Error", JOptionPane.ERROR_MESSAGE);
			}
				
			else{
				list.add(name);						//Otherwise, add the name to the list and write all the names back to the User Register List file.
				JOptionPane.showMessageDialog(null, "The username " + name + " has been created!", "You can now log in!", JOptionPane.INFORMATION_MESSAGE);
				closeFrame();			
				try{
					File writeFile = new File("source/data/UserRegisterList.txt");
					FileOutputStream fileOutStr = new FileOutputStream(writeFile);
					PrintWriter printwriter = new PrintWriter(fileOutStr);

					for(String user : list){
						printwriter.println(user);
					}
					        
					printwriter.flush();
					printwriter.close();
					fileOutStr.close(); 
				}
					
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		} 
		
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No file has been read.");
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
		finally {
		    if (in != null) {
		        in.close();				//closes the BufferedReader
		    }
		}
	}

	/**
	 * This method reads the User Register List file and checks for the existing user.
	 *
	 * @param name The name of the person who has logged in.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void readExistingUser(String name) throws IOException{
		BufferedReader in = null;	
		List<String> list = new ArrayList<String>();
		
		//the User Register List file is being read.	
		try {
			
			FileReader inb = new FileReader("source/data/UserRegisterList.txt");
			BufferedReader reader = new BufferedReader(inb);
			
		    String str;
		    while ((str = reader.readLine()) != null) { //the User Register List is read line by line until it reaches the end.
		    	list.add(str);						//the String that is read is stored to list ArrayList.
		    }
			
		    if(list.contains(name)){				//if the list contains the name of the user who has logged in, it will display a welcome message
				JOptionPane.showMessageDialog(null, "Welcome " + name + "!", "Welcome User", JOptionPane.INFORMATION_MESSAGE);
				currentUser = name;					//set the currentUser to be the name passed in the parameter
				mf.setCurrentUser(name);			//set the currentUser's name in the Menu Frame.
				boolean bool = existingUserFavFile();
				mf.updateFavourites(bool);	//enable the favourites button if the user has favourite sharks and vice versa.
			}
				
			else{									//If the user hasn't registered but logged in, an error message will appear.
				JOptionPane.showMessageDialog(null, name + " does not exist! Please register first.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} 
		
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No file for " + currentUser + "  has been read.");
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
		finally {
		    if (in != null) {
		        in.close();
		    }
		}
	}
		
	/**
	 * This method checks whether the user who has logged in has an existing favourites file with favourite sharks stored in it.
	 *
	 * @return 	True, if there is an existing favourites file for the user that contains his or her favourite sharks.
	 * 			False otherwise.
	 */
	public boolean existingUserFavFile(){
		
		if(currentUser != null){
			BufferedReader br;
			
			try {
				br = new BufferedReader(new FileReader("source/data/"+currentUser + "ListOfFavSharks.txt"));
		
				try {
					if (br.readLine() == null) {
						br.close();
						return false;
					} 
					else {
						br.close();
						return true;
					}
				}
				
				catch (IOException e) {
					e.printStackTrace();
				}
				
			} 
			catch (FileNotFoundException e) {
				return false;
			}
			return true;
		}	
		return false;
	}

	/**
	 * This method creates a User's Favourites File for users who has logged on. 
	 */
	public void createUserFavFile(){
		
		if(currentUser != null){
						
			try{
				File writeFile = new File("source/data/" + currentUser + "ListOfFavSharks.txt");
				FileOutputStream fileOutStr = new FileOutputStream(writeFile);
				PrintWriter printwriter = new PrintWriter(fileOutStr);
					      
				printwriter.flush();
				printwriter.close();
				fileOutStr.close();
			}
				
			catch(Exception ex){
				ex.printStackTrace();
			}

		}
	}
	
	/**
	 * This method gets the name of the current user.
	 *
	 * @return The current user's name.
	 */
	public String getCurrentUser(){
		return currentUser;
	}
}
