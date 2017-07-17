package com.example.brahma.yummybot;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.youtube.player.YouTubePlayer;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    private static final String API_KEY = "AIzaSyAzXKYgm04NyIfantHURq7_5X7nhgDn_Iw";
    private static String VIDEO_ID = "EGy39OMyHzw";
    Button mButton;
    String videoID;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private HashMap<String,?> item;
    private static final String ARG_MOVIE = "recipeDetails";

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        videoID = (String) item.get("video_url");
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(videoID);

        if(matcher.find()){
            videoID=matcher.group();
        }
        //videoID.substring(videoID.lastIndexOf("=")+1);
        mButton = (Button) v.findViewById(R.id.viewVideo);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(),YouTubeActivity.class);
                myIntent.putExtra("videoURL",videoID);
                startActivity(myIntent);
            }
        });



        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //movieUrl = getArguments().getString(ARG_MOVIE);
            item = (HashMap<String, ?>) getArguments().getSerializable(ARG_MOVIE);
        }


    }

    public static VideoFragment newInstance(HashMap<String, ?> item) {
        VideoFragment videoFragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE,item);
        videoFragment.setArguments(args);
        return videoFragment;
    }
}
