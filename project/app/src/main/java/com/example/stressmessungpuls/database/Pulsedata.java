package com.example.stressmessungpuls.database;
import androidx.room.*;

@Entity
public class Pulsedata {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "pulsevalue")
    public int pulsevalue;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "street")
    public String street;

    @ColumnInfo(name = "zipcode")
    public String zipcode;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "datetime")
    public String datetime;

}
