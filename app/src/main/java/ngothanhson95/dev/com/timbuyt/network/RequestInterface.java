package ngothanhson95.dev.com.timbuyt.network;

import com.google.android.gms.maps.model.LatLng;

import ngothanhson95.dev.com.timbuyt.AppConstants;
import ngothanhson95.dev.com.timbuyt.model.bus.BusStopJSON;
import ngothanhson95.dev.com.timbuyt.model.direction.DirectionJSON;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ngothanhson on 12/8/16.
 */

public interface RequestInterface {
    @GET("maps/api/place/nearbysearch/json?radius=1000&sensor=false&type=bus_station&key=" + AppConstants.DEVELOPER_KEY)
    Call<BusStopJSON> getAllBusStop(@Query("location") String location);

    @GET("maps/api/directions/json?mode=transit&transit_mode=bus&sensor=false&alternatives=true&key=" + AppConstants.DEVELOPER_KEY)
    Call<DirectionJSON> getAllRoute(@Query("origin")String origin, @Query("destination") String dest);

}
