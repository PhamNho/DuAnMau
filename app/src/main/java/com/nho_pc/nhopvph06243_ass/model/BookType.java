package com.nho_pc.nhopvph06243_ass.model;

public class BookType {
    private String typeID;
    private String typeName;
    private String Description;
    private String position;

    public BookType(String typeID, String typeName, String description, String position) {

        this.typeID = typeID;
        this.typeName = typeName;
        this.Description = description;
        this.position = position;
    }

    public BookType() {

    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeCode) {
        this.typeID = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
