package com.example.brahma.yummybot;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "sectionNumber" ;
    public RecyclerView mRecyclerView;
    public LinearLayoutManager mLinearLayoutManager;
    public MyFirebaseRecylerAdapter myFirebaseRecyclerAdapter;
    DatabaseReference childRef = FirebaseDatabase.getInstance().getReference().child("recipes").getRef();
    TextView userName;
    ImageView mImageView;
    TextView emailAddress;
    private String name;
    private Uri photoUrl;
    private String mail;
    Button categories;
    Button cuisine;
    Button createRecipe;
    Button viewAll;

    public MainPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_page,container,false);
        Query query = childRef.orderByChild("view_count");
        myFirebaseRecyclerAdapter = new MyFirebaseRecylerAdapter(Recipe.class,
                R.layout.cardviewlayout,MyFirebaseRecylerAdapter.MovieViewHolder.class,query,getApplicationContext());
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recipecards);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(myFirebaseRecyclerAdapter);
        mRecyclerView.setHasFixedSize(true);
        categories = (Button) v.findViewById(R.id.ButtonCatg);
        cuisine = (Button) v.findViewById(R.id.ButtonCuisine);
        createRecipe = (Button) v.findViewById(R.id.ButtonCreate);
        viewAll = (Button) v.findViewById(R.id.ButtonViewAll);
        final HandleClickListerner handleClickListerner;
        handleClickListerner = (HandleClickListerner)v.getContext();
        myFirebaseRecyclerAdapter.SetOnItemClick(new MyFirebaseRecylerAdapter.ClickItem(){

            @Override
            public void LongClick(View v, int position) {

            }

            @Override
            public void ShortClick(View v, int position) {
               /* DatabaseReference ref = myFirebaseRecyclerAdapter.getRef(position);
                String fireBaseKey = ref.getKey();*/
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("recipes").getRef();
                reference.child(myFirebaseRecyclerAdapter.getRef(position).getKey()).addValueEventListener(new com.google.firebase.database.ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String,?> item = (HashMap<String,String>) dataSnapshot.getValue();
                        handleClickListerner.refresh(DetailViewFragment.newInstance(item));

                        Log.d("","");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Log.d("","");
            }

            @Override
            public void OnThreeDotClick(View v, int position) {

            }
        });
        cuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickListerner.loadFragment(R.id.ButtonCuisine);
            }
        });
        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickListerner.loadFragment(R.id.ButtonCatg);
            }
        });
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickListerner.loadFragment(R.id.ButtonViewAll);
            }
        });
        createRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),CreateRecipe.class);
                startActivity(myIntent);
            }
        });



        return v;
    }


    public static MainPageFragment newInstance(int mainPageContainer) {
        MainPageFragment mainPageFragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,mainPageContainer);
        mainPageFragment.setArguments(args);
        return mainPageFragment;
    }
}
