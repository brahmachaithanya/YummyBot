package com.example.brahma.yummybot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import static com.example.brahma.yummybot.CardViewFragment.query;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HandleClickListerner {
    public RecyclerView mRecyclerView;
    public LinearLayoutManager mLinearLayoutManager;
    public FirebaseRecyclerAdapter myFirebaseRecyclerAdapter;
    DatabaseReference childRef = FirebaseDatabase.getInstance().getReference().child("recipes").getRef();
    TextView userName;
    ImageView mImageView;
    TextView emailAddress;
    static String name;
    private Uri photoUrl;
    private String mail;
    Button categories;
    Button cuisine;
    Button createRecipe;
    Fragment mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(savedInstanceState!=null)
        {
            mContent = getSupportFragmentManager().getFragment(savedInstanceState,"mcontent");
        }
        else{
            mContent=MainPageFragment.newInstance(R.id.mainPageContainer);
        }
                getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContainer,mContent)
                .commit();


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        userName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        mImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);

        getCurrentinfo();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            getSupportFragmentManager().putFragment(outState,"mcontent",mContent);
        }
        catch (Exception ae){
            mContent = null;
        }

    }

    private void getCurrentinfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
                photoUrl = profile.getPhotoUrl();
                mail = profile.getProviderId();

                userName.setText(name);
                //emailAddress.setText(mail);

                if(photoUrl!=null) {
                    Picasso.with(getApplicationContext())
                            .load(photoUrl.toString())
                            .placeholder(R.drawable.blankdp)
                            .resize(100, 100)
                            .centerCrop()
                            .into(mImageView);
                }
                else {
                    Picasso.with(getApplicationContext())
                            .load(R.drawable.blankdp)
                            .resize(100, 100)
                            .centerCrop()
                            .into(mImageView);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; th
        // is adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.homePage) {
            Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(myIntent);
            // Handle the camera action


        } else if (id == R.id.nav_manage) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            query = childRef.orderByChild("user").equalTo(name);
            mContent = CardViewFragment.newInstance(query);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer,mContent)
                    .addToBackStack("store")
                    .commit();

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void loadFragment(int Button) {
        int id = Button;
        Query query;
        switch (id){
            case R.id.ButtonCuisine:
                mContent = CuisineFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
                        .replace(R.id.mainContainer, mContent)
                        .addToBackStack("store")
                        .commit();
                break;
            case R.id.ButtonCatg:
                mContent = CategoriesFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
                        .replace(R.id.mainContainer,mContent)
                        .addToBackStack("store")
                        .commit();
                break;
            case R.id.ButtonViewAll:
                 query = childRef.orderByChild("recipe_name");
                mContent = CardViewFragment.newInstance(query);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
                        .replace(R.id.mainContainer,mContent)
                        .addToBackStack("store")
                        .commit();
                break;
            case R.id.buttonIndian:
                query = childRef.orderByChild("cuisine").equalTo("Indian");
                mContent = CardViewFragment.newInstance(query);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
                        .replace(R.id.mainContainer,mContent)
                        .addToBackStack("store")
                        .commit();
                break;
            case R.id.buttonThai:
                query = childRef.orderByChild("cuisine").equalTo("Thai");
                mContent = CardViewFragment.newInstance(query);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
                        .replace(R.id.mainContainer,mContent)
                        .addToBackStack("store")
                        .commit();
                break;
            case R.id.buttonMexican:
                query = childRef.orderByChild("cuisine").equalTo("American");
                mContent = CardViewFragment.newInstance(query);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
                        .replace(R.id.mainContainer,mContent)
                        .addToBackStack("store")
                        .commit();
                break;
            case R.id.buttonItalian:
                query = childRef.orderByChild("cuisine").equalTo("Italian");
                mContent = CardViewFragment.newInstance(query);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
                        .replace(R.id.mainContainer,mContent)
                        .addToBackStack("store")
                        .commit();
                break;
            case R.id.buttonAppetizer:
                query = childRef.orderByChild("sub_category").equalTo("Appetizer");
                mContent = CardViewFragment.newInstance(query);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
                        .replace(R.id.mainContainer,mContent)
                        .addToBackStack("store")
                        .commit();
                break;
            case R.id.buttonLunch:
                query = childRef.orderByChild("sub_category").equalTo("Lunch");
                mContent = CardViewFragment.newInstance(query);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
                        .replace(R.id.mainContainer,mContent)
                        .addToBackStack("store")
                        .commit();
                break;
            case R.id.buttonDinner:
                query = childRef.orderByChild("sub_category").equalTo("Dinner");
                mContent = CardViewFragment.newInstance(query);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
                        .replace(R.id.mainContainer,mContent)
                        .addToBackStack("store")
                        .commit();
                break;
            case R.id.buttonDesserts:
                query = childRef.orderByChild("sub_category").equalTo("Dessert");
                mContent = CardViewFragment.newInstance(query);
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_right)
                        .replace(R.id.mainContainer,mContent)
                        .addToBackStack("store")
                        .commit();
                break;

        }



    }

    @Override
    public void refresh(Fragment detailViewFragment) {
        mContent = detailViewFragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, mContent)
                .addToBackStack("store")
                .commit();
    }
}
