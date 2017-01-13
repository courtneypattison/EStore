/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.util.Scanner;

/**
 *
 * @author Frank Song
 */
public class Die {

    public static final int COMMON_FACES = 6;
    private int maxFaces;
    private int faceValue;

    public Die(int maxFaces) {
        if (valid(maxFaces)) {
            this.maxFaces = maxFaces;
            roll();
        } else {
            System.out.println("Fatal error");
            System.exit(0);
        }
    }

    public Die() {
        this(COMMON_FACES);
        //maxFaces = COMMON_FACES;
        //roll();
    }

    
    public int getMaxFaces() {
        return maxFaces;
    }

    /**
     * @param maxFaces the maxFaces to set
     */
    public boolean setMaxFaces(int maxFaces) {
        if (valid(maxFaces)) {
        this.maxFaces = maxFaces;
        return true;
        } else
            return false;
    }

    /**
     * @return the faceValue
     */
    public int getFaceValue() {
        return faceValue;
    }

    public void roll() {
        faceValue = (int) (Math.random() * maxFaces + 1);
    }

    public String toString() {
        return "maxFaces = " + maxFaces + " and faceValue = " + faceValue;
    }

    public boolean equals(Die other) {
        if (other == null) {
            return false;
        } else {
            return maxFaces == other.maxFaces
                    && faceValue == other.faceValue;
        }
    }
    
    public static boolean valid(int maxFaces) {
        return maxFaces > 0;
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Common maxfaces is " + COMMON_FACES);
        boolean valid;
        do {
            valid = true;
            System.out.print("Please enter a positive integer for maxfaces: ");
            int maxFaces = input.nextInt();
            if (valid(maxFaces)) {
                Die die1 = new Die(maxFaces);
                //Die die1 = new Die();
                System.out.println(die1.toString());
                die1.roll();
                System.out.println(die1.toString());
            } else {
                System.out.println("Invalid value for maxFaces: " + maxFaces);
                valid = false;
            }
        } while (!valid);
    }
}
