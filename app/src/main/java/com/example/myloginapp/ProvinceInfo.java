package com.example.myloginapp;

public class ProvinceInfo {
    int id;
    String province;
    ProvinceInfo(int id,String province){
        this.id=id;
        this.province=province;
    }
    int getId(){
        return id;
    }
    String getProvince(){
        return province;
    }
}
