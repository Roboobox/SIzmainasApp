package com.avg.roboo.stunduizmainas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by roboo on 08.08.2017.
 */

public class InfoNewsFragment extends Fragment {
    private static CustomAdapter_InfoNews adapter;
    ArrayList<InfoNewsListModel> info;
    private String result;
    ListView listView;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.infonews, container,false);


        listView = (ListView) view.findViewById(R.id.infonewsList);

        info = new ArrayList<>();

//        info.add(new InfoNewsListModel("Šodien,&nbsp;25.februārī no plkst.14:00&nbsp;Zolitūdes ģimnāzijā notiks florbola sacensības 10.-12.klašu zēnu komandām.", "Dimitrijs Ivanišaks"));
//        info.add(new InfoNewsListModel("Visiem <strong>11. klašu skolēniem,</strong> kuri izvirzīti mutiski aizstāvēt savus darbus <strong>ZPD Rīgas konferencē , pirmdien, 21. martā,</strong> <strong>plkst.10.00</strong>, jābūt norādītajās skolās, līdzi ņemot prezentāciju datu pārnēsātājā, kā arī iepriekš jānosūta prezentācijas, ja šādu prasību ZPD vērtēšanas komisija izvirza. Prezentācijas jāsaskaņo ar darba vadītāju un jāņem vērā recenzijās uzrādītās pētījuma neprecizitātes.<br>Visiem darbīgu noskaņojumu un lielu veiksmi! <br>Dress- code: lietišķs svētku. A.Vanaga", "Anita Vanaga"));
//        info.add(new InfoNewsListModel("Tuvojas video konkursa noslēgums. Lūgums savus video iesūtīt uz&nbsp;<a href=\"http://ej.uz/avgvideo\">http://ej.uz/avgvideo</a>", "Sergejs Zembkovskis"));
//        info.add(new InfoNewsListModel("<strong style=\"background-color:#ffff00\">FRISBIJS šodien nenotiek!</strong> &nbsp;(sakarā &nbsp;remontdarbiem sporta zālē)", "Dmitrijs Ivanišaks"));
//        info.add(new InfoNewsListModel("Apsveicam ar iegūto <span style=\"color:#cc0000\">Atzinību</span> </strong>Latvijas Sarkanā krusta&nbsp;republikāniskās skolu jauniešu sacensībās:<strong><em>Madaru Gintēnu(11.2),Lauru Līpenīti (11.2), Anci Kazušu (11.2),&nbsp;Luīzi Dipāni(11.3), Beāti Jurševsku (11.3), Lauru Tutāni (11.3)! Meitenes, labs sākums!&nbsp;</em></strong>", "Valentīna Andersone"));
//        info.add(new InfoNewsListModel("<div style=\"text-align:center\">Nobalso!!!<div><img src=\"https://web.archive.org/web/20160111184002im_/http://l2.yimg.com/bt/api/res/1.2/eS_l.NxmQnA0FvOz4dTCaw--/YXBwaWQ9eW5ld3NfbGVnbztmaT1maWxsO2g9MjMwO3E9NzU7dz0zNDU-/https://s.yimg.com/xe/i/us/sp/v/nba_cutout/players_l/20151027/5464.png\" style=\"width:140px;height:93px\"><br></div><div><a href=\"https://web.archive.org/web/20160111184002/http://ej.uz/KPbest\">http://ej.uz/KPbest </a> <br></div>", "Sergejs Zembkovskis"));

        getInfo();
        adapter = new CustomAdapter_InfoNews(info, getActivity().getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                InfoNewsListModel dataModel = info.get(position);

                //Snackbar.make(view, dataModel.getKlase() + "\n" + dataModel.getInfo() + " API: " + dataModel.getStunda(), Snackbar.LENGTH_LONG)
                //        .setAction("No action", null).show();
               //Toast.makeText(getContext().getApplicationContext(), "Pressed", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void getInfo(){
        result = SplashScreen.resultInfo;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;


            for(int i=0; i<ja.length(); i++) {
                jo = ja.getJSONObject(i);

                info.add(new InfoNewsListModel(jo.getString("info"), jo.getString("skolotajs")));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
