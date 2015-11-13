package project.db.sms.apiservices.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ritesh on 11/11/15.
 */
public class StationRouteShuttleTIme {
    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public int getShuttleID() {
        return shuttleID;
    }

    public void setShuttleID(int shuttleID) {
        this.shuttleID = shuttleID;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getShuttleRegNum() {
        return shuttleRegNo;
    }

    public void setShuttleRegNum(String shuttleRegNum) {
        this.shuttleRegNo = shuttleRegNum;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @SerializedName("route_id")
    int routeID;
    @SerializedName("shuttle_id")
    int shuttleID;
    @SerializedName("route_name")
    String routeName;
    @SerializedName("shuttle_reg_no")
    String shuttleRegNo;
    @SerializedName("arrival_time")
    int arrivalTime;
}
