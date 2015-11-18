package project.db.sms.apiservices.interfaceservices;

import java.util.List;

import project.db.sms.apiservices.model.Hello;
import project.db.sms.apiservices.model.Route;
import project.db.sms.apiservices.model.RouteWithStation;
import project.db.sms.apiservices.model.Segment;
import project.db.sms.apiservices.model.Station;
import project.db.sms.apiservices.model.StationRouteShuttleTime;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by ritesh on 11/11/15.
 */
public interface RestApiInterface {

    @GET("v2/5644dce511000047407fdbe9")
    Call<List<Hello>> getHelloList();


    @GET("/api/get_stations")
    Call<List<Station>> getStations();

    @GET("/api/get_station/{station_id}")
    Call<Station> getStation(@Path("station_id") int station_id);

    @GET("/api/get_station/{route_id}")
    Call<List<Station>> getStationRID(@Path("route_id") int route_id);




    @GET("/api/get_details")
    Call<List<StationRouteShuttleTime>> getDetails();

    @GET("/api/get_detail/{nearest_station}")
    Call<List<StationRouteShuttleTime>> getDetail(@Path("nearest_station") int nearest_station);

    @GET("/api/get_detail/{source}/{destination}")
    Call<List<StationRouteShuttleTime>> getDetail(@Path("source") int source, @Path("destination") int destination);

    @GET("/api/get_routes")
    Call<List<Route>> getRoutes();

    @GET("/api/get_route/{route_id}")
    Call<Route> getRoute(@Path("route_id") int route_id);


    @GET("/api/get_routes_stations")
    Call<List<RouteWithStation>> getRoutesStations();


    @GET("/api/get_route_stations/{route_id}")
    Call<RouteWithStation> getRouteStations(@Path("route_id") int route_id);

    @GET("/api/get_routes_stations/{source_id}/{destination_id}")
    Call<List<RouteWithStation>> getRoutesStations(@Path("source_id") int source_id, @Path("destination_id") int destination_id);

}
