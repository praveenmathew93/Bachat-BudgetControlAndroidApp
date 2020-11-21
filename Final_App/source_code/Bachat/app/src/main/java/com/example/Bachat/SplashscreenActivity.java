package com.example.Bachat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

public class SplashscreenActivity extends AppCompatActivity {

        private static int SPLASH_SCREEN = 5000;
        //Variables
        Animation topAnim, bottomAnim, rotation;
        ImageView imageApp,imageTeam;
        TextView gingerbread, bachat;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setContentView(R.layout.splash_screen);

                //Animations
                topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
                bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
                rotation = AnimationUtils.loadAnimation(this,R.anim.rotate);

                //Hooks
                imageApp = (ImageView) findViewById(R.id.imageApp);
                //imageTeam = (ImageView) findViewById(R.id.imageLogo);
                //presentedBy = (TextView) findViewById(R.id.textPresentedBy);
                bachat = (TextView) findViewById(R.id.textBachat);
                gingerbread = (TextView) findViewById(R.id.textGingerbread);

                //Setting animation
                imageApp.setAnimation(rotation);
                //imageApp.setAnimation(bottomAnim);
                bachat.setAnimation(topAnim);
                gingerbread.setAnimation(bottomAnim);
                //imageTeam.setAnimation(bottomAnim);
                //presentedBy.setAnimation(bottomAnim);


                new Handler().postDelayed(new Runnable () {
                        @Override
                        public void run() {
                                Intent intent = new Intent(SplashscreenActivity.this,SliderTest.class);
                                startActivity(intent);
                                finish();
                        }
                },SPLASH_SCREEN);

        }

}