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
public class OverloadingDemo {
    
    /*public static int sum(int a, int b) {
        return a + b;
    }*/
    
    /*public static double sum(double a, double b) {
        return a + b;
    }8/
    
    /*public static int sum(double a, double b) {
        return (int)(a + b);
    }*/
    
    public static double sum(int a, double b) {
        return a + b;
    }
    
    public static double sum(double a, int b) {
        return a + b;
    }
    
    public static void main(String[] args) {
        
        System.out.println(sum(5, (double)10));
        
    }
    
}

