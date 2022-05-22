package com.example.myloginapp.Description;

public class DesReviewInfo {

    int star;
    String title;
    String evaluation;

    public DesReviewInfo(int star, String title, String evaluation) {
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
