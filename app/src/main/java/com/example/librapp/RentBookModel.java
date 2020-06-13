package com.example.librapp;

import java.util.Date;

//firebase table to store borrowed books

public class RentBookModel {

    private String bookId;
    private Date datetime;
    private String uid;

    public RentBookModel() {
    }

    public RentBookModel(String bookId, Date datetime, String uid) {
        this.datetime = datetime;
        this.bookId = bookId;
        this.uid = uid;
    }

    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
