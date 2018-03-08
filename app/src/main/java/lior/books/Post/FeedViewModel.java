package lior.books.Post;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

import lior.books.Post.Model.PostQuote;
import lior.books.Post.Model.PostQuoteRepository;

/**
 * Created by liord on 3/8/2018.
 */

public class FeedViewModel extends ViewModel {
    private LiveData<List<PostQuote>> posts;

    public FeedViewModel() {
        super();

        posts = PostQuoteRepository.instance.getAllPosts();
    }

    public void getImage(String url, PostQuoteRepository.GetImageListener listener) {
        PostQuoteRepository.instance.getImage(url, listener);
    }

    public LiveData<List<PostQuote>> getPosts() {
        return  posts;
    }
}
