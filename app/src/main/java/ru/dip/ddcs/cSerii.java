package ru.dip.ddcs;

import java.util.ArrayList;

/**
 * Created by В on 20.03.2016.
 */
public class cSerii {
    private int m_id;
    private ArrayList<cZona> m_zonaCollection = new ArrayList<>();
    private cSrednZnach m_srednZnach = new cSrednZnach(true);
    private cParametersSerii m_parameters = null;
    private cSeriiBase.State m_state = cSeriiBase.State.Saved;
    private boolean m_nameRewrite = false;
    private String m_zagolovoc = "";

    public cSerii(double pId)
    {
        m_id = ((int)pId);
    }
    public cSerii(int pId)
    {
        m_id = pId;
    }
    public void Dispose()
    {
        m_zonaCollection.remove(DeleteZonaCollection());
        m_srednZnach.Dispose();
        m_parameters.Dispose();
       System.gc(); /* GC.SuppressFinalize(this);*/
    }
    private boolean DeleteZonaCollection()//cZonaBase czb)
    {
        return true;
    }

    {
        Dispose();
    }

    public void AddZona(cZona pZona)
    {
        m_zonaCollection.add(pZona);
        m_srednZnach.Add(pZona.GetSrednZnach());
    }

    public void SetSrednZnach(double pT, double pE, double pN, double pV)
    {
        if( m_srednZnach != null )
            m_srednZnach.Dispose();
        m_srednZnach = new cSrednZnach(pT,pE,pN,pV);
    }

    public void SetSrednZnach(cSrednZnach pSrednZnach)
    {
        if( m_srednZnach != null )
            m_srednZnach.Dispose();
        m_srednZnach = pSrednZnach;
    }

    public void SetParameters(cParametersSerii pParameters)
    {
        if( m_parameters != null )
            m_parameters.Dispose();
        m_parameters = pParameters;
    }

    public void SetState(cSeriiBase.State pState)
    {
        m_state = pState;
    }

    public int GetId()
    {
        return m_id;
    }

    public cSrednZnach GetSrednZnach()
    {
        return m_srednZnach;
    }

    public cParametersSerii GetParameters()
    {
        return m_parameters;
    }

    public int GetMaxZonaId()
    {
        int count = m_zonaCollection.size();
        if(count > 0)
            return m_zonaCollection.get(count - 1).GetId() + 1;
        else
            return 1;
    }

    public int GetCountZona()
    {
        int count = m_zonaCollection.size();
        return count;
    }

    public cSeriiBase.State GetState()
    {
        return m_state;
    }

    public boolean NameRewrite()
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
            m_parameters.SetNBool(false);
            m_parameters.SetTBool(false);
            m_parameters.SetGr(false);
            m_parameters.SetStepBool(false);
            m_parameters.SetTextBool(true);
            m_parameters.SetText(str);
            m_nameRewrite = true;
            m_zagolovoc = str;
        }
    }

    public String GetZagolovoc()
    {
        if (m_zagolovoc == "")
        {
            if (m_parameters.GetMetallBool())
            {
                m_zagolovoc = m_parameters.GetMetall();
            }
            if (m_parameters.GetNBool())
            {
                if (m_zagolovoc != "")
                {
                    m_zagolovoc += "_";
                }
                m_zagolovoc += "ZonesCount=" +String.valueOf( m_parameters.GetN());
            }
                /*
                if( m_parameters->GetTBool() )
                    res += "_T=" + (AnsiString)m_parameters->GetT();
                */
            if (m_parameters.GetGrBool())
            {
                if (m_zagolovoc != "")
                {
                    m_zagolovoc += "_";
                }
                m_zagolovoc += "Gr=" +String.valueOf( m_parameters.GetLeftGran()) + "-" +String.valueOf( m_parameters.GetRightGran());
            }
            if (m_parameters.GetStepBool())
            {
                if (m_zagolovoc != "")
                {
                    m_zagolovoc += "_";
                }
                m_zagolovoc += "Step=" + String.valueOf(m_parameters.GetStep());
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

    public cZona GetZonaById(double pId)
    {
        int i;
        int j = m_zonaCollection.size();
        for( i = 0; i < j; i++ )
            if( pId == m_zonaCollection.get(i).GetId() )
                return m_zonaCollection.get(i);
        return null;
    }

    public cZona GetZona(int pId)
    {
        int count = m_zonaCollection.size();
        if( pId < count )
            return m_zonaCollection.get(pId);
        return null;
    }

    public void Delete()
    {
        if( m_state == cSeriiBase.State.Solvered)
            m_state = cSeriiBase.State.SolveredAndDeleted;
        else
            m_state = cSeriiBase.State.Deleted;
    }

    public void DeleteZona(int pId)
    {
        m_zonaCollection.get(pId).Delete();
    }
    //bool ExistZonaId(int pIdZona);

    public void Initialize(){}

    private cSerii(){}
}

