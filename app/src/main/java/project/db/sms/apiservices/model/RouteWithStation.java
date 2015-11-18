package project.db.sms.apiservices.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritesh on 11/12/15.
 */
public class RouteWithStation {
    @SerializedName("route_name")
    String routeName;
    @SerializedName("route_id")
    int routeID;

    @SerializedName("hue_color")
    int hueColor;


    public int getHueColor() {
        return hueColor;
    }

    public void setHueColor(int hueColor) {
        this.hueColor = hueColor;
    }

    List<Station> stations = new ArrayList<Station>();

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

}
