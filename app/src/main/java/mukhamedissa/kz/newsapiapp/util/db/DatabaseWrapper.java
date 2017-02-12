package mukhamedissa.kz.newsapiapp.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import mukhamedissa.kz.newsapiapp.util.db.orm.ArticleORM;
import mukhamedissa.kz.newsapiapp.util.db.orm.SourceORM;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */
public class DatabaseWrapper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseWrapper.class.getCanonicalName();

    public static final String DATABASE_NAME = "NewsAPICache.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseWrapper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Creating database [" + DATABASE_NAME + "]");

        db.execSQL(SourceORM.SQL_CREATE_TABLE);
        db.execSQL(ArticleORM.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading database [" + DATABASE_NAME + "v." + oldVersion + "to v." + newVersion + "]");

        db.execSQL(SourceORM.SQL_DROP_TABLE);
        db.execSQL(ArticleORM.SQL_DROP_TABLE);

        onCreate(db);
    }
}
