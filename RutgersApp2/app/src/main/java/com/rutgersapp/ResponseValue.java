package com.rutgersapp;

/**
 * Created by hari on 12/28/16.
 */
public interface ResponseValue<T> {
    //Holds the Data Made From the request
    void onResponse(T value);
}
