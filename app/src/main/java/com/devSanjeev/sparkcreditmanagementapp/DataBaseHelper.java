package com.devSanjeev.sparkcreditmanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "User.db";
    private static final String TABLE_NAME_USER = "UserDatabaseTable";
    private static final String TABLE_NAME_TRANSACTION = "TransactionDatabaseTable";
    private static final String COL_1="ID";
    private static final String COL_2="NAME";
    private static final String COL_3="ACCOUNTNO";
    private static final String COL_4="AMOUNT";
    public static final String COLT_1 ="SRNO";
    private static final String COLT_2="IDT";
    private static final String COLT_3="IDR";
    private static final String COLT_4="AMOUNT";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME_USER + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,ACCOUNTNO INTEGER,AMOUNT MONEY)");
        db.execSQL("create table " + TABLE_NAME_TRANSACTION + "(SRNO INTEGER PRIMARY KEY AUTOINCREMENT, IDT INTEGER,IDR INTEGER,AMOUNT MONEY)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRANSACTION);
        onCreate(db);
    }

    public boolean insertData(String name,int account,int amount){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, account);
        contentValues.put(COL_4, amount);
        long result = db.insert(TABLE_NAME_USER,null,contentValues);
        db.close();

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME_USER,null);
        return cursor;
    }

    public long deleteData(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_USER,"ID ="+id,null);
        return result;
    }

    public  boolean insertTransaction(int ids, int idr, double amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLT_2,ids);
        contentValues.put(COLT_3,idr);
        contentValues.put(COLT_4,amount);
        long result = db.insert(TABLE_NAME_TRANSACTION,null,contentValues);
        //Toast.makeText(context,"Result : "+result,Toast.LENGTH_LONG).show();
        db.close();
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean updateData(int id, String name, int account, double amount){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, account);
        contentValues.put(COL_4, amount);
        long result = db.update(TABLE_NAME_USER,contentValues,"ID ="+id,null);
        db.close();
        if(result == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}


