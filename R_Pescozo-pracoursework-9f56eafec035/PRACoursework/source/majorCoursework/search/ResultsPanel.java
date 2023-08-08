/**
 * This is the Results Panel class that extends JPanel where results panel is created for each Shark.
 * 
 * @author Maria Alexandra Padilla
 * @author Jeff Cayaban
 * @author Ruzzel Pescozo
 * @author Anna Banasik
 * 
 */

package majorCoursework.search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultsPanel extends JPanel {
	
	
	/** 
	 * 	namePane - The pane that holds the shark's name.
	 * 	genderPane - The pane that holds the shark's gender.
	 * 	stageOfLifePane - The pane that holds the shark's stage of life.
	 * 	speciesPane - The pane that holds the shark's specie.
	 * 	lengthPane - The pane that holds the shark's length.
	 * 	weightPane - The pane that holds the shark's weight.
	 * 	descriptionLabelPane - The pane that holds the description label.
	 *  descriptionPane - The pane that holds the shark's description.
	 *  pingPaneAndFollow - The pane that holds the shark's latest ping time and the follow button.
	 */
	private JPanel namePane, genderPane, stageOfLifePane, speciesPane, lengthPane, weightPane, descriptionLabelPane, descriptionPane, pingPaneAndFollow;
	
	/**
	 * Instantiates a new results panel.
	 * This is where all the components are created and added to the ResultsPanel JPanel. 
	 */
	public ResultsPanel(){
			
		LayoutManager resultsLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(resultsLayout);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		namePane = new JPanel(new GridLayout(1,2));
		genderPane = new JPanel(new GridLayout(1,2));
		stageOfLifePane = new JPanel(new GridLayout(1,2));
		speciesPane = new JPanel(new GridLayout(1,2));
		lengthPane = new JPanel(new GridLayout(1,2));
		weightPane = new JPanel(new GridLayout(1,2));
		descriptionLabelPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		descriptionPane = new JPanel(new BorderLayout());
		pingPaneAndFollow = new JPanel(new BorderLayout());
		
		namePane.setBorder(BorderFactory.createEmptyBorder(5,5,2,0));
		genderPane.setBorder(BorderFactory.createEmptyBorder(5,5,2,0));
		stageOfLifePane.setBorder(BorderFactory.createEmptyBorder(5,5,2,0));
		speciesPane.setBorder(BorderFactory.createEmptyBorder(5,5,2,0));
		lengthPane.setBorder(BorderFactory.createEmptyBorder(5,5,2,0));
		weightPane.setBorder(BorderFactory.createEmptyBorder(5,5,2,0));
		descriptionLabelPane.setBorder(BorderFactory.createEmptyBorder(15,0,15,0));
		descriptionPane.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		pingPaneAndFollow.setBorder(BorderFactory.createEmptyBorder(5,5,5,0));
		
		
		JLabel name = new JLabel("Name:");
		JLabel gender = new JLabel("Gender:");
		JLabel stageOfLife = new JLabel("Stage of Life:");
		JLabel species = new JLabel("Species:");
		JLabel length = new JLabel("Length:");
		JLabel weight = new JLabel("Weight:");
		JLabel description = new JLabel("Description:");
		
		JPanel[] panes = {namePane, genderPane, stageOfLifePane, speciesPane, lengthPane, weightPane, descriptionLabelPane, descriptionPane, pingPaneAndFollow};
		
		namePane.add(name);
		genderPane.add(gender);
		stageOfLifePane.add(stageOfLife);
		speciesPane.add(species);
		lengthPane.add(length);
		weightPane.add(weight);
		descriptionLabelPane.add(description, BorderLayout.SOUTH);
		
		for (JPanel p: panes){
			this.add(p);
		}
		
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}

	/**
	 * This method gets the single result pane.
	 *
	 * @return The single result pane.
	 */
	public JPanel getSingleResultPane(){
		return this;
	}
	
	/**
	 * This method gets the name pane.
	 *
	 * @return The name pane.
	 */
	public JPanel getNamePane(){
		return namePane;
	}
	
	/**
	 * This method gets the gender pane.
	 *
	 * @return The gender pane.
	 */
	public JPanel getGenderPane(){
		return genderPane;
	}
	
	/**
	 * This method gets the stage of life pane.
	 *
	 * @return The stage of life pane.
	 */
	public JPanel getStageOfLifePane(){
		return stageOfLifePane;
	}
	
	/**
	 * This method gets the species pane.
	 *
	 * @return The species pane.
	 */
	public JPanel getSpeciesPane(){
		return speciesPane;
	}
	
	/**
	 * This method gets the length pane.
	 *
	 * @return The length pane.
	 */
	public JPanel getLengthPane(){
		return lengthPane;
	}
	
	/**
	 * This method gets the weight pane.
	 *
	 * @return The weight pane.
	 */
	public JPanel getWeightPane(){
		return weightPane;
	}
	
	/**
	 * This method gets the description pane.
	 *
	 * @return The description pane
	 */
	public JPanel getDescriptionPane(){
		return descriptionPane;
	}
	
	/**
	 * This method gets the ping pane.
	 *
	 * @return The ping pane.
	 */
	public JPanel getPingPane(){
		return pingPaneAndFollow;
	}
		
}


