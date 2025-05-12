package com.test.app.model;
import java.io.Serializable;
 
public class Room  implements Serializable{
    private int id;
    private String name;
    private String type;
    private float price;
    private String des;
     
    public Room() {
        super();
    }   
    public Room(String name, String type, float price, String des) {
        super();
        this.name = name;
        this.type = type;
        this.price = price;
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }
}