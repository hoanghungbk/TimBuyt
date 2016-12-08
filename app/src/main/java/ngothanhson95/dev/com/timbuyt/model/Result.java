package ngothanhson95.dev.com.timbuyt.model;

import java.util.List;

/**
 * Created by ngothanhson on 12/8/16.
 */
public class Result {

    public Geometry geometry;
    public String icon;
    public String id;
    public String name;
    public String placeId;
    public String reference;
    public String scope;
    public List<String> types = null;
    public String vicinity;
    public List<Photo> photos = null;
    public double rating;

    public Geometry getGeometry() {
        return geometry;
    }
}
