package com.neda.project_brain_android_neda.form;

public class FollowForm {

    private String username;
    private String usernameToBeFollowed;

    public FollowForm() {
    }

    public FollowForm(String username, String usernameToBeFollowed) {
        this.username = username;
        this.usernameToBeFollowed = usernameToBeFollowed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameToBeFollowed() {
        return usernameToBeFollowed;
    }

    public void setUsernameToBeFollowed(String usernameToBeFollowed) {
        this.usernameToBeFollowed = usernameToBeFollowed;
    }
}
