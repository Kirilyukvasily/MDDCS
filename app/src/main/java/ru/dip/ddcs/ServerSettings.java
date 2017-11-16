package ru.dip.ddcs;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import static android.os.BatteryManager.BATTERY_PLUGGED_AC;
import static android.os.BatteryManager.BATTERY_PLUGGED_USB;

public class ServerSettings extends AppCompatActivity {

    String[] proc = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView TextInfo = (TextView) findViewById(R.id.textView27);
        TextInfo.setText("максимально - " + Runtime.getRuntime().availableProcessors());

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, proc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        WiFi();
        Power();

    }


    public void WiFi() {
        Switch sw = (Switch) findViewById(R.id.switch2);

        // добавляем слушателя
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // в зависимости от значения isChecked выводим нужное сообщение
                if (isChecked) {
                    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(true);
                } else {
                    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(false);
                }
            }
        });
    }

  public void Power(){
      Switch sw = (Switch) findViewById(R.id.switch1);

      // добавляем слушателя
      sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              // в зависимости от значения isChecked выводим нужное сообщение
              if (isChecked) {

                  IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                  Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
                  int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                  boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

                  if (isCharging) {
                      AlertDialog.Builder builder = new AlertDialog.Builder(ServerSettings.this);
                      builder.setTitle("Подключен к зарядке")
                              .setCancelable(false)
                              .setNegativeButton("ОК",
                                      new DialogInterface.OnClickListener() {
                                          public void onClick(DialogInterface dialog, int id) {
                                              dialog.cancel();
                                          }
                                      });

                      AlertDialog alert = builder.create();
                      alert.show();
                  }else {AlertDialog.Builder builder = new AlertDialog.Builder(ServerSettings.this);
                      builder.setTitle("Подключите зарядку")
                              .setCancelable(false)
                              .setNegativeButton("ОК",
                                      new DialogInterface.OnClickListener() {
                                          public void onClick(DialogInterface dialog, int id) {
                                              dialog.cancel();
                                          }
                                      });

                      AlertDialog alert = builder.create();
                      alert.show();}

              } else {
                  AlertDialog.Builder builder = new AlertDialog.Builder(ServerSettings.this);
                  builder.setTitle("Работа в обычном режиме ")
                          .setCancelable(false)
                          .setNegativeButton("ОК",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int id) {
                                          dialog.cancel();
                                      }
                                  });

                  AlertDialog alert = builder.create();
                  alert.show();

              }
          }
      });
  }


}