package com.avg.roboo.stunduizmainas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by roboo on 21.07.2017.
 */

public class SplashScreen extends AppCompatActivity {
    static String result = "";
    static String resultKlases = "";
    static String resultDzim = "";
    static String resultDarbDzim = "";
    static String resultVardD = "";
    static String resultSlimCurr = "";
    static String resultSlimNext = "";
    static String resultInfoIzCurr = "";
    static String resultInfoIzNext = "";
    static String resultInfo = "";
    static String resultSubjects = "";
    String resultDates = "";

    static String dateCurrent = "";
    static String dateNext = "";
    static String dateNextFormat = "";
    static String dateCurrentFormat = "";
    static String nameOfDayCurrent = "";
    static String nameOfDayNext = "";

    String klase = "";
    ProgressBar progressBar;
    Boolean loadingFail = false;
    Spinner spinnerKlase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        spinnerKlase = (Spinner)findViewById(R.id.spinnerKlase);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1500);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        findViewById(R.id.imageView2).startAnimation(rotateAnimation);
        startHeavyProcessing();
        progressBar = (ProgressBar)findViewById(R.id.progressSplash);
        progressBar.setMax(90);

    }

    private void startHeavyProcessing(){
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if(checkIfDatabaseReachable()){
                updateProgressbar(10);
            }
            else{
                loadingFail = true;
            }
            loadDates();
            getDateInfo();
            updateProgressbar(20);
            loadNameDays();
            updateProgressbar(30);
            loadBirthdays();
            loadDarbBirthdays();
            updateProgressbar(40);
            getInfoIzCurr();
            getInfoIzNext();
            updateProgressbar(50);
            loadDatabase();
            updateProgressbar(60);
            getInfo();
            updateProgressbar(70);
            getKlases();
            loadSettings();
            updateProgressbar(80);
            getSlimCurr();
            getSlimNext();
            updateProgressbar(90);
            getSubjects();
            updateProgressbar(100);

//            for (int i = 0; i < 5; i++) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    Thread.interrupted();
//                }
//            }
            updateProgressbar(90);
            return "";
        }
        public void updateProgressbar(int progress){
            if(!loadingFail) {
                progressBar.setProgress(progress);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(!loadingFail) {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                i.putExtra("data", result);
                startActivity(i);
                finish();
            }
            else{
                Intent in = new Intent(SplashScreen.this, LoadingFailActivity.class);
                in.putExtra("data", result);
                startActivity(in);
                finish();
                TextView text = (TextView)findViewById(R.id.databaseInfo);
                //text.setText("Database not reachable!");
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
    public void loadDatabase(){
        //some heavy processing resulting in a Data String
        boolean noChanges = false;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Log.d("dataid", "testingenter");
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getdata.php");
            //HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
            //Log.d("dataid", result);
            //Log.d("dataid", sb.toString() + " real result");
            //con.setDoOutput(true);
            //Log.d("dataid", "firstenter");
            //con.setRequestMethod("GET");
            reader.close();
            inputn.close();
            //inputstr = new BufferedInputStream(con.getInputStream());
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
    }
    public void getKlases(){
        boolean noChanges = false;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getKlases.php");
            //HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resultKlases = sb.toString();
            reader.close();
            inputn.close();
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
        if(resultKlases.equals("")){
            loadingFail = true;
        }

    }
    public boolean checkIfDatabaseReachable(){
        try {
            URL url = new URL("http://stunduizmainas.lv/appphp/getdata.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            int code = connection.getResponseCode();

            if (code == 200) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e){
            loadingFail = true;
            return false;
        }
    }
    private void loadSettings(){
        SharedPreferences sharedPref = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String klaseSaved = sharedPref.getString("userChoiceKlase", "");
        if(!klaseSaved.equals("")) {
            // set the selected value of the spinner
            SettingsFragment.klase = klaseSaved;
        }
    }
    private void loadBirthdays(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getDzimD.php");
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resultDzim = sb.toString();
            reader.close();
            inputn.close();
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
    }
    private void loadDarbBirthdays(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getDarbDzimD.php");
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resultDarbDzim = sb.toString();
            reader.close();
            inputn.close();
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
    }
    private void loadNameDays(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getVardD.php");
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resultVardD = sb.toString();
            reader.close();
            inputn.close();
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
    }
    private void loadDates(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getDates.php");
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resultDates = sb.toString();
            reader.close();
            inputn.close();
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
    }

    private void getDateInfo(){
        result = resultDates;
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;

            for(int i=0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);

                dateCurrent = jo.getString("dateCurrent");
                dateNext = jo.getString("dateNext");
                dateCurrentFormat = jo.getString("dateCurrentFormat");
                dateNextFormat = jo.getString("dateNextFormat");
                nameOfDayCurrent = jo.getString("nameOfDayCurrent");
                nameOfDayNext = jo.getString("nameOfDayNext");

//                else{
//                    noChanges = true;
//                }
            }
        }
        catch (Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
        if(dateCurrent.equals("") || dateNext.equals("")){
            loadingFail = true;
        }
    }
    private void getSlimCurr(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getSlimCurr.php");
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resultSlimCurr = sb.toString();
            reader.close();
            inputn.close();
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
    }
    private void getSlimNext(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getSlimNext.php");
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resultSlimNext = sb.toString();
            reader.close();
            inputn.close();
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
    }

    private void getInfoIzCurr(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getInfoIzCurr.php");
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resultInfoIzCurr = sb.toString();
            reader.close();
            inputn.close();
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
    }
    private void getInfoIzNext(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getInfoIzNext.php");
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resultInfoIzNext = sb.toString();
            reader.close();
            inputn.close();
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
    }

    private void getInfo(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getInfo.php");
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resultInfo = sb.toString();
            reader.close();
            inputn.close();
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
    }

    private void getSubjects(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String line = "";
        try{
            URL url = new URL("http://stunduizmainas.lv/appphp/getSubjects.php");
            InputStream inputn;
            inputn = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputn));
            StringBuilder sb = new StringBuilder();
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resultSubjects = sb.toString();
            reader.close();
            inputn.close();
        }
        catch(Exception e){
            loadingFail = true;
            e.printStackTrace();
        }
    }
}
