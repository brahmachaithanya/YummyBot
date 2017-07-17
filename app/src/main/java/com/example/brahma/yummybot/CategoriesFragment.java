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
public class CategoriesFragment extends Fragment {
Button appetizer;
    Button lunch;
    Button dinner;
    Button desserts;

    private static final String ARG_SECTION_NUMBER = "sectionNumber";

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories,container,false);
        appetizer = (Button) v.findViewById(R.id.buttonAppetizer);
        lunch = (Button) v.findViewById(R.id.buttonLunch);
        dinner = (Button) v.findViewById(R.id.buttonDinner);
        desserts = (Button) v.findViewById(R.id.buttonDesserts);
        final HandleClickListerner handleClickListerner;
        handleClickListerner = (HandleClickListerner)v.getContext();
        appetizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickListerner.loadFragment(R.id.buttonAppetizer);
            }
        });
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickListerner.loadFragment(R.id.buttonLunch);
            }
        });
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickListerner.loadFragment(R.id.buttonDinner);
            }
        });
        desserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickListerner.loadFragment(R.id.buttonDesserts);
            }
        });

        return v;
    }

    public static CategoriesFragment newInstance() {
        CategoriesFragment categoriesFragment = new CategoriesFragment();
        return categoriesFragment;
    }
}
