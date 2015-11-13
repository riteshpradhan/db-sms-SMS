package project.db.sms.apiservices.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ritesh on 11/11/15.
 */
public class Segment {
    public Segment() { }

    @SerializedName("segment_id")
    public int segmentID;

    @SerializedName("name")
    String routeName;

    public int getSegmentID() {
        return segmentID;
    }

    public void setSegmentID(int routeID) {
        this.segmentID = routeID;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getFromStation() {
        return fromStation;
    }

    public void setFromStation(int fromStation) {
        this.fromStation = fromStation;
    }

    public int getToStation() {
        return toStation;
    }

    public void setToStation(int toStation) {
        this.toStation = toStation;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @SerializedName("from_station_id")
    int fromStation;
    @SerializedName("to_station_id")
    int toStation;
    @SerializedName("time_interval")
    float duration;
}
