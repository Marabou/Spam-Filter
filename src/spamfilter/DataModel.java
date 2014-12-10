/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alex
 */
public class DataModel {
    static String[] words; 
    static int numberOfWords; 
    static final int wordLengthLimit = 4; 
    static HashMap<String, Boolean> bloomFilter = new HashMap<String, Boolean>(); 
    static HashMap<String, Integer> wordToIndex = new HashMap<String, Integer>(); 
    
    public static void initiateModel(String rawDataPath) throws IOException{
        File hamFile;
        File spamFile;  
        
        if(SpamFilter.small){
            hamFile = new File(rawDataPath + "SmallHam");
            spamFile = new File(rawDataPath + "SmallSpam");
        }
        else{
            hamFile = new File(rawDataPath + "Ham");
            spamFile = new File(rawDataPath + "Spam");
        }
        
        // Find all words from the ham emails
        for(File file: hamFile.listFiles()){
            try { 
                BufferedReader b = new BufferedReader(new FileReader(file));
                String line = b.readLine(); 
                while(line != null){
                    buildBloomFilter(line);
                    line = b.readLine(); 
                }
            } catch (FileNotFoundException ex) {
                System.err.println(ex.toString());
            }
        }
        
        // Find all words from the ham emails
        for(File file: spamFile.listFiles()){
            try { 
                BufferedReader b = new BufferedReader(new FileReader(file));
                String line = b.readLine(); 
                while(line != null){
                    buildBloomFilter(line);
                    line = b.readLine(); 
                }
            } catch (FileNotFoundException ex) {
                System.err.println(ex.toString());
            }
        }
        
        //Read off the bloom filter to get the words
        numberOfWords = bloomFilter.size(); 
        words = new String[numberOfWords]; 
        int index = 0; 
        for(String word: bloomFilter.keySet()){
            words[index] = word; 
            wordToIndex.put(word, index); 
            index++; 
        }
        
        //JUST FOR FUN
        /*for(String word: bloomFilter.keySet()){
            System.out.println(word); 
        }
        System.out.println(bloomFilter.size()); */
    }
    
    /**
     * Builds the bloom filter by receiving text input
     * 
     * @param fileText 
     */
    private static void buildBloomFilter(String fileText){
        Scanner textScanner = new Scanner(fileText); 
        String current = textScanner.next(); 
        while(current != null){
            if(current.length() >= wordLengthLimit){
                if(bloomFilter.get(current) == null){
                    try{
                       Integer.parseInt(current);    //Avoid numbers 
                    }
                    catch(NumberFormatException e){
                        bloomFilter.put(current, Boolean.TRUE); 
                    }
                }
            }
            if(textScanner.hasNext() == true)
                current = textScanner.next(); 
            else
                current = null; 
        }
    }
}
