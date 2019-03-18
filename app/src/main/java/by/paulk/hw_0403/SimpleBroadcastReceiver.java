package by.paulk.hw_0403;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static by.paulk.hw_0403.SimpleService.ACTION_MESSAGE_TO_SERVICE;

public class SimpleBroadcastReceiver extends BroadcastReceiver {

    final static String ACTION_UPDATE_MESSAGE = "UPDATE_MSG";
    final static String KEY_MESSAGE_TO_SERVICE = "KEY_MESSAGE_TO_SERVICE";
    final static String KEY_STRING_FROM_SERVICE = "KEY_STRING_FROM_SERVICE";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if(action.equals(ACTION_MESSAGE_TO_SERVICE)){

            String message = intent.getStringExtra(KEY_MESSAGE_TO_SERVICE);
            message = new StringBuilder(message).reverse().toString();

            Intent intentMain = new Intent();
            intentMain.setAction(ACTION_UPDATE_MESSAGE);
            intentMain.putExtra(KEY_STRING_FROM_SERVICE, message);
            context.sendBroadcast(intentMain);
        }
    }
}
