package com.example.ajit.myapplicationnavigation;

public class registeruser1 {
    public registeruser1() {
    }
    String addr,bloodgroup,city,donor,email,mobile,name,id;

    public registeruser1(String addr, String bloodgroup, String city, String donor, String email, String mobile, String name,String id) {
        this.addr = addr;
        this.bloodgroup = bloodgroup;
        this.city = city;
        this.donor = donor;
        this.email = email;
        this.mobile = mobile;
        this.name = name;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
