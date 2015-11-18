package project.db.sms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.db.sms.adapter.RouteItemAdapter;
import project.db.sms.apiservices.RestClient;
import project.db.sms.apiservices.interfaceservices.RestApiInterface;
import project.db.sms.apiservices.model.StationRouteShuttleTime;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AllServicesActivity extends AppCompatActivity {

    public RestApiInterface restApiService;
    public RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_services);

        restClient = new RestClient();
        restClient.setRestApiService();
        restApiService = restClient.getRestApiService();
        stationRouteShuttle();
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
                Log.d("Log Failure", "response mmmstation = ??");
            }
        });
    }

    public void showInList(final List<StationRouteShuttleTime> details) {
        LinearLayout route_list_layout = (LinearLayout) findViewById(R.id.all_services_layout);
        ListView list_item = new ListView(this);
        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                String stationName = ((TextView) view.findViewById(R.id.station_name)).getText().toString();
                Toast toast = Toast.makeText(getApplicationContext(), stationName, Toast.LENGTH_SHORT);
                toast.show();

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
        //list_item.setBackgroundColor(0xFF00FF00);
        route_list_layout.addView(list_item);
    }

}

