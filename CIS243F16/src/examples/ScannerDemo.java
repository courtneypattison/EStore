/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

/**
 *
 * @author fsong
 */

import java.util.Scanner;

public class ScannerDemo {
    
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
    
        System.out.println("Enter the number of pads followed by");
        System.out.println("the number of peas in a pod:");
        
        int numberOfPods = keyboard.nextInt();
        
        int peasPerPod = keyboard.nextInt();
        
        int totalNumberOfPeas = numberOfPods * peasPerPod;
        
        System.out.print(numberOfPods + " pods and ");
        System.out.println(peasPerPod + " peas per pod.");
        System.out.println("The total number of peas = " 
                + totalNumberOfPeas);
    }
}

