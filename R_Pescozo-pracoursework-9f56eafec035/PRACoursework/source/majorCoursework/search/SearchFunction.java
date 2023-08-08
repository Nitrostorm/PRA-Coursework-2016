/**
 * This is the Search Function class where the sorting for the search is done.
 * 
 * @author Maria Alexandra Padilla
 * @author Jeff Cayaban
 * @author Ruzzel Pescozo
 * @author Anna Banasik
 * 
 */

package majorCoursework.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import api.jaws.Jaws;
import api.jaws.Ping;
import api.jaws.Shark;

public class SearchFunction {

	/**
	 * search - This is a reference to the Search class. This variable is used later on when SearchFunction
	 * wants to use the Search class.
	 */
	public Search search;
	
	/**
	 * jaws - This is used to connect to the jaws API in order to use the methods contained in this API.
	 */
	private Jaws jaws = new Jaws("7477xYH9nFRElJuM", "JYOFqUtwpLvcyGyJ", false);

	/**
	 * allPings - The ArrayList that contains all the Shark's pings depending on their tracking range.
	 */
	private ArrayList<Ping> allPings = new ArrayList<Ping>();
	
	/**
	 * allSharks - The ArrayList that contains all the sharks.
	 */
	private ArrayList<Shark> allSharks = new ArrayList<Shark>();
	
	/**
	 * filteredGender - The ArrayList that contains all the sharks filtered by the chosen gender.
	 */
	private ArrayList<Shark> filteredGender = new ArrayList<Shark>();
	
	/**
	 * filteredStage - The ArrayList that contains all the sharks filtered by their stage of life.
	 */
	private ArrayList<Shark> filteredStage = new ArrayList<Shark>();
	
	/**
	 * finalList - The ArrayList that contains all the shark that has been filtered according to the chosen
	 * tracking range, gender, stage of life, and tag location.
	 */
	private ArrayList<Shark> finalList = new ArrayList<Shark>();
	
	/**
	 * pingTimes - The ArrayList that contains the ping times of the sharks.
	 */
	private ArrayList<String> pingTimes = new ArrayList<String>();
	
	/**
	 * allTimeSharkMap - The HashMap that contains the shark's ping time and the shark's name.
	 */
	private Map<String, Shark> allTimeSharkMap = new HashMap<String, Shark>();
	
	/**
	 * mapStarter - The HashSet that contains removed duplicates of the ping times of the sharks.
	 */
	private Set<String> mapStarter = new HashSet<String>();
	
	/**
	 * sharkNameAndKey - The LinkedHashMap that contains the sharks' names and ping times.
	 */
	private LinkedHashMap<String, String> sharkNameAndKey = new LinkedHashMap<String, String>();

	/**
	 * Instantiates a new search function.
	 */
	public SearchFunction() {
	
	}

	/**
	 * This method sorts the range and ping times of the sharks.
	 *
	 * @param range The selected Tracking range (either Last 24 Hours, Last Week, or Last Month)
	 * @return The Array List of the Sharks in order of the latest ping.
	 * @see jaws
	 * 
	 */
	public ArrayList<Shark> firstTwoSearch(String range) {
		
		String selectedRange = range;

		/*---------STAGE 1: Sorting the Range---------*/

		if (selectedRange.equals("Last 24 Hours")) {
			allPings = jaws.past24Hours();
		}
		if (selectedRange.equals("Last Week")) {
			allPings = jaws.pastWeek();
		}
		if (selectedRange.equals("Last Month")) {
			allPings = jaws.pastMonth();
		}
		
		/*----------STAGE 2: Sorting ping times--------*/
		
		for (Ping p : allPings) {
			pingTimes.add(p.getTime());
		}
		
		Collections.sort(pingTimes);
		Collections.reverse(pingTimes);

		//creating hashmap that links ping time to shark
		
		for (Ping p : allPings) {
			String name = p.getName();
			String time = p.getTime();
			allTimeSharkMap.put(time, jaws.getShark(name));
		}

		//mapstarter is used to only have the latest ping time for each unique shark
		
		for (int i = 0; i < pingTimes.size(); i++) {
			for (int j = i + 1; j < pingTimes.size(); j++) {
				if (allTimeSharkMap.get(pingTimes.get(i)).getName().equals(allTimeSharkMap.get(pingTimes.get(j)).getName())) {
					mapStarter.add(pingTimes.get(j));
				}
			}
		}
		
		//REMOVES DUPLICATE SHARKS
		
		for (String s : mapStarter){
			for(int i = 0; i<pingTimes.size(); i++){
				if (s.equals(pingTimes.get(i))){
					pingTimes.remove(i);
				}
			}
		}
		
		//This will add the ArrayList of sharks and sharkmap in order of latest ping
		
		for (String s : pingTimes){
			sharkNameAndKey.put(allTimeSharkMap.get(s).getName(), s);
			allSharks.add(allTimeSharkMap.get(s));
		}
		return allSharks;
	}
	
	/**
	 * This method filters the sharks by the selected gender. From there, the sharks are filtered by their stage of life.
	 * From the filtered stage of life, the sharks are filtered according to the selected tag location.
	 *
	 * @param search The Search class.
	 * @param range The tracking range selected (either Last 24 Hours, Last Week, or Last Month)
	 * @param gender The selected gender (Male, Female, All)
	 * @param stage The selected stage of life (Mature, Immature, Undetermined or All)
	 * @param location The selected location or "All" locations.
	 */
	public void addFunction(Search search, String range, String gender, String stage, String location) {
		
		allSharks = firstTwoSearch(range);
		
		/*---------STAGE 3: CHECKS FOR GENDER-----------*/
		
		for (Shark s : allSharks) {
			if (s.getGender().equals(gender)) {
				filteredGender.add(s);
			}
		}
		if (gender.equals("All")) {
			filteredGender = allSharks;
		}
		
		/*---------STAGE 4: CHECKS FOR STAGE OF LIFE------------*/

		for (Shark s : filteredGender) {
			if (s.getStageOfLife().equals(stage)) {
				filteredStage.add(s);
			}
		}

		if (stage.equals("All")) {
			filteredStage = filteredGender;
		}
		
		/*----------STAGE 5: CHECKS FOR LOCATION-------------*/

		for (Shark s : filteredStage) {
			if (s.getTagLocation().equals(location)) {
				finalList.add(s);
			}
		}

		if (location.equals("All")) {
			finalList = filteredStage;
		}
		
	}

	/**
	 * This method gets the FinalList ArrayList of Sharks.
	 *
	 * @return The finalList ArrayList of Sharks.
	 */
	public ArrayList<Shark> getFinalList() {
		return finalList;
	}
	
	/**
	 * This method gets the LinkedHashMap of the Shark's name and their Ping times.
	 *
	 * @return The LinkedHashMap that contains the Shark's name and their corresponding Ping times.
	 * @see Search#searchResults()
	 */
	public LinkedHashMap<String, String> getNameAndTimes(){
		return sharkNameAndKey;
	}
	
	
}
