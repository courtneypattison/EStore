/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.util.ArrayList;

public class InheritanceDemo {

    public static void main(String[] args) {
        Employee e = new Employee("Joe Worker", new Date("January", 1, 2004));
        HourlyEmployee h = new HourlyEmployee("Joe Worker",
                new Date("January", 1, 2004), 50.50, 160);

        //ArrayList<Employee> people = new ArrayList<Employee>();
        //people.add(e);
        //people.add(h);
        //e = h;
        if (e instanceof HourlyEmployee) {
            h = (HourlyEmployee) e;
        }

        System.out.println(e.toString());
        System.out.println(h.toString());

        /*if( e.equals(h) )
         System.out.println("An Emplyee is the same as an Hourly Employee");

         if( h.equals(e))
         System.out.println("An Hourly employee is the same as an Employee");
         */
        System.out.println("joe's longer name is " + h.getName());

        System.out.println("Changing joe's name to Josephine.");
        h.setName("Josephine");

        System.out.println("joe's record is as follows:");
        System.out.println(h.toString());
    }
}
