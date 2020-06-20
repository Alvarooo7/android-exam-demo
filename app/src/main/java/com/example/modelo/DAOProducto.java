package com.example.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.entity.Producto;
import com.example.util.BDPersona;
import com.example.util.Constantes;

import java.util.ArrayList;

public class DAOProducto {

    BDPersona bdPersona;
    SQLiteDatabase sqLiteDatabase;

    public DAOProducto(Context context){
        bdPersona = new BDPersona(context);
    }

    public void openBD (){
        sqLiteDatabase = bdPersona.getWritableDatabase();
    }

    public void registrarPersona (Producto producto){
        try {
            ContentValues values = new ContentValues();
            values.put("nombre", producto.getNombre());
            values.put("cantidad", producto.getCantidad());
            values.put("precio", producto.getPrecio());
            sqLiteDatabase.insert(Constantes.TABLA,null,values);
        }catch (Exception exp ){
            Log.e("error","Error feo db"+exp.getMessage());
        }

    }

    public void modificarPersona (Producto producto){
        try {
            ContentValues values = new ContentValues();
            values.put("nombre", producto.getNombre());
            values.put("cantidad", producto.getCantidad());
            values.put("precio", producto.getPrecio());
            sqLiteDatabase.update(Constantes.TABLA,values,"id="+ producto.getId(),null);
        }catch (Exception exp ){
            Log.e("error","Error feo db"+exp.getMessage());
        }

    }

    public void eliminarPersona (Producto producto){
        try {

            sqLiteDatabase.delete(Constantes.TABLA,"id="+ producto.getId(),null);
        }catch (Exception exp ){
            Log.e("error","Error db"+exp.getMessage());
        }

    }

    public ArrayList<Producto> listar (){
        ArrayList<Producto> productos = new ArrayList<Producto>();

        try {
           Cursor cursor =  sqLiteDatabase.rawQuery  ("SELECT * FROM "+Constantes.TABLA,null);
           while (cursor.moveToNext()){
               productos.add(new Producto(
                       cursor.getString(0),
                       cursor.getString(1),
                       cursor.getInt(2),
                       cursor.getDouble(3)
               ));
               System.out.println("NOM:"+ cursor.getString(1));
           }
        }catch (Exception exp ){
            Log.e("error","Error feo db"+exp.getMessage());
        }
        return productos;
    }





}
