package com.example.brahma.yummybot;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity {
    YouTubePlayerView mYouTubePlayerView;
    Button mButton;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    String newString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mButton = (Button) findViewById(R.id.playButton);
        Bundle extras = this.getIntent().getExtras();
        if(extras==null){
            newString =null;
        }
        else{
            newString = extras.getString("videoURL");
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYouTubePlayerView.initialize(PlayerConfig.API_KEY,mOnInitializedListener);
            }
        });
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.YoutubePlayer);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(newString);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {

            }
        };


        //setSupportActionBar(toolbar);


    }



}
