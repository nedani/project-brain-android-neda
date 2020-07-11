package com.neda.project_brain_android_neda.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.neda.project_brain_android_neda.R;

public class InternetUtil {
    public static boolean isInternetAvailable(Context context) {
        Boolean isInternetPresent = isConnectingToInternet(context);

        if (!isInternetPresent) {
            Toast.makeText(context, "No Internet available", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return isInternetPresent;
        }
    }

    public static boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}
