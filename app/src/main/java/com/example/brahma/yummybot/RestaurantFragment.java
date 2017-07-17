package com.example.brahma.yummybot;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment {
    ImageButton mButton;
    private HashMap<String,?> item;
    private static final String ARG_MOVIE = "recipeDetails";
    String cuisine;
    String cusine_id;
    public RestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_restaurant, container, false);
        mButton = (ImageButton) v.findViewById(R.id.buttonRestuarant);
        cuisine = (String) item.get("cuisine");
        if (cuisine.equals("Indian"))
        {
             cusine_id="148";
        }
        if(cuisine.equals("Thai"))
        {
            cusine_id="95";
        }
        if(cuisine.equals("Italian"))
        {
            cusine_id="55";
        }
        if(cuisine.equals("American"))
        {
            cusine_id="1";
        }


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(),MapActivity.class);
                myIntent.putExtra("restaurant",cusine_id);
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

    public static RestaurantFragment newInstance(HashMap<String, ?> item) {

        RestaurantFragment restaurantFragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE,item);
        restaurantFragment.setArguments(args);
        return restaurantFragment;
    }
}
