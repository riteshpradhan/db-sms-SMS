package project.db.sms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import project.db.sms.apiservices.RestClient;
import project.db.sms.apiservices.interfaceservices.RestApiInterface;
import project.db.sms.apiservices.model.Station;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class HomeActivity extends AppCompatActivity implements InputFragment.InputSectionListener{

    private boolean isMapReady;
    private GoogleMap gMap;
    private Marker currMarker;
    public RestClient restClient;
    public Station station;
    public List<Station> stations;
    public RestApiInterface restApiService;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng currLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        restClient = new RestClient();
        restClient.setRestApiService();
        restApiService = restClient.getRestApiService();

        isMapReady = initMap();
        if (isMapReady){
            // Camera position needs to be initialized here

            //CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(34.718166, -86.661352), 8);
            //gMap.addMarker(new MarkerOptions().position(new LatLng(30.718166, -86.661352)).title("Default"));
            //gMap.moveCamera(update);

            try {
                showCurrentLocation();
                showStations();
                showShuttleListView();
            } catch (Exception e) {
                Log.d("Log", "Caught with exception" + e.toString());
            }
        }
    }

    private boolean initMap(){
        if(gMap == null){
            MapFragment mFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            gMap = mFrag.getMap();
        }
        return (gMap != null);
    }

    @Override
    public void getRoute(String origin, String Destination) {

    }

    @Override
    public void resetFields() {

    }

    private  void showCurrentLocation(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                currMarker = gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));

                //currMarker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
                //gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15);
                gMap.moveCamera(update);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
        } else {
            getCurrentPosition();
        }


    }

    private void getCurrentPosition() {
        locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
    }

    public void showShuttleListView() {
        Log.d("Logg", "so in shuttle view ");
        if (restApiService != null) {
            Call<Station> listCall = restApiService.getStation();
            listCall.enqueue(new Callback<Station>() {
                @Override
                public void onResponse(Response<Station> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Log.v("Logg", response.body().toString());
                        station = response.body();
                        Log.d("LMainActivity", "response = " + new Gson().toJson(station));
                        gMap.addMarker(new MarkerOptions().position(new LatLng(station.getLng(), station.getLat())));
                        Log.d("Log: ", "Add marker for lat and long");
                    }
                }
                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("Log Failure", "response station = ??");
                }
            });
        } else {
            Log.d("Log", "Null ssv obtained ");
        }

    }

    public void requestRestClient() {
        if (restClient == null) {
            Log.d("LGOO: ", "null obtained here ");
        } else {
            if (restApiService == null) {
                Log.d("LGOO: ", "why api is null or not ? ?  ");
            }
            restClient.setRestApiService();
            if (restClient.getRestApiService() == null) {
                Log.d("LGOO: ", "why api is null or not after set ? ?  ");
            }
            restClient.requestData();
        }
    }

    // Commented lines define steps to plot lines between stations
    // This procedure will be applied when plotting routes in a separate method
    public void showStations() {
        if (restApiService != null) {
            //final List<LatLng> positions = new ArrayList<LatLng>();
            Call<List<Station>> stationListCall = restApiService.getStations();
            stationListCall.enqueue(new Callback<List<Station>>() {
                @Override
                public void onResponse(Response<List<Station>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        stations = response.body();
                        double distance = 0;
                        distance = stations.get(1).getLat();

                        for (int i = 0; i < stations.size(); i++){
                            gMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(stations.get(i).getLat(), stations.get(i).getLng()))
                                    .title(stations.get(i).getName())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            //positions.add(i, new LatLng(stations.get(i).getLat(), stations.get(i).getLng()));

                        }
                        //gMap.addPolyline(new PolylineOptions().addAll(positions));
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("Log Failure", "response station = ??");
                }
            });

        }
    }
}
