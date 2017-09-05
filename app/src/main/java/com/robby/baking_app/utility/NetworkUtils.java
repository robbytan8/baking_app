package com.robby.baking_app.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.robby.baking_app.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Robby on 7/25/2017.
 *
 * @author Robby Tan
 */

public final class NetworkUtils {

    public static URL buildUrl() {
        Uri uri = Uri.parse(BuildConfig.DATA_URL).buildUpon()
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasInternetConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
