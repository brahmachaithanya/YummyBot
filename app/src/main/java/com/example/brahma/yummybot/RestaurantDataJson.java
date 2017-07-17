package com.example.brahma.yummybot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantDataJson {
    List<Map<String, ?>> restaurantList;
    private List<Map<String, ?>> myDataset;

    public List<Map<String, ?>> getrestaurantList() {
        return this.restaurantList;
    }

    public int getSize() {
        return this.restaurantList.size();
    }

    public HashMap getItem(int i) {
        if (i < 0 || i >= this.restaurantList.size()) {
            return null;
        }
        return (HashMap) this.restaurantList.get(i);
    }

    public RestaurantDataJson() {
        this.restaurantList = new ArrayList();
    }

    public HashMap createRestaurant(String name, String streetAddress, String city, String zipcode,String latitude ,String longitude )
    {
        HashMap restaurant = new HashMap();

        restaurant.put("name", name);
        restaurant.put("address", streetAddress);
        restaurant.put("city", city);
        restaurant.put("zipcode", zipcode);
        restaurant.put("latitude", latitude);
        restaurant.put("longitude", longitude);
        return restaurant;
    }

    public void removeItem(int i) {
        this.restaurantList.remove(i);
    }

    public int find(String query) {
        for (int i = 0; i < this.restaurantList.size(); i++) {
            if (((Map) this.restaurantList.get(i)).get("name").toString().toLowerCase().contains(query.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }
}
