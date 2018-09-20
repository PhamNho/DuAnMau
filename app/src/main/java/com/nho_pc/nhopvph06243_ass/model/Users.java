package com.nho_pc.nhopvph06243_ass.model;

public class Users {
    private String userName;
    private String password;
    private String name;
    private String phoneNumber;

    public Users(String userName, String password, String name, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Users() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Override
    public String toString() {
        return "NguoiDung{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + name + '\'' +
                ", hoTen='" + phoneNumber + '\'' +
                '}';
    }
}
