package project.db.sms.apiservices.interfaceservices;

import java.util.List;

import project.db.sms.apiservices.model.Hello;
import project.db.sms.apiservices.model.Segment;
import project.db.sms.apiservices.model.Station;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by ritesh on 11/11/15.
 */
public interface RestApiInterface {

    @GET("v2/5644dce511000047407fdbe9")
    Call<List<Hello>> getHelloList();

    @GET("api/sql/select{space}*{space}from{space}routes")
    Call<List<Segment>> getRoutes();

//        @GET("api/sql/select{space}*{space}from{space}station{space}where{space}station_id=86")
//    @GET("/sql/select{space}*{space}from{space}station{space}where{space}station_id=86")
//    Call<Station> getStation();

    @GET("/v2/564514f1110000a704c2bd76")
    Call<Station> getStation();

}
