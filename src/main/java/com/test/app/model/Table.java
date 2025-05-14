package com.test.app.model;
import java.io.Serializable;
 
public class Table implements Serializable{
    private int id;
    private String name;
    private int maxNumber;
    private String des;
     
    public Table() {
        super();
    }   
    public Table(String name, int maxNumber, String des) {
        super();
        this.name = name;
        this.maxNumber = maxNumber;
        this.des = des;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMaxNumber() {
        return maxNumber;
    }
    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }
}