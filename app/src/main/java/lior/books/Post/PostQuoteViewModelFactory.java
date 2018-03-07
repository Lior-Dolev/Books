package lior.books.Post;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.util.Log;

/**
 * Created by liord on 3/7/2018.
 */


public class PostQuoteViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String userID;

    public PostQuoteViewModelFactory(String param) {

        this.userID = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T) new PostQuoteViewModel(userID);
    }
}
