package project.db.sms;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import project.db.sms.apiservices.RestClient;
import project.db.sms.apiservices.interfaceservices.RestApiInterface;
import project.db.sms.apiservices.model.RouteWithStation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AllRoutesActivity extends AppCompatActivity {

    private GoogleMap mMap;
    public RestApiInterface restApiService;
    public RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_routes);

        restClient = new RestClient();
        restClient.setRestApiService();
        restApiService = restClient.getRestApiService();

        MapFragment mFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.allRoutesMap);
        mFrag.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                plotAllRoutes();
            }
        });
    }


    public void plotAllRoutes() {
        if (restApiService != null) {
            final List<LatLng> positions = new ArrayList<LatLng>();
            Call<List<RouteWithStation>> stationListCall = restApiService.getRoutesStations();
            stationListCall.enqueue(new Callback<List<RouteWithStation>>() {
                @Override
                public void onResponse(Response<List<RouteWithStation>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        List<RouteWithStation> routesStations = response.body();

                        for (int r = 0; r < routesStations.size(); r++) {
                            positions.clear();
                            for (int i = 0; i < routesStations.get(r).getStations().size(); i++) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(routesStations.get(r).getStations().get(i).getLat(), routesStations.get(r).getStations().get(i).getLng()))
                                        .title(routesStations.get(r).getStations().get(i).getName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(routesStations.get(r).getHueColor())));
                                positions.add(i, new LatLng(routesStations.get(r).getStations().get(i).getLat(), routesStations.get(r).getStations().get(i).getLng()));

                            }
                            Random rnd = new Random();
                            mMap.addPolyline(new PolylineOptions().addAll(positions).color(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))));
                        }
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
                                new LatLng(routesStations.get(0).getStations().get(0).getLat()
                                        , routesStations.get(0).getStations().get(0).getLng()), 15);
                        mMap.moveCamera(update);
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("Log Failure", "response sssstation = ??");
                }
            });

        }
    }
}
