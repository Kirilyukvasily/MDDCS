package ru.dip.ddcs;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import 	android.app.Activity;


import java.io.File;
import java.io.IOException;

import ru.dip.ddcs.fragments.FragmentChar;
import ru.dip.ddcs.fragments.FragmentSeria;
import ru.dip.ddcs.fragments.FragmentZagol;
import ru.dip.ddcs.fragments.Fragmentnach;


public class Main2Activity extends ActionBarActivity {

    protected static final String LOG_TAG = "my_tag";
    TabHost.TabSpec tabSpec;
    Fragmentnach fnach;
    FragmentChar fchar;
    FragmentSeria fseria;
    FragmentZagol fzagol;
    cDataBaseProxyBuilder m_dataBaseBuilder = null;
    DBHelper dbHelper;

    boolean solveGir;
    boolean solvecDislocProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            String out = "Конец расчета";



            @Override
            public void onClick(View view) {
                try{

                    Run();
                }catch (IOException e) {
                    e.printStackTrace();
                }





                /*try {

                    Run();
                } catch (IOException e) {
                    e.printStackTrace();
                    out = "Расчет не выполнен";
                }
                Snackbar.make(view, out, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }


            private void Run() throws IOException {

                int maxZonaId = 2;
                int maxSeriiId = 5;

                double U = Double.parseDouble(((EditText) findViewById(R.id.editText)).getText().toString());
                double t0 = Double.parseDouble(((EditText) findViewById(R.id.editText2)).getText().toString());
                double E0 = 1E-200;//Double.parseDouble(((EditText) findViewById(R.id.editText3)).getText().toString());
                double r0 = Double.parseDouble(((EditText) findViewById(R.id.editText4)).getText().toString());
                double Pj = Double.parseDouble(((EditText) findViewById(R.id.editText9)).getText().toString());
                double Рs = Double.parseDouble(((EditText) findViewById(R.id.editText10)).getText().toString());
                double ξ = Double.parseDouble(((EditText) findViewById(R.id.editText12)).getText().toString());
                double v = Double.parseDouble(((EditText) findViewById(R.id.editText11)).getText().toString());
                double α = Double.parseDouble(((EditText) findViewById(R.id.editText15)).getText().toString());
                double bÅ = Double.parseDouble(((EditText) findViewById(R.id.editText13)).getText().toString());
                double d = Double.parseDouble(((EditText) findViewById(R.id.editText14)).getText().toString());
                double T = Double.parseDouble(((EditText) findViewById(R.id.editText24)).getText().toString());
                double τf = Double.parseDouble(((EditText) findViewById(R.id.editText16)).getText().toString());
                double ro = Double.parseDouble(((EditText) findViewById(R.id.editText17)).getText().toString());
                double G = Double.parseDouble(((EditText) findViewById(R.id.editText18)).getText().toString());
                double В = Double.parseDouble(((EditText) findViewById(R.id.editText19)).getText().toString());
                cListNumberDislocation numbersDislocations = new cListNumberDislocation((((EditText) findViewById(R.id.editText20)).getText().toString())) ;
                double koldis = numbersDislocations.Size();
                // double kolraz = Double.parseDouble(((EditText) findViewById(R.id.editText21)).getText().toString());

                Add(U, t0, E0, r0, Pj, Рs, ξ, v, α, bÅ, d, T, τf, ro, G, В, koldis);


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

                SolveThread solveThread = new SolveThread(numbersDislocations,save,method,parametersZona);
                //SolveThread solveThread = new SolveThread();
                solveThread.SetListNumberDislocation(((EditText) findViewById(R.id.editText20)).getText().toString());
                solveThread.SetSave(m_dataBaseBuilder, Path, exportlist, null);
                try {
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
                    numbersDislocations = new cListNumberDislocation((((EditText) findViewById(R.id.editText20)).getText().toString())) ;
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
                   // Log.d("Вася хороший", "не очень");
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


        });




        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        // инициализация
        tabHost.setup();
        fchar = new FragmentChar();
        fnach = new Fragmentnach();
        fseria = new FragmentSeria();
        fzagol = new FragmentZagol();
        getSupportFragmentManager().beginTransaction().replace(R.id.tab5, fnach).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.tab6, fchar).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.tab7, fseria).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.tab8, fzagol).commit();



        tabSpec = tabHost.newTabSpec("tag5");
        tabSpec.setIndicator("Нач условия");
        tabSpec.setContent(R.id.tab5);
        tabHost.addTab(tabSpec);



        tabSpec = tabHost.newTabSpec("tag6");
        tabSpec.setIndicator("Характе-ристики");
        tabSpec.setContent(R.id.tab6);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag7");
        tabSpec.setIndicator("Серия");
        tabSpec.setContent(R.id.tab7);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag8");
        tabSpec.setIndicator("Заголовок");
        tabSpec.setContent(R.id.tab8);
        tabHost.addTab(tabSpec);



        // вторая вкладка по умолчанию активна
        tabHost.setCurrentTabByTag("tag5");

        // логгируем переключение вкладок
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.d(LOG_TAG, "tabId = " + tabId);

            }
        });

    }



    private void Add(double U, double t0, double E0, double r0, double Pj, double Ps, double eps, double V, double alfa, double bA, double d, double T, double rf, double ro, double G, double B, double koldis)
    {
        dbHelper = new DBHelper(this);
        //SQLiteDatabase db = dbHelper.getReadableDatabase();
       // String selectQuery = "SELECT * FROM Parametrs where U="+U+" t0="+t0+" E0="+E0+" r0="+r0+" Pj="+Pj+" Ps="+Ps+" eps="+eps+" V="+V+" alfa="+alfa+" bA="+bA+" d="+d+" T="+T+" rf="+rf+" ro="+ro+" G="+G+" B="+B+" koldis="+koldis;
        //Cursor cursor = db.rawQuery(selectQuery, null);
        //if (cursor.moveToFirst()) {

            SQLiteDatabase db1 = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("U", String.valueOf(U));
            cv.put("t0", String.valueOf(t0));
            cv.put("E0", String.valueOf(E0));
            cv.put("r0", String.valueOf(r0));
            cv.put("Pj", String.valueOf(Pj));
            cv.put("Ps", String.valueOf(Ps));
            cv.put("eps", String.valueOf(eps));
            cv.put("V", String.valueOf(V));
            cv.put("alfa",String.valueOf( alfa));
            cv.put("bA", String.valueOf(bA));
            cv.put("d", String.valueOf(d));
            cv.put("T", String.valueOf(T));
            cv.put("rf", String.valueOf(rf));
            cv.put("ro", String.valueOf(ro));
            cv.put("G",String.valueOf( G));
            cv.put("B", String.valueOf(B));
            cv.put("koldis", String.valueOf(koldis));
            long rowsID = db1.insert("Parametrs", null, cv);
            db1.close();
       // }
        //db.close();
        dbHelper.close();
    }

    public void GetChek(View view) {

        CheckBox chek = (CheckBox) findViewById(R.id.checkBox);
        RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton);
        RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton rb3 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton rb4 = (RadioButton) findViewById(R.id.radioButton4);
        EditText et1 = (EditText) findViewById(R.id.editText6);
        EditText et2 = (EditText) findViewById(R.id.editText7);
        EditText et3 = (EditText) findViewById(R.id.editText8);
        TextView tv1 = (TextView) findViewById(R.id.textView);
        TextView tv2 = (TextView) findViewById(R.id.textView10);
        TextView tv3 = (TextView) findViewById(R.id.textView12);
        TextView tv4 = (TextView) findViewById(R.id.textView13);
        TextView tv5 = (TextView) findViewById(R.id.textView9);
        RadioButton rb5 = (RadioButton) findViewById(R.id.radioButton5);
        RadioButton rb6 = (RadioButton) findViewById(R.id.radioButton6);
        if (chek.isChecked()) {
            rb1.setEnabled(true);
            rb2.setEnabled(true);
            rb3.setEnabled(true);
            rb4.setEnabled(true);
            et1.setEnabled(true);
            et2.setEnabled(true);
            et3.setEnabled(true);
            tv1.setEnabled(true);
            tv2.setEnabled(true);
            tv3.setEnabled(true);
            tv4.setEnabled(true);
            tv5.setEnabled(true);
            rb5.setEnabled(true);
            rb6.setEnabled(true);
        } else {
            rb1.setEnabled(false);
            rb2.setEnabled(false);
            rb3.setEnabled(false);
            rb4.setEnabled(false);
            et1.setEnabled(false);
            et2.setEnabled(false);
            et3.setEnabled(false);
            tv1.setEnabled(false);
            tv2.setEnabled(false);
            tv3.setEnabled(false);
            tv4.setEnabled(false);
            tv5.setEnabled(false);
            rb5.setEnabled(false);
            rb6.setEnabled(false);
        }
    }

    void Solve() {

    }
    class DBHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;

        public DBHelper(Main2Activity context ) {
            super(context, "DDCSDB", null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("create table Parametrs ("
                    + "id integer primary key autoincrement, "
                    + "U TEXT, "
                    + "t0 TEXT, "
                    + "E0 TEXT, "
                    + "r0 TEXT, "
                    + "Pj TEXT, "
                    + "Ps TEXT, "
                    + "eps TEXT, "
                    + "V TEXT, "
                    + "alfa TEXT,"
                    + "bA TEXT, "
                    + "d TEXT,"
                    + "T TEXT, "
                    + "rf TEXT,"
                    + "ro TEXT, "
                    + "G TEXT,"
                    + "B TEXT, "
                    + "koldis TEXT )" );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + "Parametrs");
            onCreate(db);

        }

    }


}






