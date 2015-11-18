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



    @SerializedName("shuttle_id")
    int shuttleID;
    @SerializedName("reg_no")
    String regNo;
    @SerializedName("no_seats")
    int noSeats;

}
