package com.example.myapp

import android.R.attr.password
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.entity.User
import com.example.myapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        initListener()
        observeViewModel()
    }

    private fun initListener(){
            btnLogin.setOnClickListener {
                Log.d("DEBUG","ONCLICK")
                if(txtUsername.text.isNotBlank() && txtPass.text.isNotBlank())
                     viewModel.login(User(txtUsername.text.toString(),txtPass.text.toString()))

                else
                    Toast.makeText(this,"Ingresar Credenciales",Toast.LENGTH_LONG).show()
            }
    }


    private fun observeViewModel() {
        viewModel.listsUsers.observe(this, Observer(::onUser))
        viewModel.isLoading.observe(this, Observer {
            if(!it){
                rlBase.visibility = View.INVISIBLE
            }
            else{
                rlBase.visibility = View.VISIBLE
            }
            txtUsername.isEnabled = !it
            txtPass.isEnabled = !it
            btnLogin.isEnabled = !it
        })
    }

    private fun onUser(user: List<User>){
        Log.d("DEBUG","ONUSER")
        if(user.isEmpty())
            Toast.makeText(this,"Verificar Credenciales",Toast.LENGTH_LONG).show()
        else {
            Toast.makeText(this,"Bienvenido ${user[0].getusername()}",Toast.LENGTH_LONG).show()
            val preferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("Authentication_Name", user[0].getusername())
            editor.apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}