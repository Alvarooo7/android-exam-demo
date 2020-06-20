package com.example.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.entity.Product;
import com.example.util.BDProduct;
import com.example.util.Constants;

import java.util.ArrayList;

public class DAOProduct {

    BDProduct bdProduct;
    SQLiteDatabase sqLiteDatabase;

    public DAOProduct(Context context){
        bdProduct = new BDProduct(context);
    }

    public void openBD (){
        sqLiteDatabase = bdProduct.getWritableDatabase();
    }

    public void registrarPersona (Product product){
        try {
            ContentValues values = new ContentValues();
            values.put("nombre", product.getName());
            values.put("cantidad", product.getQuantity());
            values.put("precio", product.getPrice());
            sqLiteDatabase.insert(Constants.TABLA,null,values);
        }catch (Exception exp ){
            Log.e("error","Error feo db"+exp.getMessage());
        }

    }

    public void modificarPersona (Product product){
        try {
            ContentValues values = new ContentValues();
            values.put("nombre", product.getName());
            values.put("cantidad", product.getQuantity());
            values.put("precio", product.getPrice());
            sqLiteDatabase.update(Constants.TABLA,values,"id="+ product.getId(),null);
        }catch (Exception exp ){
            Log.e("error","Error feo db"+exp.getMessage());
        }

    }

    public void eliminarPersona (Product product){
        try {

            sqLiteDatabase.delete(Constants.TABLA,"id="+ product.getId(),null);
        }catch (Exception exp ){
            Log.e("error","Error db"+exp.getMessage());
        }

    }

    public ArrayList<Product> listar (){
        ArrayList<Product> products = new ArrayList<Product>();

        try {
           Cursor cursor =  sqLiteDatabase.rawQuery  ("SELECT * FROM "+ Constants.TABLA,null);
           while (cursor.moveToNext()){
               products.add(new Product(
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
        return products;
    }





}
