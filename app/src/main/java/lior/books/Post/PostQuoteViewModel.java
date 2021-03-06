package lior.books.Post;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.graphics.Bitmap;
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

        posts = PostQuoteRepository.instance.getAllPosts();
    }

    public void addPost(PostQuote post, String userID, Bitmap bitmap) {

        PostQuoteRepository.instance.addPost(post, userID, bitmap);
    }

    public LiveData<List<PostQuote>> getPosts() {
        return  posts;
    }
}