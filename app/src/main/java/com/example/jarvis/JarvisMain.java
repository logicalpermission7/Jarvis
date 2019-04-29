package com.example.jarvis;


import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.EditText;
import android.widget.Button;
import android.util.Log;
import java.util.ArrayList;
import java.util.Locale;
import com.hanks.htextview.base.HTextView;
import android.os.Handler;




public class JarvisMain extends AppCompatActivity {
    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button speakButton;
    private Button clearButton;
    private HTextView textViewType;
    int delay = 5000; //milliseconds
    Handler handler;
    ArrayList<String> Messages1 = new ArrayList<>();
    int position=0;






    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jarvis_main);


        textViewType= findViewById(R.id.textViewTyper2);
        speakButton = findViewById(R.id.button_speak);
        clearButton = findViewById(R.id.clearButton);
        mEditText = findViewById(R.id.edit_text);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        Messages1.add("TEXT, THEN SEND");
        textViewType.animateText(Messages1.get(position));



        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language not supported");
                    }else{
                        speakButton.setEnabled(true);
                    }
                }else{
                    Log.e("TTS", "Initialization failed");
                }
            }
        });









        /* Change Messages every 5 Seconds */
        handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){

                handler.postDelayed(this, delay);
                if(position>=Messages1.size())
                    position=0;
                textViewType.animateText(Messages1.get(position));
            }
        }, delay);






        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                speak();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });



    }





    private void speak(){
        String text = mEditText.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;

        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    private void clear(){
        mEditText.setText("");
    }


// to stop the speech engine when the app stops

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }

    }





    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to go back to menu")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //super.onBackPressed();
                        //Or used finish();
                        finish();

                    }

                })
                .setNegativeButton("No", null)
                .show();

    }






}
