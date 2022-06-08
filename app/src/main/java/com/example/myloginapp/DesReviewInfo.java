package com.example.myloginapp;

import java.io.Serializable;

public class DesReviewInfo implements Serializable {

    int usernum;
    int artnum;
    int star;
    String title;
    String evaluation;
    public DesReviewInfo(){}
    public DesReviewInfo(int star, String title, String evaluation) {
        this.star = star;
        this.title = title;
        this.evaluation = evaluation;
    }

    public DesReviewInfo(int usernum,int artnum,String evaluation,int star) {
        this.artnum=artnum;
        this.usernum=usernum;
        this.star = star;
        this.evaluation = evaluation;
    }
    public int getUsernum(){
        return usernum;
    }
    public int getArtnum(){
        return artnum;
    }
    public int getStar() {
        return star;
    }

    public String getTitle() {
        return title;
    }

    public String getEvaluation() {
        return evaluation;
    }

}
