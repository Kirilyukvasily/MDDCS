package ru.dip.ddcs;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

/**
 * Created by В on 12.03.2016.
 */
public class cDisloc {
    protected int m_id;
    protected  int m_modelId;
    protected cSrednZnach m_srednZnach = null;
    ArrayList<cDislocData> m_dislocDataCollection = new  ArrayList<cDislocData>();
    // ----- Для тестовых задач задает имена осей
    protected String[] m_axisName;


    protected cDisloc()
    {
        m_srednZnach = new cSrednZnach();
        m_id = 0;
        m_modelId = 0;
    }

    public cDisloc(double pId,int pModelId)
    {
        m_srednZnach = new cSrednZnach();
        m_id = (int)pId;
        m_modelId = pModelId;
    }
    public cDisloc(double pId)
    {
        m_srednZnach = new cSrednZnach();
        m_id = (int)pId;
        m_modelId = 0;
    }

    public cDisloc(int pId,int pModelId)
    {
        m_srednZnach = new cSrednZnach();
        m_id = pId;
        m_modelId = pModelId;
    }
    public cDisloc(int pId)
    {
        m_srednZnach = new cSrednZnach();
        m_id = pId;
        m_modelId = 0;
    }

    public String[] GetAxisName()
    {
            return m_axisName;
    }

    public void SetAxisName(String[] value)
    {
            m_axisName = value;
    }


    public void Add(double pT,double pE,double pR,double pV)
    {
        m_dislocDataCollection.add(new cDislocData(pT, pE, pR, pV));
        m_srednZnach.Add(pT, pE, pR, pV);
    }

    public void Add(double[] mas)
    {
        m_dislocDataCollection.add(new cDislocData(mas));
        m_srednZnach.Add(mas[0], mas[1], mas[2], mas[3]);
    }

    public void Add(cDislocData dislocData)
    {
        m_dislocDataCollection.add(dislocData);
        m_srednZnach.Add(dislocData.GetParam(0), dislocData.GetParam(1), dislocData.GetParam(2), dislocData.GetParam(3));
    }

    public void SolveSrednZnach(double c, double e0)
    {
        int count = m_dislocDataCollection.size() ;
        if( count < 1 ){
            return;
        }
        else if( count == 1 )
        {
            if( m_srednZnach != null )
            {
                m_srednZnach.Dispose();
            }
            m_srednZnach = new cSrednZnach(
                    m_dislocDataCollection.get(0).GetT(),
                    m_dislocDataCollection.get(0).GetE(),
                    m_dislocDataCollection.get(0).GetR(),
                    m_dislocDataCollection.get(0).GetV(),
                    m_dislocDataCollection.get(0).GetE());
            return;
        }

        double R = m_dislocDataCollection.get(0).GetParam(2);
        double Emax = m_dislocDataCollection.get(0).GetParam(1);

        double b_a = m_dislocDataCollection.get(count - 1).GetParam(2) - R;

        double T = m_dislocDataCollection.get(count - 1).GetParam(0) - m_dislocDataCollection.get(0).GetParam(0);
        double S_V = b_a / T;
        double S_E = e0 * ( 1 / Math.sqrt(1 - (S_V * S_V) / (c * c)) - 1 );
        double S_R = m_dislocDataCollection.get(count - 1).GetParam(2) > R ? m_dislocDataCollection.get(count - 1).GetParam(2) : R;

        for(cDislocData x : m_dislocDataCollection)
        {
            if (S_R < x.GetParam(2))
            {
                S_R = x.GetParam(2);
            }
            if (Emax < x.GetParam(1))
            {
                Emax = x.GetParam(1);
            }
        }

        if (m_srednZnach != null )
        {
            m_srednZnach.Dispose();
        }
        m_srednZnach = new cSrednZnach(T,S_E,S_R,S_V,Emax);
    }

    public  void SolveSrednZnach(double c, double[] e0)
    {
        int lenght = e0.length;
        double[] SrZn =  new double[4*lenght+1];

        int count = m_dislocDataCollection.size();
        if (count < 1)
        {
            return;
        }
        else if (count == 1)
        {
            if (m_srednZnach != null)
            {
                m_srednZnach.Dispose();
            }

            int TreeLenghtP1 = 3 * lenght + 1;
            for (int i = 0; i < TreeLenghtP1; i++ )
            {
                SrZn[i] = m_dislocDataCollection.get(0).GetParam(i);
            }
            for (int i = 1; i <= lenght; i++)
            {
                SrZn[TreeLenghtP1+1] = m_dislocDataCollection.get(0).GetParam(i);
            }

            m_srednZnach = new cSrednZnach(SrZn);
            return;
        }

        double T = m_dislocDataCollection.get(count - 1).GetParam(0) - m_dislocDataCollection.get(0).GetParam(0);
        SrZn[0] = T;
        for (int i = 1; i <= lenght; i++)
        {
            int lenghtIp1 = lenght+i;
            double R = m_dislocDataCollection.get(0).GetParam(lenghtIp1);
            double Emax = m_dislocDataCollection.get(0).GetParam(i);
            double b_a = m_dislocDataCollection.get(count - 1).GetParam(lenghtIp1) - R;
            double S_V = b_a / T;
            double S_E = e0[i-1] * (1 / Math.sqrt(1 - (S_V * S_V) / (c * c)) - 1);
            double S_R = m_dislocDataCollection.get(count - 1).GetParam(lenghtIp1) > R ? m_dislocDataCollection.get(count - 1).GetParam(lenghtIp1) : R;

            for (cDislocData x : m_dislocDataCollection)
            {
                if (S_R < x.GetParam(lenghtIp1))
                {
                    S_R = x.GetParam(lenghtIp1);
                }
                if (Emax < x.GetParam(i))
                {
                    Emax = x.GetParam(i);
                }
            }

            SrZn[i] = S_E;
            SrZn[lenghtIp1] = S_R;
            SrZn[2*lenght + i] = S_V;
            SrZn[3 * lenght + i] = Emax;
        }

        if (m_srednZnach != null)
        {
            m_srednZnach.Dispose();
        }
        m_srednZnach = new cSrednZnach(SrZn);
    }

    public void SetSrednZnach(double pTime,double pKinEnerg,double pRadius,double pV)
    {
        if (m_srednZnach != null )
            m_srednZnach.Dispose();
        m_srednZnach = new cSrednZnach(pTime,pKinEnerg,pRadius,pV);
    }

    public void SetSrednZnach(cSrednZnach pSrednZnach)
    {
        if (m_srednZnach != null )
            m_srednZnach.Dispose();
        m_srednZnach = pSrednZnach;
    }

    public void AddSrednZnach(cSrednZnach pSrednZnach)
    {
        m_srednZnach.Add(pSrednZnach);
    }

    public  cSrednZnach GetSrednZnach()
    {
        return m_srednZnach;
    }

    public int GetId()
    {
        return m_id;
    }

    public int GetCountDislocData()
    {
        int count = m_dislocDataCollection.size();
        return count;
    }

    public cDislocData GetDislocData(int pId)
    {
        int count = m_dislocDataCollection.size();
        if( pId < count)
            return m_dislocDataCollection.get(pId);
        else
            return null;
    }
    public int GetModelId()
    {
        return m_modelId;
    }
    public int GetCountDisloc()
    {
        int count = m_dislocDataCollection.size();
        return count;
    }
    public double GetMaxX(){return 0d;}
    public double GetMinMaxX(){return 0d;}
    public cDisloc GetDisloc(int index){return this;}
    public double GetEngle(int index){return 0d;}
    public void Add(cDisloc disloc, double n){return;}
    public void SetAngle(double n) { return; }

    public void ObrezanieAfterTime(double pTime)
    {
        int count = m_dislocDataCollection.size();
        if( pTime >= m_dislocDataCollection.get(count - 1).GetParam(0) )
            return;

        for( int i = count-2; i >= 0; --i )
        {
            double t_last = m_dislocDataCollection.get(i).GetParam(0);
            if( pTime > t_last ) {

                double T_Tlast = pTime - t_last;
                double Tnext_Tlast = m_dislocDataCollection.get(count - 1).GetParam(0) - t_last;

                double e_last = m_dislocDataCollection.get(i).GetParam(1);
                double r_last = m_dislocDataCollection.get(i).GetParam(2);
                double v_last = m_dislocDataCollection.get(i).GetParam(3);

                double e_next = m_dislocDataCollection.get(count - 1).GetParam(1);
                double r_next = m_dislocDataCollection.get(count - 1).GetParam(2);
                double v_next = m_dislocDataCollection.get(count - 1).GetParam(3);

                double E = T_Tlast * (e_next - e_last) / Tnext_Tlast + e_last;
                double R = T_Tlast * (r_next - r_last) / Tnext_Tlast + r_last;
                double V = T_Tlast * (v_next - v_last) / Tnext_Tlast + v_last;

                int count_remove = m_dislocDataCollection.size() - i - 1;
                for (int j = i + 1; j <= i + count_remove; j++)
                    m_dislocDataCollection.remove(j);

                m_dislocDataCollection.add(new cDislocData(pTime, E, R, V));

                return;

            }

        }

    }

    public double GetParam(double pX,int pPar)
    {
        double eps = pX * 1E-10;
        int count = m_dislocDataCollection.size();
        int left_index = 0;
        int right_index = count-1;
        int center_index = ( right_index - left_index ) / 2;
        double left = m_dislocDataCollection.get(left_index).GetParam(0);
        double right = m_dislocDataCollection.get(right_index).GetParam(0);
        double center = m_dislocDataCollection.get(center_index).GetParam(0);
        if( right < pX ){
            return m_dislocDataCollection.get(count - 1).GetParam(pPar);
        }
        if( Math.abs(left - pX) < eps ){
            return m_dislocDataCollection.get(left_index).GetParam(pPar);
        }
        if( Math.abs(right - pX) < eps ){
            return m_dislocDataCollection.get(right_index).GetParam(pPar);
        }
        while(true){
            if( center_index == left_index || center_index == right_index ){
                break;
            }
            if( center < pX ){
                left_index = center_index;
                left = center;
                center_index = ( right_index + left_index ) / 2;
                center = m_dislocDataCollection.get(center_index).GetParam(0);
                continue;
            }
            if( center > pX ){
                right_index = center_index;
                right = center;
                center_index = ( right_index + left_index ) / 2;
                center = m_dislocDataCollection.get(center_index).GetParam(0);
                continue;
            }
            if( Math.abs(center - pX) < eps ){
                return m_dislocDataCollection.get(center_index).GetParam(pPar);
            }
        }
        double y_left = m_dislocDataCollection.get(left_index).GetParam(pPar);
        double y_right = m_dislocDataCollection.get(right_index).GetParam(pPar);
        double res = ( pX - left )*( y_right - y_left )/( right - left ) + y_left;
        return res;
    }

    public double GetParam(double pX, int pPar, int numAngle) { return 0d; }

    public void PrintDataToFile(String filename) throws IOException {
        Writer writer = new FileWriter(filename);

        int count = m_dislocDataCollection.size();
        for( int i = 0; i < count; ++i ){
            writer.write(String.valueOf(m_dislocDataCollection.get(i).GetT()) + "\t");
            writer.write(String.valueOf(m_dislocDataCollection.get(i).GetR()) + "\t");
            writer.write(String.valueOf(m_dislocDataCollection.get(i).GetE()) + "\t");
            writer.write(String.valueOf(m_dislocDataCollection.get(i).GetV()) + "\n");
        }
        writer.close();
    }

    public void Dispose()
    {
        m_dislocDataCollection.remove(DeleteDislocDataCollection());
        System.gc();//GC.SuppressFinalize(this);
    }


    {
        Dispose();
    }

    private static boolean DeleteDislocDataCollection()
    {
        return true;
    }

    public double GetMaxRadius()
    {
        double max_radius = 0, radius;
        for (cDislocData x : m_dislocDataCollection)
        {
            radius = x.GetParam(2);
            if( max_radius < radius )
            {
                max_radius = radius;
            }
        }
        return max_radius;
    }

    public double GetTimeOfTheMileage(double pPercent)
    {
        if( pPercent <= 0 )
        {
            return m_dislocDataCollection.get(0).GetParam(0);
        }
        if( pPercent >= 100 )
        {
            return GetMaxX();
        }

        double radius_tmp = m_dislocDataCollection.get(0).GetParam(2);
        double max_radius = GetMaxRadius() - radius_tmp;
        double radius = max_radius * pPercent / 100.0 + radius_tmp;
        double time = 0;

        double radius_tmp_last = m_dislocDataCollection.get(0).GetParam(2);
        double time_last = m_dislocDataCollection.get(0).GetParam(0);

        for (cDislocData x : m_dislocDataCollection)
        {
            radius_tmp = x.GetParam(2);
            if( radius < radius_tmp )
            {
                if( radius_tmp == radius_tmp_last )
                {
                    return time_last;
                }
                time = (radius-radius_tmp_last) /
                        (radius_tmp-radius_tmp_last) *
                        (x.GetParam(0)-time_last) +
                        time_last;
                return time;
            }
            radius_tmp_last = radius_tmp;
            time_last = x.GetParam(0);
        }
        return time;
    }
}


