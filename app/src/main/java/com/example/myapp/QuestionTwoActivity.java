package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.Producto;
import com.example.modelo.DAOProducto;
import com.example.myapp.adapter.ListAdapter;

import java.util.ArrayList;

public class QuestionTwoActivity extends AppCompatActivity {


    ListView listView;
    EditText txtNombre,txtCantidad,txtPrecio,txtId;
    Button btnAdd,btnUpdate,btnDelete;

    ArrayList<Producto> listaPer = new ArrayList<>();
    ArrayList<String> listViews = new ArrayList<>();
    DAOProducto daoProducto = new DAOProducto(this);

    String id,nombre,precio,cantidad ;
    Producto producto;

    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_two);
        daoProducto.openBD();
        asignarReferencias();
        listar();
    }

    private void asignarReferencias() {
        txtId = findViewById(R.id.txtId);

        txtNombre = findViewById(R.id.txtNombre);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
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
                daoProducto.registrarPersona(producto);
                listar();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(!capturarDatosById()) return;
                daoProducto.modificarPersona(producto);
                listar();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!capturarDatosById()) return;
                daoProducto.eliminarPersona(producto);
                listar();
            }
        });

    }

    public void listar(){
        listaPer = daoProducto.listar();

        adapter = new ListAdapter(this,listaPer);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Producto prod = listaPer.get(i);
                    txtId.setText(String.valueOf(prod.getId()));
                    txtNombre.setText(prod.getNombre());
                    txtPrecio.setText(prod.getPrecio().toString());
                    txtCantidad.setText(String.valueOf(prod.getCantidad()));
                }
            }
        );
    }

    public boolean capturarDatos(){
        try {
            nombre =  txtNombre.getText().toString();
            if(nombre.isEmpty()) throw  new Exception();
            precio =  txtPrecio.getText().toString();
            cantidad = txtCantidad.getText().toString();
            double precioVal = Double.parseDouble(precio);
            cantidad = txtCantidad.getText().toString();
            int cantidadVal= Integer.parseInt(cantidad);
            if(precioVal<0  || cantidadVal<0 ) throw  new Exception();
            producto = new Producto(nombre,Integer.parseInt(cantidad),Double.parseDouble(precio));
            return true;
        }catch (Exception exp){
            Toast.makeText(this,"Ingrese Correctamente todos los campos",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean  capturarDatosById(){
        try {
            id = txtId.getText().toString();
            nombre =  txtNombre.getText().toString();
            if(id.isEmpty() || nombre.isEmpty()) throw  new Exception();
            precio =  txtPrecio.getText().toString();
            double precioVal = Double.parseDouble(precio);
            cantidad = txtCantidad.getText().toString();
            int cantidadVal= Integer.parseInt(cantidad);
            if(precioVal<0  || cantidadVal<0 ) throw  new Exception();
            producto = new Producto(id,nombre,Integer.parseInt(cantidad),Double.parseDouble(precio));
            return true;
        }catch (Exception exp){
            Toast.makeText(this,"Ingrese Correctamente todos los campos",Toast.LENGTH_LONG).show();
            return false;
        }
    }


}