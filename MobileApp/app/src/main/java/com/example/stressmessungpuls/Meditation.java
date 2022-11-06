package com.example.stressmessungpuls;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Meditation extends AppCompatActivity {

    View view;
    Button play;
    Button play2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.activity_meditation);

        //Add back button - https://www.youtube.com/watch?v=s3pheMAmaPI
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Entspannung");


        // Media Player
        //Button1
        play = (Button)findViewById(R.id.button_med_play1);
        final MediaPlayer mP = MediaPlayer.create(Meditation.this, R.raw.bird);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mP.isPlaying()){
                    mP.pause();
                    play.setBackgroundResource(R.mipmap.play);

                }else{
                    mP.start();
                    play.setBackgroundResource(R.mipmap.pause);
                }
            }
        });

        //BUTTON2
        play2 = (Button)findViewById(R.id.button_med_play2);
        final MediaPlayer mP2 = MediaPlayer.create(Meditation.this, R.raw.stream);
        play2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mP2.isPlaying()){
                    mP2.pause();
                    play2.setBackgroundResource(R.mipmap.play);

                }else{
                    mP2.start();
                    play2.setBackgroundResource(R.mipmap.pause);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();

        hideNavigationBar();
    }


    private void hideNavigationBar(){
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        //View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }


}
