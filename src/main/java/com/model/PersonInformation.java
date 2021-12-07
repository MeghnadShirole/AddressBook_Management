package com.model;

public class PersonInformation {
    int id;
    String first_name;
    String address;
    String city;
    String state;
    int zip;
    int mobilenumber;
    String email;

    public PersonInformation() {
    }

    public PersonInformation(String first_name, String address, String city, String state, int zip, int mobilenumber, String email) {
        this.first_name = first_name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.mobilenumber = mobilenumber;
        this.email = email;
    }

    public PersonInformation(int id,String first_name, String address, String city, String state, int zip, int mobilenumber, String email) {
        this(first_name, address, city, state, zip, mobilenumber, email);
        this.id = id;
    }
}
