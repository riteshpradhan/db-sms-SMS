package project.db.sms;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import project.db.sms.apiservices.RestClient;
import project.db.sms.apiservices.interfaceservices.RestApiInterface;
import project.db.sms.apiservices.model.RouteWithStation;
import project.db.sms.apiservices.model.Station;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ShuttleDetailActivity extends Activity {

    private GoogleMap mMap;
    private String stationName;
    private String shuttleRegNo;
    private String arrivalTime;
    private String routeName;
    private int routeID;
    public RestApiInterface restApiService;
    public RestClient restClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle_detail);

        restClient = new RestClient();
        restClient.setRestApiService();
        restApiService = restClient.getRestApiService();

        MapFragment mFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.shuttleDetailMap);
        mMap = mFrag.getMap();

        stationName = getIntent().getStringExtra("stationName");
        shuttleRegNo = getIntent().getStringExtra("shuttleRegNo");
        arrivalTime = getIntent().getStringExtra("arrivalTime");
        routeName = getIntent().getStringExtra("routeName");
        routeID = getIntent().getIntExtra("routeID", 0);


        plotRoute(routeID);


        TextView stationLabel = (TextView) findViewById(R.id.Station_Name);
        stationLabel.setText(stationName);
//        TODO: Assign values to text fields in new activity
        TextView shuttleLabel = (TextView) findViewById(R.id.ShuttleIDData);
        shuttleLabel.setText(shuttleRegNo);
        TextView arrivalTimeLabel = (TextView) findViewById(R.id.arrivalTimeData);
        arrivalTimeLabel.setText(arrivalTime);
        TextView routeIDLabel = (TextView) findViewById(R.id.routeIDData);
        routeIDLabel.setText(routeName);




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
                            for (int i = 0; i < routesStations.get(r).getStations().size(); i++) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(routesStations.get(r).getStations().get(i).getLat(), routesStations.get(r).getStations().get(i).getLng()))
                                        .title(routesStations.get(r).getStations().get(i).getName())
                                        .icon(BitmapDescriptorFactory.defaultMarker(routesStations.get(r).getHueColor())));
                                positions.add(i, new LatLng(routesStations.get(r).getStations().get(i).getLat(), routesStations.get(r).getStations().get(i).getLng()));

                            }
                            mMap.addPolyline(new PolylineOptions().addAll(positions).color(routesStations.get(r).getHueColor()));
                        }
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
                                new LatLng(routesStations.get(0).getStations().get(0).getLat()
                                        , routesStations.get(0).getStations().get(0).getLng()), 10);
                        mMap.moveCamera(update);
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("Log Failure", "response station = ??");
                }
            });

        }
    }
    public void plotRoute(int routeID) {
        if (restApiService != null) {
            final List<LatLng> positions = new ArrayList<LatLng>();
            Call<RouteWithStation> stationListCall = restApiService.getRouteStations(routeID);
            stationListCall.enqueue(new Callback<RouteWithStation>() {
                @Override
                public void onResponse(Response<RouteWithStation> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        RouteWithStation routeStations = response.body();
                        List<Station> stations = routeStations.getStations();
                        for (int i = 0; i < stations.size(); i++) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(stations.get(i).getLat(), stations.get(i).getLng()))
                                    .title(stations.get(i).getName())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            positions.add(i, new LatLng(stations.get(i).getLat(), stations.get(i).getLng()));

                        }
                        mMap.addPolyline(new PolylineOptions().addAll(positions));
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(stations.get(0).getLat(), stations.get(0).getLng()), 10);
                        mMap.moveCamera(update);
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
