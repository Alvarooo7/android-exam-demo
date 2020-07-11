package com.example.network

import android.util.Log
import com.example.entity.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.util.*

const val USERS_COLLECTION_NAME = "users"

class FirestoreService {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()

    init {
        firebaseFirestore.firestoreSettings = settings
    }

    fun getAuthentication(callback: Callback<List<User>>, user: User ) {
        Log.d("DEBUG","GEp AUTHENTICATION")
        firebaseFirestore.collection(USERS_COLLECTION_NAME)
                .whereEqualTo("username",user.getusername())
                .whereEqualTo("password",user.password)
                .get()
                .addOnSuccessListener { result ->
                    Log.d("exito","A VER")
                    if(result.isEmpty)
                        callback.onFailed(Exception("Fail to get User"))
                    for (doc in result) {
                        Log.d("TAG", "${doc.id} => ${doc.data}")
                        val list = result.toObjects(User::class.java)
                        callback.onSuccess(list)
                        Log.d("DEBUG","GET AUTHENTICATION SUCCESS")
                        break
                    }
                }
                .addOnFailureListener {
                    Log.e("ERROR","NOT GET AUTHENTICATION")
                    callback.onFailed(Exception("Fail to get User Endpoint Information"))
                }

    }


    fun getUsersFromFirebase(callback: Callback<List<User>> ) {
        Log.d("DEBUG","getUsersFromFirebase")
        firebaseFirestore.collection(USERS_COLLECTION_NAME)
                .get()
                .addOnSuccessListener { result ->
                    for (doc in result) {
                        Log.d("TAG", "${doc.id} => ${doc.data}")
                        val list = result.toObjects(User::class.java)
                        callback.onSuccess(list)
                        Log.d("DEBUG","GET AUTHENTICATION SUCCESS")
                        break
                    }

                }
                .addOnFailureListener {
                    Log.e("ERROR","NOT GET AUTHENTICATION")
                    callback.onFailed(Exception("Fail to get User Endpoint Information"))
                }

    }

    fun saveUser(callback: Callback<Void>, user: User) {
        Log.d("DEBUG","saveUser")
        firebaseFirestore.collection(USERS_COLLECTION_NAME)
                .document(user.id)
                .set(user)
                .addOnSuccessListener { result ->
                    Log.d("exito","A VER")
                        callback.onSuccess(result)
                    }
                .addOnFailureListener {
                    Log.e("ERROR","NOT saveUser ")
                    callback.onFailed(Exception("Fail to saveUser Endpoint Information"))
                }
    }

    fun deleteUser(callback: Callback<Void>, user: User) {
        Log.d("DEBUG","saveUser")
        if (user.id.isNullOrBlank())  callback.onFailed(Exception("Fail to deleteUser Endpoint Information"))
        val id = user.id
        Log.d("DEBUG","id : $id")
        firebaseFirestore.collection(USERS_COLLECTION_NAME)
                .document(id)
                .delete()
                .addOnSuccessListener { result ->
                    Log.d("exito","A VER")
                        callback.onSuccess(result)
                    }
                .addOnFailureListener {
                    Log.e("ERROR","NOT saveUser ")
                    callback.onFailed(Exception("Fail to saveUser Endpoint Information"))
                }
    }




}