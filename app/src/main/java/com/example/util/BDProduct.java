package com.example.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDProduct extends SQLiteOpenHelper {

    public BDProduct(Context context){
        super(context, Constants.BBNAME,null, Constants.VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ Constants.TABLA + " "+
                " (id integer Primary Key autoincrement," +
                "nombre text not null," +
                "cantidad int not null, " +
                "precio float not null );" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
