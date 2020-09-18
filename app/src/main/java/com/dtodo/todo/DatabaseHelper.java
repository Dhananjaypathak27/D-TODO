package com.dtodo.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "MyTodo.db";
    public final static String TABLE_NAME = "MyTodo_Table";
    public final static String COL_1 ="ID";
    public final static String COL_2 ="Data";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATA TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean addData(String data){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,data);

        long result =db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor readData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+TABLE_NAME+" ORDER BY "+COL_1+" DESC",null);
        return cursor;
    }
    public int deleteData(String id){
        SQLiteDatabase db= this.getWritableDatabase();

        return db.delete(TABLE_NAME,"ID=?",new String[]{id});
    }
    public int deleteDataItem(String data){
        SQLiteDatabase db= this.getWritableDatabase();

        return db.delete(TABLE_NAME,"Data=?",new String[]{data});
    }
//    public String returnId(String data){
//        SQLiteDatabase db= this.getReadableDatabase();
//       String cursor= String.valueOf(db.rawQuery("SELECT "+COL_1+" FROM "+TABLE_NAME+ "WHERE "+COL_2 +"="+data,null));
//        return cursor;
//    }
}
