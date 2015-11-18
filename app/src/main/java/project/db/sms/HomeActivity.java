package project.db.sms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;

import project.db.sms.adapter.RouteItemAdapter;
import project.db.sms.apiservices.RestClient;
import project.db.sms.apiservices.interfaceservices.RestApiInterface;
import project.db.sms.apiservices.model.RouteWithStation;
import project.db.sms.apiservices.model.Station;
import project.db.sms.apiservices.model.StationRouteShuttleTime;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Path;


public class HomeActivity extends AppCompatActivity {

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
    private MarkerOptions markerOptions;
    private static List<String> stationNameList;
    private  AutoCompleteTextView originInput;
    private  AutoCompleteTextView destInput;
    private static HashMap hashMap;

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
//                showShuttleListView();
            } catch (Exception e) {
                Log.d("Log", "Caught with exception" + e.toString());
            }

            // Input fields autocomplete. Move this somewhere else later
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line, stationNameList);
            originInput = (AutoCompleteTextView) findViewById(R.id.originInput);
            destInput = (AutoCompleteTextView) findViewById(R.id.destinationInput);
            originInput.setAdapter(adapter);
            originInput.setThreshold(1);
            destInput.setAdapter(adapter);
            destInput.setThreshold(1);
        }
        stationRouteShuttle();

        //buttons intents
        Button searchButton = (Button) findViewById(R.id.searchButton);
        Button allServicesButton = (Button) findViewById(R.id.btnAllServices);
        Button allRoutesButton = (Button) findViewById(R.id.btnAllRoutes);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView originText = (AutoCompleteTextView) findViewById(R.id.originInput);
                AutoCompleteTextView destinationText = (AutoCompleteTextView) findViewById(R.id.destinationInput);
                if (originText == null && destinationText == null) {

                } else if (destinationText != null ) {
                    if (originText != null) {
                        // for map
                        plotOrigDestRoute((int) hashMap.get(originText.getText().toString()), (int) hashMap.get(destinationText.getText().toString()));

                        // soruc and destination for listing
                        stationRouteShuttle((int) hashMap.get(originText.getText().toString()), (int) hashMap.get(destinationText.getText().toString()));
                    } else {
//                        plotOrigDestRoute((int) hashMap.get(originText.getText().toString()), (int) hashMap.get(destinationText.getText().toString()));
                        //nearest and destination
//                        stationRouteShuttle( 4, (int) hashMap.get(destinationText.getText().toString()));
                    }
                }

                // if sour and des
//
            }
        });

        allServicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allServicesIntent = new Intent(getApplicationContext(), AllServicesActivity.class);
                startActivity(allServicesIntent);
            }
        });

        allRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allRoutesIntent = new Intent(getApplicationContext(), AllRoutesActivity.class);
                startActivity(allRoutesIntent);
            }
        });

    }

    private boolean initMap(){
        if(gMap == null){
            MapFragment mFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            gMap = mFrag.getMap();
        }
        return (gMap != null);
    }

    public void getRoute(String origin, String Destination) {

    }

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
        locationManager.requestLocationUpdates("gps", 60000, 0, locationListener);
    }

    public void showShuttleListView() {
        Log.d("Logg", "so in shuttle view ");
        if (restApiService != null) {
            Call<Station> listCall = restApiService.getStation(2);
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
            stationNameList = new ArrayList<String>();
            hashMap = new HashMap();

            Call<List<Station>> stationListCall = restApiService.getStations();
            stationListCall.enqueue(new Callback<List<Station>>() {
                @Override
                public void onResponse(Response<List<Station>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        stations = response.body();
                        double distance = 0;
                        distance = stations.get(1).getLat();

                        for (int i = 0; i < stations.size(); i++) {
                            gMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(stations.get(i).getLat(), stations.get(i).getLng()))
                                    .title(stations.get(i).getName())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            //positions.add(i, new LatLng(stations.get(i).getLat(), stations.get(i).getLng()));
                            stationNameList.add(stations.get(i).getLocation());
                            hashMap.put(stations.get(i).getLocation(), stations.get(i).getStationID());
                            int size = stationNameList.size();
                            Log.d("Log Failure", "response station = ??");
                        }
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("Log Failure", "response station = ??");
                }
            });
        }
    }

    public void stationRouteShuttle(int... args) {
        Call<List<StationRouteShuttleTime>> shuttleStationDetailListCall;
        if (args.length == 0) {
            shuttleStationDetailListCall = restApiService.getDetails();
        } else if (args.length == 1){
            shuttleStationDetailListCall = restApiService.getDetail(args[0]);
        } else {
            shuttleStationDetailListCall = restApiService.getDetail(args[0], args[1]);
            Log.d("LOGD: ", "Double stations");
        }

        shuttleStationDetailListCall.enqueue(new Callback<List<StationRouteShuttleTime>>() {
            @Override
            public void onResponse(Response<List<StationRouteShuttleTime>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<StationRouteShuttleTime> details = response.body();
                    showInList(details);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("Log Failure", "response mmmddddstation = ??");
            }
        });
    }

    public void showInList(final List<StationRouteShuttleTime> details) {
        LinearLayout route_list_layout = (LinearLayout) findViewById(R.id.route_list_layout);
        ListView list_item = new ListView(this);
        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                String stationName = ((TextView) view.findViewById(R.id.station_name)).getText().toString();
                //Toast toast = Toast.makeText(getApplicationContext(), stationName, Toast.LENGTH_SHORT);
                //toast.show();
                Log.d("Log Failure ", "response mmmstation = ??");
                Intent intent = new Intent(getApplicationContext(), ShuttleDetailActivity.class);
                intent.putExtra("stationName", stationName);
                intent.putExtra("shuttleRegNo", details.get(position).getShuttleRegNo());
                intent.putExtra("routeName", details.get(position).getRouteName());
                intent.putExtra("routeID", details.get(position).getRouteID());
                intent.putExtra("arrivalTime", details.get(position).getArrivalTime());
                startActivity(intent);
            }
        });

        RouteItemAdapter routeItemAdapter = new RouteItemAdapter(this, (ArrayList) details);
        list_item.setAdapter(routeItemAdapter);
        route_list_layout.addView(list_item);
    }

    public void plotOrigDestRoute(int originId, int destinationID) {
        if (restApiService != null) {
            final List<LatLng> positions = new ArrayList<LatLng>();
            Call<List<RouteWithStation>> stationListCall = restApiService.getRoutesStations(originId, destinationID);
            stationListCall.enqueue(new Callback<List<RouteWithStation>>() {

                @Override
                public void onResponse(Response<List<RouteWithStation>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        gMap.clear();
                        List<RouteWithStation> routesStations = response.body();

                        for (int r = 0; r < routesStations.size(); r++) {
                            for (int i = 0; i < routesStations.get(r).getStations().size(); i++) {
                                gMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(routesStations.get(r).getStations().get(i).getLat(), routesStations.get(r).getStations().get(i).getLng()))
                                        .title(routesStations.get(r).getStations().get(i).getName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(routesStations.get(r).getHueColor())));
                                positions.add(i, new LatLng(routesStations.get(r).getStations().get(i).getLat(), routesStations.get(r).getStations().get(i).getLng()));

                            }
                            gMap.addPolyline(new PolylineOptions().addAll(positions).color(routesStations.get(r).getHueColor()));
                        }
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
                                new LatLng(routesStations.get(0).getStations().get(0).getLat()
                                        , routesStations.get(0).getStations().get(0).getLng()), 10);
                        gMap.moveCamera(update);
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
