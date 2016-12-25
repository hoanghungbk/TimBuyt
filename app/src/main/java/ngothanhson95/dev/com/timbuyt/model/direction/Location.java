package ngothanhson95.dev.com.timbuyt.model.direction;

import java.io.Serializable;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class Location implements Serializable {

    public double lat;
    public double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
