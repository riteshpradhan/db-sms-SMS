package project.db.sms.apiservices.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ritesh on 11/12/15.
 */
public class Shuttle {
    public int getShuttleID() {
        return shuttleID;
    }

    public void setShuttleID(int shuttleID) {
        this.shuttleID = shuttleID;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public int getNoSeats() {
        return noSeats;
    }

    public void setNoSeats(int noSeats) {
        this.noSeats = noSeats;
    }

    public int getFromHubID() {
        return fromHubID;
    }

    public void setFromHubID(int fromHubID) {
        this.fromHubID = fromHubID;
    }

    public int getToHubID() {
        return toHubID;
    }

    public void setToHubID(int toHubID) {
        this.toHubID = toHubID;
    }

    @SerializedName("shuttle_id")
    int shuttleID;
    @SerializedName("reg_no")
    String regNo;
    @SerializedName("no_seats")
    int noSeats;
    @SerializedName("from_hub_id")
    int fromHubID;
    @SerializedName("to_hub_id")
    int toHubID;
}
