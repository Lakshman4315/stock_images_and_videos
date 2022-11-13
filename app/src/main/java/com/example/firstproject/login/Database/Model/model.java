package com.example.firstproject.login.Database.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class model {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "USERNAME")
    String username;

    @ColumnInfo(name = "EMAIL")
    String email;

    @ColumnInfo(name = "PHONENUMBER")
    String phone_no;

    @ColumnInfo(name = "PASSWORD")
    String password;

//    @Ignore
//    model(int id,String username,String email,String phone_no,String password){
//        this.id=id;
//        this.username = username;
//        this.email = email;
//        this.phone_no = phone_no;
//        this.password = password;
//    }

    public model(String username, String email, String phone_no, String password){
        this.username = username;
        this.email = email;
        this.phone_no = phone_no;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
