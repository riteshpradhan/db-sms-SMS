package project.db.sms;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import project.db.sms.adapter.RouteItemAdapter;
import project.db.sms.apiservices.RestClient;
import project.db.sms.apiservices.interfaceservices.RestApiInterface;
import project.db.sms.apiservices.model.Station;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteListFragment extends Fragment {

    public RestClient restClient;
    public List<Station> stations;
    public RestApiInterface restApiService;

    public RouteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        restClient = new RestClient();
        restClient.setRestApiService();
        restApiService = restClient.getRestApiService();

        ListView list_item = new ListView(getActivity());
        return listStation(list_item);

    }

    private ListView listStation(final ListView list_item) {
        Call<List<Station>> stationListCall = restApiService.getStations();
        stationListCall.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Response<List<Station>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    stations = response.body();
                    double distance = 0;
                    distance = stations.get(1).getLat();

                    RouteItemAdapter adapter = new RouteItemAdapter(getActivity(), (ArrayList)stations);
                    list_item.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("Log Failure", "response station = ??");
            }
        });

        return list_item;
    }


}
