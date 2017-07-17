package com.example.brahma.yummybot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment {
    private HashMap<String,?> item;
    private static final String ARG_MOVIE = "recipeDetails";

    public StepsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_steps, container, false);
        final TextView steps = (TextView) v.findViewById(R.id.detailSteps);
        steps.setText((String)item.get("steps"));


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

    public static StepsFragment newInstance(HashMap<String, ?> item) {
        StepsFragment stepsFragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE,item);
        stepsFragment.setArguments(args);
        return stepsFragment;
    }
}
