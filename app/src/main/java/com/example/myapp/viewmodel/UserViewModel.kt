package com.example.myapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.entity.User
import com.example.network.Callback
import com.example.network.FirestoreService

class UserViewModel : ViewModel() {
        val firestoreService = FirestoreService()
        var listsUsersEmpty = emptyList<User>()
        var isLoading = MutableLiveData<Boolean>()


        fun login(user : User) {
            getUserFromFirebase(user)
        }

    var listsUsers : MutableLiveData<List<User>> = MutableLiveData()


    fun getUserFromFirebase(user : User)  {
            isLoading.value = true
            Log.d("get view","ENTROOOO")
            firestoreService.getAuthentication(object: Callback<List<User>> {
                override fun onSuccess(result: List<User>?) {
                    Log.d("DEBUG","SUCCES")
                    listsUsers.postValue(result)
                    processFinished()
                }

                override fun onFailed(exception: Exception) {
                    Log.d("ERROR","FAIL")
                    listsUsers.postValue(listsUsersEmpty)
                    processFinished()
                }
            },user)
        }

    fun getUsersFromFirebase()  {
            isLoading.value = true
            Log.d("get view","ENTROOOO")
            firestoreService.getUsersFromFirebase(object: Callback<List<User>> {
                override fun onSuccess(result: List<User>?) {
                    Log.d("DEBUG","SUCCES")
                    listsUsers.postValue(result)
                    processFinished()
                }

                override fun onFailed(exception: Exception) {
                    Log.d("ERROR","FAIL")
                    listsUsers.postValue(listsUsersEmpty)
                    processFinished()
                }
            })
        }

    var isSaved = MutableLiveData<Boolean>()

    fun saveUserFromFirebase(user : User)  {
            isLoading.value = true
            Log.d("save view","ENTROOOO")
            firestoreService.saveUser(object: Callback<Void> {
                override fun onSuccess(result:Void?) {
                    Log.d("DEBUG","SUCCES")
                    isSaved.postValue(true)
                    processFinished()
                }

                override fun onFailed(exception: Exception) {
                    Log.d("ERROR","FAIL")
                    isSaved.postValue(false)
                    processFinished()
                }
            },user)
         }


    var isDeleted = MutableLiveData<Boolean>()
    fun deletedUserFromFirebase(user : User)  {
            isLoading.value = true
            Log.d("Deleted view","ENTROOOO")
            firestoreService.deleteUser(object: Callback<Void> {
                override fun onSuccess(result:Void?) {
                    Log.d("DEBUG","SUCCES")
                    isDeleted.postValue(true)
                    processFinished()
                }

                override fun onFailed(exception: Exception) {
                    Log.d("ERROR","FAIL")
                    isDeleted.postValue(false)
                    processFinished()
                }
            },user)
    }

        private fun processFinished() {
            isLoading.value = false
        }


}