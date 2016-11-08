package playground;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author courtney
 */
public class Playground {

    public static final String NEW_LINE = System.getProperty("line.separator");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new FileInputStream("test.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("test.txt was not found or could not be opened");
            System.exit(0);
        }
        
        while (inputStream.hasNextLine()) {
            System.out.println(inputStream.nextLine());
        }
        
        inputStream.close();
    }
    
}
