package project.db.sms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import project.db.sms.R;
import project.db.sms.apiservices.model.Station;

/**
 * Created by ritesh on 11/15/15.
 */
public class RouteItemAdapter extends ArrayAdapter<Station> {
    // View lookup cache
    private static class ViewHolder {
        TextView stationNameTextView;
        TextView stationLocationTextView;
    }

    public RouteItemAdapter (Context context, ArrayList<Station> stations) {
        super(context, R.layout.route_item_adapter, stations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Station station = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.route_item_adapter, parent, false);
            viewHolder.stationNameTextView = (TextView) convertView.findViewById(R.id.station_name);
            viewHolder.stationLocationTextView = (TextView) convertView.findViewById(R.id.station_location);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.stationNameTextView.setText(station.getName());
        viewHolder.stationLocationTextView.setText(station.getLocation());
        // Return the completed view to render on screen
        return convertView;
    }

}
