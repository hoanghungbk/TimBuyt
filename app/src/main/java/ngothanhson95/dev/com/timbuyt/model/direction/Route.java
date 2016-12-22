package ngothanhson95.dev.com.timbuyt.model.direction;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class Route implements Parcelable {

    public Bounds bounds;
    public String copyrights;
    public Fare fare;
    public List<Leg> legs = null;
    public OverviewPolyline overviewPolyline;

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public List<Object> getWaypointOrder() {
        return waypointOrder;
    }

    public void setWaypointOrder(List<Object> waypointOrder) {
        this.waypointOrder = waypointOrder;
    }

    public static Creator<Route> getCREATOR() {
        return CREATOR;
    }

    public String summary;
    public List<String> warnings = null;
    public List<Object> waypointOrder = null;


    protected Route(Parcel in) {
        copyrights = in.readString();
        summary = in.readString();
        warnings = in.createStringArrayList();
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(copyrights);
        parcel.writeString(summary);
        parcel.writeStringList(warnings);
    }
}
