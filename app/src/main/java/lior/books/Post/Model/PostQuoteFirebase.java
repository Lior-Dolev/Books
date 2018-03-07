package lior.books.Post.Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by liord on 3/5/2018.
 */

public class PostQuoteFirebase {

    public static void SetObservePosts(String userID, final Callback<List<PostQuote>> callback) {

    }

    public interface Callback<T> {
        void onComplete(T data);
    }

    public static void GetAllUserPostsAndObserve(String userID, final Callback<List<PostQuote>> callback) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts/" + userID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<PostQuote> list = new LinkedList<>();
                Log.d("TAG", "DATA CHANGED");
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    PostQuote post = snap.getValue(PostQuote.class);
                    list.add(post);
                    Log.d("TAG", "postid: " + post.ID);
                }

                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }

    public static void AddPost(PostQuote post, String userID) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("posts/" + userID + "/" + post.ID);
        myRef.setValue(post);
    }
}
