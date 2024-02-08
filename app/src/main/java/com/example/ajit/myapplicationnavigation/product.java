
package com.example.ajit.myapplicationnavigation;

public class product {
    private String name,bloodgroup,mobile,city;
    private int id;

    public product(String name, String bloodgroup, String mobile, int id,String city) {
        this.name = name;
        this.bloodgroup = bloodgroup;
        this.mobile = mobile;
        this.id = id;
        this.city=city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
