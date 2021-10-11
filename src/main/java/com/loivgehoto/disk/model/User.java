package com.loivgehoto.disk.model;

public class User
{
    private int id;
    private String name;
    private String password;
    private int vip;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getVip() {
        return vip;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }
}
