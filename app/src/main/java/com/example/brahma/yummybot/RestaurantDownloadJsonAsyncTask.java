package com.example.brahma.yummybot;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantDownloadJsonAsyncTask extends AsyncTask<String, Void, RestaurantDataJson> {
    private APIListener listener;

    public RestaurantDownloadJsonAsyncTask(APIListener listener) {
        this.listener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.listener.onStartlisten();
    }

    protected RestaurantDataJson doInBackground(String... urls)
    {
        RestaurantDataJson threadMovieData = new RestaurantDataJson();
        for (String url : urls) {
            String server = MyUtility.downloadJSONusingHTTPGetRequest(url);
            if (server != null) {
                try {
                    //JSONArray addmovie = new JSONArray(server);
                    JSONObject responseCuisines = new JSONObject(server);
                    JSONArray addmovie = responseCuisines.getJSONArray("restaurants");


                    if (addmovie == null)
                    {
                        Log.d("Test", "res");
                    }
                    for (int i = 0; i < addmovie.length(); i++) {
                        JSONObject newpart = addmovie.getJSONObject(i).getJSONObject("restaurant");
                        //JSONObject newpart_loc = addmovie.getJSONObject(i).getJSONObject("location");
                        String addname = newpart.getString("name");
                        String addstreetAddress = newpart.getJSONObject("location").getString("address");
                        String addcity = newpart.getJSONObject("location").getString("city");
                        String addzip = newpart.getJSONObject("location").getString("zipcode");
                        String addlat = newpart.getJSONObject("location").getString("latitude");
                        String addlong = newpart.getJSONObject("location").getString("longitude");

                        threadMovieData.restaurantList.add(i, threadMovieData.createRestaurant(addname,addstreetAddress,addcity,addzip,addlat,addlong));
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    this.listener.onFailure();
                }
            }
        }
        return threadMovieData;
    }

    protected void onPostExecute(RestaurantDataJson threadMovieData)
    {
        this.listener.onSuccess(threadMovieData);
    }
}
