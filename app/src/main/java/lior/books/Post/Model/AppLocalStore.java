package lior.books.Post.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import lior.books.MyApplication;

/**
 * Created by liord on 3/7/2018.
 */


@Database(entities = {PostQuote.class}, version = 1)
abstract class AppLocalStoreDb extends RoomDatabase {
    public abstract PostQuoteDao postDao();
}

public class AppLocalStore{
    static public AppLocalStoreDb db = Room.databaseBuilder(MyApplication.getMyContext(),
            AppLocalStoreDb.class,
            "database-name").build();
}
