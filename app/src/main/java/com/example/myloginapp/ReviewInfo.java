package com.example.myloginapp;

public class ReviewInfo {
	int usernum;
	int ArtNum;
	int reviewStar;
	String mention;
	ReviewInfo(int usernum,int ArtNum,String mention,int reviewStar){
		this.ArtNum=ArtNum;
		this.usernum=usernum;
		this.mention=mention;
		this.reviewStar=reviewStar;
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
	int getReviewStar() {return reviewStar;}
}
