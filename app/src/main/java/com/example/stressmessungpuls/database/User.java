package com.example.stressmessungpuls.database;
import androidx.room.*;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "email")
    public String eMail;

}
