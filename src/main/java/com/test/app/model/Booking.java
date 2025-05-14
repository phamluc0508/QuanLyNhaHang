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
    private ArrayList<BookedTable> bookedTables;

    public Booking() {
        this.bookedTables = new ArrayList<>();
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

    public ArrayList<BookedTable> getBookedTables() {
        return bookedTables;
    }

    public void setBookedTables(ArrayList<BookedTable> bookedTables) {
        this.bookedTables = bookedTables;
    }

    public void addBookedTable(BookedTable bookedTable) {
        this.bookedTables.add(bookedTable);
    }
} 