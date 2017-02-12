package mukhamedissa.kz.newsapiapp.util.db.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import mukhamedissa.kz.newsapiapp.model.Article;
import mukhamedissa.kz.newsapiapp.util.db.DatabaseWrapper;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */

public class ArticleORM implements IOrm<Article> {

    private static final String TAG = ArticleORM.class.getCanonicalName();

    private static final String TABLE_NAME = "articles";
    private static final String COMMA_SEPARATOR = ", ";

    private static final String COLUMN_AUTHOR_TYPE = "TEXT";
    private static final String COLUMN_AUTHOR = "author";

    private static final String COLUMN_TITLE_TYPE = "TEXT";
    private static final String COLUMN_TITLE = "title";

    private static final String COLUMN_DESCRIPTION_TYPE = "TEXT";
    private static final String COLUMN_DESCRIPTION = "description";

    private static final String COLUMN_URL_TYPE = "TEXT";
    private static final String COLUMN_URL = "url";

    private static final String COLUMN_URL_TO_IMAGE_TYPE = "TEXT";
    private static final String COLUMN_URL_TO_IMAGE = "url_to_image";

    private static final String COLUMN_PUBLISHED_AT_TYPE = "TEXT";
    private static final String COLUMN_PUBLISHED_AT = "published_at";

    private static final String COLUMN_SOURCE_TYPE = "TEXT";
    private static final String COLUMN_SOURCE = "source";

    private static final String UNIQUE_CONSTRAINT = "UNIQUE";
    private static final String ON_CONFLICT_CLAUSE = "ON CONFLICT REPLACE";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_AUTHOR + " " + COLUMN_AUTHOR_TYPE + COMMA_SEPARATOR +
            COLUMN_TITLE + " " + COLUMN_TITLE_TYPE + " " + UNIQUE_CONSTRAINT + " " + ON_CONFLICT_CLAUSE + COMMA_SEPARATOR +
            COLUMN_DESCRIPTION + " " + COLUMN_DESCRIPTION_TYPE + COMMA_SEPARATOR +
            COLUMN_URL  + " " + COLUMN_URL_TYPE + COMMA_SEPARATOR +
            COLUMN_URL_TO_IMAGE + " " + COLUMN_URL_TO_IMAGE_TYPE + COMMA_SEPARATOR +
            COLUMN_PUBLISHED_AT + " " + COLUMN_PUBLISHED_AT_TYPE + COMMA_SEPARATOR +
            COLUMN_SOURCE + " " + COLUMN_SOURCE_TYPE +
            ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private String mSourceId;

    @Override
    public void insert(Context context, List<Article> articles) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getWritableDatabase();
        for (Article article : articles) {
            article.setSourceId(mSourceId);
            ContentValues contentValues = objectToContentValues(article);
            database.insert(TABLE_NAME, "null", contentValues);

            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            Log.i(TAG, "Inserted articles with title: " + article.getTitle());
            Log.i(TAG, "There are " + cursor.getCount() + " articles");
        }

        database.close();
    }

    @Override
    public ContentValues objectToContentValues(Article item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_AUTHOR, item.getAuthor());
        contentValues.put(COLUMN_TITLE, item.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, item.getDescription());
        contentValues.put(COLUMN_URL, item.getUrl());
        contentValues.put(COLUMN_URL_TO_IMAGE, item.getUrlToImage());
        contentValues.put(COLUMN_PUBLISHED_AT, item.getPublishedAt());
        contentValues.put(COLUMN_SOURCE, item.getSourceId());

        return contentValues;
    }

    @Override
    public Observable<List<Article>> get(Context context) {
        return makeObservable(getArticles(context)).subscribeOn(Schedulers.computation());
    }

    private Callable<List<Article>> getArticles(Context context){
        return () -> {
            DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
            SQLiteDatabase database = databaseWrapper.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_SOURCE + " LIKE '" + mSourceId + "'", null);

            Log.i(TAG, "Loaded " + cursor.getCount() + " article with source id: " + mSourceId);
            List<Article> articleList = new ArrayList<>();

            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    Article article = cursorToObject(cursor);
                    articleList.add(article);
                    cursor.moveToNext();
                }
                Log.i(TAG, "Articles are loaded");
            }
            database.close();

            return articleList;
        };
    }

    public Observable<List<Article>> getArticlesBySourceId(Context context, @NonNull String sourceId) {
        mSourceId = sourceId;

        return get(context);
    }

    @Override
    public Article cursorToObject(Cursor cursor) {
        Article article = new Article();
        article.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
        article.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
        article.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
        article.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
        article.setUrlToImage(cursor.getString(cursor.getColumnIndex(COLUMN_URL_TO_IMAGE)));
        article.setPublishedAt(cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHED_AT)));

        return article;
    }

    @Override
    public void delete(Context context) {
        DatabaseWrapper wrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = wrapper.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_NAME);

        database.close();
    }

    public void setSourceId(@NonNull String sourceId) {
        mSourceId = sourceId;
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
