package com.example.brahma.yummybot;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailViewFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener{

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;
    private ImageView mProfileImage;
    private int mMaxScrollSize;
    private static HashMap<String,?> item;
    TabsAdapter myPagerAdapter;
    String videoID;

    private static final String ARG_MOVIE = "recipeDetails";


    public DetailViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v =  inflater.inflate(R.layout.fragment_detail_view, container, false);
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.materialup_tabs);
        ViewPager viewPager  = (ViewPager) v.findViewById(R.id.materialup_viewpager);
        AppBarLayout appbarLayout = (AppBarLayout) v.findViewById(R.id.materialup_appbar);



        //Toolbar toolbar = (Toolbar) v.findViewById(R.id.materialup_toolbar);
        videoID = (String) item.get("video_url");
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";


        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(videoID);

        if(matcher.find()){
            videoID=matcher.group();
        }
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(),YouTubeActivity.class);
                myIntent.putExtra("videoURL",videoID);
                startActivity(myIntent);
                /*Snackbar.make(view, "Check!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });*/

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        myPagerAdapter = new TabsAdapter(getActivity().getSupportFragmentManager(), 3);

        viewPager.setAdapter(myPagerAdapter);
        viewPager.setPageTransformer(true,new RotateDownTransformer());
        tabLayout.setupWithViewPager(viewPager);

        final TextView recipeName = (TextView) v.findViewById(R.id.RecipeName);
        final ImageView recipeImage = (ImageView) v.findViewById(R.id.RecipeImage);
        final TextView recipeUser = (TextView) v.findViewById(R.id.user);
        final TextView recipeDescription = (TextView)v.findViewById(R.id.RecipeDescription);

        recipeName.setText((String)item.get("recipe_name"));
        Picasso.with(getContext()).load((String)item.get("image_URL")).into(recipeImage);
        recipeUser.setText("@"+(String)item.get("user"));
        recipeDescription.setText((String)item.get("description"));

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

    public static Fragment newInstance(HashMap<String, ?> item) {
        Fragment fragment = new DetailViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE,item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;


        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;


        }
    }

    private  class TabsAdapter extends FragmentStatePagerAdapter {
        private int TAB_COUNT;

        TabsAdapter(FragmentManager fm,int size) {
            super(fm);
            TAB_COUNT = size;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int i)
        {
            Fragment frag = null;
            if(i==0)
            {
                 frag = IngredientsFragment.newInstance(item);
            }
            else if(i==1)
            {
                frag = StepsFragment.newInstance(item);
            }

            else{
                frag = RestaurantFragment.newInstance(item);
            }
            return frag;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            if(position==0)
            {
                return "Ingredients";
            }
            if(position==1)
            {
                return "Steps";
            }

            else
            {
                return "Restaurants";
            }

        }
    }
}
