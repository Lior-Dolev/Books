package lior.books.Post.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lior.books.MyApplication;

import static android.content.Context.MODE_PRIVATE;
import static lior.books.Post.Model.PostQuote.saveImageToFile;

/**
 * Created by liord on 3/5/2018.
 */

public class PostQuoteRepository {
    public static final PostQuoteRepository instance = new PostQuoteRepository();
    MutableLiveData<List<PostQuote>> postsListliveData;
    MutableLiveData<Bitmap> image;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public void addPost(final PostQuote post, final String userID, final Bitmap imageBmp) {
        synchronized (this) {

            saveImage(imageBmp, post.ID.toString(), new SaveImageListener() {
                @Override
                public void complete(String url) {
                    post.ImageURL = url;
                    PostQuoteFirebase.AddPost(post, userID);
                }

                @Override
                public void fail() {

                }
            });
        }
    }

    public LiveData<List<PostQuote>> getAllPosts() {
        synchronized (this) {
            if (postsListliveData == null) {
                postsListliveData = new MutableLiveData<>();

                final float lastUpdateDate = MyApplication.getMyContext().getSharedPreferences("TAG", MODE_PRIVATE).getFloat("lastUpdateDate", 0);

                PostQuoteFirebase.GetAllPostsAndObserve(new PostQuoteFirebase.Callback<List<PostQuote>>() {
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

                        postsListliveData.setValue(data);
                    }
                });
            }
        }
        return postsListliveData;
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


    public interface GetImageListener{
        void onSuccess(Bitmap image);
        void onFail();
    }

    public void getImage(final String url, final PostQuoteRepository.GetImageListener listener) {
        //check if image exsist localy
        String fileName = URLUtil.guessFileName(url, null, null);
        Bitmap image = PostQuote.loadImageFromFile(fileName);

        if (image != null){
            Log.d("TAG","getImage from local success " + fileName);
            listener.onSuccess(image);
        }else {
            PostQuoteFirebase.getImage(url, new GetImageListener() {
                @Override
                public void onSuccess(Bitmap image) {
                    String fileName = URLUtil.guessFileName(url, null, null);
                    Log.d("TAG","getImage from FB success " + fileName);
                    saveImageToFile(image,fileName);
                    listener.onSuccess(image);
                }

                @Override
                public void onFail() {
                    Log.d("TAG","getImage from FB fail ");
                    listener.onFail();
                }
            });

        }
    }

    public interface SaveImageListener {
        void complete(String url);
        void fail();
    }

    public void saveImage(final Bitmap imageBmp, final String name, final SaveImageListener listener) {
        synchronized (this) {

            PostQuoteFirebase.saveImage(imageBmp, name, new SaveImageListener() {
                @Override
                public void complete(String url) {

                    String fileName = URLUtil.guessFileName(url, null, null);

                    saveImageToFile(imageBmp, fileName);
                    listener.complete(url);
                }

                @Override
                public void fail() {
                    Log.d("TAG", "Failed to save image on disk");
                }
            });
        }
    }
}

