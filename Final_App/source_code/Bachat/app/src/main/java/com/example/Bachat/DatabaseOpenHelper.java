package com.example.Bachat;

        import android.content.Context;

        import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String Database_name = "Database.db";
    private static final int Database_version = 1; //*Every time the database is updated, the version should also be updated*

    //Constructor definition

    public DatabaseOpenHelper(Context context){
        super(context, Database_name,null,Database_version);
    }
}
