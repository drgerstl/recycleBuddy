package com.example.recyclebuddy;

import java.util.ArrayList;
public class RecycleCenter {
    private String locationID;
   private String address;
    private String city;
    private String phone;
    private ArrayList<Recyclable> typesAccepted;

    public RecycleCenter() {

    }

    public RecycleCenter(String locationID, String address, String city, String phone,
                         ArrayList<Recyclable> typesAccepted) {

    }

    public void print() {
        System.out.println("LocationID" + locationID);
        System.out.println("Address" + address);
        System.out.println("City" + city);
        System.out.println("Phone" + phone);
    }

}
