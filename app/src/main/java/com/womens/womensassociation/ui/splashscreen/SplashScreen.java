package com.womens.womensassociation.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.womens.womensassociation.ui.main_activity.MainActivity;
import com.womens.womensassociation.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView logo=findViewById(R.id.logo_id);
        View splash_back=findViewById(R.id.splash_back);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.myanimition);
        Animation animation2= AnimationUtils.loadAnimation(this,R.anim.success_mask_layout);

        logo.startAnimation(animation);
        splash_back.startAnimation(animation2);
        Thread t=new Thread()
        {

            public void run()
            {

                try {

                    sleep(3000);
                    finish();
                    Intent cv=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(cv);

                }

                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }
}
