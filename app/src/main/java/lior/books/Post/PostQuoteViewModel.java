package lior.books.Post;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.util.Log;

import java.util.List;

import lior.books.Post.Model.PostQuote;
import lior.books.Post.Model.PostQuoteRepository;

/**
 * Created by liord on 3/5/2018.
 */

public class PostQuoteViewModel extends ViewModel {

    private LiveData<List<PostQuote>> posts;

    public PostQuoteViewModel(String userID) {

        super();

        Log.d("TAG", "user id: " + userID);
        posts = PostQuoteRepository.instance.getAllPostsByUser(userID);
    }

    public void addPost(PostQuote post, String userID) {

        Log.d("TAG", "Here");

        Log.d("TAG", userID);
        PostQuoteRepository.instance.addPost(post, userID);
    }

    public LiveData<List<PostQuote>> getPosts() {
        return  posts;
    }
}