package lior.books.Post;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import lior.books.Post.Model.PostQuote;
import lior.books.R;

public class FeedActivity extends AppCompatActivity implements FeedFragment.OnFeedInteractionListener {

    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        this.userID = getIntent().getStringExtra("userID");

        FeedFragment feedFragment = FeedFragment.newInstance();
        feedFragment.setListener(this);
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.add(R.id.feed_container, feedFragment);
        tran.commit();
    }

    @Override
    public void OnAddClick() {
        final Intent feed = new Intent(this, PostQuoteActivity.class);
        feed.putExtra("userID", this.userID);
        startActivity(feed);
    }
}
