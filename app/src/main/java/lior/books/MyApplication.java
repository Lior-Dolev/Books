package lior.books;

import android.app.Application;
import android.content.Context;

/**
 * Created by liord on 3/7/2018.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    public static Context getMyContext(){
        return context;
    }
}
