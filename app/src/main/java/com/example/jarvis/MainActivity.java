package com.example.jarvis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.hanks.htextview.base.HTextView;
import java.util.ArrayList;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {







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

        findViewById(R.id.about_button).setOnClickListener(new AboutListener());
        textViewType= findViewById(R.id.textViewTyper);
        textViewRainBow= findViewById(R.id.textViewRainBow);

        Messages1.add("Monitoring, Analysis and Response System");
        Messages2.add("M.A.R.S.");
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
                useJarvis();

            }
        });



        final Button databaseButton = findViewById(R.id.DB_button);
        databaseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                dataBase();


            }
        });



    }

    class AboutListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String message = "<html>" +
                    "<h2>WELCOME TO M.A.R.S.</h2>" +
                    "<p>This application was originally designed as part of a final project for school. It was intended to be for the speech impaired, but has potential for something much more. Please leave us a note below at the link provided and give us some ideas.</p>" +
                    "<p><b>Lead Developer/Designer:</b> Elvis Bueno<br>" +
                    "<b>Programmer 1:</b> Michael Pender<br>" +
                    "<b>Link: </b> <a href='https://www.spacex.com/'>give us some ideas</a><br>" +
                    "<b>School: </b>Austin Peay State University" +
                    "</p></html>";
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("Ok", null);

            AlertDialog dialog = builder.create();
            dialog.show();

            // must be done after the call to show();
            // allows anchor tags to work
            TextView tv = (TextView) dialog.findViewById(android.R.id.message);
            tv.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }



    private void useJarvis(){
        Intent intent = new Intent(getApplicationContext(),
                JarvisMain.class);
        startActivity(intent);

    }

    private void dataBase(){
        Intent intent = new Intent(getApplicationContext(),
                DatabaseMain.class);
        startActivity(intent);

    }





    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit M.A.R.S.?")
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
