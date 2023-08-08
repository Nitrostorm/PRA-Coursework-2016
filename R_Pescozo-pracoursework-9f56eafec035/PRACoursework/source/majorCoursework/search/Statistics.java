/**
 * This is the Statistics class which will find how many sharks are of each category and will feed the appropriate method in
 * GraphPane with the numbers needed to generate the pie charts.
 * 
 * @author Jeff Cayaban
 * @author Maria Alexandra Padilla
 * @author Ruzzel Pescozo
 * @author Anna Banasik
 * 
 */

package majorCoursework.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import api.jaws.Shark;

public class Statistics extends JFrame {
	
	/** Will help sort out the sharks in terms of ping time and remove any duplicates. */
	private SearchFunction sf;

	/** Will store the list of all sharks depending on the selected tracking range. */
	private ArrayList<Shark> allSharks;
	
	/** Will store the user's selected tracking range. */
	private String selectRange;
	
	/** Will store the pie charts on gender, stages of life and the locations of the sharks. */
	private GraphPane genderGraph, stageGraph, locationGraph;
	
	/** Will store the graph panes. */
	private JPanel mainFrame;
	
	/** Will allow the user to scroll down on the window to see the other graph's. */
	private JScrollPane jsp;

	/** Will count the amount of sharks in their respective categories. */
	private int maleCount, femaleCount, matureCount, immatureCount, undeterminedCount;
	
	/** Will store the tally of sharks in there various locations. */
	private Map<String, Integer> locationCount;
	
	/**
	 * 
	 * This will construct the layouts needed to show the charts.
	 * 
	 * @param selectRange The tracking range that was selected from the Search window.
	 */

	public Statistics(String selectRange) {

		// Sets up the frame of the Statistics window.
		
		sf = new SearchFunction();
		this.selectRange = selectRange;
		setMinimumSize(new Dimension(945, 700));
		setResizable(true);
		setLayout(new BorderLayout());

		mainFrame = new JPanel();
		LayoutManager layout = new BoxLayout(mainFrame, BoxLayout.Y_AXIS);
		mainFrame.setLayout(layout);

		jsp = new JScrollPane(mainFrame, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		this.add(jsp, BorderLayout.CENTER);

		generatePanels(selectRange);
		setVisible(true);
		pack();

	}
	
	/**
	 * This method will count the sharks in terms of their individual categories, based on the selected tracking range.
	 * 
	 * @param trackRange The tracking range that was selected from the Search window.
	 * @see GraphPane
	 */

	public void generatePanels(String trackRange) {

		// Will set the title of the window depending on the tracking range selected from the Search window.
		
		if (trackRange.equals("Last 24 Hours")) {
			setTitle("Statistics - Last 24 Hours");
		}
		if (trackRange.equals("Last Week")) {
			setTitle("Statistics - Last Week");
		}
		if (trackRange.equals("Last Month")) {
			setTitle("Statistics - Last Month");
		}
		
		
		// Will sort out the sharks in terms of ping time and remove any duplicates.
		allSharks = sf.firstTwoSearch(trackRange);

		// All counters will be initialised to a value of 0.
		maleCount = 0;
		femaleCount = 0;
		matureCount = 0;
		immatureCount = 0;
		undeterminedCount = 0;

		for (Shark s : allSharks) {
			
			// If the selected shark is a male then the counter for the male sharks will be incremented by 1.
			if (s.getGender().equals("Male")) {
				maleCount++;
			}
			// If the selected shark is a female then the counter for the female sharks will be incremented by 1.
			if (s.getGender().equals("Female")) {
				femaleCount++;
			}
			// If the selected shark is mature then the counter for the mature sharks will be incremented by 1.
			if (s.getStageOfLife().equals("Mature")) {
				matureCount++;
			}
			// If the selected shark is immature then the counter for the immature sharks will be incremented by 1.
			if (s.getStageOfLife().equals("Immature")) {
				immatureCount++;
			}
			// If the selected shark is undetermined then the counter for the undetermined sharks will be incremented by 1.
			if (s.getStageOfLife().equals("Undetermined")) {
				undeterminedCount++;
			}
		}

		locationCount = new HashMap<String, Integer>();

		// Will store how many sharks are at each location into a map.
		for (Shark s : allSharks) {
			String tagLocation = s.getTagLocation();

			if (!locationCount.containsKey(tagLocation)) {
				locationCount.put(tagLocation, 0);
			}
			if (locationCount.containsKey(tagLocation)) {
				int tempCount = locationCount.get(tagLocation);
				tempCount++;
				locationCount.put(tagLocation, tempCount);
			}
		}

		// If all the counters are at 0, then this assumes there is no data for the pie charts.
		if (maleCount == 0 && femaleCount == 0 && matureCount == 0 && immatureCount == 0 && undeterminedCount == 0 && locationCount.isEmpty()) {
			mainFrame.add(new JLabel("There is a lack of the relevant data required to display the pie charts."));
		} else {

			// Will create a new GraphPane object, which will show the pie charts, based on the values given.
			genderGraph = new GraphPane(maleCount, femaleCount);
			stageGraph = new GraphPane(matureCount, immatureCount, undeterminedCount);
			locationGraph = new GraphPane(locationCount);

			mainFrame.add(genderGraph);
			mainFrame.add(stageGraph);
			mainFrame.add(locationGraph);

		}
	}
}
