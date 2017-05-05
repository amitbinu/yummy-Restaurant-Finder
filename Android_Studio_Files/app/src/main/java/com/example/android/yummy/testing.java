package com.example.android.yummy;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlacesSearchResponse;

/**
 * Created by amitb on 2017-05-04.
 */

public class testing {
    public testing() throws Exception{
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA");
        PlacesSearchResponse address = PlacesApi.textSearchQuery(context, "Pizza" + " restaurants in " + "Mississauga").await();
    }
}
