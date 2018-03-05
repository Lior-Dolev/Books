package lior.books.UserAuthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;

import lior.books.Post.PostQuoteActivity;
import lior.books.R;


public class RegistrationActivity extends AppCompatActivity implements RegistrationFragment.OnRegistrationFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        RegistrationFragment registrationFragment = RegistrationFragment.newInstance();
        registrationFragment.setListener(this);
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.add(R.id.registration_container, registrationFragment);
        tran.commit();
    }


    @Override
    public void OnAccountCreated(String userID) {

        final Intent feed = new Intent(this, PostQuoteActivity.class);
        feed.putExtra("userID", userID);
        startActivity(feed);
    }

    @Override
    public void OnAccountCreationFailure() {
            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OnLoginClick() {
        final Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }
}
