package com.yaquobi.arkitektura2.model;

import com.yaquobi.arkitektura2.model.CollegeModel;

public class CollegeModel {
    private final int imageRes;
    private final int logoRes;
    private final String name;
//    private final String history;
//    private final String info;

    public CollegeModel(int imageRes, int logoRes, String name) {
        this.imageRes = imageRes;
        this.logoRes = logoRes;
        this.name = name;

    }

    public int getImageRes() { return imageRes; }
    public int getLogoRes() { return logoRes; }
    public String getName() { return name; }
}


