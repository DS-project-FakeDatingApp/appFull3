package com.example.dating.Main;


import com.example.dating.Profile.Profile_Activity;

import java.util.Comparator;

public class Cards {
    private String userId;
    private String name, profileImageUrl, bio, interest;
    private int age;
    private int distance;
    private int similarinteret;

    public Cards(String name, int age, String profileImageUrl, String bio, String interest, int distance, int similarinterest) {
        this.name = name;
        this.age = age;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.interest = interest;
        this.distance = distance;
        this.similarinteret = similarinterest;
    }

    public Cards(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getDistance() {
        return distance;
    }

    public String getBio() {
        return bio;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getInterest() {
        return interest;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getSimilarinterest() {
        return similarinteret;
    }

    public void setSimilarinterest(int similarinterest) {
        this.similarinteret = similarinterest;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Comparator <Cards> dahsusun = new Comparator<Cards>() {
        @Override
        public int compare(Cards a, Cards b) {

            int first = a.getSimilarinterest();
            int second = b.getSimilarinterest();

            return second-first;
        }
    };
}


