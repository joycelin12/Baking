package com.example.android.baking.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by joycelin12 on 7/14/18.
 */

public class NetworkUtils {

    final static String RECIPES_BASE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     * Builds the URL used to display the recipe.
     *
     * @return The URL to use to query the recipe server.
     */
    public static URL buildUrl() {
        // TODO (1) Fill in this method to build the proper recipe URL
        Uri builtUri = Uri.parse(RECIPES_BASE_URL).buildUpon()
                  .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    //trying out OkHttp https://square.github.io/okhttp/
    OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        return response.body().string();
    }



}
