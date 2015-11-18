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
import project.db.sms.apiservices.model.Station;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ShuttleDetailActivity extends Activity {

    private GoogleMap mMap;
    private String stationName;
    private String shuttleRegNo;
    private int arrivalTime;
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
        arrivalTime = getIntent().getIntExtra("arrivalTime", 0);
        routeName = getIntent().getStringExtra("routeName");
        routeID = getIntent().getIntExtra("routeID", 0);


        plotRoute(routeID);


        TextView stationLabel = (TextView) findViewById(R.id.Station_Name);
        stationLabel.setText(stationName);
//        TODO: Assign values to text fields in new activity
        TextView shuttleLabel = (TextView) findViewById(R.id.ShuttleIDData);
        shuttleLabel.setText(shuttleRegNo);
        TextView arrivalTimeLabel = (TextView) findViewById(R.id.arrivalTimeData);
        arrivalTimeLabel.setText(String.valueOf(arrivalTime) + " minutes");
        TextView routeIDLabel = (TextView) findViewById(R.id.routeIDData);
        routeIDLabel.setText(routeName);




    }

    public void plotRoute(int routeID) {
        if (restApiService != null) {
            final List<LatLng> positions = new ArrayList<LatLng>();
            Call<List<Station>> stationListCall = restApiService.getStationRID(routeID);
            stationListCall.enqueue(new Callback<List<Station>>() {
                @Override
                public void onResponse(Response<List<Station>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        List<Station> stations = response.body();
                        double distance = 0;
                        distance = stations.get(1).getLat();

                        for (int i = 0; i < stations.size(); i++) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(stations.get(i).getLat(), stations.get(i).getLng()))
                                    .title(stations.get(i).getName())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            positions.add(i, new LatLng(stations.get(i).getLat(), stations.get(i).getLng()));

                        }
                        mMap.addPolyline(new PolylineOptions().addAll(positions));
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
