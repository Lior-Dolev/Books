package lior.books.UserAuthentication.Model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import lior.books.Post.PostQuoteActivity;
import lior.books.UserAuthentication.LoginActivity;
import lior.books.UserAuthentication.RegistrationActivity;

/**
 * Created by liord on 3/4/2018.
 */

public class UserAuthFirebase {
    final static FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public interface Callback<T> {
        void onComplete(T data);
    }

    public static void UserLogin(String email, String password, final Callback<String> callback) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            callback.onComplete(user.getUid());
                        }
                        else {
                            callback.onComplete(null);
                        }
                    }
        });

    }

    public static void CreateUser(String email, String password, final Callback<String> callback) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    callback.onComplete(user.getUid());
                }
                else {
                    callback.onComplete(null);
                }
            }
        });
    }
}
