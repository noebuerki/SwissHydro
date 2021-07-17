package ch.bbcag.swisshydro.repository.api;

public interface ApiCallback<T> {
    void onSuccess(T response);
}