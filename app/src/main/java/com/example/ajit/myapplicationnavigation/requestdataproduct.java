package com.example.ajit.myapplicationnavigation;

public class requestdataproduct {
    public requestdataproduct() {
    }
    String bg,nm,date,order,mob,status;



    public requestdataproduct(String bg, String nm, String date, String order, String mob, String status) {
        this.bg = bg;
        this.nm = nm;
        this.date = date;
        this.order=order;
        this.mob=mob;
        this.status=status;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
