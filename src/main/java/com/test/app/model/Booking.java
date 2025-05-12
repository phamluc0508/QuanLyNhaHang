package com.test.app.model;

import java.util.ArrayList;
import java.util.Date;

public class Booking {
    private int id;
    private Date bookDate;
    private float totalAmount;
    private String note;
    private User user;
    private Client client;
    private ArrayList<BookedRoom> bookedRooms;

    public Booking() {
        this.bookedRooms = new ArrayList<BookedRoom>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<BookedRoom> getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(ArrayList<BookedRoom> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    public void addBookedRoom(BookedRoom bookedRoom) {
        this.bookedRooms.add(bookedRoom);
    }
} 