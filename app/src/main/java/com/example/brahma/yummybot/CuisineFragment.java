package com.example.brahma.yummybot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class CuisineFragment extends Fragment {

    Button indian;
    Button thai;
    Button mexican;
    Button italian;
    private static final String ARGS_SECTION_NUMBER = "sectionNumber";

    public CuisineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cuisine,container,false);
        indian = (Button) v.findViewById(R.id.buttonIndian);
        thai = (Button) v.findViewById(R.id.buttonThai);
        mexican = (Button) v.findViewById(R.id.buttonMexican);
        italian = (Button) v.findViewById(R.id.buttonItalian);
        final HandleClickListerner handleClickListerner;
        handleClickListerner = (HandleClickListerner)v.getContext();
        indian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickListerner.loadFragment(R.id.buttonIndian);
            }
        });
        thai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickListerner.loadFragment(R.id.buttonThai);
            }
        });
        mexican.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickListerner.loadFragment(R.id.buttonMexican);
            }
        });
        italian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickListerner.loadFragment(R.id.buttonItalian);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    public static CuisineFragment newInstance() {
        CuisineFragment cuisineFragment = new CuisineFragment();
        return cuisineFragment;
    }
}
