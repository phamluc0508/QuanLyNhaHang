package com.test.app.model;
import java.io.Serializable;
 
public class User implements Serializable{
    private int id;
    private String username;
    private String password;
    private String name;
    private String position;
    private String note;
     
    public User() {
        super();
    }
    public User(String username, String password, String name, String position, String note) {
        super();
        this.username = username;
        this.password = password;
        this.name = name;
        this.position = position;
        this.note = note;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}