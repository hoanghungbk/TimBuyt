package ngothanhson95.dev.com.timbuyt.model.direction;

import java.util.List;


public class DirectionJSON {

    public List<GeocodedWaypoint> geocodedWaypoints = null;
    public List<Route> routes = null;
    public String status;

    public List<Route> getRoutes() {
        return routes;
    }
}