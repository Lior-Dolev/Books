package lior.books;

import android.app.Activity;
import android.os.Bundle;

import lior.books.Models.ModelFireBase;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ModelFireBase fireBase = new ModelFireBase();
    }
}
