package ru.dip.ddcs;

/**
 * Created by В on 19.03.2016.
 */
public class cParametersZona {
    private String metall;
    private double napr;
    private double t0;
    private double e0;
    private double r0;

    private boolean tNBool;
    private double tN;
    private boolean minStepBool;
    private boolean maxStepBool;
    private double minStep;
    private double maxStep;

    private double pj;
    private double ps;
    private double ksi;
    private double nu;
    private double alfa;
    //private double plotnost;
    private double T;
    private double tauf;
    private double ro;
    private int N;
    private double lamda;
    private double lamdaBig;
    private double f;
    private boolean metallBool;
    private boolean numberBool;
    private boolean tBool;
    private boolean plotnostBool;
    private boolean textBool;
    private String text;

    int minCountCicle;
    double epsilonCicle;

    public cParametersZona(){}

    public cParametersZona(cParametersZona pz)
    {
        metall = pz.GetMetall();
        napr = pz.GetNapr();
        t0 = pz.GetT0();
        e0 = pz.GetE0();
        r0 = pz.GetR0();

        tNBool = pz.GetTNBool();
        tN = pz.GetTN();
        minStepBool = pz.GetMinStepBool();
        maxStepBool = pz.GetMaxStepBool();
        minStep = pz.GetMinStep();
        maxStep = pz.GetMaxStep();

        pj = pz.GetPj();
        ps = pz.GetPs();
        ksi = pz.GetKsi();
        nu = pz.GetNu();
        alfa = pz.GetAlfa();
        T = pz.GetT();
        tauf = pz.GetTauf();
        ro = pz.GetRo();
        N = pz.GetN();
        lamda = pz.GetLamdaSmall();
        lamdaBig = pz.GetLamdaBig();
        f = pz.GetF();
        metallBool = pz.GetMetallBool();
        numberBool = pz.GetNumberBool();
        tBool = pz.GetTBool();
        plotnostBool = pz.GetPlotnostBool();
        textBool = pz.GetTextBool();
        text = pz.GetText();

        minCountCicle = pz.GetMinCountCicle();
        epsilonCicle = pz.GetEpsilonCicle();
    }

    //Раньше функции возвращати ссылку,
    //поэтому нужно отсмотреть, где
    //осуществляются вызовы этих функций
    //и заменить их на Set...
    public String GetMetall(){return metall;}
    public double GetNapr(){return napr;}
    public double GetT0(){return t0;}
    public double GetE0(){return e0;}
    public double GetR0(){return r0;}

    public boolean GetTNBool(){return tNBool;}
    public double GetTN(){return tN;}
    public boolean GetMinStepBool(){return minStepBool;}
    public boolean GetMaxStepBool(){return maxStepBool;}
    public double GetMinStep(){return minStep;}
    public double GetMaxStep(){return maxStep;}

    public double GetPj(){return pj;}
    public double GetPs(){return ps;}
    public double GetKsi(){return ksi;}
    public double GetNu(){return nu;}
    public double GetAlfa(){return alfa;}
    //double& GetPlotnost(){return plotnost;}
    public double GetT(){return T;}
    public double GetTauf(){return tauf;}
    public double GetRo(){return ro;}
    public int GetN(){return N;}
    public double GetLamdaSmall(){return lamda;}
    public double GetLamdaBig(){return lamdaBig;}
    public double GetF(){return f;}
    public boolean GetMetallBool(){return metallBool;}
    public boolean GetNumberBool(){return numberBool;}
    public boolean GetTBool(){return tBool;}
    public boolean GetPlotnostBool(){return plotnostBool;}
    public boolean GetTextBool(){return textBool;}
    public String GetText(){return text;}
    public int GetMinCountCicle(){return minCountCicle;}
    public double GetEpsilonCicle(){return epsilonCicle;}

    //-----------Set-Functions--------------------------

    public void SetMetall(String met)
    {
        metall = met;
    }
    public void SetNapr(double nap)
    {
        napr = nap;
    }
    public void SetT0(double t)
    {
        t0 = t;
    }
    public void SetE0(double e)
    {
        e0 = e;
    }
    public void SetR0(double r)
    {
        r0 = r;
    }

    public void SetTNBool(boolean ptN)
    {
        tNBool = ptN;
    }
    public void SetTN(double ptN)
    {
        tN = ptN;
    }
    public void SetMinStepBool(boolean pminStep)
    {
        minStepBool = pminStep;
    }
    public void SetMaxStepBool(boolean pmaxStep)
    {
        maxStepBool = pmaxStep;
    }
    public void SetMinStep(double pminStep)
    {
        minStep = pminStep;
    }
    public void SetMaxStep(double pmaxStep)
    {
        maxStep = pmaxStep;
    }

    public void SetPj(double ppj)
    {
        pj = ppj;
    }
    public void SetPs(double pps)
    {
        ps = pps;
    }
    public void SetKsi(double pksi)
    {
        ksi = pksi;
    }
    public void SetNu(double pnu)
    {
        nu = pnu;
    }
    public void SetAlfa(double palfa)
    {
        alfa = palfa;
    }
    //double& GetPlotnost(){return plotnost;}
    public void SetT(double pT)
    {
        T = pT;
    }
    public void SetTauf(double ptauf)
    {
        tauf = ptauf;
    }
    public void SetRo(double pro)
    {
        ro = pro;
    }
    public void SetN(int pN)
    {
        N = pN;
    }
    public void SetLamdaSmall(double plamda)
    {
        lamda = plamda;
    }
    public void SetLamdaBig(double plamdaBig)
    {
        lamdaBig = plamdaBig;
    }
    public void SetF(double pf)
    {
        f = pf;
    }
    public void SetMetallBool(boolean pmetallBool)
    {
        metallBool = pmetallBool;
    }
    public void SetNumberBool(boolean pnumberBool)
    {
        numberBool = pnumberBool;
    }
    public void SetTBool(boolean ptBool)
    {
        tBool = ptBool;
    }
    public void SetPlotnostBool(boolean pplotnostBool)
    {
        plotnostBool = pplotnostBool;
    }
    public void SetTextBool(boolean ptextBool)
    {
        textBool = ptextBool;
    }
    public void SetText(String ptext)
    {
        text = ptext;
    }
    public void SetMinCountCicle(int pminCountCicle)
    {
        minCountCicle = pminCountCicle;
    }
    public void SetEpsilonCicle(double pepsilonCicle)
    {
        epsilonCicle = pepsilonCicle;
    }

    public void Dispose()
    {
       System.gc(); /*GC.SuppressFinalize(this);*/
    }


    {
        Dispose();
    }
}

