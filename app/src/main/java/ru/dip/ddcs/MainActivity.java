package ru.dip.ddcs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {


    final String LOG_TAG = "myLogs";
    //DBHelper dbHelper;
    public static String _FileSettingsName = "ServerSettings";
    public static String _FileSettingsWiFi = "WiFiSettings";
    public static String _FileSettingsPower = "PowerSettings";
    public static String _FileSettingsLowPower = "LowPowerSettings";
    public static boolean _WiFiIsActive = false;
    public static boolean _OnlyPower = false;
    public static boolean _LowPower = false;
    public static  SharedPreferences mSettings;
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // создаем объект для создания и управления версиями БД
        //dbHelper = new DBHelper(this);

        mSettings = getSharedPreferences(_FileSettingsName, Context.MODE_PRIVATE);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }




    @Override
    protected void onPause() {
        super.onPause();
        // Запоминаем данные
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(_FileSettingsWiFi, _WiFiIsActive);
        editor.putBoolean(_FileSettingsPower, _OnlyPower);
        editor.putBoolean(_FileSettingsLowPower, _LowPower);
        editor.apply();
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (mSettings.contains(_FileSettingsWiFi)) {
            // Получаем число из настроек
            _WiFiIsActive = mSettings.getBoolean(_FileSettingsWiFi,false);
            // Выводим на экран данные из настроек
        }
        if (mSettings.contains(_FileSettingsPower)) {
            // Получаем число из настроек
            _OnlyPower = mSettings.getBoolean(_FileSettingsPower,false);
            // Выводим на экран данные из настроек
        }
        if (mSettings.contains(_FileSettingsLowPower)) {
            // Получаем число из настроек
            _LowPower = mSettings.getBoolean(_FileSettingsLowPower,false);
            // Выводим на экран данные из настроек
        }
    }
  /*  public void newScreen(View v) {

        Intent intObj = new Intent(this, Main2Activity.class);
        startActivity(intObj);
        }*/

    public void newScreen2(View v) {
        Intent intObj = new Intent(this, Main22Activity.class);
        startActivity(intObj);
    }
    public void newScreen4 (View v) {
        Intent intObj = new Intent(this, Colculation.class);
        startActivity(intObj);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
