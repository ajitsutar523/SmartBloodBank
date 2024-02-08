package com.example.ajit.myapplicationnavigation;

public class user1 {
    private String name,bloodgroup,mobile,donor,city;


    public user1() {
    }



    public user1(String name, String bloodgroup, String mobile,String donor,String city) {
        this.name = name;
        this.bloodgroup = bloodgroup;
        this.mobile = mobile;
        this.donor=donor;
        this.city=city;
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

    public String getName() {
        return name;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public String getMobile() {
        return mobile;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
