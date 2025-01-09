package com.datingapp;

public class Profile{
    private String name;
    private int age;
    private String bio;
    private String imagePath;

    public Profile(String name, int age, String bio, String imagePath){
        this.name = name;
        this.age = age;
        this.bio = bio;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public int getAge(){
        return age;
    }

    public String getBio(){
        return bio;
    }

    public String getImagePath(){
        return imagePath;
    }
}