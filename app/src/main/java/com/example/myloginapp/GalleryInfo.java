package com.example.myloginapp;

import android.graphics.Bitmap;

import com.example.myloginapp.Description.DesReviewInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private String ExhibitionName;

    //dscReviewInfo객체형성, 해당 작품에서의 리뷰가 여러개 있기때문.
    private ArrayList<DesReviewInfo> desReviewInfo = new ArrayList<>();

    //img src를 int로 임시변경 -> url로 바꿀예정
    public GalleryInfo(int ArtNum,String ArtName,String StartPeriod,String EndPeriod,String Price,String image,String explanation,String ExhibitionName,ArrayList<DesReviewInfo> desReviewInfo){
        this.ArtNum=ArtNum;
        this.name=ArtName;
        this.StartPeriod=StartPeriod.replace(".","-");
        this.EndPeriod=EndPeriod.replace(".","-");
        this.Price=Price;
        image=image.replace("\\","");
        this.url=image;
        //this.ExhibitionNum=exhibitionNum; 전시회 번호는 나중에 추가
        this.desc=explanation;
        this.desReviewInfo=desReviewInfo;
        this.ExhibitionName=ExhibitionName;
    }
    public StringBuilder PrintArt() {
        StringBuilder msg=new StringBuilder();
        msg.append("시작 날짜 : "+getStartPeriod()+"\n종료 날짜 : "+getEndPeriod()+"\n가격 : "+getPrice()+"\n");
        return msg;
    }
    public String getExhibitionName() {return ExhibitionName;}
    public int getArtNum() {
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
