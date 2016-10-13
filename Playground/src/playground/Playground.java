
package playground;

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

    }
    
    public double average(int[] integers) {
        double sum = 0;
        for (int i = 0; i < integers.length; i++) {
            sum += integers[i];
        } 
        return (sum / (double)integers.length);
    }
}
