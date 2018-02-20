package com.avg.roboo.stunduizmainas;

/**
 * Created by roboo on 18.06.2017.
 */

public class ListModel {

    String stundasNum;
    String klase;
    String stunda;
    String info;

    public ListModel(String stundasNum, String klase, String stunda, String info){
        this.stundasNum = stundasNum;
        this.klase = klase;
        this.stunda = stunda;
        this.info = info;
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
}
