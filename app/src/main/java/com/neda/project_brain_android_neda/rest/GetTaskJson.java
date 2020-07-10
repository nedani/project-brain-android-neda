package com.neda.project_brain_android_neda.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.neda.project_brain_android_neda.callback.ApiCallBackGet;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class GetTaskJson<T> extends AsyncTask<String, Void, ResponseEntity<T>> {

    private final Class<T> tClass;
    private ApiCallBackGet<T> apiCallback;

    public GetTaskJson(Class<T> tClass, ApiCallBackGet<T> apiCallback) {
        this.tClass = tClass;
        this.apiCallback = apiCallback;
    }

    @Override
    protected ResponseEntity<T> doInBackground(String... uri) {

        final String url = uri[0];

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        try {
            RestTemplate restTemplate = new RestTemplate(true);
            return restTemplate.exchange(url, HttpMethod.GET, httpEntity, tClass);
        } catch (RestClientException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<T> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            Log.e("GetTaskJson", "Network Error: " + responseEntity.getStatusCode().toString());
        } else {
            apiCallback.getResult(responseEntity);
        }
    }
}
