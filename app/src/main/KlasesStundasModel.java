package com.avg.roboo.stunduizmainas;

/**
 * Created by roboo on 03.07.2017.
 */

public class KlasesStundasModel {
    String stundasNum;
    String stunda;
    String kabinets;

    public KlasesStundasModel(String stundasNum, String stunda, String kabinets){
        this.stundasNum = stundasNum;
        this.stunda = stunda;
        this.kabinets = kabinets;
    }

    public String getStundasNum() {
        return stundasNum;
    }

    public String getStunda() {
        return stunda;
    }

    public String getKabinets() {
        return kabinets;
    }
}
