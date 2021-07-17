package com.example.swisshydro.repository.api;

public interface ApiCallback<T> {
    void onSuccess(T response);
}
