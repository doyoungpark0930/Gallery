package com.example.myloginapp;

public class DesReviewInfo {

    int usernum;
    int artnum;
    int star;
    String title;
    String evaluation;

    public DesReviewInfo(int star, String title, String evaluation) {
        this.star = star;
        this.title = title;
        this.evaluation = evaluation;
    }

    public DesReviewInfo(int usernum,int artnum,int star,String evaluation) {
        this.artnum=artnum;
        this.usernum=usernum;
        this.star = star;
        this.evaluation = evaluation;
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
