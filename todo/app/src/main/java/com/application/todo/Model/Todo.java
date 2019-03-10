package com.application.todo.Model;

public class Todo {
    private String title;
    private String content;
    private String user_id;


    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
