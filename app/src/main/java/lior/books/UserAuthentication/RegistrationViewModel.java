package lior.books.UserAuthentication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import lior.books.UserAuthentication.Model.UserAuthRepository;

/**
 * Created by liord on 3/3/2018.
 */

public class RegistrationViewModel extends ViewModel {
    private LiveData<String> userID;

    public RegistrationViewModel() {
        super();
    }

    public LiveData<String> createUser(String email, String password) {

        this.userID = UserAuthRepository.instance.CreateUser(email, password);
        return this.userID;
    }
}
