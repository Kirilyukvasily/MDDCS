package ru.dip.ddcs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import static ru.dip.ddcs.R.id.activity_server;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    //DBHelper dbHelper;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // создаем объект для создания и управления версиями БД
        //dbHelper = new DBHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void newScreen(View v) {

        Intent intObj = new Intent(this, Main2Activity.class);
        startActivity(intObj);
    }
    public void newScreen2(View v) {
        Intent intObj = new Intent(this, Main22Activity.class);
        startActivity(intObj);
    }
    public void newScreen3 (View v) {
        Intent intObj = new Intent(this, BDActivity.class);
        startActivity(intObj);
    }
    public void newScreen4 (View v) {
        Intent intObj = new Intent(this, Server.class);
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
