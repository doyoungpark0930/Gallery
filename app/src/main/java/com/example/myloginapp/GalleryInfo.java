package com.example.myloginapp;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;

public class GalleryInfo {

    private int ArtNum;
    private String name;
    private String desc;
    private String url;
    private Bitmap Realimage;
    private int image; //곧 삭제예정
    private String StartPeriod;
    private String EndPeriod;
    private String Price;
    private int ExhibitionNum;

    public GalleryInfo(String name, String desc, int image) {
        this.name = name;
        this.desc = desc;
        this.image = image;
    }

    //img src를 int로 임시변경 -> url로 바꿀예정
    public GalleryInfo(int ArtNum,String ArtName,String StartPeriod,String EndPeriod,String Price,int SRC,String image,String explanation){
        this.ArtNum=ArtNum;
        this.name=ArtName;
        this.StartPeriod=StartPeriod;
        this.EndPeriod=EndPeriod;
        this.Price=Price;
        image=image.replace("\\","");
        this.url=image;
        this.ExhibitionNum=SRC;
        //this.ExhibitionNum=exhibitionNum; 전시회 번호는 나중에 추가
        this.desc=explanation;
    }
    StringBuilder PrintArt() {
        StringBuilder msg=new StringBuilder();
        msg.append("기간 : "+getStartPeriod()+" ~ "+getEndPeriod()+"\n가격 : "+getPrice()+"\n\n설명 : "+getDesc());;
        return msg;
    }
    int getArtNum() {
        return ArtNum;
    }
    //왜 int로 선언..?
    public Bitmap getImage() {
        return Realimage;
    }
    public String getUrl() {return url;}
    public void setImage(Bitmap bitmap) {
        Realimage = bitmap;
    }
    public String getName() {
        return name;
    }
    public String getStartPeriod() {
        return StartPeriod;
    }
    public String getEndPeriod() {
        return EndPeriod;
    }
    public String getPrice() {
        return Price;
    }
    public int getExhibitionNum() {
        return ExhibitionNum;
    }
    public String getDesc() {return desc;}
}
