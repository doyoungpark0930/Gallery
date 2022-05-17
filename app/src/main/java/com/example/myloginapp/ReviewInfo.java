package com.example.myloginapp;

public class ReviewInfo {
	int usernum;
	int ArtNum;
	String mention;
	ReviewInfo(int usernum,int ArtNum,String mention){
		this.ArtNum=ArtNum;
		this.usernum=usernum;
		this.mention=mention;
	}
	int getUsernum(){
		return usernum;
	}
	int getArtNum(){
		return ArtNum;
	}
	String getMention(){
		return mention;
	}
}
