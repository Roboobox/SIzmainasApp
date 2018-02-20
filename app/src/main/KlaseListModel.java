package com.avg.roboo.stunduizmainas;

/**
 * Created by roboo on 01.08.2017.
 */

public class KlaseListModel {
    String stundasNum;
    String klase;
    String stunda;
    String info;
    String datums;

    public KlaseListModel(String stundasNum, String klase, String stunda, String info, String datums){
        this.stundasNum = stundasNum;
        this.klase = klase;
        this.stunda = stunda;
        this.info = info;
        this.datums = datums;
    }

    public String getStundasNum() {
        return stundasNum;
    }

    public String getKlase() {
        return klase;
    }

    public String getStunda() {
        return stunda;
    }
    public String getInfo() {
        return info;
    }
    public String getDatums(){ return datums; }
}
