package com.example.myloginapp;

public class LocationInfo {
	private int LocNum;
	private String City;
	private int province;
	LocationInfo(int locNum,String city,int province){
		this.LocNum=locNum;
		this.City=city;
		this.province=province;
	}
	public int getProvince(){return province;}
	public int getLocNum() {return LocNum;}
	public String getCity() {return City;}
}
