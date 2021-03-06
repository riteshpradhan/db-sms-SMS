package project.db.sms.apiservices.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ritesh on 11/12/15.
 */
public class Route {
    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }


    public int getHueColor() {
        return hueColor;
    }

    public void setHueColor(int hueColor) {
        this.hueColor = hueColor;
    }


    @SerializedName("hue_color")
    int hueColor;
    @SerializedName("route_name")
    String routeName;
    @SerializedName("route_id")
    int routeID;

}
