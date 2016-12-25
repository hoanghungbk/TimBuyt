package ngothanhson95.dev.com.timbuyt.model.direction;

import java.io.Serializable;
import java.util.List;


public class DirectionJSON implements Serializable{

    public List<GeocodedWaypoint> geocodedWaypoints = null;
    public List<Route> routes = null;
    public String status;

    public List<Route> getRoutes() {
        return routes;
    }
}