package com.avg.roboo.stunduizmainas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by roboo on 20.08.2017.
 */

public class LoadingFailActivity extends AppCompatActivity {

    ImageButton buttonReload;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadingfail);

        buttonReload = (ImageButton)findViewById(R.id.buttonReload);

        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoadingFailActivity.this, SplashScreen.class);
                startActivity(i);
                finish();
            }
        });

    }
}
