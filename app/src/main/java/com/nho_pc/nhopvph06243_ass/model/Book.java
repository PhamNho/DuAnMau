package com.nho_pc.nhopvph06243_ass.model;

public class Book {
    private String BookID,BookName,Type_Book_ID,Author,NXB,Price,Quantity;

    public Book(String bookID, String bookName, String type_Book_ID, String author, String NXB, String price, String quantity) {
        BookID = bookID;
        BookName = bookName;
        Type_Book_ID = type_Book_ID;
        Author = author;
        this.NXB = NXB;
        Price = price;
        Quantity = quantity;
    }

    public Book() {

    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getType_Book_ID() {
        return Type_Book_ID;
    }

    public void setType_Book_ID(String type_Book_ID) {
        Type_Book_ID = type_Book_ID;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
