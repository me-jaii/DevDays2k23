package com.example.devdays;

import androidx.annotation.NonNull;

public class User {

    String Item_Name;
    String Quantity;
    String Duration;

    String storType;

    @NonNull
    @Override
    public String toString() {
        return Item_Name + Quantity + Duration + storType;
    }

    public User() {
    }

    public User(String item_Name, String quantity, String duration, String storType) {
        Item_Name = item_Name;
        Quantity = quantity;
        Duration = duration;
        this.storType = storType;
    }

    public String getItem_Name() {return Item_Name;}

    public void setItem_Name(String Item_name) {this.Item_Name = Item_name;}

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity){this.Quantity = quantity;}

    public String getDuration(){return Duration;}

    public void setDuration(String duration){this.Duration = duration;}

    public String getStorType() {
        return storType;
    }

    public void setStorType(String storType) {
        this.storType = storType;
    }
}
