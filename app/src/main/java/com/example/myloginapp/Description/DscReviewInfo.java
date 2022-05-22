package com.example.myloginapp.Description;

public class DscReviewInfo {

    int star;
    String title;
    String evaluation;

    public DscReviewInfo(int star, String title, String evaluation) {
        this.star = star;
        this.title = title;
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
