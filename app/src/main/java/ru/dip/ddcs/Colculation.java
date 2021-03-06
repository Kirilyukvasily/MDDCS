package ru.dip.ddcs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Colculation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colculation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void newScreen(View v) {

        Intent intObj = new Intent(this, Main2Activity.class);
        startActivity(intObj);
    }

    public void newScreen2(View v) {

       // new Thread(new SolveThread(this)).start();

    Intent intObj = new Intent(this, BDActivity.class);
        startActivity(intObj);
    }


    public void newScreen4(View v) {
        Intent intObj = new Intent(this, Colculation.class);
        startActivity(intObj);
    }

    public void newScreen5(View v) {
        Intent intObj = new Intent(this, ServerSettings.class);
        startActivity(intObj);
    }

    public void newScreen6(View v) {
        Intent intObj = new Intent(this, ConnectionServer.class);
        startActivity(intObj);
    }

    class SolveThread implements Runnable{

        Colculation colculation;

        SolveThread(Colculation colculation){
            this.colculation = colculation;
        }
        public void run(){

            try{
                Intent intObj = new Intent(colculation, Main2Activity.class);
                startActivity(intObj);
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}