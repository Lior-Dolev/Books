package lior.books.Models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by liord on 2/14/2018.
 */

public class ModelFireBase {
    public ModelFireBase() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("message");
        ref.setValue("Hello world");
    }
}
