package duck;

/**
 *
 * @author Courtney Pattison
 */
public class Quack implements QuackBehaviour {
    @Override
    public void quack() {
        System.out.println("Quack");
    }
}
