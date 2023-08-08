/**
 * This is the Graph Pane class where Statistics class will display a chart for each filter option depending on the tracking range selected.
 * 
 * @author Jeff Cayaban
 * @author Maria Alexandra Padilla
 * @author Ruzzel Pescozo
 * @author Anna Banasik
 * 
 */

package majorCoursework.search;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.util.Map;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class GraphPane extends JPanel {
	
	/** Will store the data for the pie chart. */
	private DefaultPieDataset data;
	
	/** Will store the generated pie chart from the data. */
	private JFreeChart chart;
	
	/** Stores the plot based on the pie chart. */
	private PiePlot plot;
	
	/** The object will help generate the labels needed for the pie chart. */
	private PieSectionLabelGenerator generator;
	
	/** The panel will store the pie chart*/
	private ChartPanel panel;
	
	/**
	 * This constructor will generate a pie chart showing distribution of female and male sharks.
	 * 
	 * @param maleCount The number of male sharks found.
	 * @param femaleCount The number of female sharks found.
	 * @see DefaultPieDataset
	 * @see JFreeChart
	 * @see PiePlot
	 * @see PieSecionLabelGenerator
	 * @see ChartPanel
	 * 
	 */
	
	public GraphPane(int maleCount, int femaleCount) {
		
		// Creates a new data set.
		data = new DefaultPieDataset();
		data.setValue("Male", maleCount);
		data.setValue("Female", femaleCount);
		
		// Creates a chart from the data.
		chart = ChartFactory.createPieChart("Gender", data, true, false, false);
		
		// Creates a plot based on the chart.
		plot = (PiePlot) chart.getPlot();
		plot.setNoDataMessage("Not enough relevant data available.");
		
		// Creates the labels for the chart.
		generator = new StandardPieSectionLabelGenerator("{0}: {1} - {2}", new DecimalFormat("0"), new DecimalFormat("0%"));
		plot.setLabelGenerator(generator);
		
		// Adds the chart to the JPanel.
		panel = new ChartPanel(chart);
		this.add(panel, BorderLayout.CENTER);
	
	}
	
	/**
	 * 
	 * This constructor will generate a pie chart showing distribution of the different sharks at their different stages in life.
	 * 
	 * @param matureCount The number of mature sharks found.
	 * @param immatureCount The number of immature sharks found.
	 * @param undeterminedCount The number of undetermined sharks found.
	 * @see DefaultPieDataset
	 * @see JFreeChart
	 * @see PiePlot
	 * @see PieSecionLabelGenerator
	 * @see ChartPanel
	 */
	
	public GraphPane(int matureCount, int immatureCount, int undeterminedCount) {
		
		// Creates a new data set.
		data = new DefaultPieDataset();
		data.setValue("Mature Sharks", matureCount);
		data.setValue("Immature Sharks", immatureCount);
		data.setValue("Undetermined Sharks", undeterminedCount);
		
		chart = ChartFactory.createPieChart("Stages of Life", data, true, false, false);
		
		// Creates a plot based on the chart.
		plot = (PiePlot) chart.getPlot();
		plot.setNoDataMessage("Not enough relevant data available.");
		
		// Creates the labels for the chart.
		generator = new StandardPieSectionLabelGenerator("{0}: {1} - {2}", new DecimalFormat("0"), new DecimalFormat("0%"));
		plot.setLabelGenerator(generator);
		
		// Adds the chart to the JPanel.
		panel = new ChartPanel(chart);
		this.add(panel, BorderLayout.CENTER);
			
	}
	

	/**
	 * This constructor will generate a pie chart showing the distribution of the sharks across the available locations.
	 * 
	 * @param locationCount A map of all the available locations of all the sharks.
	 * @see DefaultPieDataset
	 * @see JFreeChart
	 * @see PiePlot
	 * @see PieSecionLabelGenerator
	 * @see ChartPanel
	 */
	
	public GraphPane(Map<String, Integer> locationCount) {
		
		// Creates a new data set.
		data = new DefaultPieDataset();
		
		for(String s : locationCount.keySet()) {
			data.setValue(s, locationCount.get(s));
		}
		
		// Creates a plot based on the chart.
		chart = ChartFactory.createPieChart("Tag Locations", data, true, false, false);
		
		plot = (PiePlot) chart.getPlot();
		plot.setNoDataMessage("Not enough relevant data available.");
		
		// Creates the labels for the chart.
		generator = new StandardPieSectionLabelGenerator("{0}: {1} - {2}", new DecimalFormat("0"), new DecimalFormat("0%"));
		plot.setLabelGenerator(generator);
		
		// Adds the chart to the JPanel.
		panel = new ChartPanel(chart);
		this.add(panel, BorderLayout.CENTER);
		
	}
}
