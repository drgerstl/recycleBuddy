package com.example.recyclebuddy;

public class Recyclable {
    String itemID; //specific thing
    String type; //
    int isRecyclable;
    String UPC;

    public Recyclable() {

    }

    public Recyclable(String itemID, String UPC, int isRecyclable) {
        this.itemID = itemID;
        this.UPC = UPC;
        this.isRecyclable = isRecyclable;
    }
    public void print(){
        System.out.println("itemID: "+itemID);
        System.out.println("isRecyclable: "+isRecyclable);
        System.out.println("UPC: ");
    }
}
