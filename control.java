//  Authors:
//  Ben McCarron C22400034
//  Limani Tsatiashvili C22334951
//  Caitriona McCann C22408674
//
//  Topic Meddeler
//
//  Program Description:
//  This program asks users to select 2 text files to be compared agaisnt
//  each other to compare the list of common words to see if the text files
//  are about simaler topics. Before comparison the files have their stop
//  words removed (Words like "a", "the", "where") the user can add stop
//  words to be removed from the text file
//  

package com.Assignment;

import javax.swing.SwingUtilities;

public class control {
	
    public static void main(String[] args) {
        
    	
    	SwingUtilities.invokeLater(FileComparator::new);
        
    }
    
}
