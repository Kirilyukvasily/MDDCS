package ru.dip.ddcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by В on 12.03.2016.
 */
public class cPointIsInInterval {


    private int m_countPer;
    private int m_countPoint;
    private double[][] m_point;
    private int[] m_numberPoint;
    private double[] m_interval = new double[2];
    private boolean m_initialize;
    private int m_indexPoint;
    private int m_existsPoint;
    List<double[]> m_mapPoint = new  ArrayList<double[]>();


    private double m_a;
    private double m_b;
    private double m_stepInterval;
    private double m_aLocalInterval;
    private double m_bLocalInterval;
    private int m_param;
    private boolean m_sjatie = false;

    public cPointIsInInterval(int pCountPer)
    {
        m_countPer = pCountPer;
        m_countPoint = 2*pCountPer;
        m_numberPoint = new int[m_countPoint];
        m_point = new double[m_countPoint][];
        for (int i = 0; i < m_countPoint; ++i)
        {
            m_point[i] = new double[m_countPer + 1];
        }
        //m_countPer+1];
        m_indexPoint = m_countPoint;
        m_existsPoint = 0;
        m_initialize = false;
    }
    public boolean Insert(double x, double[] y)
    {
        if( m_param == m_countPer )
        {
            if( !( x >= m_aLocalInterval && x < m_bLocalInterval ) )
            {
                return false;
            }
        }
        else
        {
            if( !( y[m_param] >= m_aLocalInterval && y[m_param] < m_bLocalInterval ) )
            {
                return false;
            }
        }

        if( m_existsPoint != 0 )
        {
            ++m_existsPoint;
            int k, j;
            for(int i = 0; i < m_countPer; ++i)
            {
                if( m_point[i][i] > y[i] ){
                    for(j = 0; j < m_countPer; ++j){
                        m_point[i][j] = y[j];
                    }
                    m_point[i][m_countPer] = x;
                    //m_numberPoint[i] = m_existsPoint;
                }
                k = i + m_countPer;
                if (m_point[k][i] < y[i])
                {
                    for(j = 0; j < m_countPer; ++j){
                        m_point[k][j] = y[j];
                    }
                    m_point[k][m_countPer] = x;
                    //m_numberPoint[k] = m_existsPoint;
                }
            }
        }
        else
        {
            ++m_existsPoint;
            int k, j;
            // ----- Ñîõðàíèÿåì âñòàâëÿåìóþ òî÷êó
            for(int i = 0; i < m_countPer; ++i)
            {
                k = i + m_countPer;
                for(j = 0; j < m_countPer; ++j)
                {
                    m_point[i][j] = y[j];
                    m_point[k][j] = y[j];
                }
                m_point[k][m_countPer] = m_point[i][m_countPer] = x;
                //m_numberPoint[k] = m_numberPoint[i] = m_existsPoint;
            }
        }
        return true;
    }

    public void Open()
    {
        //  ----- Если точек нет, то ничего не делаем
        if (m_existsPoint == 0)
        {
            return;
        }
        if (m_existsPoint != 0)
        {
            m_mapPoint.clear();
        }

        boolean flagSort = false;

        for(int i = 0; i < m_countPoint; ++i)
        {
            //  ----- Находим, Была ли сохранена эта же точка
            boolean flag = false;
            int count = m_mapPoint.size();
            for (int j = 0; j < count; ++j)
            {
                if (m_mapPoint.get(j)[m_countPer] == m_point[i][m_countPer])
                {
                    flag = true;
                    break;
                }
            }
            //  ----- Если такая точка уже сохранена, то её пропускаем
            if (flag)
            {
                continue;
            }
            //  ----- Сохраняем точку
            else
            {
                if ( m_mapPoint.size() > 0 )
                {
                    if (m_point[i][m_countPer] < m_mapPoint.get(m_mapPoint.size() - 1)[m_countPer])
                    {
                        flagSort = true;
                    }
                }
                m_mapPoint.add(m_point[i]);

            }
        }
        m_indexPoint = 0;
        if (flagSort)
        {

            Collections.sort(m_mapPoint, new Comparator<double[]>() {
                @Override
                public int compare(double[] x, double[] y) {
                    if (x == null)
                    {
                        if (y == null)
                        {
                            // If x is null and y is null, they're
                            // equal.
                            return 0;
                        }
                        else
                        {
                            // If x is null and y is not null, y
                            // is greater.
                            return -1;
                        }
                    }
                    else
                    {
                        // If x is not null...
                        //
                        if (y == null)
                        // ...and y is null, x is greater.
                        {
                            return 1;
                        }
                        else
                        {
                            int index = y.length - 1;
                            if (x[index] == y[index])
                            {
                                return 0;
                            }
                            else if (x[index] < y[index])
                            {
                                return -1;
                            }
                            else
                            {
                                return 1;
                            }
                        }
                    }
                }
            });
                    //m_mapPoint.sort(comp);
        }
        //m_mapPointIterator = m_mapPoint.begin();
    }

    public boolean Eof()
    {
        if (m_existsPoint == 0)
        {
            return true;
        }
        return m_mapPoint.size() == m_indexPoint;
    }

    public void Next()
    {
        m_indexPoint++;
    }

    public double[] GetPoint()
    {
        return m_mapPoint.get(m_indexPoint);
    }

    public void SetInterval(double a, double b, double count,int param) //Çàäàòü èíòåðâàë è ðàâíîìåðíî ðàçáèòü åãî íà count ïîäèíòåðâàëîâ
    {
        // ----- Если sjatie = false, то происходит сжатие дислокаций (и наоборот)
        m_sjatie = false;
            m_a = a;
            m_b = b;
            m_stepInterval = (b - a) / count;
            m_aLocalInterval = a;
            m_bLocalInterval = a + m_stepInterval;
        m_initialize = true;
        m_param = param;

    }
    public void SetInterval(double a, double b, double count, int param, boolean sjatie) //Çàäàòü èíòåðâàë è ðàâíîìåðíî ðàçáèòü åãî íà count ïîäèíòåðâàëîâ
    {
        // ----- Если sjatie = false, то происходит сжатие дислокаций (и наоборот)
        m_sjatie = sjatie;
        if (m_sjatie)
        {
            m_a = a;
            m_b = b;
            m_stepInterval = (b - a) / count;
            m_aLocalInterval = b - m_stepInterval;
            m_bLocalInterval = b;
        }
        else
        {
            m_a = a;
            m_b = b;
            m_stepInterval = (b - a) / count;
            m_aLocalInterval = a;
            m_bLocalInterval = a + m_stepInterval;
        }
        m_initialize = true;
        m_param = param;

    }


    public void NextSubInterval()
    {
        if (m_sjatie)
        {
            m_bLocalInterval = m_aLocalInterval;
            m_aLocalInterval -= m_stepInterval;
        }
        else
        {
            m_aLocalInterval = m_bLocalInterval;
            m_bLocalInterval += m_stepInterval;
        }
        m_existsPoint = 0;
    }

    public void NextSubInterval(double x, double[] y)
    {
        if (m_param == m_countPer)
        {
            if (x >= m_bLocalInterval)
            {
                m_aLocalInterval = m_bLocalInterval;
                m_bLocalInterval += m_stepInterval;
                m_existsPoint = 0;
            }
            else
            {
                if (x < m_aLocalInterval)
                {
                    m_bLocalInterval = m_aLocalInterval;
                    m_aLocalInterval -= m_stepInterval;
                    m_existsPoint = 0;
                }
            }
        }
        else
        {
            if (y[m_param] >= m_bLocalInterval)
            {
                m_aLocalInterval = m_bLocalInterval;
                m_bLocalInterval += m_stepInterval;
                m_existsPoint = 0;
            }
            else
            {
                if (y[m_param] < m_aLocalInterval)
                {
                    m_bLocalInterval = m_aLocalInterval;
                    m_aLocalInterval -= m_stepInterval;
                    m_existsPoint = 0;
                }
            }
        }
        //m_aLocalInterval = m_bLocalInterval;
        //m_bLocalInterval += m_stepInterval;
        //m_existsPoint = 0;
    }

    public void FindInterval(double x, double[] y)
    {
        if (m_param == m_countPer)
        {
            if (x < m_aLocalInterval)
            {
                while (x < m_aLocalInterval)
                {
                    m_bLocalInterval = m_aLocalInterval;
                    m_aLocalInterval -= m_stepInterval;
                    m_existsPoint = 0;
                }
            }
            else
            {
                if (x >= m_bLocalInterval)
                {
                    while (x >= m_bLocalInterval)
                    {
                        m_aLocalInterval = m_bLocalInterval;
                        m_bLocalInterval += m_stepInterval;
                        m_existsPoint = 0;
                    }
                }
            }
        }
        else
        {
            if (y[m_param] < m_aLocalInterval)
            {
                while (y[m_param] < m_aLocalInterval)
                {
                    m_bLocalInterval = m_aLocalInterval;
                    m_aLocalInterval -= m_stepInterval;
                    m_existsPoint = 0;
                }
            }
            else
            {
                if (y[m_param] >= m_bLocalInterval)
                {
                    while (y[m_param] >= m_bLocalInterval)
                    {
                        m_aLocalInterval = m_bLocalInterval;
                        m_bLocalInterval += m_stepInterval;
                        m_existsPoint = 0;
                    }
                }
            }
        }
        Insert(x, y);
    }

    public boolean GetFlagInitialize()
    {
        return m_initialize;
    }

    public void Clear()
    {
        m_aLocalInterval = m_a;
        m_bLocalInterval = m_a + m_stepInterval;
        m_existsPoint = 0;
    }

    public void ClearThisInterval()
    {
        m_aLocalInterval = m_a;
        m_bLocalInterval = m_a + m_stepInterval;
        m_existsPoint = 0;
    }
}
