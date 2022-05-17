package com.example.myloginapp;

public class ExhibitionInfo {
	private int ExhibitionNum;
	private String ExhibitionName;
	private String siteURL;
	private int LocationNum;
	ExhibitionInfo(int ExhibitionNum,String ExhibitionName,String siteURL,int loc){
		this.ExhibitionNum=ExhibitionNum;
		this.ExhibitionName=ExhibitionName;
		this.siteURL=siteURL;
		this.LocationNum=loc;
	}
	/*
	public String PrintExh() {
		   for(LocationInfo tmp: Object.location) {
			   if(tmp.getLocNum()==LocationNum) {
					   return this.ExhibitionName+" : "+tmp.getCity();
				}
		   }
		return this.ExhibitionName+" : ";
	}
	*/
	public int getLocationNum(){
		return LocationNum;
	}
	public int getNum() {
		return ExhibitionNum;
	}
	public String getExhibitionName() {
		return ExhibitionName;
	}
	public String getSiteURL() {
		return siteURL;
	}
}
