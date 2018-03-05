package lior.books.Post;

//import android.app.Activity;
//import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import lior.books.R;

public class PostQuoteActivity extends AppCompatActivity implements PostQuoteFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_quote);

        PostQuoteFragment postFragment = PostQuoteFragment.newInstance();
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.add(R.id.post_container, postFragment);
        tran.commit();
    }
}
