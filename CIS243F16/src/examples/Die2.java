/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author fsong
 */
public class Die2 {

    public static final int COMMON_FACES = 6;
    private int maxFaces;
    private int faceValue;

    public Die2(int maxFaces) throws Exception {
        if (maxFaces > 0) {
            this.maxFaces = maxFaces;
            roll();
        } else {
            try {
            throw new Exception("Fatal error. \nMax Faces must be positive");
            //System.out.println("Fatal error. \nMax Faces should be a positive integer");
            //System.exit(0);
            } catch (Exception e) {
                
            }
        }
    }

    public Die2() {
        maxFaces = COMMON_FACES;
        roll();
    }

    public static boolean valid(int maxFaces) {
        return maxFaces > 0;
    }

    public boolean equals(Die2 other) {
        if (other == null) {
            return false;
        } else {
            return maxFaces == other.maxFaces
                    && faceValue == other.faceValue;
        }

    }

    public String toString() {
        return "maxFaces = " + maxFaces + ", faceValue = " + faceValue;
    }

    public void roll() {
        faceValue = (int) (Math.random() * maxFaces + 1);
    }

    public int getFaceValue() {
        return faceValue;
    }

    public int getMaxFaces() {
        return maxFaces;
    }

    public boolean setMaxFaces(int maxFaces) {
        if (maxFaces > 0) {
            this.maxFaces = maxFaces;
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        boolean valid = false;
        while (!valid) {
            try {
                System.out.println("Enter the maximum faces: ");
                int maxFaces = keyboard.nextInt();
                Die2 die = new Die2(maxFaces);
                System.out.println(die.getFaceValue());
                valid = true;
            } catch (Exception e) {
                keyboard.nextLine();
                System.out.println("Maximum faces has to be a positive interger.");
            }
        }
    }
}
