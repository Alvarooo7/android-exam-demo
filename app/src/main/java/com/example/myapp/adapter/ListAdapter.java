package com.example.myapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.entity.Product;
import com.example.myapp.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Activity context;
    ArrayList<Product> lista ;
    private static LayoutInflater inflater = null;

    public ListAdapter(Activity context, ArrayList<Product> lista) {
        this.context = context;
        this.lista = lista;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        itemView = (itemView == null ) ? inflater.inflate(R.layout.list_item,null): itemView;
        TextView textViewName =  itemView.findViewById(R.id.txtNameProduct);
        TextView textViewQuantity =  itemView.findViewById(R.id.txtQuantity);
        TextView textViewAmount =  itemView.findViewById(R.id.txtAmount);
        Product product = lista.get(i);
        try{
            textViewName.setText(product.getName());
            textViewQuantity.setText("Cantidad: "+product.getQuantity());
            textViewAmount.setText("Precio: "+product.getPrice().toString());
        }catch (Exception exp){
            System.out.println("ERROR:"+exp.getMessage());
        }

        return itemView;
    }
}
