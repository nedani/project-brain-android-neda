package com.neda.project_brain_android_neda.form;

public class NewIdea {
    private String username;
    private String title;
    private String context;
    private String content;

    public NewIdea() {
    }

    public NewIdea(String username, String title, String context, String content) {
        this.username = username;
        this.title = title;
        this.context = context;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
