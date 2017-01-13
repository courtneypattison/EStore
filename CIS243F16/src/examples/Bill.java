/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.util.Scanner;

public class Bill {

    public static final double RATE = 150.00; //Dollars per quarter hour
    private int hours;
    private int minutes;
    private double fee;
    
    public Bill(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }
    
    public Bill() {
        hours = 0;
        minutes = 0;
    }
    
    public int getHours() {
        return hours;
    }
    
    public boolean setHours(int h) {
        if( h>= 0 ) {
            hours = h;
            return true;
        } else
            return false;
    }
    
    public int getMinutes() {
        return minutes;
    }
    
    public boolean setMinutes(int m) {
        if( m>= 0 ) {
            minutes = m;
            return true;
        } else
            return false;
    }
    
    public double getFee() {
        return fee;
    }
    
    public boolean equals(Bill other) {
        if (other == null) {
            return false;
        } else {
            return hours == other.hours
                    && minutes == other.minutes
                    && fee == other.fee;
        }
    }

    public String toString() {
        return hours + " hours, " + minutes + " minutes for a fee of $" + fee;
    }
    
    /*public void inputTimeWorked() {
        System.out.println("Enter number of full hours worked");
        System.out.println("followed by number of minutes:");
        Scanner keyboard = new Scanner(System.in);
        hours = keyboard.nextInt();
        minutes = keyboard.nextInt();
    }*/

    private static double computeFee(int hoursWorked, int minutesWorked) {
        minutesWorked = hoursWorked * 60 + minutesWorked;
        int quarterHours = minutesWorked / 15;   // Any remaining fraction of a
        // quarter hour is not charged for.
        return quarterHours * RATE;
    }

    public void updateFee() {
        fee = computeFee(hours, minutes);
    }

    /*public void outputBill() {
        System.out.println("Time worked: ");
        System.out.println(hours + " hours and " + minutes + " minutes");
        System.out.println("Rate: $" + RATE + " per quarter hour.");
        System.out.println("Amount due: $" + fee);
    }*/
    
    public static void main(String[] args) {
        System.out.println("Welcome to the law offices of");
        System.out.println("Dewey, Cheatham, and Howe.");
        System.out.println("Enter number of full hours worked");
        System.out.println("followed by number of minutes:");
        Scanner keyboard = new Scanner(System.in);
        int hours = keyboard.nextInt();
        int minutes = keyboard.nextInt();
        Bill yourBill = new Bill(hours, minutes);
        //yourBill.RATE = 100.0;
        //yourBill.inputTimeWorked();
        yourBill.updateFee();
        //yourBill.outputBill( );
        System.out.println(yourBill.toString());
        System.out.println("We have placed a lien on your house.");
        System.out.println("It has been our pleasure to serve you.");
    }
}

