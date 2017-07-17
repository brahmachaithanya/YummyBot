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
public class IngredientsFragment extends Fragment {

    private HashMap<String,?> item;
    private static final String ARG_MOVIE = "recipeDetails";
    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ingredients, container, false);
        final TextView ingredients = (TextView) v.findViewById(R.id.detailIngredients);
        ingredients.setText((String)item.get("ingredients"));

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

    public static IngredientsFragment newInstance(HashMap<String, ?> item) {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE,item);
        ingredientsFragment.setArguments(args);
        return ingredientsFragment;
    }
}
