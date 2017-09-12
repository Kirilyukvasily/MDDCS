package ru.dip.ddcs;

/**
 * Created by В on 20.03.2016.
 */
public class cParametersSerii {
    private int data_serii_id;
    private boolean solveSerii;
    private String typeSerii;

    private boolean grBool;
    private double leftGran;
    private double rightGran;

    private boolean stepBool;
    private double step;

    private boolean MBool;
    private String M;

    private boolean NBool;
    private int N;

    private boolean TBool;
    //AnsiString T;

    private boolean textBool;
    private String text;

    private boolean arifmProgres;
    private boolean geomProgres;

    public cParametersSerii() { }

    public cParametersSerii(cParametersSerii ps)
    {
        data_serii_id = ps.GetDataSeriiId();
        solveSerii = ps.GetSolveSerii();
        typeSerii = ps.GetTypeSerii();
        grBool = ps.GetGrBool();
        leftGran = ps.GetLeftGran();
        rightGran = ps.GetRightGran();
        stepBool = ps.GetStepBool();
        step = ps.GetStep();
        MBool = ps.GetMetallBool();
        M = ps.GetMetall();
        NBool = ps.GetNBool();
        N = ps.GetN();
        TBool = ps.GetTBool();
        textBool = ps.GetTextBool();
        text = ps.GetText();
        arifmProgres = ps.GetArifmProgres();
        geomProgres = ps.GetGeomProgres();
    }

    //Раньше функции возвращали ссылку,
    //поэтому нужно отсмотреть, где
    //осуществляются вызовы этих функций
    //и заменить их на Set...
    //public double GetParam(int pParameterNumber);

    public int GetDataSeriiId(){return data_serii_id;}
    public boolean GetSolveSerii(){return solveSerii;}
    public String GetTypeSerii(){return typeSerii;}
    public boolean GetGrBool(){return grBool;}
    public double GetLeftGran(){return leftGran;}
    public double GetRightGran(){return rightGran;}
    public boolean GetStepBool(){return stepBool;}
    public double GetStep(){return step;}
    public boolean GetMetallBool(){return MBool;}
    public String GetMetall(){return M;}
    public boolean GetNBool(){return NBool;}
    public int GetN(){return N;}
    public boolean GetTBool(){return TBool;}
    //AnsiString& GetT(){return T;}
    public boolean GetTextBool(){return textBool;}
    public String GetText(){return text;}
    public boolean GetArifmProgres(){return arifmProgres;}
    public boolean GetGeomProgres(){return geomProgres;}

    //-----------Set-Functions--------------------------

    public void SetDataSeriiId(int pdata_serii_id)
    {
        data_serii_id = pdata_serii_id;
    }
    public void SetSolveSerii(boolean psolveSerii)
    {
        solveSerii = psolveSerii;
    }
    public void SetTypeSerii(String ptypeSerii)
    {
        typeSerii = ptypeSerii;
    }
    public void SetGr(boolean pgrBool)
    {
        grBool = pgrBool;
    }
    public void SetLeftGran(double pleftGran)
    {
        leftGran = pleftGran;
    }
    public void SetRightGran(double prightGran)
    {
        rightGran = prightGran;
    }
    public void SetStepBool(boolean pstepBool)
    {
        stepBool = pstepBool;
    }
    public void SetStep(double pstep)
    {
        step = pstep;
    }
    public void SetMetallBool(boolean pMBool)
    {
        MBool = pMBool;
    }
    public void SetMetall(String pM)
    {
        M = pM;
    }
    public void SetNBool(boolean pNBool)
    {
        NBool = pNBool;
    }
    public void SetN(int pN)
    {
        N = pN;
    }
    public void SetTBool(boolean pTBool)
    {
        TBool = pTBool;
    }
    public void SetTextBool(boolean ptextBool)
    {
        textBool = ptextBool;
    }
    public void SetText(String ptext)
    {
        text = ptext;
    }
    public void SetArifmProgres(boolean parifmProgres)
    {
        arifmProgres = parifmProgres;
    }
    public void SetGeomProgres(boolean pgeomProgres)
    {
        geomProgres = pgeomProgres;
    }

    public void Dispose()
    {
       System.gc(); /*GC.SuppressFinalize(this);*/
    }

    {
        Dispose();
    }
}

