package ngothanhson95.dev.com.timbuyt.network;

import ngothanhson95.dev.com.timbuyt.Constants;
import ngothanhson95.dev.com.timbuyt.model.bus.BusStopJSON;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ngothanhson on 12/8/16.
 */

public interface RequestInterface {
    @GET("maps/api/place/nearbysearch/json?radius=1000&type=bus_station&key=" + Constants.DEVELOPER_KEY)
    Call<BusStopJSON> getAllBusStop(@Query("location") String location);

//    @GET("maps/api/directions/json?mode=transit&transit_mode=bus&key=" + Constants.DEVELOPER_KEY)
//    Call<>
}
