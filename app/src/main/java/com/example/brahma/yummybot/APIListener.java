package com.example.brahma.yummybot;

import com.google.android.gms.maps.GoogleMap;

public interface
APIListener {
    void onMapReady(GoogleMap googleMap);

    void onFailure();

    void onStartlisten();

    void onSuccess(Object obj);
}
