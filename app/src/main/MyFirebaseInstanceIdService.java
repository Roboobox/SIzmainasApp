package com.avg.roboo.stunduizmainas;

//import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
/**
 * Created by roboo on 04.12.2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d("firebaseid", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String refreshedToken){
        //Log.d("firebaseid", "Sending registration");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", refreshedToken)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.5/myfiles/insertData.php")
                .post(body)
                .build();

        try{
            client.newCall(request).execute();
        }
        catch (IOException e){
            e.printStackTrace();
            //Log.d("ErrorStack", e.toString());
        }
    }
}
