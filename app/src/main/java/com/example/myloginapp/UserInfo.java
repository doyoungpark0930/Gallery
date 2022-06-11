package com.example.myloginapp;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserInfo {

    private int userNum;
    private String id;
    private String passwd;
    private String email;
    ArrayList<Integer> subscription=new ArrayList<Integer>();

    UserInfo(int userNum,String id, String passwd, String email) {
        this.userNum=userNum;
        this.id = id;
        this.passwd = passwd;
        this.email = email;
    }
    String getPasswd(){ return passwd; }
    public int getNum(){
        return userNum;
    }
    public String getId() {
        return id;
    }
    public String getEmail(){
        return email;
    }
    @Exclude
    public Map<String, java.lang.Object> toMap() {
        HashMap<String, java.lang.Object> result = new HashMap<>();

        result.put("usernum", userNum);
        result.put("id", id);
        result.put("email", email);
        result.put("passwd", passwd);
        return result;
    }
}
