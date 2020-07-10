package com.neda.project_brain_android_neda.form;

import org.json.JSONObject;

public class LoginForm implements ApiJsonForm {

    private String email;
    private String password;

    public LoginForm() {
    }

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String getUrl() {
        return "http://192.168.0.110:8080/brain/signin";
    }

    @Override
    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", getEmail());
            jsonObject.put("password", getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
