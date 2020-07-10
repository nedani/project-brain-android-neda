package com.neda.project_brain_android_neda.callback;

import org.springframework.http.ResponseEntity;

public interface ApiCallBackPost<T> {

    void postResult(ResponseEntity<T> responseEntity);
}
