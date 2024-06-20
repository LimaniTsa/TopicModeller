package com.Assignment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FileComparator extends JFrame {
    //this is where we declare our variables for the GUI
	private final JTextArea textArea;
    private final JButton openFileButton, addStopWordButton;
    private final JTextField stopwordField;
    compare compare = new compare();
 

    public FileComparator() {

        //set up our GUI window
    	setTitle("File Comparator");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Center the window
        setLocationRelativeTo(null);

        //initialize the stop words through a hash set to avoid duplicates
        compare.stopwords = new HashSet<>(Arrays.asList(  "the", "a", "an", "and", "or", "but", "on", "in", "at", "to", "of", "for", "with", "by", "as", "is", "are", "was", "were", "be", "being",
        "been", "this", "that", "these", "those", "such", "which", "who", "whom")); //stop words

        // Setup Layout
        setLayout(new BorderLayout());

        // Text Area to display results
        textArea = new JTextArea();

        //make non-editable
        textArea.setEditable(false);

        //enable scrolling
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Panel for buttons and text field
        JPanel controlPanel = new JPanel();
        
        //open file button
        openFileButton = new JButton("Open Files");
        openFileButton.addActionListener(this::openFiles);
        
        //add stop word button
        addStopWordButton = new JButton("Add Stop Word");
        stopwordField = new JTextField(20);
        
        //listener to add stop word
        addStopWordButton.addActionListener(this::addStopWord);

        // Set colors of open file button & font to black
        openFileButton.setForeground(Color.BLACK);
        openFileButton.setBackground(new Color(153, 170, 181));
        
        // Set colors of stop word button & font to black
        addStopWordButton.setForeground(Color.BLACK);
        addStopWordButton.setBackground(new Color(153, 170, 181));
        
        //main background colour
        controlPanel.setBackground(new Color(114,137,218));
        textArea.setBackground(new Color(144,167,248));
        
        //add things to the panel and labels
        controlPanel.add(openFileButton);
        controlPanel.add(new JLabel("Stop Word:"));
        controlPanel.add(stopwordField);
        controlPanel.add(addStopWordButton);

        //set the control panel to the top and the scroll to the center
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        //make window visible
        setVisible(true);
    }

    //method to handle the user pressing open files 
    private void openFiles(ActionEvent event) {
        //create the file chooser to allow the user to select files
    	JFileChooser fileChooser = new JFileChooser();
        
    	//allows you to select multiple files
        fileChooser.setMultiSelectionEnabled(true);
        
        //display the dialog box to get the files
        int returnValue = fileChooser.showOpenDialog(this);

        //see if the user clicked on open 
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            //if the user clicked on open then get an array of the files
        	File[] files = fileChooser.getSelectedFiles();
            
        	//call the compare method to compare the files in compare.java
        	String result = compare.compareFiles(files);
            
        	//display the result in the text area
        	textArea.setText(result); // Display the result in the text area
        }
    }

    //method to add new stop words that arent included in initial list
    private void addStopWord(ActionEvent event) {
        
    	// Get the new stop word from the text field without the whitespaces
    	String newstopword = stopwordField.getText().trim();
        
    	//check to see that its not empty
    	if (!newstopword.isEmpty()) {
            
    		//change it to lowercase to not mess up the comparison
    		compare.stopwords.add(newstopword.toLowerCase());
            
    		//tell the user that the stop word was added
    		JOptionPane.showMessageDialog(this, "Added \"" + newstopword + "\" to stop words.");
            
    		// Clear the field after adding stop word
            stopwordField.setText(""); 
        }
    }
}
