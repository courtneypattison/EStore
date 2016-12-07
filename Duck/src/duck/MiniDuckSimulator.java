package duck;

/**
 *
 * @author Courtney Pattison
 */
public class MiniDuckSimulator {
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        //mallard.performQuack();
        //mallard.performFly();
        
        Duck model = new ModelDuck();
        model.setFlyBehaviour(new FlyRocketPowered());
        model.performFly();
    }
}
