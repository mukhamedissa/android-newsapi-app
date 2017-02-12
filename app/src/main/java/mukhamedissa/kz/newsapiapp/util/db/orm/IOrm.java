package mukhamedissa.kz.newsapiapp.util.db.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

import io.reactivex.Observable;
import mukhamedissa.kz.newsapiapp.model.AbstractModel;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */

public interface IOrm<T extends AbstractModel> {

    public void insert(Context context, List<T> item);
    public Observable<List<T>> get(Context context);
    public ContentValues objectToContentValues(T item);
    public T cursorToObject(Cursor cursor);
    public void delete(Context context);
}
