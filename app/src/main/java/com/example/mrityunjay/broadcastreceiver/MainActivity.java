package com.example.mrityunjay.broadcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean charger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txt = new TextView(this);
        TextView text = (TextView) findViewById(R.id.text);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(broadcastReceiver,intentFilter);
    }


    LowBattery broadcastReceiver = new LowBattery() {
        public void onReceive(Context context, Intent intent) {
            context.unregisterReceiver(this);
            intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

            int curLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            Intent notify = new Intent(context, MainActivity.class);
            Notification noti;
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, notify, 0);


            if(curLevel <= 15 && !charger) {
                noti = new Notification.Builder(context).setTicker("Ticker Title")
                        .setContentTitle("Battery Low")
                        .setContentText("Battery " + curLevel + "% Please Connect Charger")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pIntent).getNotification();
                noti.flags = Notification.FLAG_AUTO_CANCEL;
                NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                nm.notify(0, noti);
            }
            else if(curLevel == 100 && charger){
                noti = new Notification.Builder(context).setTicker("Ticker Title")
                        .setContentTitle("Battery Okay")
                        .setContentText("Battery full Please disconnect Charger")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pIntent).getNotification();
                noti.flags = Notification.FLAG_AUTO_CANCEL;
                NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                nm.notify(0, noti);
            }
        }
    };
}



