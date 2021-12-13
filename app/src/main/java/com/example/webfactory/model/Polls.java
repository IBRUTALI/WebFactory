package com.example.webfactory.model;

public class Polls {
    int id;
    String title;
    String var1;
    String var2;
    String var3;

    public Polls(int id, String title, String var1, String var2, String var3) {
        this.id = id;
        this.title = title;
        this.var1 = var1;
        this.var2 = var2;
        this.var3 = var3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }

    public String getVar3() {
        return var3;
    }

    public void setVar3(String var3) {
        this.var3 = var3;
    }
}
