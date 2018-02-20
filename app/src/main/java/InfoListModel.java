package com.avg.roboo.stunduizmainas;

/**
 * Created by roboo on 07.08.2017.
 */

public class InfoListModel {

    String kurinfo;
    String darbinieksinfo;
    String info;

    public InfoListModel(String kurinfo, String darbinieksinfo, String info){
        this.kurinfo = kurinfo;
        this.darbinieksinfo = darbinieksinfo;
        this.info = info;
    }

    public String getKurinfo() {
        return kurinfo;
    }

    public String getDarbinieksinfo() {
        return darbinieksinfo;
    }

    public String getInfo() {
        return info;
    }
}
