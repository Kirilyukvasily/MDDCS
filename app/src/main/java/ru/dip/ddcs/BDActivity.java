package ru.dip.ddcs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BDActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    DBHelper dbHelper;
    private int id=0;
    // подключаемся к БД

    List<String> str =new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newScreen();
            }
        }
        );

        final GridView g = (GridView) findViewById(R.id.gridView2);
        //ProductAdapter mAdapter = new ProductAdapter(getApplicationContext(), "weawe");
        //g.setAdapter(mAdapter);
        //g.setOnItemSelectedListener(this);
        g.setOnItemClickListener(new OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stub
                newScreen();
            }
        });

        ReadDB();
    }

    @Override
    public void onClick(View v) {
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Parametrs",null,null);
        db.close();
        newScreen1();

    }

    public void ReadDB (){
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery =  "SELECT * FROM Parametrs";
        Cursor c = db.rawQuery(selectQuery, null);

        GridView gvbd = (GridView) findViewById(R.id.gridView2);
        gvbd.setNumColumns(1);
        int i=0;
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int UColIndex = c.getColumnIndex("U");
            int t0ColIndex = c.getColumnIndex("t0");
            int E0ColIndex = c.getColumnIndex("E0");
            int r0ColIndex = c.getColumnIndex("r0");
            int PjColIndex = c.getColumnIndex("Pj");
            int РsColIndex = c.getColumnIndex("Ps");
            int ξColIndex = c.getColumnIndex("eps");
            int VColIndex = c.getColumnIndex("V");
            int αColIndex = c.getColumnIndex("alfa");
            int bÅColIndex = c.getColumnIndex("bA");
            int dColIndex = c.getColumnIndex("d");
            int TColIndex = c.getColumnIndex("T");
            int τfColIndex = c.getColumnIndex("rf");
            int roColIndex = c.getColumnIndex("ro");
            int GColIndex = c.getColumnIndex("G");
            int ВColIndex = c.getColumnIndex("B");
            int koldisColIndex = c.getColumnIndex("koldis");

            do {

                str.add(
                        "ID = " + c.getInt(idColIndex) +
                                ", U = " + c.getString(UColIndex) +
                                ", t = " + c.getString(t0ColIndex)+
                                ", E0 = "+ c.getString(E0ColIndex) +
                                ", r0 = " + c.getString(r0ColIndex)+
                                ", Pj = " + c.getString(PjColIndex) +
                                ", Ps = " + c.getString(РsColIndex)+
                                ",eps = " + c.getString(ξColIndex) +
                                ", V = " + c.getString(VColIndex)+
                                ", alfa = " + c.getString(αColIndex) +
                                ", bA = " + c.getString(bÅColIndex)+
                                ", d = "+ c.getString(dColIndex) +
                                ", T = " + c.getString(TColIndex)+
                                ", rf = " + c.getString(τfColIndex) +
                                ", ro = " + c.getString(roColIndex)+
                                ", G = "+ c.getString(GColIndex) +
                                ", B = " + c.getString(ВColIndex) +
                                ", koldis =" + c.getString(koldisColIndex));
                gvbd.setAdapter(new ProductAdapter(this, str));

            } while (c.moveToNext());
            c.close();
            dbHelper.close();
        } else
        {
            c.close();
            dbHelper.close();
            newScreen();
        }
        //newScreen();
            //gvbd.setAdapter(new ProductAdapter(this, str));
    }
    public void newScreen() {
        Intent intObj = new Intent(this, Main2Activity.class);
        startActivity(intObj);
    }
    public void newScreen1() {
        Intent intObj = new Intent(this, BDActivity.class);
        startActivity(intObj);
    }



    class DBHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;

        public DBHelper(Context context ) {
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

    public class ProductAdapter extends BaseAdapter {

        Context context ;
        List<String> str;

        public ProductAdapter(Context context, List<String> str) {
            this.context = context;
            this.str = str;
        }
        public ProductAdapter(Context context, String str) {
            this.context = context;
            this.str.add(str);
        }


        @Override
        public int getCount() {
            return str.size();
        }

        @Override
        public String getItem(int position) {
            return str.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView label = (TextView) convertView;

            if (convertView == null) {
                convertView = new TextView(context);
                label = (TextView) convertView;
            }
            label.setText(str.get(position));
            return (convertView);
        }
    }

}
