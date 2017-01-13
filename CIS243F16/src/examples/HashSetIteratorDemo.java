/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.util.HashSet;
import java.util.Iterator;

public class HashSetIteratorDemo
{
    public static void main(String[] args)
    {
        HashSet<String> s = new HashSet<String>( );

        s.add("health");
        s.add("love");
        s.add("money");
        s.add("health");

        System.out.println("The set contains:");

        Iterator<String> i = s.iterator( );
        while (i.hasNext( ))
            System.out.println(i.next( ));

        i.remove( );

        System.out.println( );
        System.out.println("The set now contains:");

        i = s.iterator( );
        while (i.hasNext( ))
            System.out.println(i.next( ));

        System.out.println("End of program.");
    }
}
 


