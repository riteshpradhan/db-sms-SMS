package project.db.sms.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import project.db.sms.R;
import project.db.sms.apiservices.model.StationRouteShuttleTime;

/**
 * Created by ritesh on 11/15/15.
 */
public class RouteItemAdapter extends ArrayAdapter<StationRouteShuttleTime> {
    // View lookup cache
    private static class ViewHolder {
        TextView stationNameTextView;
        TextView stationLocationTextView;
        TextView arrivalTimeTextView;
        TextView routeNameTextView;
        TextView shuttleRegNoTextView;

    }

    public RouteItemAdapter (Context context, ArrayList<StationRouteShuttleTime> stationRouteShuttleTimes) {
        super(context, R.layout.route_item_adapter, stationRouteShuttleTimes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        StationRouteShuttleTime stationRouteShuttleTime = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.route_item_adapter, parent, false);
            viewHolder.stationNameTextView = (TextView) convertView.findViewById(R.id.station_name);
            viewHolder.stationLocationTextView = (TextView) convertView.findViewById(R.id.station_location);
            viewHolder.arrivalTimeTextView = (TextView) convertView.findViewById(R.id.arrival_time);
            viewHolder.routeNameTextView = (TextView) convertView.findViewById(R.id.route_name);
            viewHolder.shuttleRegNoTextView = (TextView) convertView.findViewById(R.id.shuttle_reg_no);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.stationNameTextView.setText(stationRouteShuttleTime.getStationName());
        viewHolder.stationLocationTextView.setText(stationRouteShuttleTime.getLocation());
        viewHolder.arrivalTimeTextView.setText(String.valueOf(stationRouteShuttleTime.getArrivalTime()));
        viewHolder.routeNameTextView.setText(String.valueOf(stationRouteShuttleTime.getRouteName()));
        viewHolder.shuttleRegNoTextView.setText(String.valueOf(stationRouteShuttleTime.getShuttleRegNo()));


        // Return the completed view to render on screen
        return convertView;
    }

}
