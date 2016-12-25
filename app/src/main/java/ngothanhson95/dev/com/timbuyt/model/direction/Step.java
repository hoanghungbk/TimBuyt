package ngothanhson95.dev.com.timbuyt.model.direction;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class Step implements Serializable {

    @SerializedName("distance")
    public Distance distance;

    @SerializedName("duration")
    public Duration duration;

    @SerializedName("end_location")
    public EndLocation endLocation;

    @SerializedName("html_instructions")
    public String htmlInstructions;

    @SerializedName("polyline")
    public Polyline polyline;

    @SerializedName("start_location")
    public StartLocation startLocation;

    @SerializedName("steps")
    public List<Step> steps = null;

    @SerializedName("travel_mode")
    public String travelMode;

    @SerializedName("transit_details")
    public TransitDetails transitDetails;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public EndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public StartLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocation startLocation) {
        this.startLocation = startLocation;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public TransitDetails getTransitDetails() {
        return transitDetails;
    }

    public void setTransitDetails(TransitDetails transitDetails) {
        this.transitDetails = transitDetails;
    }
}
