package com.example.jarvis;

import android.support.v7.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.hanks.htextview.base.HTextView;
import java.util.ArrayList;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;


public class MainActivity extends AppCompatActivity {






    //--Media state class with four instances."NOT_READY", "PLAYING", "PAUSED" and "STOPPED"
    enum MediaState {NOT_READY, PLAYING, PAUSED, STOPPED}


    private MediaPlayer mediaPlayer;
    private MediaState mediaState;
    private HTextView textViewType, textViewRainBow;
    int delay = 5000; //milliseconds
    Handler handler;
    ArrayList<String> Messages1 = new ArrayList<>();
    ArrayList<String> Messages2 = new ArrayList<>();
    int position=0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        playAudio();

        textViewType= findViewById(R.id.textViewTyper);
        textViewRainBow= findViewById(R.id.textViewRainBow);

        Messages1.add("Hello World, You're Welcome!");
        Messages2.add("J.A.R.V.I.S.");
        textViewType.animateText(Messages1.get(position));
        textViewRainBow.animateText(Messages2.get(position));




        /* Change Messages every 5 Seconds */
        handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){

                handler.postDelayed(this, delay);
                if(position>=Messages1.size())
                    position=0;
                textViewRainBow.animateText(Messages2.get(position));
                textViewType.animateText(Messages1.get(position));
            }
        }, delay);



        final Button englishButton = findViewById(R.id.english_button);
        englishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                useJarvis();

                //final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);

                //--- Use bounce interpolator with amplitude 0.2 and frequency 20----
                //MyBounceInterpolator interpolator = new MyBounceInterpolator(0.5, 30);
                //myAnim.setInterpolator(interpolator);
                //startButton.startAnimation(myAnim);
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        stopAudio();
    }


    @Override
    protected void onResume() {
        super.onResume();
        playAudio();
    }

    private void stopAudio(){

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaState = mediaState.STOPPED;
        }

    }





    private void playAudio(){

        if (mediaPlayer == null){
            mediaState = mediaState.NOT_READY;
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alian_welding);
            mediaPlayer.setLooping(true);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    mediaState = mediaState.PLAYING;

                }

            });

        }else if(mediaState == MediaState.PAUSED){
            mediaPlayer.start();
            mediaState = mediaState.PLAYING;
        }else if(mediaState == MediaState.STOPPED){
            mediaPlayer.prepareAsync();
        }
    }





    private void useJarvis(){
        Intent intent = new Intent(getApplicationContext(),
                JarvisMain.class);
        startActivity(intent);

    }





    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit jarvis?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //super.onBackPressed();
                        //Or used finish();
                        finish();
                        stopAudio();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }







}
