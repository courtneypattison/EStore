package playground;

import java.util.function.*;

/**
 *
 * @author courtney
 */
public class Playground {

    public static <V> Function<V, V> identityFunction() {
        return value -> value;
    }
    
    public static void greetWorld(Function<String, String> greeter) {
        System.out.println(greeter.apply("world"));
    }
    
    public static void function1(Supplier<Number> function) {
        System.out.println(function.get());
    }
    
    public static void concatinate(BiFunction<String, String, String> concat, String one, String two) {
        System.out.println(concat.apply(one, two));
    }
    
    public static void main(String[] args) {
        String greeting = "Hello, ";
        Function<String, String> greeter = whom -> greeting + whom + "!";
        greetWorld(greeter);
        
        Supplier<Number> function = () -> 1;
        function1(function);
        
        BiFunction<String, String, String> concat = (a, b) -> a + b;
        concatinate(concat, "one", "two");
    }
}
