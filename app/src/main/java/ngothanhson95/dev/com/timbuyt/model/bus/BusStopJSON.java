package ngothanhson95.dev.com.timbuyt.model.bus;

import java.util.List;

/**
 * Created by ngothanhson on 12/8/16.
 */


public class BusStopJSON {
    public List<Object> htmlAttributions = null;
    public String nextPageToken;
    public List<Result> results = null;
    public String status;

    public List<Result> getResults() {
        return results;
    }
}


