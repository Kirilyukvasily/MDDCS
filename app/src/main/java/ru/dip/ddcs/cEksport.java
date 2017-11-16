package ru.dip.ddcs;

/**
 * Created by В on 19.03.2016.
 */

import android.Manifest;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


public class cEksport {
    boolean[] m_selectRows;
    String m_katalog;
    String m_nameSerii;
    String m_nameZona;
    String m_nameDisloc;
    Double vrem;
    Double[] kinet = new Double[0];
    Double rad;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    final String LOG_TAG = "myLogs";

    public cEksport(String katalog, boolean[] eksportList)
    {
        m_selectRows = eksportList;
        if (!CreateDirectory(katalog))
        {
            return;
        }
        m_katalog = katalog;
    }

    public cEksport(cSeriiBase pSerii, String pKatalog, boolean[] eksportList)
    {
        m_selectRows = eksportList;
        if (!CreateDirectory(pKatalog))
        {
            return;
        }
        m_katalog = pKatalog;
        CreateDirectory(pSerii);
    }
    private cEksport() { }

    private boolean CreateDirectory(String name)
    {
        File file = new File(Environment.getExternalStorageDirectory()+File.separator + name);
        file.mkdirs();
       /* if (!file.exists() && file.isDirectory())
       {
            try
            {
                CreateDirectory(name);
            }
            catch (Exception error)
            {
                MessageBox.Show(error.Message, "Экспорт данных прерван");
                return false;
            }
        }*/
        return true;
    }

    public void CreateDirectory(cSerii pSerii)
    {
        m_nameSerii = "Серия_" + pSerii.GetZagolovoc() + "/";
        if (!CreateDirectory(m_katalog + m_nameSerii))
        {
            return;
        }
    }
    public void CreateDirectory(cSeriiBase pSerii)
    {
        m_nameSerii = "Серия_" + pSerii.GetZagolovoc() + "/";
        if (!CreateDirectory(m_katalog + m_nameSerii))
        {
            return;
        }
    }

    public void CreateDirectory(cZona pZona)
    {
        m_nameZona = "Зона_" + String.valueOf( pZona.GetId()) + "_" + pZona.GetFullZagolovoc() + "/";
        if (!CreateDirectory(m_katalog + m_nameSerii + m_nameZona))
        {
            return;
        }
    }

    public void CreateDirectory(int numberZona, cParametersZona pParametersZona)
    {
        String m_zagolovoc = "";
        if (pParametersZona.GetMetallBool())
        {
            m_zagolovoc = pParametersZona.GetMetall();
        }
        if (pParametersZona.GetNumberBool())
        {
            if (m_zagolovoc != "")
            {
                m_zagolovoc += "_";
            }
            m_zagolovoc += "№" +String.valueOf( pParametersZona.GetN());
        }
        if (pParametersZona.GetTBool())
        {
            if (m_zagolovoc != "")
            {
                m_zagolovoc += "_";
            }
            m_zagolovoc += "T=" + String.valueOf(pParametersZona.GetT());
        }
        if (pParametersZona.GetPlotnostBool())
        {
            if (m_zagolovoc != "")
            {
                m_zagolovoc += "_";
            }
            m_zagolovoc += "Ro=" +String.valueOf(pParametersZona.GetRo());
        }
        if (pParametersZona.GetTextBool())
        {
            if (m_zagolovoc != "")
            {
                m_zagolovoc += "_";
            }
            m_zagolovoc += pParametersZona.GetText();
        }
        m_nameZona = "Зона_" +String.valueOf(  numberZona) + "_" + m_zagolovoc + "/";
        if (!CreateDirectory(m_katalog + m_nameSerii + m_nameZona))
        {
            return;
        }
    }

    public void CreateDirectory(int numberZona, cParametersSerii pParametersSerii)
    {
        String zagolovoc = "";
        if (pParametersSerii.GetMetallBool())
        {
            zagolovoc = pParametersSerii.GetMetall();
        }
        if (pParametersSerii.GetNBool())
        {
            if (zagolovoc != "")
            {
                zagolovoc += "_";
            }
            zagolovoc += "ZonesCount=" +String.valueOf(pParametersSerii.GetN());
        }
        if (pParametersSerii.GetGrBool())
        {
            if (zagolovoc != "")
            {
                zagolovoc += "_";
            }
            zagolovoc += "Gr=" + String.valueOf( pParametersSerii.GetLeftGran()) + "-" +String.valueOf(pParametersSerii.GetRightGran());
        }
        if (pParametersSerii.GetStepBool())
        {
            if (zagolovoc != "")
            {
                zagolovoc += "_";
            }
            zagolovoc += "Step=" + String.valueOf(pParametersSerii.GetStep());
        }
        if (pParametersSerii.GetTextBool())
        {
            if (zagolovoc != "")
            {
                zagolovoc += "_";
            }
            zagolovoc += pParametersSerii.GetText();
        }
        m_nameSerii = "Серия_" + zagolovoc + "/";
        if (!CreateDirectory(m_katalog + m_nameSerii))
        {
            return;
        }
    }

    public void CreateDirectory(cDisloc pDisloc)
    {
        m_nameDisloc = "Disloc" +String.valueOf(  pDisloc.GetId()) + ".txt";
    }

    public void CreateDirectory(int pNumberDisloc)
    {
        m_nameDisloc = "Disloc" + String.valueOf( pNumberDisloc) + ".txt";
    }

    public void PrintToFile(cZonaBase zona) throws IOException {

        zona.Initialize();
        String dir = m_katalog + "Зона" +String.valueOf(
                zona.GetId()) + "_" + zona.GetFullZagolovoc() + "/";
        if (!CreateDirectory(dir))
        {
            return;
        }

        FileWriter f;
        Writer SrZnDisloc;
        double count;
        String rez;
        String str;

        str = dir + "Параметры";
        f = new FileWriter( str + ".txt" );
        rez = "pj  =  " + String.valueOf( zona.GetParameters().GetPj()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "ps  =  " +String.valueOf(zona.GetParameters().GetPs()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "ksi  =  " +String.valueOf( zona.GetParameters().GetKsi()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "nu  =  " + String.valueOf(zona.GetParameters().GetNu()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "alfa  =  " +String.valueOf( zona.GetParameters().GetAlfa()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "t  =  " + String.valueOf(zona.GetParameters().GetT()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "tauf  =  " +String.valueOf( zona.GetParameters().GetTauf()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "ro  =  " +String.valueOf( zona.GetParameters().GetRo()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "n  =  " +String.valueOf( zona.GetParameters().GetN()).replace(',', '.') + "\r\n\r\n";
        f.write(rez);
        rez = "Напряжение  =  " +String.valueOf( zona.GetParameters().GetNapr()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "Начальная кинетическая энергия  =  " + String.valueOf(zona.GetParameters().GetE0()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "Начальный радиус  =  " + String.valueOf(zona.GetParameters().GetR0()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "Левый предел интегрирования  =  " +String.valueOf( zona.GetParameters().GetT0()).replace(',', '.') + "\r\n";
        f.write(rez);
        if( zona.GetParameters().GetTNBool() )
        {
            rez = "Правый предел интегрирования  =  " +String.valueOf( zona.GetParameters().GetTN()).replace(',', '.') + "\r\n";
            f.write(rez);
        }
        if( zona.GetParameters().GetMinStepBool() )
        {
            rez = "Минимальный шаг  =  " +String.valueOf( zona.GetParameters().GetMinStep()).replace(',', '.') + "\r\n";
            f.write(rez);
        }
        if( zona.GetParameters().GetMaxStepBool() )
        {
            rez = "Максимальный шаг  =  " +String.valueOf( zona.GetParameters().GetMaxStep()).replace(',', '.') + "\r\n";
            f.write(rez);
        }
        f.close();

        str = dir + "СредниеЗначенияДислокаций";
        SrZnDisloc = new FileWriter(str + ".txt");
        rez = "Номер дислокации\tВремя\tРадиус\tКинетическая энергия\tСкорость";
        SrZnDisloc.write(rez);

        cDisloc disloc;
        int countDisloc = zona.GetCountDisloc();
        for(int i = 0; i < countDisloc; i++)
        {
            disloc = zona.GetDisloc(i);
            String DislocDir = dir + "Disloc" +String.valueOf( disloc.GetId());
            if( disloc.GetModelId() == 10 )
            {
                int countDisloc2 = disloc.GetCountDisloc();
                String filename;
                for (int j = 0; j < countDisloc2; ++j)
                {
                    filename = DislocDir + "_" + String.valueOf( disloc.GetEngle(j));
                    f = new FileWriter(filename + ".txt");
                    PrintDisloc( f, disloc.GetDisloc(j) );
                }
            }
            else
            {
                PrintDisloc( f, disloc );
            }

            count = disloc.GetSrednZnach().GetCount();
            if( count <= 0 ) count = 1;
            rez = String.valueOf(disloc.GetId()) + "\t";
            SrZnDisloc.write(rez);
            if( m_selectRows[4] ){
                rez = String.valueOf(disloc.GetSrednZnach().GetT()).replace(',', '.') + "\t";
                SrZnDisloc.write(rez);
            }
            if( m_selectRows[5] ){
                rez =String.valueOf( disloc.GetSrednZnach().GetR()).replace(',', '.') + "\t";
                SrZnDisloc.write(rez);
            }
            if( m_selectRows[6] ){
                rez =String.valueOf( (disloc.GetSrednZnach().GetE() / count)).replace(',', '.') + "\t";
                SrZnDisloc.write(rez);
            }
            if( m_selectRows[7] ){
                rez = String.valueOf((disloc.GetSrednZnach().GetV() / count)).replace(',', '.') + "\t";
                SrZnDisloc.write(rez);
            }
            rez =String.valueOf( (disloc.GetSrednZnach().GetEmax())).replace(',', '.') + "\r\n";
            SrZnDisloc.write(rez);
        }
        SrZnDisloc.close();
    }

    public void PrintToFile(cSeriiBase serii) throws IOException {
        String FileName = m_katalog + "СредниеЗначенияЗонСдвига";

        double count;
        String rez;
        int countZona = serii.GetCountZona();
        FileWriter f = new FileWriter( FileName + ".txt" );
        rez = "НомерЗоны\tВремя\tРадиус\tКинетическаяЭнергия\tСкорость\tМаксКинетЭнергия\r\n";
        f.write(rez);
        cZonaBase zona;
        for( int i = 0; i < countZona; i++ )
        {
            zona = serii.GetZona(i);
            zona.Initialize();
            count = zona.GetSrednZnach().GetCount();
            if( count <= 0 ) count = 1;
            rez = String.valueOf(zona.GetId()) + "\t";
            f.write(rez);
            if( m_selectRows[4] ){
                rez =String.valueOf( zona.GetSrednZnach().GetT()).replace(',', '.') + "\t";
                f.write(rez);
            }
            if( m_selectRows[5] ){
                rez =String.valueOf( zona.GetSrednZnach().GetR()).replace(',', '.') + "\t";
                f.write(rez);
            }
            if( m_selectRows[6] ){
                rez =String.valueOf( (zona.GetSrednZnach().GetE() / count)).replace(',', '.') + "\t";
                f.write(rez);
            }
            if( m_selectRows[7] ){
                rez = String.valueOf((zona.GetSrednZnach().GetV() / count)).replace(',', '.') + "\t";
                f.write(rez);
            }
            rez =String.valueOf( (zona.GetSrednZnach().GetEmax())).replace(',', '.') + "\r\n";
            f.write(rez);
        }
        f.close();
    }

    private void PrintDisloc(FileWriter file, cDisloc disloc) throws IOException {
        String rez = "Время\tРадиус\tКинетическаяЭнергия\tСкорость\n";
        file.write(rez);
        cDislocData dislocData;
        int countDislocData = disloc.GetCountDislocData();
        for (int j = 0; j < countDislocData; j++)
        {
            dislocData = disloc.GetDislocData(j);
            if ( m_selectRows[0] )
            {
                rez = String.valueOf(dislocData.GetT()).replace(',', '.') + "\t";
                file.write(rez);
            }
            if ( m_selectRows[1] )
            {
                rez =String.valueOf( dislocData.GetR()).replace(',', '.') + "\t";
                file.write(rez);
            }
            if ( m_selectRows[2] )
            {
                rez = String.valueOf(dislocData.GetE()).replace(',', '.') + "\t";
                file.write(rez);
            }
            if ( m_selectRows[3] )
            {
                rez =String.valueOf( dislocData.GetV()).replace(',', '.') + "\t";
                file.write(rez);
            }
            rez = "\r\n";
            file.write(rez);
        }
        file.close();
    }

     void PrintZonaParameters(cParametersZona pParametersZona) throws IOException {
        // ----- Откроем файл


         if (!Environment.getExternalStorageState().equals(
                 Environment.MEDIA_MOUNTED)) {
             Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
             return;
         }

         // получаем путь к SD
         File sdPath ;
         // добавляем свой каталог к пути
         if(m_nameSerii!=null)
         sdPath = new File(m_katalog + m_nameSerii + m_nameZona);
         else
             sdPath = new File(m_katalog +"/"+ m_nameZona);
         // создаем каталог
         sdPath.mkdirs();
         // формируем объект File, который содержит путь к файлу
         File sdFile = new File(sdPath, "Параметры.txt");
         try {
             // открываем поток для записи
            BufferedWriter f = new BufferedWriter(new FileWriter(sdFile));
             // пишем данныe
        // ----- Запишем информацию о значениях параметров
        String rez;
        rez = "pj  =  " + String.valueOf(pParametersZona.GetPj()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "ps  =  " + String.valueOf(pParametersZona.GetPs()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "ksi  =  " +String.valueOf( pParametersZona.GetKsi()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "nu  =  " +String.valueOf( pParametersZona.GetNu()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "alfa  =  " +String.valueOf( pParametersZona.GetAlfa()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "t  =  " + String.valueOf(pParametersZona.GetT()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "tauf  =  " +String.valueOf( pParametersZona.GetTauf()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "ro  =  " + String.valueOf(pParametersZona.GetRo()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "n  =  " + String.valueOf(pParametersZona.GetN()).replace(',', '.') + "\r\n\r\n";
        f.write(rez);
        rez = "Напряжение  =  " +String.valueOf( pParametersZona.GetNapr()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "Начальная кинетическая энергия  =  " +String.valueOf( pParametersZona.GetE0()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "Начальный радиус  =  " +String.valueOf( pParametersZona.GetR0()).replace(',', '.') + "\r\n";
        f.write(rez);
        rez = "Левый предел интегрирования  =  " +String.valueOf( pParametersZona.GetT0()).replace(',', '.') + "\r\n";
        f.write(rez);
        if (pParametersZona.GetTNBool())
        {
            rez = "Правый предел интегрирования  =  " +String.valueOf( pParametersZona.GetTN()).replace(',', '.') + "\r\n";
            f.write(rez);
        }
        if (pParametersZona.GetMinStepBool())
        {
            rez = "Минимальный шаг  =  " +String.valueOf( pParametersZona.GetMinStep()).replace(',', '.') + "\r\n";
            f.write(rez);
        }
        if (pParametersZona.GetMaxStepBool())
        {
            rez = "Максимальный шаг  =  " +String.valueOf( pParametersZona.GetMaxStep()).replace(',', '.') + "\r\n";
            f.write(rez);
        }
        f.close();
         Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
     } catch (IOException e) {
        e.printStackTrace();
    }
    }



    public void PrintZonaSrednZnach(cZona zona) throws IOException {
        PrintZonaSrednZnach(zona.GetId(),zona.GetSrednZnach());
    }

    public void PrintZonaSrednZnach(int pZonaId, cSrednZnach pZonaSrednZnach) throws IOException {
        // ----- Откроем файл на дописывание
        FileWriter f = new FileWriter(m_katalog + m_nameSerii + "СредниеЗначенияЗонСдвига.txt", true);
        // ----- Запишем информацию о средних значениях данной зоны сдвига
        // !!!!! Проверить  нужно ли здесь деление на count
        int count = pZonaSrednZnach.GetCount();
        if (count <= 0)
            count = 1;
        String rez = String.valueOf(pZonaId) + "\t";
        f.write(rez);
        if (m_selectRows[4])
        {
            rez = String.valueOf(pZonaSrednZnach.GetT()).replace(',', '.') + "\t";
            f.write(rez);
        }
        if (m_selectRows[5])
        {
            rez =String.valueOf( pZonaSrednZnach.GetR()).replace(',', '.') + "\t";
            f.write(rez);
        }
        if (m_selectRows[7])
        {
            rez =String.valueOf( pZonaSrednZnach.GetV()).replace(',', '.') + "\t";
            f.write(rez);
        }
        if (m_selectRows[6])
        {
            rez =String.valueOf( pZonaSrednZnach.GetE()).replace(',', '.') + "\t";
            f.write(rez);
        }
        rez = String.valueOf((pZonaSrednZnach.GetEmax())).replace(',', '.') + "\r\n";
        f.write(rez);

        f.close();
    }

    public void PrintDislocSrednZnach(cDisloc disloc) throws IOException {
        PrintDislocSrednZnach(disloc.GetId(), disloc.GetSrednZnach());
    }

    public void PrintDislocSrednZnach(int pDislocId,cSrednZnach pDislocSrednZnach) throws IOException {
        // ----- Откроем файл на дописывание
        FileWriter f = new FileWriter(m_katalog + "/" + m_nameZona+ "/" +  "СредниеЗначенияДислокаций.txt", true);
        // ----- Запишем информацию о средних значениях текущей дислокации
        f.write(String.valueOf((pDislocId) + "\t"));
        f.write(String.valueOf(pDislocSrednZnach.GetT()) + "\t");
        f.write(String.valueOf(pDislocSrednZnach.GetR()) + "\t");
        f.write(String.valueOf(pDislocSrednZnach.GetV()) + "\t");
        f.write(String.valueOf(pDislocSrednZnach.GetE()) + "\t");
        f.write(String.valueOf(pDislocSrednZnach.GetEmax()) + "\r\n");
        f.close();
    }

    public void PrintDislocData(cDislocData dislocData) throws IOException {
        // ----- Откроем файл на дописывание
        FileWriter file = new FileWriter(m_katalog + m_nameSerii + m_nameZona + m_nameDisloc,true);
        // ----- Запишем информацию текущих значениях дислокации
        PrintDislocData(file, dislocData);
    }

    public void PrintDislocData(cDisloc pDisloc, cDislocData dislocData) throws IOException {
        // ----- Откроем файл на дописывание
        String nameDisloc = "Disloc" +String.valueOf( pDisloc.GetId()) + ".txt";
        FileWriter file = new FileWriter(m_katalog + m_nameSerii + m_nameZona + nameDisloc, true);
        // ----- Запишем информацию текущих значениях дислокации
        PrintDislocData(file, dislocData);
    }

    public void PrintDislocData(cDislocData dislocData, int pId) throws IOException {
        // ----- Откроем файл на дописывание
        String dir = m_katalog + m_nameSerii + m_nameZona + "Disloc" + String.valueOf(pId);

        // ----- Запишем информацию текущих значениях дислокации
        String rez;
        String T = String.valueOf(dislocData.GetT()).replace(',', '.') + "\t";
        int count = dislocData.GetCount() / 3;
        for (int i = 0; i < count; i++)
        {
            rez = "";
            FileWriter file = new FileWriter(dir + "_" + i + ".txt", true);
            if (m_selectRows[0])
            {
                rez += T;// dislocData.GetT().ToString().Replace(',', '.') + "\t";
            }
            if (m_selectRows[1])
            {
                rez +=String.valueOf( dislocData.GetParam(i + 1)).replace(',', '.') + "\t";
            }
            if (m_selectRows[2])
            {
                rez +=String.valueOf( dislocData.GetParam(count + i + 1)).replace(',', '.') + "\t";
            }
            if (m_selectRows[3])
            {
                rez +=String.valueOf( dislocData.GetParam(2 * count + i + 1)).replace(',', '.') + "\t";
            }
            file.write(rez);
            file.close();
        }
    }

    public void PrintDislocData(FileWriter file, cDislocData dislocData) throws IOException {
        // ----- Запишем информацию текущих значениях дислокации
        String rez = "";
        int count = dislocData.GetCount();
        int countStart = 0, countFinish = 0;
        if (m_selectRows[0])
        {
            rez += String.valueOf(dislocData.GetT()).replace(',', '.') + "\t";
        }
        if (m_selectRows[1])
        {
            countStart = 1;
            countFinish = count / 3;
            for (int i = countStart; i <= countFinish; i++)
            {
                rez +=String.valueOf( dislocData.GetParam(i)).replace(',', '.') + "\t";
            }
        }
        if (m_selectRows[2])
        {
            countStart = count / 3 + 1;
            countFinish = 2 * count / 3;
            for (int i = countStart; i <= countFinish; i++)
            {
                rez += String.valueOf(dislocData.GetParam(i)).replace(',', '.') + "\t";
            }
        }
        if (m_selectRows[3])
        {
            countStart = 2 * count / 3 + 1;
            countFinish = count;
            for (int i = countStart; i < countFinish; i++)
            {
                rez +=String.valueOf( dislocData.GetParam(i)).replace(',', '.') + "\t";
            }
        }
        file.write(rez);
        file.close();
    }

    public void PrintDislocData(double time, double Ek, double R, double V) throws IOException {
        // ----- Откроем файл на дописывание
        cDisloc pDisloc=null;
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        File sdPath ;
        // добавляем свой каталог к пути
        if(m_nameSerii!=null)
            sdPath = new File(m_katalog + m_nameSerii + m_nameZona);
        else
            sdPath = new File(m_katalog +"/"+ m_nameZona);
        // создаем каталог
        sdPath.mkdirs();
        //String nameDisloc = "Disloc" +String.valueOf( pDisloc.GetId()) + ".txt";
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, m_nameDisloc);
        try {
            // открываем поток для записи
            BufferedWriter f = new BufferedWriter(new FileWriter(sdFile,true));
            // пишем данные
            // ----- Запишем информацию о значениях параметров
            String rez;
            if (m_selectRows[0]) {
                rez = String.valueOf(time).replace(',', '.') + " "+"\t";
                f.write(rez);
            }
            if (m_selectRows[1]) {
                rez = String.valueOf(R).replace(',', '.') + " "+"\t";
                f.write(rez);
            }
            if (m_selectRows[2]) {
                rez = String.valueOf(Ek).replace(',', '.') + " "+"\t";
                f.write(rez);
            }
            if (m_selectRows[3]) {
                rez = String.valueOf(V).replace(',', '.') + " "+"\t";
                f.write(rez);
            }
            rez = "\r\n";
            f.write(rez);
            rez = "\r\n";
            f.write(rez);
            f.close();
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }


       /* cDisloc pDisloc=null;
       FileWriter file;
        if (pDisloc == null)
        {
            file = new FileWriter(m_katalog + m_nameSerii + m_nameZona + m_nameDisloc, true);
        }
        else
        {
            String nameDisloc = "Disloc" +String.valueOf( pDisloc.GetId()) + ".txt";
            file = new FileWriter(m_katalog + m_nameSerii + m_nameZona + nameDisloc, true);
        }
        // ----- Запишем информацию текущих значениях дислокации
        String rez;
        if (m_selectRows[0])
        {
            rez = String.valueOf( time).replace(',', '.') + "\t";
            file.write(rez);
        }
        if (m_selectRows[1])
        {
            rez =String.valueOf( R).replace(',', '.') + "\t";
            file.write(rez);
        }
        if (m_selectRows[2])
        {
            rez =String.valueOf( Ek).replace(',', '.') + "\t";
            file.write(rez);
        }
        if (m_selectRows[3])
        {
            rez =String.valueOf( V).replace(',', '.') + "\t";
            file.write(rez);
        }
        rez = "\r\n";
        file.write(rez);
        file.close();*/
    }
    public void PrintDislocData(double time, double Ek, double R, cDisloc pDisloc,double V) throws IOException {
        // ----- Откроем файл на дописывание
        FileWriter file;
        if (pDisloc == null)
        {
            file = new FileWriter(m_katalog + m_nameSerii + m_nameZona + m_nameDisloc, true);
        }
        else
        {
            String nameDisloc = "Disloc" +String.valueOf( pDisloc.GetId()) + ".txt";
            file = new FileWriter(m_katalog + m_nameSerii + m_nameZona + nameDisloc, true);
        }
        // ----- Запишем информацию текущих значениях дислокации
        String rez;
        if (m_selectRows[0])
        {
            rez =String.valueOf( time).replace(',', '.') + "\t";
            file.write(rez);
        }
        if (m_selectRows[1])
        {
            rez = String.valueOf(R).replace(',', '.') + "\t";
            file.write(rez);
        }
        if (m_selectRows[2])
        {
            rez =String.valueOf( Ek).replace(',', '.') + "\t";
            file.write(rez);
        }
        if (m_selectRows[3])
        {
            rez = String.valueOf(V).replace(',', '.') + "\t";
            file.write(rez);
        }
        rez = "\r\n";
        file.write(rez);
        file.close();
    }

    public void PrintDislocData(double time, double Ek, double R, double V, int pID) throws IOException {
        // ----- Откроем файл на дописывание
        String nameDisloc = "Disloc" +String.valueOf(pID)+ ".txt";
        FileWriter file = new FileWriter(m_katalog + m_nameSerii + m_nameZona + nameDisloc, true);

        // ----- Запишем информацию текущих значениях дислокации
        String rez = "";
        if (m_selectRows[0])
        {
            rez +=String.valueOf( time) + "\t";
        }
        if (m_selectRows[1])
        {
            rez +=String.valueOf( R) + "\t";
        }
        if (m_selectRows[2])
        {
            rez +=String.valueOf( Ek) + "\t";
        }
        if (m_selectRows[3])
        {
            rez +=String.valueOf( V) + "\t";
        }
        file.write(rez.replace(',', '.'));
        file.close();
    }

    public void PrintDislocData(double[] mas) throws IOException {
        // ----- Откроем файл на дописывание
        FileWriter file = new FileWriter(m_katalog + m_nameSerii + m_nameZona + m_nameDisloc, true);
        // ----- Запишем информацию текущих значениях дислокации
        int count = mas.length;
        String rez;
        for (int i = 0; i < count; ++i )
        {
            rez = String.valueOf(mas[i]).replace(',', '.') + "\t";
            file.write(rez);
        }
        rez = "\r\n";
        file.write(rez);
        file.close();
    }

    public String GetFileName()
    {
        return m_katalog + m_nameSerii + m_nameZona + m_nameDisloc;
    }

    public void FileRename(String OldName, String NewName)
    {
        File file = new File(NewName);

        if (file.exists())
        {
            file.delete();
        }
        file = new File(OldName);
        file.renameTo(new File(NewName));
    }

    public void FileDelete()
    {
        File file = new File(m_katalog + m_nameSerii + m_nameZona + m_nameDisloc);
        file.delete();
    }

    public void FileDeleteAfterTime(double pTimeProbega) throws IOException {
        File file2 = new File(m_katalog + m_nameSerii + m_nameZona + m_nameDisloc);
        // ----- Читаем весь файл
        String[] fileStrings = file2.list(); //File.ReadAllLines(m_katalog + m_nameSerii + m_nameZona + m_nameDisloc);
        // ----- Удаляем файл
        file2.delete();
        int len = fileStrings.length;
        char[] sim = {'\t','\n',' '};
        double time;
        for (int i = len-2; i >= 0; --i)
        {
            String[] str = fileStrings[i].split(String.valueOf(sim));
            time = Double.parseDouble(str[0].replace(".", ","));
            if (time < pTimeProbega)
            {
                // ----- Откроем файл на дописывание
                FileWriter file = new FileWriter(m_katalog + m_nameSerii + m_nameZona + m_nameDisloc);
                // ----- Все строчки до j-ой опять возвращаем в файл
                for (int j = 0; j < i; ++j)
                {
                    file.write(fileStrings[j]);
                }
                // ----- Записываем последнюю строчку
                String[] str_next = fileStrings[i + 1].split(String.valueOf(sim));
                int len_str = str_next.length;
                if (str_next[len_str - 1] == "")
                {
                    len_str--;
                }
                double time_next = Double.parseDouble(str_next[0].replace(".", ","));
                file.write((String.valueOf(pTimeProbega) + "\t").replace(",", "."));
                double per_last, per_next, per;
                for (int j = 1; j < len_str; ++j)
                {
                    per_last = Double.parseDouble(str[j].replace(".", ","));
                    per_next = Double.parseDouble(str_next[j].replace(".", ","));
                    per = (pTimeProbega - time) / (time_next - time) * (per_next - per_last) + per_last;
                    file.write(String.valueOf(per + "\t").replace(",", "."));
                }
                file.close();
                break;
            }
        }
    }

    public double GetMinMaxX() throws IOException {
        // ----- Читаем весь файл
        BufferedReader br = new BufferedReader(new FileReader(m_katalog + m_nameSerii + m_nameZona + m_nameDisloc));
        String s,s2 = null;
        while ((s=br.readLine()) != null)
        {
            if (!s.isEmpty())
                s2=s;
        }
        String[] fileStrings = s2.split("\n");
        // ----- Выбираем из него последнюю строку
        String lastString = fileStrings[fileStrings.length-1];
        // ----- Находим первое слово (число) - время остановки
        int position = lastString.indexOf("\t", 0)+1;
        double res = Double.parseDouble(lastString.substring(0, position - 1).replace(".", ","));
        return res;
    }

    public double GetDiametr( ) throws IOException {
        // ----- Читаем весь файл
        File sdFile = new File(m_katalog + "/" + m_nameZona, "Disloc1"  + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(sdFile));
        String s,s2 = null;
        while ((s=br.readLine()) != null)
        {
            s2=s;
        }
        String[] fileStrings = s2.split("\t");
        /*// ----- Выбираем из него последнюю строку
        String lastString = fileStrings[fileStrings.length - 1];
        // ----- Находим второй слово (число) - радиус зоны сдвига
        int position1 = lastString.indexOf("\t", 0) + 1;
        int position2 = lastString.indexOf("\t", position1) + 1;*/
        //String str = lastString.substring(position1, position2 - position1 - 1);

        String qwer = fileStrings[1].replace('.', ',');
        double res = Double.parseDouble(fileStrings[1].replace('.', ','));
        return res;
    }

    public double GetDiametr(EditText et ) throws IOException {
        // ----- Читаем весь файл
        File sdFile = new File(m_katalog + "/" + m_nameZona, "Disloc1"  + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(sdFile));
        String s,s2 = null;
        while ((s=br.readLine()) != null)
        {
            if (!s.isEmpty())
            s2=s;
        }
        String[] fileStrings = s2.split("\t");
        /*// ----- Выбираем из него последнюю строку
        String lastString = fileStrings[fileStrings.length - 1];
        // ----- Находим второй слово (число) - радиус зоны сдвига
        int position1 = lastString.indexOf("\t", 0) + 1;
        int position2 = lastString.indexOf("\t", position1) + 1;*/
        //String str = lastString.substring(position1, position2 - position1 - 1);
    //et.setText( fileStrings[1] );
        //String qwer = fileStrings[1].replace('.', ',');
        double res = Double.parseDouble(fileStrings[1]);
        return res;
    }

    public double GetParam(int pId, int param, EditText et) throws IOException {//Проверочный метод. все работает. Были ошибки в пути к файлу и в  замене '.' и ','
        // ----- Читаем весь файл

        File sdFile = new File(m_katalog + "/" + m_nameZona, "Disloc1"  + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(sdFile));
        et.setText("");
        String str = "";
        // читаем содержимое
        str = br.readLine();
        et.setText(str);
        str = br.readLine();

        String s=str;
        String s2 = s;
        while ((s=br.readLine()) != null)
        {
            if(!s.isEmpty())
              s2=s;
        }
        //String[] fileStrings = s2.split("\n");
        // ----- Выбираем из него последнюю строку
       //String lastString = fileStrings[fileStrings.length - 1];
        // ----- Находим второй слово (число) - радиус зоны сдвига
        String[] datas = s2.split(" ");
        double res = Double.parseDouble(datas[param]);
        return res;
    }
    public double GetParam(int pId, int param) throws IOException {
        // ----- Читаем весь файл

        File sdFile = new File(m_katalog + "/" + m_nameZona, "Disloc1"  + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(sdFile));

        String str = "";
        // читаем содержимое
        str = br.readLine();

        str = br.readLine();

        String s=str;
        String s2 = s;
        while ((s=br.readLine()) != null)
        {
            if(!s.isEmpty())
                s2=s;
        }
        // ----- Находим второй слово (число) - радиус зоны сдвига
        String[] datas = s2.split(" ");
        double res = Double.parseDouble(datas[param]);
        return res;
    }


    public cSrednZnach SolveSrednZnach() throws IOException {
        cSrednZnach srzn = new cSrednZnach();
        // ----- Читаем весь файл
        BufferedReader br = new BufferedReader(new FileReader(m_katalog + m_nameSerii + m_nameZona + m_nameDisloc));
        String s,s2 = null;
        while ((s=br.readLine()) != null)
        {
            s2=s2+s+"\n";
        }
        String[] fileStrings = s2.split("\n");
        // ----- Определяем количество строк
        int len = fileStrings.length;
        // ----- Подсчитываем среднее значение
        char[] sim = {'\t',' '};
        int count = fileStrings[0].split(String.valueOf(sim)).length;
        String[] str = new String[count];
        double T, E, R, V;
        for (int i = 0; i < len; ++i )
        {
            str = fileStrings[i].split(String.valueOf(sim));
            T = Double.parseDouble(str[0].replace(".", ","));
            R = Double.parseDouble(str[1].replace(".", ","));
            E = Double.parseDouble(str[2].replace(".", ","));
            V = Double.parseDouble(str[3].replace(".", ","));
            srzn.Add(T,E,R,V);
        }
        return srzn;
    }

    public cSrednZnach SolveDislocWithOrientationSrednZnach() throws IOException {
        cSrednZnach srzn = new cSrednZnach();
        // ----- Читаем весь файл
        BufferedReader br = new BufferedReader(new FileReader(m_katalog + m_nameSerii + m_nameZona + "СредниеЗначенияДислокаций.txt"));
        String s,s2 = null;
        while ((s=br.readLine()) != null)
        {
            s2=s2+s+"\n";
        }
        String[] fileStrings = s2.split("\n");
        // ----- Определяем количество строк
        int len = fileStrings.length;
        // ----- Подсчитываем среднее значение
        char[] sim = { '\t', ' ' };
        int count = fileStrings[len-1].split(String.valueOf(sim)).length;
        String[] str = new String[count];
        int num = Integer.parseInt(fileStrings[len - 1].split(String.valueOf(sim))[0]);
        int num_tmp;
        double T, E, R, V, Emax;
        for (int i = len-1; i >= 0; --i)
        {
            str = fileStrings[i].split(String.valueOf(sim));
            num_tmp = Integer.parseInt(str[0]);
            if (num_tmp != num)
            {
                break;
            }
            T = Double.parseDouble(str[1].replace(".", ","));
            R = Double.parseDouble(str[2].replace(".", ","));
            V = Double.parseDouble(str[3].replace(".", ","));
            E = Double.parseDouble(str[4].replace(".", ","));
            Emax = Double.parseDouble(str[5].replace(".", ","));
            cSrednZnach srzn_tmp = new cSrednZnach(T,E,R,V,Emax);
            srzn.Add(srzn_tmp);
        }
        return srzn;
    }
    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDBb", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "U number,"
                    + "t0 number"
                    + "E0 number,"
                    + "r0 number"
                    + "Pj number,"
                    + "Рs number"
                    + "ξ number,"
                    + "V number"
                    + "α number,"
                    + "bÅ number"
                    + "d number,"
                    + "T number"
                    + "τf number,"
                    + "ro number"
                    + "G number,"
                    + "В number"
                    + "t0 number" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}

