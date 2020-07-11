package com.neda.project_brain_android_neda.form;

public class ToDoForm {

    private String username;
    private String ideaId;

    public ToDoForm() {
    }

    public ToDoForm(String username, String ideaId) {
        this.username = username;
        this.ideaId = ideaId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(String ideaId) {
        this.ideaId = ideaId;
    }
}
