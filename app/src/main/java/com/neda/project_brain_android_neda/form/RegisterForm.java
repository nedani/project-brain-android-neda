package com.neda.project_brain_android_neda.form;

import org.json.JSONObject;

public class RegisterForm implements ApiJsonForm {

    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private String password;

    public RegisterForm() {
    }

    public RegisterForm(String email, String password, String username, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public String getUrl() {
        return "http://192.168.0.110:8080/brain/signup";
    }

    @Override
    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", getEmail());
            jsonObject.put("username", getUsername());
            jsonObject.put("firstname", getFirstname());
            jsonObject.put("lastname", getLastname());
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
