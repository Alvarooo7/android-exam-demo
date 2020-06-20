package com.example.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDPersona extends SQLiteOpenHelper {

    public BDPersona(Context context){
        super(context,Constantes.BBNAME,null,Constantes.VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ Constantes.TABLA + " "+
                " (id integer Primary Key autoincrement," +
                "nombre text not null," +
                "cantidad int not null, " +
                "precio float not null );" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
