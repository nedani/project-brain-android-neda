package com.neda.project_brain_android_neda.callback;

import org.springframework.http.ResponseEntity;

public interface ApiCallBackGet<T> {

    void getResult(ResponseEntity<T> responseEntity);
}
