package ru.dip.ddcs;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import static android.os.BatteryManager.BATTERY_PLUGGED_AC;
import static android.os.BatteryManager.BATTERY_PLUGGED_USB;
import static ru.dip.ddcs.MainActivity._FileSettingsLowPower;
import static ru.dip.ddcs.MainActivity._LowPower;
import static ru.dip.ddcs.MainActivity._OnlyPower;
import static ru.dip.ddcs.MainActivity._WiFiIsActive;
import static ru.dip.ddcs.MainActivity.mSettings;
import static ru.dip.ddcs.MainActivity._FileSettingsPower;
import static ru.dip.ddcs.MainActivity._FileSettingsWiFi;
import static ru.dip.ddcs.R.id.button9;

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
        LowPower();
    }
    protected void onPause() {
        super.onPause();
        // Запоминаем данные
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(_FileSettingsWiFi, _WiFiIsActive);
        editor.putBoolean(_FileSettingsPower, _OnlyPower);
        editor.putBoolean(_FileSettingsLowPower, _LowPower);
        editor.apply();

    }

    public void WiFi() {
        Switch sw = (Switch) findViewById(R.id.switch2);
        sw.setChecked(_WiFiIsActive);
        // добавляем слушателя
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // в зависимости от значения isChecked выводим нужное сообщение
                _WiFiIsActive = isChecked;
                if (isChecked) {
                    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(true);
                }
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putBoolean(_FileSettingsWiFi, _WiFiIsActive);
                editor.apply();
            }
        });
    }

  public void Power(){
      Switch sw = (Switch) findViewById(R.id.switch1);
      sw.setChecked(_OnlyPower);
      // добавляем слушателя
      sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              // в зависимости от значения isChecked выводим нужное сообщение
              _OnlyPower = isChecked;
              SharedPreferences.Editor editor = mSettings.edit();
              editor.putBoolean(_FileSettingsPower, _OnlyPower);
              editor.apply();
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
                  }else {
                      Button button = (Button) findViewById(R.id.button9);
                      button.setEnabled(false);

                      AlertDialog.Builder builder = new AlertDialog.Builder(ServerSettings.this);
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
              }
          }
      });
  }

    private void LowPower()
    {
        Switch sw = (Switch) findViewById(R.id.switch3);
        sw.setChecked(_LowPower);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // в зависимости от значения isChecked выводим нужное сообщение
                _LowPower = isChecked;
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putBoolean(_FileSettingsLowPower, _LowPower);
                editor.apply();
            }
        });
    }

    public void newScreen(View v) {
        Intent intObj = new Intent(this, Compact.class);
        startActivity(intObj);
    }
}