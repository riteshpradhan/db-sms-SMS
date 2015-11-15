package project.db.sms.apiservices;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.List;

import project.db.sms.apiservices.interfaceservices.RestApiInterface;
import project.db.sms.apiservices.model.Station;
import project.db.sms.apiservices.model.Hello;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ritesh on 11/11/15.
 */
public class RestClient {
//    public static final String ENDPOINT = "http://10.0.0.6:3001/";
    public static final String ENDPOINT = "http://192.168.0.21:8080/";
//    private static final String ENDPOINT = "http://www.mocky.io/";
    private static final OkHttpClient httpClient = new OkHttpClient();
    public RestApiInterface restApiService;

    /* Model */
//    List<Station> stations = null;

    public void RestClient() {
        //Use single call per activity
//        setRestApi();
    }

    public void setRestApiService() {
        Log.d("LOG: ", "Inside Rest Client");
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                com.squareup.okhttp.Request original = chain.request();
                com.squareup.okhttp.Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "applicaton/json")
                        .method(original.method(), original.body());
                com.squareup.okhttp.Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        this.restApiService = retrofit.create(RestApiInterface.class);
    }

    public RestApiInterface getRestApiService() {
        return restApiService;
    }


    //example to be implemented in every call
    public void requestData() {
        if (this.getRestApiService() != null) {
            Call<List<Hello>> listCall = this.getRestApiService().getHelloList();
            listCall.enqueue(new Callback<List<Hello>>() {
                @Override
                public void onResponse(Response<List<Hello>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Log.v("Logg", response.body().toString());
                        List<Hello> hellos = response.body();
                        Log.d("MainActivity", "response = " + new Gson().toJson(hellos));
                        for (Hello hell : hellos) {
                            System.out.println(
                                    hell.getHello());
                        }
                    }
                }
                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("Log Failure", "response = ??");
                }
            });
        } else {
            Log.d("LOGG: ", "How come null here");
        }
    }

    public void showStation() {
        if (this.getRestApiService() != null) {
            Call<Station> listCall = this.getRestApiService().getStation();
            listCall.enqueue(new Callback<Station>() {
                @Override
                public void onResponse(Response<Station> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Log.v("Logg", response.body().toString());
                        Station station = response.body();
                        Log.d("MainActivity", "response = " + new Gson().toJson(station));
//                        gMap.addMarker(new MarkerOptions().position(new LatLng(station.getLat(), station.getLng())));
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("Log Failure", "response station = ??");
                }
            });
        } else {
            Log.d("Log", "Null ssv obtained ");
        }
    }
}
