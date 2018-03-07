package lior.books.Post.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by liord on 3/5/2018.
 */

@Entity
public class PostQuote {
    @PrimaryKey
    @NonNull
    public String ID;

    public String BookName;
    public String Author;
    public String QuoteText;
    public float LaseUpdated;
}
