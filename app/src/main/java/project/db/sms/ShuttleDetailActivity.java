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

public class ShuttleDetailActivity extends Activity {
    private String stationName;
    private String shuttleID;
    private String arrivalTime;
    private String numSeats;
    private String route;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle_detail);

        stationName = getIntent().getStringExtra("stationName");
//        TODO: Get values of all the necessary fields from intent
//        shuttleID = getIntent().getStringExtra("shuttleID");
//        arrivalTime = getIntent().getStringExtra("arrivalTime");
//        numSeats = getIntent().getStringExtra("numSeats");
//        route = getIntent().getStringExtra("route");

        TextView stationLabel = (TextView) findViewById(R.id.Station_Name);
        stationLabel.setText(stationName);
//        TODO: Assign values to text fields in new activity
//        TextView shuttleLabel = (TextView) findViewById(R.id.ShuttleIDData);
//        shuttleLabel.setText(shuttleID);
//        TextView arrivalTimeLabel = (TextView) findViewById(R.id.arrivalTimeData);
//        arrivalTimeLabel.setText(arrivalTime);
//        TextView numSeatsLabel = (TextView) findViewById(R.id.numSeatsData);
//        numSeatsLabel.setText(numSeats);

    }

}
