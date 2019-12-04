package Logic;

import java.util.Random;

public class Util {
    public static int GetRandomNumberInRange(int min, int max) {
        if(min >= max) {
            throw new IllegalArgumentException("max must be greater than min.");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
