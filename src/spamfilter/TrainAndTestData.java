/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spamfilter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random; 

import weka.core.Instances; 
import weka.classifiers.trees.J48; 
import weka.classifiers.bayes.NaiveBayesSimple; 
import weka.classifiers.Evaluation; 
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.functions.LibSVM; 

/**
 *
 * @author alex
 */
public class TrainAndTestData {
    
    public static void trainData() throws Exception{
        BufferedReader reader = null; 
        Instances trainData = null;
        Instances testData = null;
        
        try { 
            reader = new BufferedReader(new FileReader(SpamFilter.arffTrainFile));
            trainData = new Instances(reader);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.toString()); 
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                System.err.println(ex.toString()); 
            }
        } //Read in testdata also
        try { 
            reader = new BufferedReader(new FileReader(SpamFilter.arffTestFile));
            testData = new Instances(reader);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.toString()); 
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                System.err.println(ex.toString()); 
            }
        }
        
        
        trainData.setClassIndex(trainData.numAttributes() -1);
        testData.setClassIndex(testData.numAttributes() -1); 
        
        
        //Naive Bayes Classifier
        NaiveBayesMultinomial naive = new NaiveBayesMultinomial(); 
        naive.buildClassifier(trainData);
        
        //Testing on testdata
        Evaluation eval = new Evaluation(trainData); 
        eval.evaluateModel(naive, testData); 
        System.out.println("summary " + eval.toSummaryString());
    
        //Support vector machines rbf kernels
        /*LibSVM svm = new LibSVM(); 
        svm.buildClassifier(trainData);
        
        //Testing on testdata
        Evaluation eval = new Evaluation(trainData); 
        eval.evaluateModel(svm, testData); 
        System.out.println("summary " + eval.toSummaryString());*/
    
    }
    
}
