package lior.books.Post.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lior.books.MyApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by liord on 3/5/2018.
 */

public class PostQuoteRepository {
    public static final PostQuoteRepository instance = new PostQuoteRepository();
    MutableLiveData<List<PostQuote>> postsListliveData;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public void addPost(PostQuote post, String userID) {
        synchronized (this) {

            PostQuoteFirebase.AddPost(post, userID);
        }
    }

    public LiveData<List<PostQuote>> getAllPostsByUser(String userID) {
        synchronized (this) {
            if (postsListliveData == null) {
                postsListliveData = new MutableLiveData<>();

                final float lastUpdateDate = MyApplication.getMyContext().getSharedPreferences("TAG", MODE_PRIVATE).getFloat("lastUpdateDate", 0);

                PostQuoteFirebase.GetAllUserPostsAndObserve(userID, new PostQuoteFirebase.Callback<List<PostQuote>>() {
                    @Override
                    public void onComplete(List<PostQuote> data) {

                        if (data != null && data.size() > 0) {

                            float recentUpdate = lastUpdateDate;
                            for (PostQuote post : data) {

                                executor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppLocalStore.db.postDao().insertAll();
                                    }
                                });

                                if (post.LaseUpdated > recentUpdate) {
                                    recentUpdate = post.LaseUpdated;
                                }
                            }
                            SharedPreferences.Editor editor = MyApplication.getMyContext().getSharedPreferences("TAG", MODE_PRIVATE).edit();
                            editor.putFloat("lastUpdateDate", recentUpdate);
                        }

                        executor.execute(new Runnable() {
                            @Override
                            public void run() {

                                List<PostQuote> postsList = AppLocalStore.db.postDao().getAll();

                                postsListliveData.postValue(postsList);
                            }
                        });
                    }
                });
            }
        }
        return postsListliveData;
    }
}

