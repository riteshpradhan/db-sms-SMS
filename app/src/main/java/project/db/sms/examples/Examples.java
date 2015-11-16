package project.db.sms.examples;

/**
 * Created by ritesh on 11/12/15.
 */
public class Examples {
    //        listCall.cancel();

    //get routes
//        Call<List<Segment>> callRoutes = helloAPI.getRoutes();
//        callRoutes.enqueue(new Callback<List<Segment>>() {
//            @Override
//            public void onResponse(Response<List<Segment>> response, Retrofit retrofit) {
//                if (response.isSuccess()){
//                    Log.v("Logg", response.body().toString());
////                Log.v("Header", response.headers().get("token"));
//                    List<Segment> routes = response.body();
//
//                    Log.d("MainActivity", "response = " + new Gson().toJson(routes));
//
//                    for (Segment route: routes) {
//                        System.out.println("Segment id: " + route.getRouteID());
//                        System.out.println("Segment Name: " + route.getRouteName());
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


/*    Android framework limitations that only permit views to be updated on the main UI thread.
If you need to update any views, you will need to use runOnUiThread()
or post the result back on the main thread */

//    client.newCall(request).enqueue(new Callback() {
//        @Override
//        public void onResponse(final Response response) throws IOException {
//            // ... check for failure using `isSuccessful` before proceeding
//
//            // Read data on the worker thread
//            final String responseData = response.body().string();
//
//            // Run view-related code back on the main thread
//            MainActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        TextView myTextView = (TextView) findViewById(R.id.myTextView);
//                        myTextView.setText(responseData);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    });



//    private void call(String method, String url, final HttpCallback cb) {
//        ...
//
//        client.newCall(request).enqueue(new Callback() {
//            Handler mainHandler = new Handler(context.getMainLooper());
//
//            @Override
//            public void onFailure(Request request,final Throwable throwable) {
//                mainHandler.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        cb.onFailure(null, throwable);
//                    }
//                });
//
//            }
//
//            @Override
//            public void onResponse(final Response response) throws IOException {
//                mainHandler.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        if (!response.isSuccessful()) {
//                            cb.onFailure(response, null);
//                            return;
//                        }
//                        cb.onSuccess(response);
//                    }
//                });
//
//            }
//        });
//    }


}
