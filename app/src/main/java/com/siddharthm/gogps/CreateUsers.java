package com.siddharthm.gogps;

public class CreateUsers {
    public CreateUsers(){

    }
    public String name,email,password,code,isSharing,lat,lng,imageUrl;



    public CreateUsers(String name, String email, String password, String code, String isSharing, String lat, String lng, String imageUrl) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.isSharing = isSharing;
        this.lat = lat;
        this.lng = lng;
        this.imageUrl = imageUrl;
    }
}
