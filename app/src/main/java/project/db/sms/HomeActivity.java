package project.db.sms;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

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
    private Location currLocation;
    private Marker currMarker;
    public RestClient restClient;
    public Station station;
    public RestApiInterface restApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        restClient = new RestClient();
        restClient.setRestApiService();
        restApiService = restClient.getRestApiService();

        isMapReady = initMap();
        if (isMapReady){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(34.718166, -86.661352), 8);
            gMap.addMarker(new MarkerOptions().position(new LatLng(30.718166, -86.661352)).title("Default"));
            gMap.moveCamera(update);

            try {
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
                        Log.d("MainActivity", "response = " + new Gson().toJson(station));
                        gMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(station.getLng()), Double.parseDouble(station.getLat()))));
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
}
