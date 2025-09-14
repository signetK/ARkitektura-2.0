package com.yaquobi.arkitektura2.model;

public class FacultyModel {
    private int imageResId;
    private String name;
    private String role;

    public FacultyModel(int imageResId, String name, String role) {
        this.imageResId = imageResId;
        this.name = name;
        this.role = role;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}

