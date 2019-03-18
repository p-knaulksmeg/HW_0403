package by.paulk.hw_0403;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;


public class SimpleService extends Service{

    final static String KEY_INT_FROM_SERVICE = "KEY_INT_FROM_SERVICE";
    final static String ACTION_UPDATE_COUNT = "UPDATE_CNT";
    final static String ACTION_MESSAGE_TO_SERVICE = "MSG_TO_SERVICE";

    SimpleBroadcastReceiver simpleBroadcastReceiver;
    MyServiceThread myServiceThread;
    int count;

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(getApplicationContext(),
                "onCreate", Toast.LENGTH_LONG).show();
        simpleBroadcastReceiver = new SimpleBroadcastReceiver();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(),
                "onStartCommand", Toast.LENGTH_LONG).show();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_MESSAGE_TO_SERVICE);
        registerReceiver(simpleBroadcastReceiver, intentFilter);

        myServiceThread = new MyServiceThread();
        myServiceThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_LONG).show();
        myServiceThread.setRunning(false);
        unregisterReceiver(simpleBroadcastReceiver);
        super.onDestroy();
    }

    private class MyServiceThread extends Thread{

        private boolean running;

        public void setRunning(boolean running){
            this.running = running;
        }

        @Override
        public void run() {
            count = 0;
            running = true;
            while (running){
                try {
                    Thread.sleep(1000);

                    Intent intent = new Intent();
                    intent.setAction(ACTION_UPDATE_COUNT);
                    intent.putExtra(KEY_INT_FROM_SERVICE, count);
                    sendBroadcast(intent);

                    count++;
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
