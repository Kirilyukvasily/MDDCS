package ru.dip.ddcs;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import static android.os.BatteryManager.BATTERY_PLUGGED_AC;
import static android.os.BatteryManager.BATTERY_PLUGGED_USB;
import static ru.dip.ddcs.MainActivity._LowPower;
import static ru.dip.ddcs.MainActivity._OnlyPower;
import static ru.dip.ddcs.MainActivity._WiFiIsActive;

import java.io.File;
import java.io.IOException;

public class Compact extends AppCompatActivity  {

    cDataBaseProxyBuilder m_dataBaseBuilder = null;
    private PendingIntent contentIntent;
    private static final int NOTIFY_ID = 101;

    boolean isCharging;

    boolean isWifi;

    Button t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compact);

        t = (Button) findViewById(R.id.button9);

        t.setClickable(true);
    }

    public void newScreen5(View v) {
        Intent intObj = new Intent(this, ServerSettings.class);
        startActivity(intObj);
    }
    public void onMyClick(View view) throws IOException {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
        int status =  batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);;
        isCharging= status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

        boolean lowStatus =  status == BatteryManager.BATTERY_STATUS_DISCHARGING && status != BatteryManager.BATTERY_STATUS_CHARGING;

        ConnectivityManager conMan = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        isWifi = netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI;


        if (_OnlyPower && !isCharging) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Compact.this);
            builder.setTitle("Подключите зарядку")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();
            return;
        }
        if (_WiFiIsActive && !isWifi) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Compact.this);
            builder.setTitle("Подключите WiFi")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();
            return;
        }
        if (!_LowPower && lowStatus) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Compact.this);
            builder.setTitle("Низкий уровень заряда батареи")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();
            return;
        }
            Run();
    }



    private void Run() throws IOException {

        int maxZonaId = 2;
        int maxSeriiId = 5;

        double U = 15.889;
        double t0 = 0;
        double E0 = 1E-200;//Double.parseDouble(((EditText) findViewById(R.id.editText3)).getText().toString());
        double r0 = 0.000001889;
        double Pj = 0.5;
        double Рs = 0.33333333333;
        double ξ = 0.5;
        double v = 0.35;
        double α = 0.5;
        double bÅ = 2.56;
        double d = 8934.82295482295;
        double T = 293;
        double τf = 1;
        double ro = 1000000000000.0;
        double G = 55657777777.7778;
        double В = 0.000021;
       cListNumberDislocation numbersDislocations = new cListNumberDislocation("1,2,3") ;
        double koldis = numbersDislocations.Size();
        // double kolraz = Double.parseDouble(((EditText) findViewById(R.id.editText21)).getText().toString());

       // Add(U, t0, E0, r0, Pj, Рs, ξ, v, α, bÅ, d, T, τf, ro, G, В, koldis);


        boolean[] exportlist = {true, true, true, true, true, true, true, true, true, true, true, true};
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        String Path=(sdPath.getAbsolutePath() + "/" + "DDCSData");
        cSave save = new cSave(m_dataBaseBuilder, Path, exportlist, null);

        cParametersZona parametersZona = new cParametersZona();
        parametersZona.SetNapr(U);
        parametersZona.SetT0(t0);
        parametersZona.SetE0(E0);
        parametersZona.SetR0(r0);
        parametersZona.SetPj(Pj);
        parametersZona.SetPs(Рs);
        parametersZona.SetKsi(ξ);
        parametersZona.SetNu(v);
        parametersZona.SetAlfa(α);
        parametersZona.SetT(T);
        parametersZona.SetTauf(τf);
        parametersZona.SetRo(ro);

        save.CreateZona(1,parametersZona);
        GIRwithcDislocProblem method = new GIRwithcDislocProblem(t0, E0, U, r0, Pj, Рs, ξ, v, α, bÅ, d, T, τf, ro, G,
                В, koldis, save, 1, 1, 50, 0.009, (EditText) findViewById(R.id.editText3));

        method.SetTN(0.009);
        method.SetTOL(0.00000000001);
        // !!!!! Скопировать в функцию, вызывающую данную. Учесть все зоны, если считается серия зон сдвига.

        //m_progressBar.ProgressBar.Invoke((MethodInvoker)delegate { m_progressBar.Maximum = numbersDislocations.Size() + 1; });
        //m_progressBar.ProgressBar.Invoke((MethodInvoker)delegate { m_progressBar.Value = 1; });
        //Application.DoEvents();

        try {
        SolveThread solveThread = new SolveThread(numbersDislocations,save,method,parametersZona);
        //SolveThread solveThread = new SolveThread();
            EditText t = (EditText) findViewById(R.id.editText20);
        solveThread.SetListNumberDislocation(numbersDislocations);
        solveThread.SetSave(m_dataBaseBuilder, Path, exportlist, null);
            solveThread.start();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        //   method.Solve();
    }

    class SolveThread extends Thread implements Runnable {

        cListNumberDislocation numbersDislocations;
        cSave save;
        GIRwithcDislocProblem method;
        cParametersZona parametersZona;
        SolveThread(){
            int t =0;
        }

        SolveThread(cListNumberDislocation numbersDislocations, cSave save, GIRwithcDislocProblem method
                ,cParametersZona tParametersZona){
            this.numbersDislocations = numbersDislocations;
            this.save = save;
            this.method = method;
            parametersZona = tParametersZona;
        }

        void SetListNumberDislocation(String pString)
        {
            numbersDislocations = new cListNumberDislocation(pString) ;
        }
        void SetListNumberDislocation(cListNumberDislocation tNumbersDislocations)
        {
            numbersDislocations = tNumbersDislocations ;
        }


        void SetSave(cDataBaseProxyBuilder pDataBaseBuilder, String pKatalogExporta,
                     boolean[] pExportList, cDislocObserver pDislocObserver) throws IOException {
            boolean[] exportlist = {true, true, true, true, true, true, true, true, true, true, true, true};
            // получаем путь к SD
            File sdPath = Environment.getExternalStorageDirectory();
            // добавляем свой каталог к пути
            String Path=(sdPath.getAbsolutePath() + "/" + "DDCSData");
            //save = new cSave(m_dataBaseBuilder, Path, exportlist, null);
            cSave tSave = new cSave(m_dataBaseBuilder, Path, exportlist, null);

            cParametersZona parametersZona = this.parametersZona;

            this.save.CreateZona(1,this.parametersZona);
        }

        @Override
        public void run(){

            try{

                for (numbersDislocations.Open(); !numbersDislocations.Eof; numbersDislocations.Next())
                {
                    // ----- Создание и сохранение дислокации
                    save.CreateDisloc(0, numbersDislocations.GetId()); //index??? countAngle???
                    // ----- Ñîçäàåì ñòàòèñòèêó
                        /*if (m_stat != null)
                            m_stat.CreateNewStatistics();*/
                    if (method.Solve())
                    {
                        save.AddDislocForZona();
                        //m_progressBar.ProgressBar.Invoke((MethodInvoker)delegate { m_progressBar.Value++; });
                    }
                    else
                    {
                        //Ïîñëåäóþùèå äèñëîêàöèè íå ñ÷èòàåì, ò.ê. åñëè äàííàÿ
                        //äèñëîêàöèÿ íå ãåíåðèðóåòñÿ, òî è ïîñëåäóþùèå òîæå íå áóäóò
                        for (; !numbersDislocations.Eof; numbersDislocations.Next())
                        {
                            // m_progressBar.ProgressBar.Invoke((MethodInvoker)delegate { m_progressBar.Value++; });
                        }
                        break;
                    }
                      /*  if (m_stat != null)
                        {
                            m_stat.ShowStatisticWindow();
                            m_stat.ClearStatisticWindow();
                        }*/
                }
                Push();


                        /*AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                        builder.setTitle("Расчет завершен")
                                .setCancelable(false)
                                .setNegativeButton("ОК",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                        AlertDialog alert = builder.create();
                        alert.show();*/

            }
            catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void Push(){
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.abc_btn_check_material)
                .setContentTitle("MDDCS")
                .setContentText("Расчет закончен!"); // Текст уведомления

        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        }
        notification.defaults = Notification.DEFAULT_SOUND |
                Notification.DEFAULT_VIBRATE;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFY_ID, notification);
    }

}
