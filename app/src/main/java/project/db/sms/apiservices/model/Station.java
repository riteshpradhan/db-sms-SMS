package project.db.sms.apiservices.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ritesh on 11/11/15.
 */
public class Station {
    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int isHub() {
        return isHub;
    }

    public void setIsHub(int isHub) {
        this.isHub = isHub;
    }

    @SerializedName("station_id")
    int stationID;
    String name;
    String lng;
    String lat;
    String location;
    @SerializedName("is_hub")
    int isHub;
}
