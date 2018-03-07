package lior.books.Post;

//import android.app.Activity;
//import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import lior.books.R;

public class PostQuoteActivity extends AppCompatActivity implements PostQuoteFragment.OnPostQuoteInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_quote);
        String userID = getIntent().getStringExtra("userID");

        PostQuoteFragment postFragment = PostQuoteFragment.newInstance(userID);
        postFragment.setListener(this);
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.add(R.id.post_container, postFragment);
        tran.commit();
    }
}
