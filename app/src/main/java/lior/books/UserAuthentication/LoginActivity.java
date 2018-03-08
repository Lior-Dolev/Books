package lior.books.UserAuthentication;

//import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;

import lior.books.Post.FeedActivity;
import lior.books.Post.PostQuoteActivity;
import lior.books.R;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnLoginFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = LoginFragment.newInstance();
        loginFragment.setListener(this);
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.add(R.id.login_container, loginFragment);
        tran.commit();
    }

    @Override
    public void OnSuccessfulLogin(String userID) {
        final Intent feed = new Intent(this, FeedActivity.class);
        feed.putExtra("userID", userID);
        startActivity(feed);
    }

    @Override
    public void OnLoginFailure() {
        Toast.makeText(LoginActivity.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnRegistrationClick() {
        final Intent registration = new Intent(this, RegistrationActivity.class);
        startActivity(registration);
    }
}
