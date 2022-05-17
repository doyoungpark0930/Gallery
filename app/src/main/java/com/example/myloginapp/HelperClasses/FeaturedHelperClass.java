package com.example.myloginapp.HelperClasses;

import com.example.myloginapp.R;
import com.example.myloginapp.Object;

import java.util.ArrayList;

public class FeaturedHelperClass {
    int image;
    String title;
    String description;

    public FeaturedHelperClass(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public int getImage() {
        return image;
    }


    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }

    public static ArrayList<FeaturedHelperClass> createContactsList(int numContacts){
        ArrayList<FeaturedHelperClass> contacts = new ArrayList<FeaturedHelperClass>();
        for(int i = 1; i <= numContacts; i++){
            //contacts.add(new FeaturedHelperClass(R.drawable.test, Object.art.getName(), "내용"));
        }

        return contacts;
    }
}
