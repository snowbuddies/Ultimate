package com.buds.snow.ultimate;

import android.support.annotation.NonNull;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.List;

import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;


import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;




//navigation:
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity{


    private Button button;
    private DirectionsRoute currentRoute;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private TextInputEditText st;
    private TextInputEditText ed;
    private String sLoc;
    private String eLoc;
    private Point start;
    private Point end;
    private static final String TAG = "Snow Buddies";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiYWN2aW9sYSIsImEiOiJjanNoODJrY2owZ3B2NDRxemFuZWEzc2hrIn0.FI4zY9I5DT6fdSOkg0knqg");
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

// Map is set up and the style has loaded. Now you can add data or make other map adjustments


                    }
                });
            }
        });
        InitializeApp();




    }



    //NAVIGATION: NAVIGATION UI IMPLEMENTATION IS INCOMPATIBLE WITH CURRENT ANDROID APPCOMPAT
//
//
//    @Override
//    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
//        this.mapboxMap = mapboxMap;
//        mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
//            @Override
//            public void onStyleLoaded(@NonNull Style style) {
//                t = findViewById(R.id.b);
//                t.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        boolean simulateRoute = true;
//
//                        //Special stuff
//                        sLoc = "5432 Longwood Ct. SE Ada, MI 49301";
//                        eLoc = "1703 Townsend Drive Houghton, MI 49931";
//
//
//                        MapboxGeocoding s = MapboxGeocoding.builder()
//                                .accessToken("pk.eyJ1IjoiYWN2aW9sYSIsImEiOiJjanNoODJrY2owZ3B2NDRxemFuZWEzc2hrIn0.FI4zY9I5DT6fdSOkg0knqg")
//                                .query(sLoc)
//                                .build();
//
//                        MapboxGeocoding e = MapboxGeocoding.builder()
//                                .accessToken("pk.eyJ1IjoiYWN2aW9sYSIsImEiOiJjanNoODJrY2owZ3B2NDRxemFuZWEzc2hrIn0.FI4zY9I5DT6fdSOkg0knqg")
//                                .query(eLoc)
//                                .build();
//
//
//
//                        s.enqueueCall(new Callback<GeocodingResponse>() {
//                            @Override
//                            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
//                                List<CarmenFeature> results = response.body().features();
//
//
//                                if(results.size() > 0){
//                                    Point firstResP = results.get(0).center();
//                                    start = firstResP;
//                                    Log.d("response", "onResponse: " + firstResP.toString());
//                                } else {
//                                    Log.d("response", "onResponse: No resp.");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<GeocodingResponse> call, Throwable t) {
//                                t.printStackTrace();
//                            }
//                        });
//
//
//
//                        e.enqueueCall(new Callback<GeocodingResponse>() {
//                            @Override
//                            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
//                                List<CarmenFeature> results = response.body().features();
//
//
//                                if(results.size() > 0){
//                                    Point firstResP = results.get(0).center();
//                                    end = firstResP;
//                                    Log.d("response", "onResponse: " + firstResP.toString());
//                                } else {
//                                    Log.d("response", "onResponse: No resp.");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<GeocodingResponse> call, Throwable t) {
//                                t.printStackTrace();
//                            }
//                        });
//
//
//
//
//
//
//
//                        getRoute(start,end);
//
//
//                        //End special stuff
//
//
//
//                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
//                                .directionsRoute(currentRoute)
//                                .shouldSimulateRoute(simulateRoute)
//                                .build();
//// Call this method with Context from within an Activity
//                        NavigationLauncher.startNavigation(MainActivity.this, options);
//                    }
//                });
//
//            }
//        });
//    }
//



    private void InitializeApp(){


        button = (Button) findViewById(R.id.b);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    sLoc = st.getText().toString();
                    eLoc = ed.getText().toString();
                }catch(NullPointerException e){
                    return;
                }

                MapboxGeocoding s = MapboxGeocoding.builder()
                        .accessToken("pk.eyJ1IjoiYWN2aW9sYSIsImEiOiJjanNoODJrY2owZ3B2NDRxemFuZWEzc2hrIn0.FI4zY9I5DT6fdSOkg0knqg")
                        .query(sLoc)
                        .build();

                MapboxGeocoding e = MapboxGeocoding.builder()
                        .accessToken("pk.eyJ1IjoiYWN2aW9sYSIsImEiOiJjanNoODJrY2owZ3B2NDRxemFuZWEzc2hrIn0.FI4zY9I5DT6fdSOkg0knqg")
                        .query(eLoc)
                        .build();



                s.enqueueCall(new Callback<GeocodingResponse>() {
                    @Override
                    public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                        List<CarmenFeature> results = response.body().features();


                        if(results.size() > 0){
                            Point firstResP = results.get(0).center();
                            start = firstResP;
                            Log.d("response", "onResponse: " + firstResP.toString());
                        } else {
                            Log.d("response", "onResponse: No resp.");
                        }
                    }

                    @Override
                    public void onFailure(Call<GeocodingResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });



                e.enqueueCall(new Callback<GeocodingResponse>() {
                    @Override
                    public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                        List<CarmenFeature> results = response.body().features();


                        if(results.size() > 0){
                            Point firstResP = results.get(0).center();
                            end = firstResP;
                            Log.d("response", "onResponse: " + firstResP.toString());
                        } else {
                            Log.d("response", "onResponse: No resp.");
                        }
                    }

                    @Override
                    public void onFailure(Call<GeocodingResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

                System.out.println("START: " + start.latitude() + ", " + start.longitude());
                System.out.println("END: " + end.latitude() + ", " + end.longitude());
                //getRoute(start,end);


            }



        });



    }




//
//    private void getRoute(Point origin, Point destination) {
//        NavigationRoute.builder(this)
//                .accessToken(Mapbox.getAccessToken())
//                .origin(origin)
//                .destination(destination)
//                .build()
//                .getRoute(new Callback<DirectionsResponse>() {
//                    @Override
//                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
//// You can get the generic HTTP info about the response
//                        Log.d(TAG, "Response code: " + response.code());
//                        if (response.body() == null) {
//                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
//                            return;
//                        } else if (response.body().routes().size() < 1) {
//                            Log.e(TAG, "No routes found");
//                            return;
//                        }
//
//                        currentRoute = response.body().routes().get(0);
//
//// Draw the route on the map
//                        if (navigationMapRoute != null) {
//                            navigationMapRoute.removeRoute();
//                        } else {
//                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
//                        }
//                        navigationMapRoute.addRoute(currentRoute);
//                    }
//
//                    @Override
//                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
//                        Log.e(TAG, "Error: " + throwable.getMessage());
//                    }
//                });
//    }
//




    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
