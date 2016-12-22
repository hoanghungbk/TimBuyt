package ngothanhson95.dev.com.timbuyt.model.direction;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class TransitDetails {


    @SerializedName("arrival_stop")
    public ArrivalStop arrivalStop;
    @SerializedName("arrival_time")
    public ArrivalTime arrivalTime;
    @SerializedName("departure_stop")
    public DepartureStop departureStop;
    @SerializedName("departure_time")
    public DepartureTime departureTime;
    @SerializedName("headsign")
    public String headsign;
    @SerializedName("headway")
    public int headway;
    @SerializedName("line")
    public Line line;
    @SerializedName("num_stops")
    public int numStops;

}
