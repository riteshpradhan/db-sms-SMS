package project.db.sms;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.List;

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
    public static final String ENDPOINT = "http://www.mocky.io/";
    private static OkHttpClient httpClient = new OkHttpClient();

    public void RestClient() {}

    public void requestData(String uri) {
        final String basic = "pradhan";

        Log.d("LOG: ", "After request inside request Data");

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

        RestApiInterface helloAPI = retrofit.create(RestApiInterface.class);

        Call<List<Hello>> listCall = helloAPI.getHelloList();
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
//                    showInSpinner(hellos);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("Log Failure", "response = ??");
            }
        });
//        listCall.cancel();

        //get routes
//        Call<List<Route>> callRoutes = helloAPI.getRoutes();
//        callRoutes.enqueue(new Callback<List<Route>>() {
//            @Override
//            public void onResponse(Response<List<Route>> response, Retrofit retrofit) {
//                if (response.isSuccess()){
//                    Log.v("Logg", response.body().toString());
////                Log.v("Header", response.headers().get("token"));
//                    List<Route> routes = response.body();
//
//                    Log.d("MainActivity", "response = " + new Gson().toJson(routes));
//
//                    for (Route route: routes) {
//                        System.out.println("Route id: " + route.getRouteID());
//                        System.out.println("Route Name: " + route.getRouteName());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//
//            }
//        });
//        listCall.cancel();

    }

}
