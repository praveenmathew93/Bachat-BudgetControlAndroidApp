package com.example.Bachat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseAccess {
    //Creating instances for all imported classes and the current class
    private static final String TAG = "DatabaseAccess";
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor cursor = null;

    //Private constructor of the class - object creation outside the class is not possible
    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    //to return the single instance of the database
    public static DatabaseAccess getInstance(Context context){
        if (instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }
    //function to open the database
    public void open() {
        this.db = openHelper.getWritableDatabase();
    }
    //function to close the database
    public void close(){
        if(db!=null){
            this.db.close();
        }
    }
    // now to query and get results from the database
    //Querying for sub_category by passing category
    public String getSubCategory(String category){
        cursor = db.rawQuery("select Sub_categories from sub_category where Categories ='"+category+"'",new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()){
            String sub_categories = cursor.getString(0);
            buffer.append("\n"+sub_categories);
        }
        return buffer.toString();
    }
    public Cursor getListContents(String category) {
        Cursor data = db.rawQuery("select DISTINCT Sub_categories from sub_category where Categories ='"+category+"'" + " and Sub_categories not in ('None','null')",new String[]{});
        return data;
    }
    public void insertListContents(String category, String subCategory) {
        Log.d(TAG, "insertListContents: the values being passed are " + subCategory + " and " + category);
        db.execSQL("INSERT INTO sub_category (Sub_categories,Categories) values('" + subCategory + "','" + category + "')");

        /*ContentValues contentValues = new ContentValues();
        contentValues.put("Sub_categories",subCategory);
        contentValues.put("Categories",category);
        long result = db.insert("sub_category",null,contentValues);
        if(result == -1){
            return false;
        }
        else
        {
            return true;
        }*/
    }
}