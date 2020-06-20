package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.entity.Product;
import com.example.model.DAOProduct;
import com.example.myapp.adapter.ListAdapter;

import java.util.ArrayList;

public class QuestionTwoActivity extends AppCompatActivity {


    ListView listView;
    EditText txtNameProduct,txtQuantity,txtPrice,txtId;
    Button btnAdd,btnUpdate,btnDelete;

    ArrayList<Product> listaPer = new ArrayList<>();
    DAOProduct daoProduct = new DAOProduct(this);

    String id,name,price,quantity ;
    Product product;

    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_two);
        daoProduct.openBD();
        asignarReferencias();
        listar();
    }

    private void asignarReferencias() {
        txtId = findViewById(R.id.txtId);

        txtNameProduct = findViewById(R.id.txtNameProduct);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtPrice = findViewById(R.id.txtPrice);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        listView = findViewById(R.id.lvProducts);
        eventos();

    }

    private void eventos(){
        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(!capturarDatos()) return;
                daoProduct.registrarPersona(product);
                listar();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(!capturarDatosById()) return;
                daoProduct.modificarPersona(product);
                listar();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!capturarDatosById()) return;
                daoProduct.eliminarPersona(product);
                listar();
            }
        });

    }

    public void listar(){
        listaPer = daoProduct.listar();

        adapter = new ListAdapter(this,listaPer);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Product prod = listaPer.get(i);
                    txtId.setText(String.valueOf(prod.getId()));
                    txtNameProduct.setText(prod.getName());
                    txtQuantity.setText(String.valueOf(prod.getQuantity()));
                    txtPrice.setText(prod.getPrice().toString());
                }
            }
        );
    }

    public boolean capturarDatos(){
        try {
            name =  txtNameProduct.getText().toString();
            if(name.isEmpty()) throw  new Exception();
            price =  txtPrice.getText().toString();
            quantity = txtQuantity.getText().toString();
            double priceVal = Double.parseDouble(price);
            quantity = txtQuantity.getText().toString();
            int quantityVal= Integer.parseInt(quantity);
            if(priceVal<0  || quantityVal<0 ) throw  new Exception();
            product = new Product(name,Integer.parseInt(quantity),Double.parseDouble(price));
            return true;
        }catch (Exception exp){
            Toast.makeText(this,"Ingrese Correctamente todos los campos",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean  capturarDatosById(){
        try {
            id = txtId.getText().toString();
            name =  txtNameProduct.getText().toString();
            if(id.isEmpty() || name.isEmpty()) throw  new Exception();
            price =  txtPrice.getText().toString();
            double priceVal = Double.parseDouble(price);
            quantity = txtQuantity.getText().toString();
            int quantityVal= Integer.parseInt(quantity);
            if(priceVal<0  || quantityVal<0 ) throw  new Exception();
            product = new Product(id,name,Integer.parseInt(quantity),Double.parseDouble(price));
            return true;
        }catch (Exception exp){
            Toast.makeText(this,"Ingrese Correctamente todos los campos",Toast.LENGTH_LONG).show();
            return false;
        }
    }


}