package com.example.ajit.myapplicationnavigation;

public class registerdata {
    String email,username,pwd,name,mobile,donor,addr,city,id,gender,bloodgroup;

    public  registerdata()
    {
    }

    public registerdata(String email,  String pwd, String name, String mobile, String donor, String addr, String city, String id,String gender,String bloodgroup) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.mobile = mobile;
        this.donor = donor;
        this.addr = addr;
        this.city = city;
        this.id = id;
        this.bloodgroup=bloodgroup;
        this.gender=gender;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPwd() {
        return pwd;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDonor() {
        return donor;
    }

    public String getAddr() {
        return addr;
    }

    public String getCity() {
        return city;
    }

    public String getId() {
        return id;
    }
}
