package ngothanhson95.dev.com.timbuyt.model.direction;

import java.util.List;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class Route {

    public Bounds bounds;
    public String copyrights;
    public Fare fare;
    public List<Leg> legs = null;
    public OverviewPolyline overviewPolyline;
    public String summary;
    public List<String> warnings = null;
    public List<Object> waypointOrder = null;

}
