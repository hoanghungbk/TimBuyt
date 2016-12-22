package ngothanhson95.dev.com.timbuyt.model.direction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class Step {

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

}
