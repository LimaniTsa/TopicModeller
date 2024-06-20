package com.Assignment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public class compare {
	
	// constants
	public final int top = 10;//analyse top 10 words
	// set of stopwords to cut from input files
	public Set<String> stopwords;
    
	//method to compare the contents of files selected
    public String compareFiles(File[] files) {
        if (files.length < 2) {
            return "Please select at least 2 files to compare";
            
        }

        Map<String, List<String>> fileTopWords = new HashMap<>();
        for (File file : files) {
            try {
                
            	//extract the top words from file
                List<String> topWords = extractTopWords(readFile(file), top);
                fileTopWords.put(file.getName(), topWords);
                
            } catch (IOException e) {
                return ("Error reading " + file.getName() + "File Read Error" + JOptionPane.ERROR_MESSAGE);
                
            }
        }
        
        //display results
        return compareTopWords(fileTopWords);
    }
    //fileTopWords A map containing the file names as keys and their corresponding top words as values.
    
    //method to compare top words across files and calculate the similarity of the files 
    private String compareTopWords(Map<String, List<String>> fileTopWords) {
    	
        StringBuilder result = new StringBuilder();
        fileTopWords.forEach((fileName, topWords) -> {
            result.append("Top words in ").append(fileName).append(":\n").append(String.join(", ", topWords)).append("\n");
        });

       
        List<String> fileNames = new ArrayList<>(fileTopWords.keySet());

        for (int i = 0; i < fileNames.size(); i++) {

            for (int j = i + 1; j < fileNames.size(); j++) {
            	
            	// retrieve the top words of each file
                Set<String> words1 = new HashSet<>(fileTopWords.get(fileNames.get(i)));
                Set<String> words2 = new HashSet<>(fileTopWords.get(fileNames.get(j)));
                
                words1.retainAll(words2);
                //calculate simularity of files as a percentage
                double similarity = ((double) words1.size() / top) * 100;
                
                //apend results of simalarity to string builder
                result.append("\nSimilarity between ").append(fileNames.get(i)).append(" and ").append(fileNames.get(j)).append(": ")
                      .append(String.format(" is %.2f", similarity)).append("%\n");
            }
        }

        return result.toString();
    }
    
    //method to extract the top words from the file
    //returns a list of the top words
    private List<String> extractTopWords(String content, int top) {
    	//split the content into words to be converted to lower case
        List<String> words = Arrays.asList(content.toLowerCase().split("\\W+"));
        
        return words.stream()
                    .filter(word -> !stopwords.contains(word) && word.length() > 1)
                    // group words by occurance and count
                    .collect(Collectors.groupingBy(word -> word, Collectors.counting()))
                    .entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    //limit resultsd to top words
                    .limit(top)
                    .map(Map.Entry::getKey)
                    //collect top words into a list
                    .collect(Collectors.toList());
    }
    
    //method to read contents of a file
    private String readFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }
}