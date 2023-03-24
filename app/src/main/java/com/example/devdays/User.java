package com.example.devdays;

import androidx.annotation.NonNull;

public class User {

    String Item_Name;
    String Quantity;
    String Duration;
    @NonNull
    @Override
    public String toString() {
        return Item_Name + Quantity + Duration;
    }

    public User(String Item_name, String quantity, String duration) {
        this.Item_Name = Item_name;
        this.Quantity = quantity;
        this.Duration = duration;
//        this.storage = Gender;
    }

    public String getItem_Name() {return Item_Name;}

    public void setItem_Name(String Item_name) {this.Item_Name = Item_name;}

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity){this.Quantity = quantity;}

    public String getDuration(){return Duration;}

    public void setDuration(String duration){this.Duration = duration;}

}
