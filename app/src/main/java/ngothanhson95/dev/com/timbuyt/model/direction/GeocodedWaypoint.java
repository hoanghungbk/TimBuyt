package ngothanhson95.dev.com.timbuyt.model.direction;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class GeocodedWaypoint implements Serializable {

    public String geocoderStatus;
    public boolean partialMatch;
    public String placeId;
    public List<String> types = null;

}
