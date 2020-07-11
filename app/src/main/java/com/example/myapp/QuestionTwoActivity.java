package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.entity.Product;
import com.example.entity.User;
import com.example.model.DAOProduct;
import com.example.myapp.adapter.ListAdapter;
import com.example.myapp.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestionTwoActivity extends AppCompatActivity {

    private UserViewModel viewModel = new UserViewModel();

    RelativeLayout rlBase;

    ListView listView;
    EditText txtUsername,txtName,txtPassword,txtId;
    Button btnAdd,btnUpdate,btnDelete;

    ArrayList<User> listaPer = new ArrayList<>();
    DAOProduct daoProduct = new DAOProduct(this);

    String id,username,name,password;
    Product product;


    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_two);
        daoProduct.openBD();
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        asignarReferencias();
        observeViewModel();
        viewModel.getUsersFromFirebase();
    }

    private void asignarReferencias() {
        rlBase = findViewById(R.id.rlBase);

        txtId = findViewById(R.id.txtId);

        txtUsername = findViewById(R.id.txtUsername);
        txtName = findViewById(R.id.txtName);
        txtPassword = findViewById(R.id.txtPassword);
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
                String uuId = UUID.randomUUID().toString();
                viewModel.saveUserFromFirebase(new User(uuId,username,name,password));
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(!capturarDatosById()) return;
                viewModel.saveUserFromFirebase(new User(id,username,name,password));
                //daoProduct.modificarPersona(product);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isLast()) return;
                if(!capturarDatosById()) return;
                viewModel.deletedUserFromFirebase(new User(id,username,name,password));
            }
        });

    }

    private void observeViewModel() {
        viewModel.getListsUsers().observe(this,new Observer() {
            @Override
            public void onChanged(Object o) {
                listaPer = (ArrayList<User>) o;
                setAdapter();
            }
        });

        viewModel.isSaved().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Boolean it = (Boolean) o;
                onSaved(it);
                viewModel.getUsersFromFirebase();
            }
        });

        viewModel.isDeleted().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Boolean it = (Boolean) o;
                onDeleted(it);
                viewModel.getUsersFromFirebase();
            }
        });
        viewModel.isLoading().observe(this,  new Observer() {
            @Override
            public void onChanged(Object o) {
                Boolean it = (Boolean) o;
                if(!it){
                    rlBase.setVisibility(View.INVISIBLE);
                }
                else{
                    rlBase.setVisibility(View.VISIBLE);
                    rlBase.bringToFront();
                }
                txtUsername.setEnabled(!it);
                txtName.setEnabled(!it);
                txtPassword.setEnabled(!it);
                btnAdd.setEnabled(!it);
                btnUpdate.setEnabled(!it);
                btnDelete.setEnabled(!it);
            }
        });
    }

    private Boolean isLast(){
        if(listaPer.size()==1){
            Toast.makeText(this,"No puede eliminar el último usuario",Toast.LENGTH_LONG).show();
            return true;
        }
        else
            return false;
    }

    private void onSaved(Boolean isSaved){
        if(isSaved)
            Toast.makeText(this,"Se guardó con exito",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"No se guardó el usuario",Toast.LENGTH_LONG).show();
    }

    private void onDeleted(Boolean isSaved){
        if(isSaved)
            Toast.makeText(this,"Se eliminó con exito",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"No se eliminó el usuario",Toast.LENGTH_LONG).show();
    }


    public void setAdapter(){
        if(listaPer.isEmpty())
            return;
        adapter = new ListAdapter(this,listaPer);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    User user = listaPer.get(i);
                    txtId.setText(String.valueOf(user.getId()));
                    txtUsername.setText(user.getusername());
                    txtName.setText(String.valueOf(user.getName()));
                    txtPassword.setText(user.getPassword());
                    Log.d("ID","ONCLIKC: "+user.getId());
                }
            }
        );
    }

    public boolean capturarDatos(){
        try {
            txtId.getText().clear();
            id="";
            username =  txtUsername.getText().toString().trim();
            if(username.isEmpty() || username.length()<3) throw  new Exception();
            name =  txtName.getText().toString().trim();
            if(name.isEmpty() || name.length()<3) throw  new Exception();
            password =txtPassword.getText().toString().trim();
            if(password.isEmpty() || password.length()<3) throw  new Exception();
        }catch (Exception exp){
            Toast.makeText(this,"Ingrese Correctamente todos los campos",Toast.LENGTH_LONG).show();
            return false;
        }

        try{
            for(User user : listaPer)
                if(user.getusername().equals(username)) throw new Exception();

            return true;
        }catch (Exception exp){
            Toast.makeText(this,"Ingrese otro Usuario",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean  capturarDatosById(){
        try {
            id = txtId.getText().toString();
            username =  txtUsername.getText().toString().trim();
            if(id.isEmpty() || username.isEmpty() || username.length()<3) throw  new Exception();
            name = txtName.getText().toString().trim();
            if(name.isEmpty() || name.length()<3) throw  new Exception();
            password =txtPassword.getText().toString().trim();
            if(password.isEmpty() || password.length()<3) throw  new Exception();
            return true;

        }catch (Exception exp){
            Toast.makeText(this,"Ingrese Correctamente todos los campos",Toast.LENGTH_LONG).show();
            return false;
        }

    }


}