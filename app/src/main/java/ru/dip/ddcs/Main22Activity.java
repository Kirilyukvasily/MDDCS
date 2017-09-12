package ru.dip.ddcs;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main22Activity extends ActionBarActivity {

    protected static final String LOG_TAG = "my_tag";
    TabHost.TabSpec tabSpec;
    GridView gv;
    ArrayAdapter<String> adapter;
    String[] data = new String[10];
    String[] data1 = new String[10];
    String[] data2 = new String[10];
    String[] data3 = new String[10];

    boolean[] m_selectRows;
    String m_katalog;
    String m_nameSerii;
    String m_nameZona;
    String m_nameDisloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main22);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        // инициализация
        readFileSD();
        tabHost.setup();
        FragmentTransaction ftrans = getFragmentManager().beginTransaction();

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Время");
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Ср.кин. энергия");
        tabSpec.setContent(R.id.tab2);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("Радиус");
        tabSpec.setContent(R.id.tab3);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag4");
        tabSpec.setIndicator("Ср. скорость");
        tabSpec.setContent(R.id.tab4);
        tabHost.addTab(tabSpec);



        // вторая вкладка по умолчанию активна
        tabHost.setCurrentTabByTag("tag1");

        // логгируем переключение вкладок
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.d(LOG_TAG, "tabId = " + tabId);
               zxcvbn(tabId);
            }
        });
        if(tabHost.getCurrentTabTag()=="tag1")
            adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, data);
        else if(tabHost.getCurrentTabTag()=="tag2")
            adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, data1);
        else if(tabHost.getCurrentTabTag()=="tag3")
            adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, data2);
        else if(tabHost.getCurrentTabTag()=="tag4")
            adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, data3);
        gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter(adapter);
        qwerty();

    }

    public  void qwerty()
    {
        gv.setNumColumns(1);
    }

    public void zxcvbn(String tabId)
    {
        if (tabId == "tag1")
            adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, data);
        else if (tabId == "tag2")
            adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, data1);
        else if (tabId == "tag3")
            adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, data2);
        else if (tabId == "tag4")
            adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, data3);
        gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter(adapter);
        qwerty();
    }
    void readFileSD() {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        File sdPath ;
        // добавляем свой каталог к пути
       /* if(m_nameSerii!=null)
            sdPath = new File(m_katalog + m_nameSerii + m_nameZona);
        else*/
        sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        m_katalog =(sdPath.getAbsolutePath() + "/" + "DDCSData");
            sdPath = new File(m_katalog +"/"+ "Зона_1_"+"/");
       // sdPath = new File("/storage/emulated/0/DDCSData/null/");
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "Disloc1.txt");
        try {
            // открываем поток для записи
            BufferedReader f = new BufferedReader(new FileReader(sdFile));
            // пишем данные
            String str = "";
            // читаем содержимое
            String [] qwer;
            int i=0;
            while ((str = f.readLine()) != null)
                i++;
            int j=0;
            int z =0;
            f = new BufferedReader(new FileReader(sdFile));
            while ((str = f.readLine()) != null) {
                if(str.length()>6 && j<(i-2)) {
                    qwer = str.split(" ");
                    data[z] = qwer[0];
                    data1[z] = qwer[2];
                    data2[z] = qwer[1];
                    data3[z] = qwer[3];
                    if(j>(i-23)&& z<9)
                        z++;
                }
                j++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}