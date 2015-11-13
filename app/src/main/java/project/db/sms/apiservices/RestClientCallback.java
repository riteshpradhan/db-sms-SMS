
package project.db.sms.apiservices;


import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;


import java.io.IOException;
import java.util.List;

import project.db.sms.apiservices.interfaceservices.RestApiInterface;
import project.db.sms.apiservices.model.Hello;
import project.db.sms.apiservices.model.Station;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by ritesh on 11/12/15.
 */

public class RestClientCallback {
    private static RestClientCallback instance = null;
    private ResultReadyCallback resultCallback;
    private static final String ENDPOINT = "http://www.mocky.io/";
    private static final OkHttpClient httpClient = new OkHttpClient();
    private RestApiInterface apiInterface;

    /*  Models  */
    List<Hello> hellos = null;
    Station station = null;

    public  RestClientCallback() {
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

        apiInterface = retrofit.create(RestApiInterface.class);
    }

    public List<Hello> getHellos() {
        final Call<List<Hello>> helloList = apiInterface.getHelloList();
        helloList.enqueue(new Callback<List<Hello>>() {
            @Override
            public void onResponse(Response<List<Hello>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    hellos = response.body();
                    resultCallback.resultReady(hellos);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("REST", t.getMessage());
            }
        });
        return hellos;
    }

    public void setResultCallback(ResultReadyCallback callback) {
        this.resultCallback = callback;
    }

    public static RestClientCallback getInstance() {
        if(instance == null) {
            instance = new RestClientCallback();
        }
        return instance;
    }

    public interface ResultReadyCallback {
        public void resultReady(List<Hello> hellos);
    }

}



//    public boolean createUser(final Context ctx, User user) {
//        Call<User> u = service.createUser(user);
//        u.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Response<User> response) {
//                success = response.isSuccess();
//                if(success) {
//                    Toast.makeText(ctx, "User Created", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ctx, "Couldn't create user", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.w("REST", t.getMessage());
//                Toast.makeText(ctx, "Couldn't create user", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        return success;
//    }





//}