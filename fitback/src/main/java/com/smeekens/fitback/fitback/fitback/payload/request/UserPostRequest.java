package com.smeekens.fitback.fitback.fitback.payload.request;

public class UserPostRequest {
    private String fullName;
    private String address;
    private String zipcode;
    private String country;
    private int age;
    private int height;
    private int weight;

    public UserPostRequest(String fullName, String address, String zipcode, String country, int age, int height, int weight) {
        this.fullName = fullName;
        this.address = address;
        this.zipcode = zipcode;
        this.country = country;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
