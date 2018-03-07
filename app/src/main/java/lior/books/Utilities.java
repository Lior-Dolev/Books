package lior.books;

import java.util.Date;

/**
 * Created by liord on 3/7/2018.
 */

public class Utilities {
    public static final Utilities instance = new Utilities();

    public static String GenerateID() {
        Long milli = System.currentTimeMillis();
        return milli.toString();
    }

    public static float GetDateAsFloat() {
        Long d = new Date().getTime();
        return d.floatValue();
    }
}
