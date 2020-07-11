package com.example.network;

public interface Callback<T> {

        void onSuccess(T result);

        void onFailed(Exception exception);

}
