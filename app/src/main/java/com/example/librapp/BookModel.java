package com.example.librapp;

import java.io.Serializable;

public class BookModel implements Serializable {

    String author;
    String title;
    String isbn;
    String category;
    String desc;
    String image;


    //TODO future ideas
    /*
    double rating;
    int popularity;*/



    public BookModel() {
    }

    public BookModel(String author, String title, String isbn, String category, String desc, String image) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.category = category;
        this.desc = desc;
        this.image = image;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
