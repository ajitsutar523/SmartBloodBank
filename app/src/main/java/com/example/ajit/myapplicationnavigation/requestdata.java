package com.example.ajit.myapplicationnavigation;

public class requestdata {

private     String nm,bg,mob,date,order,status,email,city,addr;

    public requestdata() {
    }

    public requestdata(String nm, String bg, String mob, String date,String order,String status,String email,String addr,String city) {

        this.nm = nm;
        this.bg = bg;
        this.mob = mob;
        this.date = date;
        this.order=order;
        this.status=status;
        this.email=email;
        this.city=city;
        this.addr=addr;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getAddr() {
        return addr;
    }

    public String getStatus() {
        return status;
    }

    public String getOrder() {
        return order;
    }

    public String getNm() {
        return nm;
    }

    public String getBg() {
        return bg;
    }

    public String getMob() {
        return mob;
    }

    public String getDate() {
        return date;
    }
}
