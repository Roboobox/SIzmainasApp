package com.avg.roboo.stunduizmainas;

/**
 * Created by roboo on 08.08.2017.
 */

public class InfoNewsListModel {
    String info;
    String skol;

    public InfoNewsListModel(String info, String skol){
        this.skol = skol;
        this.info = info;
    }

    public String getSkol() { return skol; }
    public String getInfo() {
        return info;
    }
}
