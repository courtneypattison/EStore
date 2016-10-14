package estoresearch;

/**
 * @author Courtney Bodi
 */
public class Electronic extends Product {
    
    private String maker;
    
    public Electronic() {
        maker = "";
    }

    /**
     * @return the maker
     */
    public String getMaker() {
        return maker;
    }

    /**
     * @param maker the maker to set
     * @return success of setting maker
     */
    public boolean setMaker(String maker) {
        if (maker == null) {
            System.out.println("Invalid input: you must enter a maker.");
            return false;
        } else {
            this.maker = maker;
            return true;
        }
    }
    
    /*public boolean equals(Electronic other) {
        return this.equals(other)
            && maker.equals(other.maker);
    }*/
    
    public String toString() {
        return this.toString() + System.lineSeparator()
            + maker;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Electronic elec = new Electronic();
        System.out.println(elec.getMaker());
    }
}
