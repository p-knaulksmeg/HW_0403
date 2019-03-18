package by.paulk.hw_0403;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    Button buttonStart, buttonStop, buttonSend;
    EditText editTextMessageToSend;
    TextView textViewCountReceived, textViewMessageReceived;

    MainReceiver mainReceiver;
    Intent myIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        buttonStart = (Button)findViewById(R.id.startService);
        buttonStop = (Button)findViewById(R.id.stopService);
        buttonSend = (Button)findViewById(R.id.send);
        editTextMessageToSend = (EditText)findViewById(R.id.messageToSend);
        textViewCountReceived = (TextView)findViewById(R.id.countReceived);
        textViewMessageReceived = (TextView)findViewById(R.id.messagesReceived);

        buttonStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startService();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopService();
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String messageToService = editTextMessageToSend.getText().toString();

                Intent intent = new Intent();
                intent.setAction(SimpleService.ACTION_MESSAGE_TO_SERVICE);
                intent.putExtra(SimpleBroadcastReceiver.KEY_MESSAGE_TO_SERVICE, messageToService);
                sendBroadcast(intent);
            }
        });
    }

    private void startService(){
        myIntent = new Intent(MainActivity.this, SimpleService.class);
        startService(myIntent);
    }

    private void stopService(){
        if(myIntent != null){
            stopService(myIntent);
        }
        myIntent = null;
    }

    @Override
    protected void onStart() {
        mainReceiver = new MainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SimpleService.ACTION_UPDATE_COUNT);
        intentFilter.addAction(SimpleBroadcastReceiver.ACTION_UPDATE_MESSAGE);
        registerReceiver(mainReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mainReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService();
    }

    private class MainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(SimpleService.ACTION_UPDATE_COUNT)){
                int int_from_service = intent.getIntExtra(SimpleService.KEY_INT_FROM_SERVICE, 0);
                textViewCountReceived.setText(String.valueOf(int_from_service));
            }else if(action.equals(SimpleBroadcastReceiver.ACTION_UPDATE_MESSAGE)){
                String string_from_service = intent.getStringExtra(SimpleBroadcastReceiver.KEY_STRING_FROM_SERVICE);
                textViewMessageReceived.setText(String.valueOf(string_from_service));
            }
        }
    }
}
