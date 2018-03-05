package lior.books.UserAuthentication;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


import lior.books.UserAuthentication.Model.UserAuthRepository;

/**
 * Created by liord on 3/3/2018.
 */

public class LoginViewModel extends ViewModel {
    private LiveData<String> userID;

    public LoginViewModel() {
        super();
    }

    public LiveData<String> getUserID() {
        return userID;
    }

    public LiveData<String> userLogin(String email, String password) {

        this.userID = UserAuthRepository.instance.UserLogin(email, password);
        return this.userID;
    }
}
