package com.example.myloginapp;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class GalleryInfo implements Serializable{

    private int ArtNum;
    private String name;
    private String desc;
    private String url;
    private Bitmap Realimage;
    private String StartPeriod;
    private String EndPeriod;
    private String Price;
    private int ExhibitionNum;
    private String ExhibitionName; // 없앨 예정.

    //dscReviewInfo객체형성, 해당 작품에서의 리뷰가 여러개 있기때문.
    private ArrayList<DesReviewInfo> desReviewInfo = new ArrayList<>();

    public GalleryInfo(int i){
        Log.v("tag","출력 출력할거애용");

    }
    public GalleryInfo(String ArtName,String StartPeriod,String EndPeriod,String Price,String image,String explanation){
        this.name=ArtName;
        this.StartPeriod=StartPeriod.replace(".","-");
        this.EndPeriod=EndPeriod.replace(".","-");
        this.Price=Price;
        image=image.replace("\\","");
        this.url=image;
        this.desc=explanation;
    }
    //img src를 int로 임시변경 -> url로 바꿀예정
    public GalleryInfo(int ArtNum,String ArtName,String StartPeriod,String EndPeriod,String Price,String image,String explanation,int ExhibitionNum,ArrayList<DesReviewInfo> desReviewInfo){
        Log.v("tag","1");
        this.ArtNum=ArtNum;
        this.name=ArtName;
        this.StartPeriod=StartPeriod.replace(".","-");
        this.EndPeriod=EndPeriod.replace(".","-");
        this.Price=Price;
        image=image.replace("\\","");
        this.url=image;
        this.ExhibitionNum=ExhibitionNum; //전시회 번호는 나중에 추가
        this.desc=explanation;
        this.desReviewInfo=desReviewInfo;
        this.ExhibitionName=ExhibitionName;
    }
    public StringBuilder PrintArt() {
        StringBuilder msg=new StringBuilder();
        msg.append("전시회장 :"+getExhibitionName()+"\n시작 날짜 : "+getStartPeriod()+"\n종료 날짜 : "+getEndPeriod()+"\n가격 : "+getPrice()+"\n");
        return msg;
    }
    public String getExhibitionName() {return ExhibitionName;}
    public int getNum() {
        return ArtNum;
    }
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
    public ArrayList<DesReviewInfo> getDesReviewInfo() {
        return desReviewInfo;
    }


}
