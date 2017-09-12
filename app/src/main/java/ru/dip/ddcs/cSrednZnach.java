package ru.dip.ddcs;
import java.lang.*;
/**
 * Created by В on 19.03.2016.
 */
public class cSrednZnach {
    public double m_t;
    public double m_r;
    public double m_e;
    // ----- Необходимо для усреднения скорости зоны сдвига
    public double m_v;
    public int m_count;
    public double m_e_max;
    // ----- Указывает для чего применяется:
    // ----- для дислокации или зоны сдвига (серии)
    private boolean m_notdisloc = false;

    double[] m_mas = null;
    double m_r_sum = 0;

    public cSrednZnach()
    {
        m_t = 0;
        m_e = 0;
        m_r = 0;
        m_v = 0;
        m_count = 0;
        m_e_max = 0;
        m_notdisloc = false;
    }
    public cSrednZnach(boolean notdisloc)
    {
        m_t = 0;
        m_e = 0;
        m_r = 0;
        m_v = 0;
        m_count = 0;
        m_e_max = 0;
        m_notdisloc = notdisloc;
    }


    public void Dispose()
    {

      System.gc();
    }


    public cSrednZnach(double pT, double pE, double pR, double pV)
    {
        m_t = pT;
        m_e = pE;
        m_r = pR;
        m_v = pV;
        m_count = -1;
        m_e_max = pE;
        m_notdisloc = false;
    }
    public cSrednZnach(double pT, double pE, double pR, double pV, boolean notdisloc)
    {
        m_t = pT;
        m_e = pE;
        m_r = pR;
        m_v = pV;
        m_count = -1;
        m_e_max = pE;
        m_notdisloc = notdisloc;
    }

    public cSrednZnach(double pT, double pE, double pR, double pV, double pEmax)
    {
        m_t = pT;
        m_e = pE;
        m_r = pR;
        m_v = pV;
        m_count = -1;
        m_e_max = pEmax;
        m_notdisloc = false;
    }
    public cSrednZnach(double pT, double pE, double pR, double pV, double pEmax, boolean notdisloc)
    {
        m_t = pT;
        m_e = pE;
        m_r = pR;
        m_v = pV;
        m_count = -1;
        m_e_max = pEmax;
        m_notdisloc = notdisloc;
    }

    public cSrednZnach(double[] mas)
    {
        m_mas = mas;/*
       mas.CopyTo(m_mas, 0);*/
        m_count = -1;
        m_notdisloc = false;
    }
    public cSrednZnach(double[] mas, boolean notdisloc )
    {
        m_mas = mas;
       /* mas.copyTo(m_mas, 0);*/
        m_count = -1;
        m_notdisloc = notdisloc;
    }

    public void Add(double pT,double pE,double pR,double pV)
    {
        if( m_count < 0 )
            return;
        if(m_t < pT)
            m_t = pT;
        m_e += pE;
        if(m_r < pR)
            m_r = pR;
        m_v += pV;
        m_count++;
        if( m_e_max < pE )
            m_e_max = pE;
    }

    public void Add(cSrednZnach pSrednZnach)
    {
        if (pSrednZnach != null)
        {
                /*
                int count = pSrednZnach.GetCount();
                double time = pSrednZnach.GetT();
                if( m_t < time )
                    m_t = time;
                double radius = pSrednZnach.GetR();
                if( m_r < radius )
                    m_r = radius;
                m_e += pSrednZnach.GetE();
                m_v += pSrednZnach.GetV();
                m_count += count;

                double e_max = pSrednZnach.GetEmax();
                if( m_e_max < e_max )
                    m_e_max = e_max;
                */
            double per;
            if (m_notdisloc)
            {
                m_t += pSrednZnach.GetT();
                m_r_sum += pSrednZnach.GetR();
            }
            else
            {
                per = pSrednZnach.GetT();
                if (m_t < per)
                    m_t = per;
            }
            per = pSrednZnach.GetR();
            if (m_r < per)
                m_r = per;
            m_e += pSrednZnach.GetE();
            m_v += pSrednZnach.GetV();
            m_count++;
            per = pSrednZnach.GetEmax();
            if (m_e_max < per)
                m_e_max = per;
        }
    }

    public void Add(double[] mas)
    {
        m_count++;
        int count = mas.length;
        m_mas = new double[count];
        for (int i = 0; i < count; ++i )
        {
            m_mas[i] += mas[i];
        }
    }

    public double GetT()
    {

        //Обратить внимание - раньше
        //функция возвращала ссылку
        //double& GetT()!!!
        if( m_count == 0 )
            return m_count;
        if (m_mas != null)
            return m_mas[0];
        return m_t;
    }

    public void SetT(double T)
    {
        m_t = T;
        if (m_mas != null)
            m_mas[0] = m_t;
    }

    public double GetR()
    {
        if ( m_count == 0 )
            return m_count;
        if (m_mas != null)
        {
            int lenght = m_mas.length / 4;
            double sum = 0;
            for (int i = 0; i < lenght; i++ )
            {
                sum += m_mas[lenght + i + 1] / lenght;
            }
            return sum;
        }
        return m_r;
    }

    public double GetE()
    {

        if (m_count <= 0)
        {
            if (m_mas != null)
            {
                int lenght = m_mas.length / 4;
                double sum = 0;
                for (int i = 0; i < lenght; i++)
                {
                    sum += m_mas[i + 1] / lenght;
                }
                return sum;
            }
            return m_e;
        }
        return m_e / m_count;
    }

    public double GetV()
    {
        if (m_mas != null)
        {
            int lenght = m_mas.length / 4;
            double sum = 0;
            for (int i = 0; i < lenght; i++)
            {
                sum += m_mas[2*lenght + i + 1] / lenght;
            }
            return sum;
        }
        return m_e;
        //if (m_notdisloc==true)
        //{
        //    // ----- Средняя скорость зоны сдвига
        //    if (m_r_sum == 0)
        //    {
        //        return m_v;
        //    }
        //    else
        //    {
        //        return m_r_sum / m_t;
        //    }
        //}
        //else
        //{
        //    // ----- Средняя скорость дислокаций
        //    if (m_t == 0)
        //        return 0;
        //    return m_r / m_t;
        //}
    }

    public double GetVsr()
    {
        if (m_mas != null)
        {
            int lenght = m_mas.length / 4;
            double sum = 0;
            for (int i = 0; i < lenght; i++)
            {
                sum += m_mas[3 * lenght + i + 1] / lenght;
            }
            return sum;
        }
        if (m_count < 0)
            m_count = -m_count;
        if (m_count == 0)
            return m_count;
        return m_v / m_count;
    }

    public int GetCount()
    {
        if (m_count < 0)
            return (-m_count);
        return m_count;
    }

    public double GetEmax()
    {
        if (m_mas != null)
        {
            int lenght = m_mas.length / 4;
            double sum = 0;
            for (int i = 0; i < lenght; i++)
            {
                sum += m_mas[3 * lenght + i + 1] / lenght;
            }
            return sum;
        }
        if ( m_count == 0 )
            return m_count;
        return m_e_max;
    }

}

