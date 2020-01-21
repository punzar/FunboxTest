package com.punzar.funboxtest;


import android.os.Parcel;
import android.os.Parcelable;

public class SmartPhone implements Parcelable {

    private String name;
    private double price;
    private int count;



    public SmartPhone(String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    protected SmartPhone(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        count = in.readInt();
    }

    public static final Creator<SmartPhone> CREATOR = new Creator<SmartPhone>() {
        @Override
        public SmartPhone createFromParcel(Parcel in) {
            return new SmartPhone(in);
        }

        @Override
        public SmartPhone[] newArray(int size) {
            return new SmartPhone[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(price);
        parcel.writeInt(count);
    }
}
