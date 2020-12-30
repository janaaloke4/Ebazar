package com.example.ebazer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="EBAZER_DB";
    public static final String TABLE_NAME="EBAZER_TABLE";
    public static final String COL_1 = "id";
    public static final String COL_2 = "USER_NAME";
    public static final String COL_3 = "PHONE_NO";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PASSWORD";




    public database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,USER_NAME TEXT,PHONE_NO TEXT,EMAIL TEXT,PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean insertData(String username,String ph,String email,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,ph);
        contentValues.put(COL_4,email);
        contentValues.put(COL_5,pass);
        long result = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if(result==-1){
            return false;
        }else{
            return true;//return row value
        }
    }

    //to retrive data from database
    public Cursor getAllData(String eml){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME,null);
        return res;
    }

    public Cursor uniqueUserData(String eml){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c=db.rawQuery("Select * from "+ TABLE_NAME +" WHERE EMAIL=?",new String[]{eml});

           //if(c.moveToFirst())
            return c;
          // else
           //    return null;

    }


    public boolean checkEmail(String id)
    {
        SQLiteDatabase db1=this.getWritableDatabase();
        Cursor c=db1.rawQuery("Select * from "+ TABLE_NAME +" WHERE EMAIL=?",new String[]{id});
        if(c!=null) {
            if (c.getCount() > 0)
                return true;
        }
        return false;
    }



    public boolean checkPhone(String ph)
    {
        SQLiteDatabase db1=this.getWritableDatabase();
        Cursor c=db1.rawQuery("Select * from "+ TABLE_NAME +" WHERE PHONE_NO=?",new String[]{ph});
        if(c!=null) {
            if (c.getCount() > 0)
                return true;
        }
        return false;
    }

    public boolean checkLogIn(String id, String pass)
    {
        SQLiteDatabase db1=this.getWritableDatabase();
        Cursor c=db1.rawQuery("Select * from "+ TABLE_NAME +" WHERE EMAIL=? AND PASSWORD=?",new String[]{id,pass});
        if(c!=null) {
            if (c.getCount() > 0)
                return true;
        }
        return false;
    }

    public boolean updatePass(String pass,String ph){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5,pass);
        db.update(TABLE_NAME,contentValues,"PHONE_NO = ?",new String[]{ph});
        return true;
    }



}
