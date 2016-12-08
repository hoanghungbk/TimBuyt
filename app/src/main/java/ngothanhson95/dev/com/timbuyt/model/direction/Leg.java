package ngothanhson95.dev.com.timbuyt.model.direction;

import java.util.List;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class Leg {

    public ArrivalTime arrivalTime;
    public DepartureTime departureTime;
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public EndLocation endLocation;
    public String startAddress;
    public StartLocation startLocation;
    public List<Step> steps = null;
    public List<Object> trafficSpeedEntry = null;
    public List<Object> viaWaypoint = null;

}
