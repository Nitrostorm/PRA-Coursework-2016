/**
 * This is the Quiz that will be displayed when the 'Quiz' button is clicked from the menu screen.
 * 
 * @author Jeff Cayaban
 * @author Maria Alexandra Padilla
 * @author Ruzzel Pescozo
 * @author Anna Banasik
 * 
 */

package majorCoursework.menuframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Quiz extends JFrame {

	/** JPanels are required to store the questions, answers, buttons and the main panel */
	private JPanel questionsBase, buttonBase, mainBase, q1MainPanel, q2MainPanel, q3MainPanel, q4MainPanel, q5MainPanel, titlePanel, q1Answer, q2Answer, q3Answer, q4Answer, q5Answer;
	
	/** The button groups for each question in the quiz. */
	private ButtonGroup qButtons, q2Buttons, q3Buttons, q4Buttons, q5Buttons;
	
	/** The check button which will be clicked on to check whether the user's answers are correct. */
	private JButton check;
	
	/** JLabels are created to inform the user as to how to use the quiz. */
	private JLabel titleLabel, instructionLabel;
	
	/** Will allow the user to scroll vertically or horizontally, if the questions goes outside the visible space of the window. */
	private JScrollPane jsp;
	
	/** Will store their respective questions */
	private JTextArea q1, q2, q3, q4, q5;
	
	/** The buttons required to allow the user to select their chosen answer. */
	private JRadioButton q1a1btn, q1a2btn, q1a3btn, q2a1btn, q2a2btn, q2a3btn, q3a1btn, q3a2btn, q3a3btn, q4a1btn, q4a2btn, q4a3btn, q5a1btn, q5a2btn;
	
	/** Will store the scores for each of the questions and one that will add the other scores. */
	private int q1FinalAns, q2FinalAns, q3FinalAns, q4FinalAns, q5FinalAns, finalScore;

	/**
	 * Will construct a new Quiz window with five different questions.
	 */
	
	public Quiz() {
		
		super("Shark Quiz");
		setMinimumSize(new Dimension(800, 730));
		setResizable(false);
		setLayout(new BorderLayout());
		
		createComponents();
		
		setVisible(true);
		
	}
	
	/**
	 * Will create the layout and interface for the user to interact with the Quiz.
	 */
	
	public void createComponents() {
		
		// Title
		
		titlePanel = new JPanel(new BorderLayout());
		
		titleLabel = new JLabel("<html><span style='font-size:20px'>Shark Quiz</span></html>", JLabel.CENTER);
		instructionLabel = new JLabel("<html><span style='font-size:10px'>Instructions: Click on the answer you think is right and then click 'Check' to see your score.</span></html>", JLabel.CENTER);
		
		titlePanel.add(titleLabel, BorderLayout.NORTH);
		titlePanel.add(instructionLabel, BorderLayout.CENTER);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		
		// Base Panel - Main
		
		mainBase = new JPanel(new BorderLayout());
		
		// Base Panel - Questions
		
		questionsBase = new JPanel();
		LayoutManager layout = new BoxLayout(questionsBase, BoxLayout.Y_AXIS);
		questionsBase.setLayout(layout);
		jsp = new JScrollPane(questionsBase, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// Base Panel - Button
		
		buttonBase = new JPanel(new BorderLayout());
		
		generateQuestions();
		generateQuizButton();
		
		// FINAL
		
		mainBase.add(titlePanel, BorderLayout.NORTH);
		mainBase.add(jsp, BorderLayout.CENTER);
		mainBase.add(buttonBase, BorderLayout.SOUTH);
		
		this.add(mainBase, BorderLayout.CENTER);
			
	}
	
	/**
	 * Will generate the five questions that is required for the quiz.
	 */
	
	public void generateQuestions() {
		
		// ----------------QUESTION1 1------------------ //

		q1MainPanel = new JPanel(new BorderLayout());
		q1MainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// --------------------------------------------- //
		
		// The question.
		q1 = new JTextArea("1) Which of the following sharks is a Tiger Shark?");
		q1.setOpaque(false);
		q1.setEditable(false);
		q1.setWrapStyleWord(true);
		q1.setLineWrap(true);
		
		q1Answer = new JPanel(new GridLayout(3,1));
		
		// The possible answers for this question.
		q1a1btn = new JRadioButton("Lampiao");
		q1a2btn = new JRadioButton("Bill Nye");
		q1a3btn = new JRadioButton("Daymond");
		
		qButtons = new ButtonGroup();
		
		qButtons.add(q1a1btn);
		qButtons.add(q1a2btn);
		qButtons.add(q1a3btn);
		
		// The ActionListener which will handle the event when the user clicks on a JRadioButton.
		ActionListener q1BtnListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton q1Button =  (AbstractButton) e.getSource();
				
				if(q1a1btn.isSelected() == true) {
					// If the user selects the correct answer then the score for this question will be 1.
					q1FinalAns = 1;
				}
				else if(q1a2btn.isSelected() == true || q1a3btn.isSelected() == true) {
					// If the user selects any incorrect answer then the score for this question will be 0.
					q1FinalAns = 0;
				}
			}
		};
		
		// Adds the ActionListener to each of the JRadioButtons.
		q1a1btn.addActionListener(q1BtnListener);
		q1a2btn.addActionListener(q1BtnListener);
		q1a3btn.addActionListener(q1BtnListener);
		
		q1Answer.add(q1a1btn);
		q1Answer.add(q1a2btn);
		q1Answer.add(q1a3btn);
		
		// Adds the question and the answers to its respective panel.
		q1MainPanel.add(q1, BorderLayout.NORTH);
		q1MainPanel.add(q1Answer, BorderLayout.CENTER);
		
		questionsBase.add(q1MainPanel);
		
		
		// ----------------QUESTION 2------------------ //

		q2MainPanel = new JPanel(new BorderLayout());
		q2MainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// --------------------------------------------- //
		
		// The question.
		q2 = new JTextArea("2) Which of the following sharks has a name whose meaning is Miracle?");
		q2.setOpaque(false);
		q2.setEditable(false);
		q2.setWrapStyleWord(true);
		q2.setLineWrap(true);
		
		q2Answer = new JPanel(new GridLayout(3,1));
		
		// The possible answers for this question.
		q2a1btn = new JRadioButton("Freo");
		q2a2btn = new JRadioButton("iSimangaliso");
		q2a3btn = new JRadioButton("Alex");
		
		q2Buttons = new ButtonGroup();
		
		q2Buttons.add(q2a1btn);
		q2Buttons.add(q2a2btn);
		q2Buttons.add(q2a3btn);
		
		// The ActionListener which will handle the event when the user clicks on a JRadioButton.
		ActionListener q2BtnListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton q2Button =  (AbstractButton) e.getSource();
				
				// If the user selects the correct answer then the score for this question will be 1.
				if(q2a2btn.isSelected() == true) {
					q2FinalAns = 1;
				}
				// If the user selects any incorrect answer then the score for this question will be 0.
				else if(q2a1btn.isSelected() == true || q2a3btn.isSelected() == true){
					q2FinalAns = 0;
				}
			}
		};
		
		// Adds the ActionListener to each of the JRadioButtons.
		q2a1btn.addActionListener(q2BtnListener);
		q2a2btn.addActionListener(q2BtnListener);
		q2a3btn.addActionListener(q2BtnListener);
		
		q2Answer.add(q2a1btn);
		q2Answer.add(q2a2btn);
		q2Answer.add(q2a3btn);
		
		// Adds the question and the answers to its respective panel.
		q2MainPanel.add(q2, BorderLayout.NORTH);
		q2MainPanel.add(q2Answer, BorderLayout.CENTER);
		
		questionsBase.add(q2MainPanel);
		
		// ----------------QUESTION 3------------------ //

		q3MainPanel = new JPanel(new BorderLayout());
		q3MainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// --------------------------------------------- //
		
		// The question.
		q3 = new JTextArea("3) Which shark is named after a famous scientist?");
		q3.setOpaque(false);
		q3.setEditable(false);
		q3.setWrapStyleWord(true);
		q3.setLineWrap(true);
		
		q3Answer = new JPanel(new GridLayout(3,1));
		
		// The possible answers for this question.
		q3a1btn = new JRadioButton("Galileo");
		q3a2btn = new JRadioButton("Newton");
		q3a3btn = new JRadioButton("Einstein");
		
		q3Buttons = new ButtonGroup();
	
		q3Buttons.add(q3a1btn);
		q3Buttons.add(q3a2btn);
		q3Buttons.add(q3a3btn);
		
		// The ActionListener which will handle the event when the user clicks on a JRadioButton.
		ActionListener q3BtnListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton q3Button =  (AbstractButton) e.getSource();
				
				// If the user selects the correct answer then the score for this question will be 1.
				if(q3a3btn.isSelected() == true) {
					q3FinalAns = 1;
				}
				// If the user selects any incorrect answer then the score for this question will be 0.
				else if(q3a2btn.isSelected() == true || q3a3btn.isSelected() == true){
					q3FinalAns = 0;
				}
			}
		};
		
		// Adds the ActionListener to each of the JRadioButtons.
		q3a1btn.addActionListener(q3BtnListener);
		q3a2btn.addActionListener(q3BtnListener);
		q3a3btn.addActionListener(q3BtnListener);
		
		// Adds the question and the answers to its respective panel.
		q3Answer.add(q3a1btn);
		q3Answer.add(q3a2btn);
		q3Answer.add(q3a3btn);
		
		q3MainPanel.add(q3, BorderLayout.NORTH);
		q3MainPanel.add(q3Answer, BorderLayout.CENTER);
		
		questionsBase.add(q3MainPanel);
		
		// ----------------QUESTION 4------------------ //

		q4MainPanel = new JPanel(new BorderLayout());
		q4MainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// --------------------------------------------- //
		
		// The question.
		q4 = new JTextArea("4) Which of the following sharks in a Male?");
		q4.setOpaque(false);
		q4.setEditable(false);
		q4.setWrapStyleWord(true);
		q4.setLineWrap(true);
		
		q4Answer = new JPanel(new GridLayout(3,1));
		
		// The possible answers for this question.
		q4a1btn = new JRadioButton("Jax");
		q4a2btn = new JRadioButton("Lampiao");
		q4a3btn = new JRadioButton("Finley");
		
		q4Buttons = new ButtonGroup();
	
		q4Buttons.add(q4a1btn);
		q4Buttons.add(q4a2btn);
		q4Buttons.add(q4a3btn);
		
		// The ActionListener which will handle the event when the user clicks on a JRadioButton.
		ActionListener q4BtnListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton q4Button =  (AbstractButton) e.getSource();
				
				// If the user selects the correct answer then the score for this question will be 1.
				if(q4a2btn.isSelected() == true) {
					q4FinalAns = 1;
				}
				// If the user selects any incorrect answer then the score for this question will be 0.
				else if(q4a2btn.isSelected() == true || q4a3btn.isSelected() == true){
					q4FinalAns = 0;
				}
			}
		};
		
		// Adds the ActionListener to each of the JRadioButtons.
		q4a1btn.addActionListener(q4BtnListener);
		q4a2btn.addActionListener(q4BtnListener);
		q4a3btn.addActionListener(q4BtnListener);
		
		// Adds the question and the answers to its respective panel.
		q4Answer.add(q4a1btn);
		q4Answer.add(q4a2btn);
		q4Answer.add(q4a3btn);
		
		q4MainPanel.add(q4, BorderLayout.NORTH);
		q4MainPanel.add(q4Answer, BorderLayout.CENTER);
		
		questionsBase.add(q4MainPanel);
		
		// ----------------QUESTION 5------------------ //

		q5MainPanel = new JPanel(new BorderLayout());
		q5MainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// --------------------------------------------- //
		
		// The question.
		q5 = new JTextArea("5) Is Mary Lee a Hammerhead Shark?");
		q5.setOpaque(false);
		q5.setEditable(false);
		q5.setWrapStyleWord(true);
		q5.setLineWrap(true);
		
		q5Answer = new JPanel(new GridLayout(3,1));
		
		// The possible answers for this question.
		q5a1btn = new JRadioButton("True");
		q5a2btn = new JRadioButton("False");
		
		q5Buttons = new ButtonGroup();
	
		q5Buttons.add(q5a1btn);
		q5Buttons.add(q5a2btn);
		
		// The ActionListener which will handle the event when the user clicks on a JRadioButton.
		ActionListener q5BtnListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton q5Button =  (AbstractButton) e.getSource();
				
				// If the user selects the correct answer then the score for this question will be 1.
				if(q5a1btn.isSelected() == true) {
					q5FinalAns = 1;
				}
				// If the user selects any incorrect answer then the score for this question will be 0.
				else if(q5a2btn.isSelected() == true){
					q5FinalAns = 0;
				}
			}
		};
		
		// Adds the ActionListener to each of the JRadioButtons.
		q5a1btn.addActionListener(q5BtnListener);
		q5a2btn.addActionListener(q5BtnListener);
		
		// Adds the question and the answers to its respective panel.
		q5Answer.add(q5a1btn);
		q5Answer.add(q5a2btn);
		
		q5MainPanel.add(q5, BorderLayout.NORTH);
		q5MainPanel.add(q5Answer, BorderLayout.CENTER);
		
		questionsBase.add(q5MainPanel);
		
		// -----------------------//
		
	}
	
	/**
	 * Will generate the button that will allow the user to check his or her answers.
	 */
		
	public void generateQuizButton() {
		
		check = new JButton("Check");
		
		ActionListener checkListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// The final score of all the questions.
				finalScore = q1FinalAns + q2FinalAns + q3FinalAns + q4FinalAns + q5FinalAns;
				
				// Will display the appropriate message depending on the final score.
				
				if(finalScore == 5) {
					JOptionPane.showMessageDialog(null, "Your Score: 5/5");
				}
				if(finalScore == 4) {
					JOptionPane.showMessageDialog(null, "Your Score: 4/5");
				}
				if(finalScore == 3) {
					JOptionPane.showMessageDialog(null, "Your Score: 3/5");
				}
				if(finalScore == 2) {
					JOptionPane.showMessageDialog(null, "Your Score: 2/5");
				}
				if(finalScore == 1) {
					JOptionPane.showMessageDialog(null, "Your Score: 1/5");
				}
				if(finalScore == 0) {
					JOptionPane.showMessageDialog(null, "Your Score: 0/5");
				}
			}
		};
		
		check.addActionListener(checkListener);
		buttonBase.add(check);
		
	}

}
