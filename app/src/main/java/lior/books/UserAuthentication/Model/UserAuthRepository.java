package lior.books.UserAuthentication.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.content.SharedPreferences;

import java.util.List;

/**
 * Created by liord on 3/3/2018.
 */

public class UserAuthRepository {
    public static final UserAuthRepository instance = new UserAuthRepository();
    MutableLiveData<String> userIdLiveData;

    public UserAuthRepository(){}

    public LiveData<String> UserLogin(String email, String password) {

        synchronized (this) {

            userIdLiveData = new MutableLiveData<>();

            UserAuthFirebase.UserLogin(email, password, new UserAuthFirebase.Callback<String>() {

                @Override
                public void onComplete(String data) {

                    userIdLiveData.setValue(data);
                }
            });
        }

        return userIdLiveData;
    }

    public LiveData<String> CreateUser(String email, String password) {
        synchronized (this) {

            userIdLiveData = new MutableLiveData<>();

            UserAuthFirebase.CreateUser(email, password, new UserAuthFirebase.Callback<String>() {
                @Override
                public void onComplete(String data) {

                    userIdLiveData.setValue(data);
                }
            });
        }

        return userIdLiveData;
    }
}
