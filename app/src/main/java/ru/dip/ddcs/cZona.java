package ru.dip.ddcs;

import java.util.ArrayList;

/**
 * Created by В on 19.03.2016.
 */
public class cZona {
    private int m_id;
    private ArrayList<cDisloc> m_dislocCollection = new ArrayList<cDisloc>();
    private cSrednZnach m_srednZnach = new cSrednZnach(true);
    private cParametersZona m_parameters = new cParametersZona();
    //private int m_rash;
    private cZonaBase.State m_state;
    private cStatistic m_statistic = new cStatistic();
    private boolean m_nameRewrite = false;
    private String m_zagolovoc = "";

    public cZona(double pId)
    {
        m_id = (int)pId;
        //m_rash = 1;
    }
    public cZona(int pId)
    {
        m_id = pId;
        //m_rash = 1;
    }
    public cZona(cZona zona)
    {
        // ----- Копирование по значению
        m_id = zona.GetId();
        m_nameRewrite = zona.GetNameRewrite();
        m_zagolovoc = zona.GetFullZagolovoc();
        // ----- Копирование по ссылке
        int countDisloc = zona.GetCountDisloc();
        for (int i = 0; i < countDisloc; ++i )
        {
            m_dislocCollection.add(zona.GetDisloc(i));
        }
        m_srednZnach = zona.GetSrednZnach();
        m_parameters = zona.GetParameters();
        m_state = zona.GetState();
        m_statistic = zona.GetStatistic();
    }

    public void Dispose()
    {
        m_dislocCollection.remove(DeleteDislocCollection());
        m_srednZnach.Dispose();
        m_parameters.Dispose();
        m_statistic.Dispose();
       System.gc(); /* GC.SuppressFinalize(this);*/
    }
    private static boolean DeleteDislocCollection()//cDisloc s)
    {
        return true;
    }

    {
        Dispose();
    }

    public void AddDisloc(cDisloc pDisloc)
    {    int idDisloc = 0;
        boolean Solve = true;
        m_dislocCollection.add(pDisloc);
        if (Solve == true)
        {
            //m_srednZnach.SetT(m_srednZnach.GetT() + pDisloc.GetSrednZnach().GetT());
            m_srednZnach.Add(pDisloc.GetSrednZnach());
        }
    }


    public void SetSrednZnach(double pTime, double pKinEnerg, double pRadius, double pV, boolean notdisloc)
    {
        if( m_srednZnach != null )
            m_srednZnach.Dispose();
        m_srednZnach = new cSrednZnach(pTime, pKinEnerg, pRadius, pV, notdisloc);
    }

    public void SetSrednZnach(cSrednZnach pSrednZnach)
    {
        if( m_srednZnach != null )
            m_srednZnach.Dispose();
        m_srednZnach = pSrednZnach;
    }

    public void SetParameters(cParametersZona pParameters)
    {
        if( m_parameters != null )
            m_parameters.Dispose();
        m_parameters = pParameters;
    }

    public void SetState(cZonaBase.State pState)
    {
        m_state = pState;
    }

    public boolean GetNameRewrite()
    {
        return m_nameRewrite;
    }

    public String GetName()
    {
        return m_zagolovoc;
    }

    public void SetName(String str)
    {
        if (m_zagolovoc != str)
        {
            m_parameters.SetMetallBool(false);
            m_parameters.SetNumberBool(false);
            m_parameters.SetTBool(false);
            m_parameters.SetPlotnostBool(false);
            m_parameters.SetTextBool(true);
            m_parameters.SetText(str);
            m_nameRewrite = true;
            m_zagolovoc = str;
        }
    }

    public cSrednZnach GetSrednZnach()
    {
        return m_srednZnach;
    }

    public cParametersZona GetParameters()
    {
        return m_parameters;
    }

    public int GetId()
    {
        return m_id;
    }

    public cZonaBase.State GetState()
    {
        return m_state;
    }

    public cDisloc GetDisloc(int pDislocId)
    {

        int count = m_dislocCollection.size();
        if( pDislocId < count )
            return m_dislocCollection.get(pDislocId);
        return null;
    }

    public int GetCountDisloc()
    {
        int count = m_dislocCollection.size();
        return count;
    }

    public String GetFullZagolovoc()
    {
        if (m_zagolovoc == "")
        {
            if (m_parameters.GetMetallBool())
            {
                m_zagolovoc = m_parameters.GetMetall();
            }
            if (m_parameters.GetNumberBool())
            {
                if (m_zagolovoc != "")
                {
                    m_zagolovoc += "_";
                }
                m_zagolovoc += "№" +String.valueOf( m_parameters.GetN());
            }
            if (m_parameters.GetTBool())
            {
                if (m_zagolovoc != "")
                {
                    m_zagolovoc += "_";
                }
                m_zagolovoc += "T=" +String.valueOf( m_parameters.GetT());
            }
            if (m_parameters.GetPlotnostBool())
            {
                if (m_zagolovoc != "")
                {
                    m_zagolovoc += "_";
                }
                m_zagolovoc += "Ro=" +String.valueOf( m_parameters.GetRo());
            }
            if (m_parameters.GetTextBool())
            {
                if (m_zagolovoc != "")
                {
                    m_zagolovoc += "_";
                }
                m_zagolovoc += m_parameters.GetText();
            }
        }
        return m_zagolovoc;
    }

    public int GetMaxDislocId()
    {
        int count = m_dislocCollection.size();
        if(count > 0)
            return m_dislocCollection.get(count - 1).GetId();
        else
            return 1;
    }

    public void Delete()
    {
        if( m_state == cZonaBase.State.Solvered)
            m_state = cZonaBase.State.SolveredAndDeleted;
        else
            m_state = cZonaBase.State.Deleted;
    }

    public void SetStatistic(cStatistic statistic)
    {
        m_statistic = statistic;
    }

    public cStatistic GetStatistic()
    {
        return m_statistic;
    }

        /*
        public override List<cDisloc> GetDislocCollection()
        {
            return m_dislocCollection;
        }
        */

    private cZona(){}
    public  void Initialize() { }


}


