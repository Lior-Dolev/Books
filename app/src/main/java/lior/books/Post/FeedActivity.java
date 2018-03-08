package lior.books.Post;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import lior.books.R;

public class FeedActivity extends AppCompatActivity implements FeedFragment.OnFeedInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        FeedFragment feedFragment = FeedFragment.newInstance();
        feedFragment.setListener(this);
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.add(R.id.feed_container, feedFragment);
        tran.commit();
    }
}
