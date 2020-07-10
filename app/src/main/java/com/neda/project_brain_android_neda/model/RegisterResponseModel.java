package com.neda.project_brain_android_neda.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "username",
        "firstname",
        "lastname",
        "password",
        "followers",
        "todos"
})
public class RegisterResponseModel {

    @JsonProperty("email")
    private String email;
    @JsonProperty("username")
    private String username;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("password")
    private String password;
    @JsonProperty("followers")
    private List<Object> followers = null;
    @JsonProperty("todos")
    private List<Object> todos = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public RegisterResponseModel() {
    }

    /**
     *
     * @param firstname
     * @param password
     * @param followers
     * @param todos
     * @param email
     * @param username
     * @param lastname
     */
    public RegisterResponseModel(String email, String username, String firstname, String lastname, String password, List<Object> followers, List<Object> todos) {
        super();
        this.email = email;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.followers = followers;
        this.todos = todos;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("firstname")
    public String getFirstname() {
        return firstname;
    }

    @JsonProperty("firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @JsonProperty("lastname")
    public String getLastname() {
        return lastname;
    }

    @JsonProperty("lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("followers")
    public List<Object> getFollowers() {
        return followers;
    }

    @JsonProperty("followers")
    public void setFollowers(List<Object> followers) {
        this.followers = followers;
    }

    @JsonProperty("todos")
    public List<Object> getTodos() {
        return todos;
    }

    @JsonProperty("todos")
    public void setTodos(List<Object> todos) {
        this.todos = todos;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}