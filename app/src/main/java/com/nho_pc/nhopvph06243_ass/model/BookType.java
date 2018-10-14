package com.nho_pc.nhopvph06243_ass.model;

public class BookType {
    private String id,name,description,position;

    public BookType(String id, String name, String description, String position) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.position = position;
    }

    public BookType() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return getId()+"("+getName()+")";

    }
}
