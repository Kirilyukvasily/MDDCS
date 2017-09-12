package ru.dip.ddcs;

import java.util.ArrayList;

/**
 * Created by В on 20.03.2016.
 */
public class cDislocWithOrientation <strong> extends cDisloc {

    //double m_id;
    //Дислокации, движущиеся по разным направлениям ориентации
    private ArrayList<cDisloc> m_dislocCollection = new ArrayList<cDisloc>();
    private ArrayList<Double> m_engleCollection = new ArrayList<Double>();
    //Среднее значение параметров, расчитанное как
    //сумма всех значений дислокаций для вех ориентаций,
    //деленное на количество всех значений
    //cSrednZnach* m_srednZnach;

    public cDislocWithOrientation(double pId)
    {
        super.m_id = (int)pId;
        m_modelId = 10;
    }
    public cDislocWithOrientation(int pId)
    {
        super.m_id = pId;
        m_modelId = 10;
    }
    //public cDislocWithOrientation(double,int);
    //public cDislocWithOrientation(int,int);
    public void Dispose()
    {
        m_dislocCollection.clear();
        m_engleCollection.clear();
        System.gc(); //GC.SuppressFinalize(this);
    }

    {
        Dispose();
    }
    //void Add(double,double,double,double);
    public void Add(cDisloc disloc,double engle)
    {
        //m_dislocCollection.push_back( disloc );
        //m_engleCollection.push_back( engle );
        m_dislocCollection.add(0, disloc);
        m_engleCollection.add(0,engle);
        //m_srednZnach.Add( disloc.GetSrednZnach() );
    }
    //void SetSrednZnach(double,double,double,double);
    //void SetSrednZnach(cSrednZnach*);
    //inline cSrednZnach* GetSrednZnach(){return m_srednZnach;}
    //inline double GetId(){return m_id;}
    //void Sea();//TestFunction
    //unsigned int GetCountDislocData();
    //cDislocData* GetDislocData(unsigned int pId);
    public int GetCountDisloc()
    {
        return m_dislocCollection.size();
    }
    public double GetMaxX()
    {
        int countDisloc = m_dislocCollection.size();
        double maxX = 0, tmp;
        for( int i = 0; i < countDisloc; ++i ){
            int countData = (m_dislocCollection.get(i)).GetCountDislocData();
            tmp = ((m_dislocCollection.get(i)).GetDislocData(countData-1)).GetParam(0);
            if( maxX < tmp ){
                maxX = tmp;
            }
        }
        return maxX;
    }
    public double GetMinMaxX()
    {
        int countDisloc = m_dislocCollection.size();
        double tmp;
        if( countDisloc == 0 )
        {
            return 0;
        }
        int countData = (m_dislocCollection.get(countDisloc - 1)).GetCountDislocData();
        double min_maxX = ((m_dislocCollection.get(countDisloc - 1)).GetDislocData(countData-1)).GetParam(0);
            /*
            int i = 0;
            for( i = 0; i < countDisloc; i++ )
            {
                if( m_engleCollection[i] > 15  )
                {
                    break;
                }
            }

            int countData = (m_dislocCollection[i]).GetCountDislocData();
            double min_maxX = ((m_dislocCollection[i]).GetDislocData(countData-1)).GetParam(0);
            for( ++i; i < countDisloc; ++i )
            {
                if( m_engleCollection[i] > 15  )
                {
                    countData = (m_dislocCollection[i]).GetCountDislocData();
                    tmp = ((m_dislocCollection[i]).GetDislocData(countData-1)).GetParam(0);
                    if( min_maxX > tmp )
                    {
                        min_maxX = tmp;
                    }
                }
            }
            */
        return min_maxX;
    }
    public double GetTimeOfTheMileage(double pPercent)
    {
        if (pPercent <= 0)
        {
            return m_dislocCollection.get(0).GetDislocData(0).GetParam(0);
        }
        if (pPercent >= 100)
        {
            return GetMaxX();
        }
        int countDisloc = m_dislocCollection.size();
        double time = (m_dislocCollection.get(countDisloc - 1)).GetTimeOfTheMileage(pPercent);
        return time;
    }
    public double GetMaxRadius()
    {
        double max_radius = 0, radius;
        //std::vector<cDisloc*>::iterator x = m_dislocCollection.begin();
        for (cDisloc x : m_dislocCollection)
        //for( ; x != m_dislocCollection.end(); x++ )
        {
            radius = x.GetMaxRadius();
            if( max_radius < radius )
            {
                max_radius = radius;
            }
        }
        return max_radius;
    }

    public cDisloc GetDisloc(int pIndex)
    {
        int countDisloc = m_dislocCollection.size();
        if (pIndex < countDisloc)
        {
            return m_dislocCollection.get(pIndex);
        }
        return null;
    }

    public double GetEngle(int pIndex)
    {
        int countEngle = m_engleCollection.size();
        if (pIndex < countEngle)
        {
            return m_engleCollection.get(pIndex);
        }
        return 0;
    }
    //cSrednZnach* GetSrednZnach();
    // ----- Вычисление среднего значения
    // ----- характеристик по всем ориентациям
    public void SolveSrednZnach(double c, double e0)
    {
        int countDisloc = m_dislocCollection.size();
        double E = 0;
        double Emax = 0;
        double R = 0;
        double R_tmp;
        double Emax_tmp;
        double T = 0;
        double T_tmp;
        for(cDisloc x : m_dislocCollection)
        {
            E += x.GetSrednZnach().GetE() / countDisloc;
            Emax_tmp = x.GetSrednZnach().GetEmax();
            if (Emax < Emax_tmp)
            {
                Emax = Emax_tmp;
            }
            R_tmp = x.GetSrednZnach().GetR();
            if (R < R_tmp)
            {
                R = R_tmp;
            }
            T_tmp = x.GetSrednZnach().GetT();
            if (T < T_tmp)
            {
                T = T_tmp;
            }
        }
        double V = R / T;
        if( m_srednZnach != null )
        {
            m_srednZnach.Dispose();
        }
        m_srednZnach = new cSrednZnach(T,E,R,V,Emax);
    }

}
