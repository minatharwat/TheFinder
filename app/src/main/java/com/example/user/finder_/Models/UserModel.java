package com.example.user.finder_.Models;

/**
 * Created by User on 19/01/2017.
 */

public class UserModel {

    public   String PhoneNumber;
    public    String userName;
    public UserModel(String PhoneNumber,String usrName){

        this.PhoneNumber=PhoneNumber;
        this.userName=usrName;

    }


    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
