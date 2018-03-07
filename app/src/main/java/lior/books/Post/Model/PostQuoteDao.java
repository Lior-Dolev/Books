package lior.books.Post.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by liord on 3/7/2018.
 */

@Dao
public interface PostQuoteDao {
        @Query("SELECT * FROM PostQuote")
    List<PostQuote> getAll();
//
//    @Query("SELECT * FROM Posts WHERE id IN (:userIds)")
//    List<Employee> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM PostQuote WHERE id = :id")
    PostQuote findById(String id);

    @Insert
    void insertAll(PostQuote... Posts);

    @Delete
    void delete(PostQuote post);
}
