package ngothanhson95.dev.com.timbuyt.model.direction;

import java.util.List;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class Step {

    public Distance distance;
    public Duration duration;
    public EndLocation endLocation;
    public String htmlInstructions;
    public Polyline polyline;
    public StartLocation startLocation;
    public List<Step> steps = null;
    public String travelMode;
    public TransitDetails transitDetails;

}
