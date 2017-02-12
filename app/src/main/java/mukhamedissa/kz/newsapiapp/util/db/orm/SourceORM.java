package mukhamedissa.kz.newsapiapp.util.db.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import mukhamedissa.kz.newsapiapp.model.Source;
import mukhamedissa.kz.newsapiapp.util.db.DatabaseWrapper;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */

public class SourceORM implements IOrm<Source> {

    private static final String TAG = SourceORM.class.getCanonicalName();

    private static final String TABLE_NAME = "sources";
    private static final String COMMA_SEPARATOR = ", ";

    private static final String COLUMN_ID_TYPE = "TEXT PRIMARY KEY";
    private static final String COLUMN_ID = "id";

    private static final String COLUMN_NAME_TYPE = "TEXT";
    private static final String COLUMN_NAME = "name";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " " + COLUMN_ID_TYPE + COMMA_SEPARATOR +
            COLUMN_NAME + " " + COLUMN_NAME_TYPE +
            ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public void insert(Context context, List<Source> sources) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getWritableDatabase();
        for (Source source : sources) {
            ContentValues contentValues = objectToContentValues(source);
            database.insert(TABLE_NAME, "null", contentValues);

            Log.i(TAG, "Inserted category with id: " + source.getId());
        }
        database.close();
    }

    @Override
    public ContentValues objectToContentValues(Source item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, item.getId());
        contentValues.put(COLUMN_NAME, item.getName());
        return contentValues;
    }

    @Override
    public Observable<List<Source>> get(Context context) {
        return makeObservable(getSources(context)).subscribeOn(Schedulers.computation());
    }

    private Callable<List<Source>> getSources(Context context){

        return () -> {
            DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
            SQLiteDatabase database = databaseWrapper.getReadableDatabase();

            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            Log.i(TAG, "Loaded " + cursor.getCount() + " categories");
            List<Source> sourceList = new ArrayList<>();

            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    Source source = cursorToObject(cursor);
                    sourceList.add(source);
                    cursor.moveToNext();
                }
                Log.i(TAG, "Categories are loaded");
            }

            database.close();

            return sourceList;
        };
    }


    @Override
    public Source cursorToObject(Cursor cursor) {
        Source category = new Source();
        category.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
        category.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));

        return category;
    }

    @Override
    public void delete(Context context) {
        DatabaseWrapper wrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = wrapper.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_NAME);

        database.close();
    }

    private <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(func.call());
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
