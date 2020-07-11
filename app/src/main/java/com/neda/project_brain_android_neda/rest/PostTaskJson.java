package com.neda.project_brain_android_neda.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.neda.project_brain_android_neda.callback.ApiCallBackPost;
import com.neda.project_brain_android_neda.form.ApiJsonForm;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class PostTaskJson<T extends ApiJsonForm, E> extends AsyncTask<T, Void, ResponseEntity<E>> {

    private final Class<E> eClass;
    private ApiCallBackPost<E> apiCallback;

    public PostTaskJson(Class<E> eClass, ApiCallBackPost<E> apiCallback) {
        this.eClass = eClass;
        this.apiCallback = apiCallback;
    }

    @Override
    protected ResponseEntity<E> doInBackground(T... ts) {

        final String url = ts[0].getUrl();
        JSONObject jsonObject = ts[0].getJson();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);

        Log.i("Request","url: " + url);
        Log.i("Request","Body: " + jsonObject.toString());
        Log.i("Request","header: " + httpHeaders.toString());

        try {
            RestTemplate restTemplate = new RestTemplate(true);
            return restTemplate.exchange(url, HttpMethod.POST, httpEntity, eClass);
        } catch (RestClientException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<E> responseEntity) {
        apiCallback.postResult(responseEntity);
    }
}
