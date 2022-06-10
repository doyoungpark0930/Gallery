package com.example.myloginapp;

import android.graphics.Bitmap;

import java.util.ArrayList;

//* 도영님께
// static 변수는 프로그램이 종료 전까지 살아있는 변수로, 이곳에 저장 후 사용예정*//
public class Object {
	static public ArrayList<GalleryInfo> art=new ArrayList<GalleryInfo>();
    static ArrayList<ExhibitionInfo> exhibition=new ArrayList<ExhibitionInfo>();
	static public ArrayList<DesReviewInfo> review=new ArrayList<DesReviewInfo>();
	static ArrayList<UserInfo> userlist=new ArrayList<UserInfo>();
	static public UserInfo user; //로그인한 사용자 정보를 담을 객체

}
