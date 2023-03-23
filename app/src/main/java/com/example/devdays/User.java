package com.example.devdays;

import androidx.annotation.NonNull;

public class User {

    String itemID;
    String itemName;
    String Quantity;
    String PurchaseDate;
    @NonNull
    @Override
    public String toString() {
        return itemName + itemID + Quantity + PurchaseDate;
    }

    public User(String Item_ID, String Item_name, String Quantity, String PurchaseDate) {
        this.itemName = Item_name;
        this.itemID = Item_ID;
        this.Quantity = Quantity;
        this.PurchaseDate = PurchaseDate;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String Item_ID) {
        this.itemID = Item_ID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String Item_Name) {this.itemName = Item_Name;}

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity){this.Quantity = Quantity;}

    public String getPurchaseDate(){return PurchaseDate;}

    public void setPurchaseDate(String PurchaseDate){this.PurchaseDate = PurchaseDate;}




}
