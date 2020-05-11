import java.util.Random;

public class Helper {
    private static Random random = new Random();
    static public int chooseRandomPath(int n){
        return random.nextInt(n) + 1;
    }
    static public int chooseRandom(int n){
        return random.nextInt(n);
    }
}
