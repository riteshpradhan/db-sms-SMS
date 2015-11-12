package project.db.sms;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by ritesh on 11/11/15.
 */
public interface RestApiInterface {

        @GET("v2/5643d92d1100003e097fdb1b")
        Call<List<Hello>> getHelloList();

        @GET("api/sql/select{space}*{space}from{space}routes")
        Call<List<Route>> getRoutes();

}
