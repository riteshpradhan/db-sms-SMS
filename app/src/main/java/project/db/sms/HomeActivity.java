package project.db.sms;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeActivity extends AppCompatActivity implements InputFragment.InputSectionListener{

    private boolean isMapReady;
    private GoogleMap gMap;
    private Location currLocation;
    private Marker currMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        isMapReady = initMap();
        if (isMapReady){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(34.718166, -86.661352), 8);
            gMap.addMarker(new MarkerOptions().position(new LatLng(34.718166, -86.661352)));
            gMap.moveCamera(update);
        }


        showshuttleListView();
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

    public void showshuttleListView() {

    }
}
