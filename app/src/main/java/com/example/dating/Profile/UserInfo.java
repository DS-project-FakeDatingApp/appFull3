package com.example.dating.Profile;

import java.util.ArrayList;

public class UserInfo {
    private String name, bio, status, interestedin, seekingfor, gender;
    private ArrayList<String> interests;

    public UserInfo(){

    }

    public UserInfo(String name,String gender, String bio, String status,String interestedin,String seekingfor,
                    ArrayList<String> interests){
        this.name=name;this.gender=gender;this.bio=bio;this.status=status;this.interestedin=interestedin;
        this.seekingfor=seekingfor;this.interests=interests;
    }

    public String getBio(){
        return bio;
    }

    public void setBio(){
        this.bio = bio;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getInterestedin() {
        return interestedin;
    }

    public void setInterestedin(String interestedin) {
        this.interestedin = interestedin;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public String getSeekingfor() {
        return seekingfor;
    }

    public void setSeekingfor(String seekingfor) {
        this.seekingfor = seekingfor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() { return gender; }
}
