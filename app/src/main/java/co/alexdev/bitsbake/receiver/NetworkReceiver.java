package co.alexdev.bitsbake.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;

import co.alexdev.bitsbake.events.NetworkConnectionEvent;

        public class NetworkReceiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isConnected = checkConnection(context);
                EventBus.getDefault().post(new NetworkConnectionEvent(isConnected));
            }

            private boolean checkConnection(Context context) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();
            }
        }

