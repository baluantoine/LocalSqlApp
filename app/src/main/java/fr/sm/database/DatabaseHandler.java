package fr.sm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Formation on 08/01/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contacts";
    private static final int DATABASE_VERSION = 1;

    private static final String CONTACT_TABLE_SQL = "CREATE TABLE contacts(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "first_name TEXT," +
            "name TEXT NOT NULL," +
            "email TEXT NOT NULL)";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //Crée la table
        sqLiteDatabase.execSQL(CONTACT_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");//supprime la table si elle existe
        this.onCreate(sqLiteDatabase); //on recrée la table
    }
}
