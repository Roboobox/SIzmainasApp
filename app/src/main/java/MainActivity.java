package com.avg.roboo.stunduizmainas;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;
    SharedPreferences prefs = null;
    private ViewPager viewPager;
    ViewPagerAdapter viewPageAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    MenuItem prevMenuItem;

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavBot);
        //scheduleNotification(getNotification("", "Jaunas izmaiņas klasei"), 1000 * 7200);//1000 * 7200
        savePreviousChanges();


        //scheduleNotification(getNotification("5 second delay"), 5000);
        //bottomNavigationView.setSelectedItemId(R.id.changesItem);



        //listView=(ListView)findViewById(R.id.stunduList);
        //stunduIzmainasSdn= new ArrayList<>();

        //stunduIzmainasSdn.add(new ListModel("5", "10.3", "Matemātika"));
        //stunduIzmainasSdn.add(new ListModel("2", "12.4", "Sports"));
        //stunduIzmainasSdn.add(new ListModel("1", "9.1", "Fizika"));
        //stunduIzmainasSdn.add(new ListModel("6", "7.2", "Latv. val."));

        //adapter= new CustomAdapter(stunduIzmainasSdn,getApplicationContext());

        //listView.setAdapter(adapter);
        //listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           // @Override
           // public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           //     ListModel dataModel= stunduIzmainasSdn.get(position);
//
          //      Snackbar.make(view, dataModel.getKlase()+"\n"+dataModel.getInfo()+" API: "+dataModel.getStunda(), Snackbar.LENGTH_LONG)
          //              .setAction("No action", null).show();
          //  }
       // });



        //bottomNavigationView.bringToFront();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.changesItem:
                                viewPager.setCurrentItem(2);
                                //StunduIzmainasFragment stf = new StunduIzmainasFragment();
                                //getSupportFragmentManager().beginTransaction().replace(R.id.frame, stf).commit();
                                break;

                            case R.id.settingsItem:
                                viewPager.setCurrentItem(4);
                                //SettingsFragment sf = new SettingsFragment();
                               // getSupportFragmentManager().beginTransaction().replace(R.id.frame, sf).commit();
                                break;

                            case R.id.placeItem:
                                viewPager.setCurrentItem(3);
                                //KlasesIzmainasFragment kf = new KlasesIzmainasFragment();
                                //getSupportFragmentManager().beginTransaction().replace(R.id.frame, kf).commit();
                                break;

                            case R.id.homeItem:
                                viewPager.setCurrentItem(0);
                                //HomeFragment hf = new HomeFragment();
                                //getSupportFragmentManager().beginTransaction().replace(R.id.frame, hf).commit();
                                break;
                            case R.id.infoItem:
                                viewPager.setCurrentItem(1);
                                //InfoActivity inf = new InfoActivity();
                                //getSupportFragmentManager().beginTransaction().replace(R.id.frame, inf).commit();
                                break;
                        }
                        return true;
                    }
                });

        //HomeFragment hf = new HomeFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.frame, hf).commit();
        viewPager.setCurrentItem(0);
        //viewPager.setOffscreenPageLimit(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                //Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       /*  //Disable ViewPager Swipe
       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
        */

        setupViewPager(viewPager);




//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            if (item.getItemId() == R.id.changesItem) {
//                bottomNavigationView.setSelectedItemId(R.id.changesItem);
//                StunduIzmainasFragment f = new StunduIzmainasFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame, f).commit();
//            }
//            else if(item.getItemId() == R.id.settingsItem){
//                SettingsFragment f = new SettingsFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame, f).commit();
//            }
//            else if(item.getItemId() == R.id.placeItem){
//                KlasesIzmainasFragment f = new KlasesIzmainasFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame, f).commit();
//            }
//
//            return false;
//        });

        setTheme(R.style.AppDarkTheme);


    }
//    private void scheduleNotification(Notification notification, int delay) {
//
//        Calendar calendar = Calendar.getInstance();
//
//        calendar.setTimeInMillis(System.currentTimeMillis());
//
//        //long wakeupTime = calendar.getTimeInMillis() + 10000;
//
//        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
//        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, 1);
//        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        long futureInMillis = SystemClock.elapsedRealtime() + delay;
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), delay, pendingIntent);
//    }
//    private Notification getNotification(String content, String contentTitle) {
//        Notification.Builder builder = new Notification.Builder(this);
//
//        Intent notificationIntent = new Intent(getApplicationContext(), SplashScreen.class);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
//                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        builder.setContentTitle(contentTitle);
//        builder.setContentIntent(contentIntent);
//        builder.setAutoCancel(true);
//        builder.setContentText(content);
//        builder.setSmallIcon(R.drawable.ic_stat_icon_trnsp);
//        return builder.build();
//    }

    private void savePreviousChanges(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();


        editor.putString("userSelectedKlasePrevious", SettingsFragment.klase);
        editor.commit();
    }

    private void setupViewPager(ViewPager viewPager)
    {

        HomeFragment hf = new HomeFragment();
        StunduIzmainasFragment stf = new StunduIzmainasFragment();
        InfoActivity inf = new InfoActivity();
        KlasesIzmainasFragment kf = new KlasesIzmainasFragment();
        SettingsFragment sf = new SettingsFragment();
        viewPageAdapter.addFragment(hf);
        viewPageAdapter.addFragment(stf);
        viewPageAdapter.addFragment(inf);
        viewPageAdapter.addFragment(kf);
        viewPageAdapter.addFragment(sf);
        viewPager.setAdapter(viewPageAdapter);
    }



//    private void Notify(){
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//        mBuilder.setSmallIcon(R.drawable.ic_stat_icon_trnsp);
//        mBuilder.setContentTitle("Notification Alert, Click Me!");
//        mBuilder.setContentText("Hi, This is Android Notification Detail!");
//        //mBuilder.setContentIntent()
//
//        //mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
//
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//// notificationID allows you to update the notification later on.
//        mNotificationManager.notify(1, mBuilder.build());


}
