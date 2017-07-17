package com.example.brahma.yummybot;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,APIListener
{
    private GoogleMap mgoogleMap;
    RestaurantDataJson restaurantDataJson;
    String newString = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = this.getIntent().getExtras();
        if(extras==null){
            newString =null;
        }
        else{
            newString = extras.getString("restaurant");
        }
        // Retrieve the content view that renders the map.
        setContentView(R.layout.maplayout);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //new RestaurantDownloadJsonAsyncTask(this).execute( new String[]{"https://api.eatstreet.com/publicapi/v1/restaurant/search?method=both&search=samosa&street-address=316+W.+Washington+Ave.+Madison,+WI"});
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mgoogleMap=googleMap;
        String apiurl="https://developers.zomato.com/api/v2.1/search?entity_type=city&lat=43.0481&lon=-76.1474&cuisines=";
        new RestaurantDownloadJsonAsyncTask(this).execute( new String[]{apiurl+newString});
    }
    @Override
    public void onFailure() {
    }
    @Override
    public void onStartlisten() {
    }
    @Override
    public void onSuccess(Object obj)
    {
        List<Marker> markers = new ArrayList<Marker>();
        LatLng newloc=null;
        RestaurantDataJson RecipeData = (RestaurantDataJson) obj;
        mgoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        for (int i = 0; i < RecipeData.getSize(); i++)
        {
            newloc = new LatLng(Double.parseDouble(RecipeData.getItem(i).
                    get("latitude").toString()), Double.parseDouble(RecipeData.getItem(i).get("longitude").toString()));

            Marker marker = mgoogleMap.addMarker(new MarkerOptions().position(newloc)
                    .title(RecipeData.getItem(i).get("name").toString()).visible(true));
            markers.add(marker);
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers)
        {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mgoogleMap.animateCamera(cu);
    }
}