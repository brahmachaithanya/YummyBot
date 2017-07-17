package com.example.brahma.yummybot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class CardViewFragment extends Fragment {
    static Query query;
    Fragment mContent;
    public RecyclerView mRecyclerView;
    public LinearLayoutManager mLinearLayoutManager;
    public MyFirebaseRecylerAdapter myFirebaseRecyclerAdapter;
    DatabaseReference childRef = FirebaseDatabase.getInstance().getReference().child("recipes").getRef();


    public CardViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card_view, container, false);
        //Query query = childRef.orderByChild("view_count");
        myFirebaseRecyclerAdapter = new MyFirebaseRecylerAdapter(Recipe.class, R.layout.cardviewlayout,
                MyFirebaseRecylerAdapter.MovieViewHolder.class, query, getApplicationContext());
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recipecards);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(myFirebaseRecyclerAdapter);
        final HandleClickListerner handleClickListerner;
        handleClickListerner = (HandleClickListerner) v.getContext();
        myFirebaseRecyclerAdapter.SetOnItemClick(new MyFirebaseRecylerAdapter.ClickItem() {

            @Override
            public void LongClick(View v, int position) {

            }

            @Override
            public void ShortClick(View v, int position) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("recipes").getRef();
                reference.child(myFirebaseRecyclerAdapter.getRef(position).getKey()).
                        addValueEventListener(new com.google.firebase.database.ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, ?> item = (HashMap<String, String>) dataSnapshot.getValue();
                        handleClickListerner.refresh(DetailViewFragment.newInstance(item));
                        Log.d("", "");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Log.d("", "");
            }

            @Override
            public void OnThreeDotClick(View v, int position) {

            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    public static CardViewFragment newInstance(Query mQuery) {
        query = mQuery;
        CardViewFragment cardViewFragment = new CardViewFragment();
        return cardViewFragment;
    }

    public void ShortClick(View v, int position) {
        try {

            Log.d("", "");
            //DatabaseReference ref = mMovieData.getFireBaseRef();
            //ref.child(id).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                /*@Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap<String,String> movie = (HashMap<String,String>)dataSnapshot.getValue();
                    mRecyclerInterface.refresh(DetailViewFragment.newInstance(movie));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }
                /*getFragmentManager().beginTransaction().
                        replace(R.id.activity_recycler_view, MovieFragment.newInstance(m.getItem(position))).addToBackStack(null).commit();*//*

    }*/

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (menu.findItem(R.id.menu_search_view) == null)
            inflater.inflate(R.menu.serach_menu, menu);
        SearchView search = (SearchView) menu.findItem(R.id.menu_search_view).getActionView();
        if (search != null) {

            search.setQueryHint("Name...");

            search.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    Log.d("", "");
                    return true;
                }
            });

        }


    }
}
