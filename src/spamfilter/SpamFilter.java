/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alex
 */
public class SpamFilter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Build the data model
        try {
            DataModel.initiateModel("Data/Raw");
            RawToARFF.createARFF();
        } catch (IOException ex) {
            System.out.println(ex.toString()); 
        }
    }
    
}
