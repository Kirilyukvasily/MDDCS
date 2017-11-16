package ru.dip.ddcs;

import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by В on 12.03.2016.
 */
public class GIRwithcDislocProblem {

    Long aufrufe_awp = new  Long(0);
    Double hk1 = new Double(0);
    Double xka = new Double( 0 );

    private int n;
    //Utils::cMatrix<double> y;
    private double[] y;
    //private double x;
    private double h;
    private double[] result;
    private boolean res_prognoz;
    public double delta_x_for_prognoz;
    private double epsabs;
    private double epsrel;

    private double[] hilf;
    private double[] f;
    private double[] ykp1;
    private double[] con;
    private int[] perm;
    private double[][] zj;
    private double[][] zjp1;
    private double[][] fs;
    private double[][] fsg;

    //private int m_countStep;

    private boolean m_tNBool;
    private double m_tN;
    private boolean m_minStepBool;
    private double m_minStep;
    private boolean m_maxStepBool;
    private double m_maxStep;
    private double m_nextUserPoint;
    private boolean m_flagUserPoint; // ôëàã ïîêàçûâàåò ÷òî ñëåäóþùåå ðåøåíèå ñîâïàäàåò ñ òî÷êîé ïîëüçîâàòåëÿ (óçëîì ñåòêè)
    private double m_tol; // òî÷íîñòü ðàñ÷åòîâ

    private int m_countflagoutglobal = 5000;

    private enum eMethod
    {
        Euler1I1,
        AdamsaBahforda2I2,
        AdamsaBahforda3I3,
        AdamsaBahforda4I4,
        Trapezoidal1NI2,
        Simpson2NI4,
    };

    double x;
    double E0;
    double r0;
    double Pj;
    double Рs;
    double ksi;
    double v;
    double alfl;
    //double bÅ;
    double d;
    double T;
    double tauf;
    double ro;
    double G;
    double koldis;
    double kolraz;
    double e0;
    double e0_2;


    private cProbeFunction m_probeFunction;
    private cStatistic m_stat;

    // ==============================cDislocProblem==============

    protected List<Double> m_functions;
    protected double m_TOL;
    protected int m_rastSgatieStatic;
    protected int m_rastSgatie;
    protected int m_countRastSgatie;
    // ----- флаг указывает нужно ли считать средние значения
    protected boolean m_solveSrednZnach = true;

    public double rend_cDisloc = 0;

    protected double t0_cDisloc;
    protected double E0_cDisloc;
    protected double r0_cDisloc;
    //protected double x_cDisloc;
    protected double h_cDisloc;
    protected double xend;
    protected double bb;
    protected double ct;
    protected double taud_cDisloc;
    //protected double e0_cDisloc;
    //protected double e0_2_cDisloc;
    protected double taur;
    protected double tau;
    protected double dim;
    protected double x_final_cDisloc;
    protected double Socr1;
    protected double Socr2;
    protected boolean[] m_variant = new boolean[4];
    protected boolean m_OgranichDim = false;

    protected int m_dislocId;
    protected double Socr1_id_1;

    protected boolean m_flag;

    protected int m_minCountCicle;

    protected cPointIsInInterval m_insertPoint;

    protected double m_minZnachKinetEnerg;

    protected double coef1;
    protected double coef2;
    protected double m_debagCountPoints;
    protected String m_FileName;

     protected int m_countSubintervals;

    protected boolean m_testCalculationComplete;
    protected double m_epsCalculationComplete;
    protected int m_countPointsRasheta;
    protected int m_CountPointsRashetaCritical;
    protected double m_epsCalcComplete;
    protected boolean m_SaveInsertPoints;
    protected double m_lastSpeed;
    protected double x_max;
    protected cSave m_save;

    private EditText et;


    public GIRwithcDislocProblem(int pMinCountCicle,
                          int pCountSubintervals, double pXEnd, int pRastSgatie,
                          double pTau, double pT0, double pE0, double pR0,
                          double pB, double pCt, double pE0_Socr,
                          double pSocr1, double pSocr2, double pTauR,
                          double pDim, boolean[] model, cSave pSave) {

        m_save = pSave; //Временно
        m_minStepBool=true;
        m_maxStep= 1E-300;
        m_maxStepBool = true;
        m_maxStep = 1E-07;
        m_tN = pXEnd;
        m_tNBool = true;



        m_minCountCicle = pMinCountCicle;
        m_countSubintervals = pCountSubintervals;

        xend = pXEnd;

        m_rastSgatieStatic = pRastSgatie;
        if (pRastSgatie == 1)
            m_rastSgatie = 2;
        else
            m_rastSgatie = pRastSgatie;

        tau = pTau;
        t0_cDisloc = pT0;
        E0_cDisloc = pE0;
        r0_cDisloc = pR0;
        bb = pB;
        ct = 2495.8584754751528;


        e0 = 0.0000000011079670106722141;
        e0_2 = e0 * e0;
        Socr1 = pSocr1;
        Socr2 = pSocr2;
        taur = pTauR;
        dim = pDim;

        m_variant = model;

        m_insertPoint = new cPointIsInInterval(2);
        m_minZnachKinetEnerg = 1E-300;

        m_epsCalculationComplete = 1E-16;
        m_testCalculationComplete = false;

        m_countPointsRasheta = 0;
        m_CountPointsRashetaCritical = 10000;

        m_epsCalcComplete = 1E-10;

        m_lastSpeed = 0;

        // ----- Добавляем два произвольных числа
        /*m_functions = new List<Double>();
        m_functions.add(1);
        m_functions.add(1);*/
       // -------------------------------ВРЕМЕННО
        m_minStepBool=true;
        m_maxStep= 1E-300;
        m_maxStepBool = true;
        m_maxStep = 1E-07;
        m_tN = pXEnd;
        m_tNBool = true;

        m_functions = new ArrayList<Double>();
        m_functions.add(1.0);
        m_functions.add(1.0);
    }


    public GIRwithcDislocProblem(double pt0,double pE0, double ptau, double pr0, double pPj, double pРs, double pksi, double pv, double palfl, double pbm, double pd,
               double pT, double pτf, double pro, double pG, double pВ, double pkoldis, cSave pSave,
                                 int pRastSgatie, int pMinCountCicle, int pCountSubintervals, double pxend,EditText editText3)
    {
        et=editText3;
        m_minCountCicle = pMinCountCicle;
        m_countSubintervals = pCountSubintervals;
        m_save = pSave; //Временно


        m_rastSgatieStatic = pRastSgatie;
        if (pRastSgatie == 1)
            m_rastSgatie = 2;
        else
            m_rastSgatie = pRastSgatie;

        m_minStepBool=true;
        m_maxStep= 1E-300;
        m_maxStepBool = true;
        m_maxStep = 1E-07;
        m_tN = pxend;
        m_tNBool = true;

        m_CountPointsRashetaCritical = 10000;
        m_countPointsRasheta = 0;

        m_epsCalcComplete = 1E-10;

        m_lastSpeed = 0;

        epsabs = 1E-25;

        t0_cDisloc = pt0;
        E0_cDisloc = pE0;
        r0_cDisloc = pr0;
        Pj = pPj;
        Рs = pРs;
        ksi = pksi;
        v = pv;
        alfl = palfl;
        double bm = pbm*1E-10;
        d = pd;
        T = pT;
        tauf = pτf*1E+6;
        ro = pro;
        G = pG;
        bb = pВ;
        koldis = pkoldis;
       // kolraz = pkolraz;
        tau = ptau*1E+6;
        ct = Math.sqrt(G / d);
        e0 = G * bm * bm / (4 * Math.PI) * (2 - v) / (2 * (1 - v)) * Math.log(1 / (Math.sqrt(ro) * bm));
        e0_2 = e0 * e0;
        double btr = 0.14;
        tau = tau * bm;
        Socr1 = G * bm * bm * (2 - v) / (2 * (2 * Math.PI) * (1 - v));
        Socr2 = Pj * Рs * ksi * G * bm * bm * ro / 8;
        m_insertPoint = new cPointIsInInterval(2);
        m_functions = new ArrayList<Double>();
        m_functions.add(1.0);
        m_functions.add(1.0);
        m_variant[0] = true;
        m_variant[1] = true;
        m_variant[2] = true;
        m_variant[3] = true;

        double dislocTrenue = alfl * G * bm * Math.sqrt(ro);
        taur = (m_variant[2] ? tauf : 0) + dislocTrenue;
        taur = taur * bm;
        if (m_variant[0])
            dim=0;
        else
            dim = (2 * Math.PI * Math.PI * tau / (alfl * btr * ksi * G * bm * bm * ro));
        xend = pxend;

        m_epsCalculationComplete = 1E-16;
        m_testCalculationComplete = false;




    }

    public void SetCountFlagOutGlobal(int count)
    {
        m_countflagoutglobal = count;
    }

    public boolean SolveGIR() throws IOException {
        n = 2;
        result = new double[n];

        y = new double[n];
        hilf = new double[n];
        f    = new double[n];
        ykp1 = new double[n];
        con  = new double[n];
        perm = new int[n]; //(int  *) vmalloc(vmblock, VVEKTOR, n, sizeof(*perm));
        zj   = new double[5][]; //(REAL **)vmalloc(vmblock, MATRIX,  5, n);
        zjp1 = new double[5][]; //(REAL **)vmalloc(vmblock, MATRIX,  5, n);
        for(int i = 0; i < 5; i++)
        {
            zj[i] = new double[n];
            zjp1[i] = new double[n];
        }
        fs   = new double[n][]; //(REAL **)vmalloc(vmblock, MATRIX,  n, n);
        fsg  = new double[n][]; //(REAL **)vmalloc(vmblock, MATRIX,  n, n);
        for(int i = 0; i < n; i++)
        {
            fs[i] = new double[n];
            fsg[i] = new double[n];
        }
        // ----- Определяем ограничения модели
        // ----- = 0 - продолжить расчет
        // ----- = 1 - уменьшить шаг
        // ----- = 2 - остановить расчеты
        int constr;
        //if (! vmcomplete(vmblock))
        //{
        //  vmfree(vmblock);
        //  return false;
        //}

        //  --- çàäàíèå îòíîñèòåëüíîé òî÷íîñòè âû÷èñëåíèé
        epsrel = m_tol;
        //  --- ìàêñèìàëüíîå ÷èñëî âûçîâîâ ïðàâîé ÷àñòè
        long fmax = (long) 1E+16 ;
        //  --- èíèöèàëèçàöèÿ íà÷àëüíîãî øàãà
        //h = 1E-23;
        //h = 1E-8;
        //*h = 1E-10;
        //*h = FirstStep();
        //  --- èíèöèàëèçàöèÿ ïåðâîé òî÷êè
        //x  = pNachZnachPer;
        //  --- èíèöèàëèçàöèÿ ïåðâîé òî÷êè ïîëüçîâàòåëÿ, çíà÷åíèå â êîòîðîé ïîëó÷èòü îáÿçàòåëüíî
        if( m_maxStepBool )
        {
            m_nextUserPoint = x + m_maxStep;
        }
        m_flagUserPoint = false;

        double miny = E0_cDisloc;
        //for(int i = 0; i < n; i++){
        y[0] = E0_cDisloc;
        if( miny > y[0] )
            miny = y[0];
        y[1] = r0_cDisloc;
        if( miny > y[1] )
            miny = y[1];
        //}
        //  --- èíèöèàëèçàöèÿ ïåðåìåííûõ
        double dummy    = 0;
        double eps1     = Math.pow(1E-15, 0.75);
        double eps2     = 100 * 1E-15;
        double hs       = Math.min(10 * Math.sqrt(1E-15), Math.max(1e-15 * miny, 1e-120));
        //double hs = Math.Min(10 * Math.Sqrt(1E-15), 1e-15 * miny);
        double sg       = (m_tN >= 0) ? 1 : -1; //çíàê m_tN
        double xe       = (1 - sg * eps2) * m_tN;
        int fehler      = 0;
        long aufrufe    = 0;
        int amEnde      = 0;
        //  --- ïðîâåðêà âõîäíûõ ïàðàìåòðîâ
        double ymax = norma(y, n);
        //if (epsabs <= eps2 * ymax && epsrel <= eps2)
        //  fehler = 1;
        //else
        if (xe < x)
        {
            fehler = 2;
        }
        //else if (*h < eps2 * FABS(*x))
        //  fehler = 3;
        else if (n <= 0)
        {
            fehler = 4;
        }
        if (fehler != 0)
        {
            return fehler != 0;
        }
        //  --- ïåðâûé øàã èíòåãðèðîâàíèÿ
        if (x + h > xe)
        {
            h     = m_tN - x;
            dummy  = h;
            amEnde = 1;
        }
       copy_vector(hilf,y,n);
        delta_x_for_prognoz = x;
        x = 0;
        xka = x;
        double xke = xka;

        boolean chikl = false;
        l2 : for(int k = 1; k < 5; ++k)
        {
           //if(!chikl) {
                double hka = (0.25 * h);
                hk1 = hka;

                res_prognoz = true;
                xke += hka;
            //}
            //Delegate fun = new Delegate(ProbeFunctionPrognoz);
            fehler = awp(xka, xke, n, hilf, epsabs, epsrel, hk1, 6, fmax - aufrufe, aufrufe_awp);
            if( res_prognoz == false ){
                h *= 0.5;
                x = delta_x_for_prognoz;
                if( h == 0 )
                    return false;
                //chikl=false;
                k=0;
                continue l2;
            }
            //chikl=true;
            aufrufe += aufrufe_awp;
            if (fehler!=0)
            {
                //vmfree(vmblock);
                //return fehler;
                return false;
            }
            copy_vector(zjp1[k], hilf, n);
        }
        x = delta_x_for_prognoz;
        //dgl(*x, y, f);
       /*// if (m_stat != null)
        {
            m_stat.SetStepAndOrder(h, 4, x);
        }*/
        Probe(x, y, f);
        ++aufrufe;
        //  --- Âû÷èñëèòü âåêòîð Íîðäñèêà
        for (int i = 0; i < n; ++i)
        {
            zj[0][i] = y[i];
            zj[1][i] = h * f[i];
            zj[2][i] = 1 / 24.0 * (35.0 * y[i] -
                    104.0 * zjp1[1][i] +
                    114.0 * zjp1[2][i] -
                    56.0  * zjp1[3][i] +
                    11.0  * zjp1[4][i]);
            double t1 = -5 * y[i];
            double t2 = 18.0 * zjp1[1][i] -
                    24.0 * zjp1[2][i] +
                    14.0 * zjp1[3][i] -
                    3.0 * zjp1[4][i];
            double t3 = t1 + t2;

            zj[3][i] = 1 / 12.0 * (-5 * y[i] +
                    18.0  * zjp1[1][i] -
                    24.0  * zjp1[2][i] +
                    14.0  * zjp1[3][i] -
                    3.0   * zjp1[4][i]);
            zj[4][i] = 1 / 24.0 * (y[i] -
                    4.0   * zjp1[1][i] +
                    6.0   * zjp1[2][i] -
                    4.0   * zjp1[3][i] +
                    zjp1[4][i]);
        }
        //  --- âû÷èñëåíèÿ
        int signdet;
        boolean nochmal;
        double halt = h;
        double quot1,quot2,quot3,quot4;
        double q;
        double eps;
        double diff;
        int flagoutlocal = 0;
        int flagoutglobal = 0;
        labl: while( true )
        {
            // - use Newton method for an implicit approximation

            for (int i = 0; i < n; ++i)
                ykp1[i] = zj[0][i] + zj[1][i] + zj[2][i] + zj[3][i] + zj[4][i];
            constr = Constraints(x, ykp1);

            if (constr == 0) {
                if (flagoutlocal > 0) {
                    ++flagoutglobal;
                    flagoutlocal = 0;
                } else {
                    flagoutglobal = 0;
                }
                Probe(x + h, ykp1, f);
            } else {
                if (constr == 1) {
                    //      --- Óìåíüøåíèå øàãà â 2 ðàçà
                    Stepcorrect(halt, zj, amEnde);
                    //      --- ïðîâåðêà óìåíüøàåòñÿ-ëè øàã 5 ðàç ïîäðÿä
                    ++flagoutlocal;

                    if (flagoutlocal == 500 || flagoutglobal > 5000) {
                        if (SolveProceed(ykp1, zj)) {
                            for (int i = 0; i < n; ++i)
                                y[i] = ykp1[i];
                            flagoutlocal = 0;
                            flagoutglobal = 0;
                            continue;
                        }
                        for (int i = 0; i < n; ++i)
                            result[i] = ykp1[i];
                        //vmfree( vmblock );
                        return true;

                    }
                    break labl;
                } else // constr = 2
                {
                    for (int i = 0; i < n; i++)
                        result[i] = y[i];
                    return true;
                }
            }


            for (int k = 0; k < n; ++k)
            {
                copy_vector(hilf, ykp1, n);
                hilf[k] -= hs;
                constr = Constraints(x,hilf);
                if (constr == 0)
                {
                    Probe(x + h, hilf, fs[k]);
                }
                else
                {
                    if (constr == 1)
                    {
                        Stepcorrect(halt, zj, amEnde);
                        continue labl;
                    }
                    else // constr = 2
                    {
                        for (int i = 0; i < n; i++)
                            result[i] = y[i];
                        return true;
                    }
                }
                for (int i = 0; i < n; ++i)
                {
                    fs[k][i] = -h * 0.48 * (f[i] - fs[k][i]) / hs;
                }
                fs[k][k] += 1;
            }
            aufrufe += n + 1;
            for (int i = 0; i < n; ++i)
            {
                con[i] = ykp1[i] - 0.48 * (zj[1][i] + 2 * zj[2][i] +
                        3 * zj[3][i] + 4 * zj[4][i]);
                for(int k = 0; k < n; k++)
                {
                    fsg[k][i] = fs[i][k];
                }
            }
            //  ----- signed = 0 чтобы не было ошибки при компиляции при вызове функции gauss
            signdet = 0;
            fehler = gauss(1, n, fsg, fsg, perm, null, null, signdet);
            if (fehler!=0)
            {
                //fehler = 6;
                //vmfree(vmblock);
                return false;
            }
            for (int iter = 1; iter <= 3; ++iter)
            {
                for (int i = 0; i < n; ++i)
                {
                    hilf[i] = - ykp1[i];
                    for (int k = 0; k < n; ++k)
                        hilf[i] += fs[k][i] * ykp1[k];
                    hilf[i] = h * 0.48 * f[i] + hilf[i] + con[i];
                }
                fehler = gauss(2, n, fsg, fsg, perm, hilf, ykp1, signdet);
                if(fehler!=0)
                {
                    //vmfree(vmblock);
                    return false;
                }
                constr = Constraints(x,ykp1);
                if (constr == 0)
                {
                    Probe(x + h, ykp1, f);
                }
                else
                {
                    if (constr == 1)
                    {
                        Stepcorrect(halt, zj, amEnde);
                        continue labl;
                    }
                    else // constr = 2
                    {
                        for (int i = 0; i < n; i++)
                            result[i] = y[i];
                        return true;
                    }
                }
            }
            aufrufe += 3;

            // ---- Compute corresponding Gear-Nordsieck approximation ----

            for (int i = 0; i < n; ++i)
                hilf[i] = h * f[i] - zj[1][i] - 2 * zj[2][i] -
                        3 * zj[3][i] - 4 * zj[4][i];
            for (int i = 0; i < n; ++i)
            {
                //result[i] = zjp1[0][i];
                zjp1[0][i] = ykp1[i];
                zjp1[1][i] = h * f[i];
                zjp1[2][i] = zj[2][i] + 3 * zj[3][i] + 6 * zj[4][i] +
                        0.7 * hilf[i];
                zjp1[3][i] = zj[3][i] + 4 * zj[4][i] + 0.2 * hilf[i];
                zjp1[4][i] = zj[4][i] + 0.02 * hilf[i];
            }

            // -- decide whether to accept last step --

            copy_vector(hilf, zjp1[4], n);
            copy_vector(con, zj[4], n);
            diff = dist_max(hilf, con, n);
            ymax = norma(ykp1, n);
            eps  = (epsabs + epsrel * ymax) / 6;
            if( diff < 1e-300 )
                q = 2;
            else
                q  = Math.sqrt(Math.sqrt(eps / diff)) / 1.2;//:
            //  q  = TWO;

            if( diff < eps )
            {
                //  accept last step; prepare for next one
              /*  if( m_stat != null )
                {
                    //m_stat.SetStep(h,x);
                    //m_stat.SetOrder(4,x);
                    m_stat.SetStepAndOrder(h,4,x);
                    //m_stat.ShowStatisticWindow();
                }*/
                x += h;
                copy_vector(y, ykp1, n);
                //      --- Ñîõðàíèì âû÷èñëåííóþ òî÷êó
                Insert(y);
                if( m_maxStepBool && m_flagUserPoint )
                    m_flagUserPoint = false;
                //  stop integration, if interval end m_tN has been reached
                //  or if there have been too many function calls
                nochmal = false;
                do
                {
                    if( amEnde != 0 )
                    {
                        h = dummy;
                        for(int i = 0; i < n; i++)
                            result[i] = y[i];
                        //vmfree(vmblock);
                        return true;
                    }
                    else if(aufrufe > fmax)
                    {
                        fehler = 5;
                        for(int i = 0; i < n; i++)
                            result[i] = y[i];
                        //vmfree(vmblock);
                        return true;
                    }
                    // -- adjust step size for next step
                    halt = h;
                    h   = Math.min(q, 2) * h;
                    // ------ åñëè ìû ñ íîâûì øàãîì ïåðåñêàêèâàåì òî÷êó ïîëüçîâàòåëÿ, òî øàã äåëàåì òàêèì, ÷òîáû ìû îêàçàëèñü íåïîñðåäñòâåííî â òî÷êå ïîëüçîâàòåëÿ
                    if( m_maxStepBool ){
                        if( x + h > m_nextUserPoint ){
                            h = m_nextUserPoint - x;
                            // ----- ïåðåéäåì ê ñëåäóþùåé òî÷êå ïîëüçîâàòåëÿ, íî åñëè âåëè÷èíà øàãà äî äàííîé òî÷êè ïîëüçîâàòåëÿ áóäåò îòâåðãíóòà, òî îïÿòü âåðíåìñÿ ê ðàññìîòðåíèþ äàííîé òî÷êè ïîëüçîâàòåëÿ
                            m_nextUserPoint += m_maxStep;
                            // ----- ôëàã ïîêàçûâàåò ÷òî ñëåäóþùåå ðåøåíèå ñîâïàäàåò ñ òî÷êîé ïîëüçîâàòåëÿ (óçëîì ñåòêè)
                            m_flagUserPoint = true;
                        }
                    }
                    if(x + h >= xe)
                    {
                        dummy  = h;
                        h     = m_tN - x;
                        amEnde = 1;
                        // ------- close enough to m_tN  => stop integration
                        if (h < eps1 * Math.abs(m_tN))
                            nochmal = true;
                    }
                } while( nochmal );
                // ------ compute Gear-Nordsieck approximation
                // ------ for the next step
                quot1 = h / halt;
                quot2 = Math.sqrt(quot1);
                quot3 = quot2 * quot1;
                quot4 = quot3 * quot1;
                for (int i = 0; i < n; ++i)
                {
                    zj[0][i] = zjp1[0][i];
                    zj[1][i] = quot1 * zjp1[1][i];
                    zj[2][i] = quot2 * zjp1[2][i];
                    zj[3][i] = quot3 * zjp1[3][i];
                    zj[4][i] = quot4 * zjp1[4][i];
                }
            }
            else
            {
                //Âåðíåìñÿ ê ïðåäûäóùåé òî÷êå ïîëüçîâàòåëÿ ò.ê. ïðåäïîëàãàåìàÿ âåëè÷èíà øàãà äî íåå îêàçàëàñü ñèëüíî áîëüøîé
                if( m_maxStepBool && m_flagUserPoint ){
                    m_nextUserPoint -= m_maxStep;
                    m_flagUserPoint = false;
                }
                // ------ repeat last step with smaller step size;
                // ------ adjust Gear-Nordsieck approximation
                halt  = h;
                h    = Math.max(0.5, q) * h;
                // ------ åñëè ìû ñ íîâûì øàãîì ïåðåñêàêèâàåì òî÷êó ïîëüçîâàòåëÿ, òî øàã äåëàåì òàêèì, ÷òîáû ìû îêàçàëèñü íåïîñðåäñòâåííî â òî÷êå ïîëüçîâàòåëÿ
                if( m_maxStepBool ){
                    if( x + h > m_nextUserPoint ){
                        h = m_nextUserPoint - x;
                        // ----- ïåðåéäåì ê ñëåäóþùåé òî÷êå ïîëüçîâàòåëÿ, íî åñëè âåëè÷èíà øàãà äî äàííîé òî÷êè ïîëüçîâàòåëÿ áóäåò îòâåðãíóòà, òî îïÿòü âåðíåìñÿ ê ðàññìîòðåíèþ äàííîé òî÷êè ïîëüçîâàòåëÿ
                        m_nextUserPoint += m_maxStep;
                        // ----- ôëàã ïîêàçûâàåò ÷òî ñëåäóþùåå ðåøåíèå ñîâïàäàåò ñ òî÷êîé ïîëüçîâàòåëÿ (óçëîì ñåòêè)
                        m_flagUserPoint = true;
                    }
                }
                quot1 = h / halt;
                quot2 = Math.sqrt(quot1);
                quot3 = quot2 * quot1;
                quot4 = quot3 * quot1;
                for (int i = 0; i < n; ++i)
                {
                    //zj[0][i] = result[i];
                    zj[1][i] = quot1 * zj[1][i];
                    zj[2][i] = quot2 * zj[2][i];
                    zj[3][i] = quot3 * zj[3][i];
                    zj[4][i] = quot4 * zj[4][i];
                }
                amEnde = 0;
            }
        }
        for (int i = 0; i < n; i++)
            result[i] = y[i];

        //vmfree( vmblock );
        return true;
    }
    public  void SetTN(double pTN)
    {
        m_tNBool = true;
        m_tN = pTN;
    }

    public void SetMinStep(double pMinStep)
    {
        m_minStepBool = true;
        m_minStep = pMinStep;
    }
    public void SetMaxStep(double pMaxStep)
    {
        m_maxStepBool = true;
        m_maxStep = pMaxStep;
    }
    public void SetTOL(double pTOL)
    {
        m_tol = pTOL;
    }
    public void SetStatistic(cStatistic pStat)
    {
        m_stat = pStat;
    }
    public void SetStep(double pStep)
    {
        h = pStep;
    }
    private double norma(double[] y, int n)
    {
        double rez = 0;
        for (int i = 0; i < n; ++i)
        {
            rez += y[i] * y[i];
        }
        return Math.sqrt(rez);
    }

    private void copy_vector(double[] hilf, double[] y,int n)
    {
        for (int i = 0; i < n; ++i)
        {
            hilf[i] = y[i];
        }
    }
    double[] yhilf;     /* [0..n-1] aux vectors for the embedding       */
    double[] k1;        /* formulas in ruku23() and engl45().           */
    double[] k2;        /* dynamically allocated                        */
    double[] k3;
    double[] k4;
    double[] k5;
    double[] k6;
    double[] k7;        /* more [0..n-1] aux vectors for embedding      */
    double[] g6;        /* formula  in prdo45() (dynamic allocation)    */
    double[] g7;
    static int  steif1;   /* Flag, that is set in prdo45() if its         */
    /* stiffness test (dominant eigenvalue)         */
                              /* indicates so. Otherwise no changes.          */
    static int steifanz;  /* counter for number of successive successes   */
    /* of stiffness test of Shampine and Hiebert in */
                              /* prdo45().                                    */
    static int  steif2;   /* Flag, set in prdo45(), when the stiffness    */
    /* test of  Shampine and Hiebert wa successful  */
                              /* three times in a row; otherwise no changes   */
    enum girboolean {FALSE, TRUE} ;

    private int awp                /* 1st order DESs with autom. step size control*/
    (
            double x,         /* initial/final x value ..............*/
            double   xend,       /* desired end point ..................*/
            int    n,          /* number of DEs ......................*/
            double[]   y,        /* initial/final y value ..............*/
            double   epsabs,     /* absolute error bound ...............*/
            double   epsrel,     /* relative error bound ...............*/
            double h,         /* initial/final step size ............*/
            int        methode,    /* desired method (3, 6, 7) ...........*/
            long       fmax,       /* maximal # of calls of  dgl() .......*/
            long   aufrufe    /* actual # of calls of  dgl() ........*/
    )
    {
        double MACH_2 = 1e-15;//100.0 * 1e-30;  /* machine constant related*/
                                                   /* value used for break-off*/
                                                   /* criteria as zero        */

        double    xend_h,    /* |xend| - MACH_2, carrying same sign as xend   */
                ymax,      /* Maximum norm of newest approximation of max   */
                                 /* order                                         */
                hhilf = 0, /* aux storage for the latest value of h     */
                                 /* produced by step size control. It is saved    */
                                 /* here in order to avoid to return a `h' that   */
                                 /* resulted from an arbitrary reduction at the   */
                                 /* end of the interval.                          */
                diff,      /* distance of the two approximations from the   */
                                 /* embedding formula                             */
                s,         /* indicates acceptance level for results from   */
                                 /* embeding formula                              */
                mach_1;    /* machine constant dependent variable which     */
        double[]  y_bad;     /* approximate solution of low order             */
        double[]  y_good;    /* ditto of high order                           */
                                 /* avoids using too little steps near xend       */
        int       i,         /* Loop variable                                 */
                fehler;    /* error code                                    */
        girboolean amEnde,   /* flag that shows if the end of the interval    */
                                 /* can be reached with the actual step size      */
                fertig;    /* Flag indicating end of iterations             */
        //void    *vmblock,      /* List of dynamic allocations                   */
        //      (*einbett)(REAL      x,      /* pointer to the function     */
        //                 REAL      *y,     /* that computes the two       */
        //                 int       n,      /* approximations via a pair   */
        //                 dglsysfnk dgl,    /* of embedding formulas       */
        //                 REAL      h,
        //                 REAL      *ybad,
        //                 REAL      *ygut) = NULL;


        fehler   = 0;                         /* initialize some variables  */
        mach_1 = Math.pow(MACH_2, 0.75);
        amEnde   = girboolean.FALSE;
        fertig   = girboolean.FALSE;
        steif1   = 0;
        steif2   = 0;
        steifanz = 0;
        aufrufe_awp = 0l;
        ymax     = norm_max(y, n);
        xend_h   = (xend >= 0) ? (xend * (1 - MACH_2)) :
                (xend * (1 + MACH_2));


              /* ------------------- check input               ------------------ */

        if (epsabs <= MACH_2 * ymax && epsrel <= MACH_2)
            return 1;
        double kgbk = xend_h - xka;
        if (xend_h < xka)
            return 2;
        //if (*h < MACH_2 * FABS(*x))
        //  return 3;
        if (n <= 0)
            return 4;
        if (methode != 3 && methode != 6 && methode != 7)
            return 6;


              /* - allocate aux vectors : six [0..n-1] vectors for method = 3,  - */
              /* - nine for  methode = 6 or 7                                   - */

        //vmblock = vminit();                 /* initialize storage           */
        y_bad  = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0);
        y_good = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0);
        yhilf  = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0);
        k1     = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0);
        k2     = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0);
        k3     = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0);
        if (methode == 6 || methode == 7)
        {
            k4   = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0),
            k5   = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0),
            k6   = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0);
        }
        if (methode == 7)
        {
            k7   = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0),
            g6   = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0),
            g7   = new double[n];//(REAL *)vmalloc(vmblock, VEKTOR, n, 0);
        }

        //if (!vmcomplete(vmblock))                      /* lack of memory ? */
        //{
        //  vmfree(vmblock);                /* free storage and report error  */
        //  return 7;
        //}

        /*********************************************************************
         *                                                                    *
         *                 I t e r a t i o n                                  *
         *                                                                    *
         *********************************************************************/

        if (xka + hk1 > xend_h)           /* almost at end point ?          */
        {
            hhilf  = hk1;                 /* A shortened step might be      */
            hk1     = xend - xka;           /* enough.                        */
            amEnde = girboolean.TRUE;
        }
        do                            /* solve DE system by integrating from     */
        {                             /* x0 to xend by suitable steps            */

            switch (methode)            /* point function to correct embedding  */
            {                           /* formula                              */
                case 3:
                    //ruku23;
                    break;
                case 6:
                    engl45(xka, y, n, hk1, y_bad, y_good); /* integrate   */
                    break;
                case 7:
                    //prdo45;
                    break;
            }

            //#ifdef DEBUG
            //    zeig(y_bad, n);
            //    zeig(y_good, n);
            //#endif
            aufrufe_awp += methode;

            if ((diff = dist_max(y_bad, y_good, n)) < MACH_2)  /* compute s   */
                s = 2;
            else
            {
                ymax = norm_max(y_good, n);
                s    = Math.sqrt(hk1 * (epsabs + epsrel * ymax) / diff);
                if (methode != 3)
                    s = Math.sqrt(s);
            }

            if (s > 1)               /* integration acceptable? */
            {
                for (i = 0; i < n; i++)  /* accept highest order solution       */
                    y[i] = y_good[i];      /* move x                             */
                xka += hk1;

                if ( amEnde == girboolean.TRUE )                /* at end of interval?               */
                {
                    fertig = girboolean.TRUE;           /* stop iteration                    */
                    if (methode == 7)
                    {
                        if (steif1!=0 || steif2!=0)
                            fehler = 8;
                        if (steif1!=0 && steif2!=0)
                            fehler = 9;
                    }
                }
                else if (aufrufe_awp > fmax)  /* too many calls of   dgl()?        */
                {
                    hhilf  = hk1;             /* save actual step size             */
                    fehler = 5;              /* report error and stop             */
                    fertig = girboolean.TRUE;
                    if (methode == 7 &&
                            (steif1!=0 || steif2!=0))
                        fehler = 10;
                }

                else                          /* Integration was successful     */
                {                             /* not at the interval end?       */
                    hk1 *= Math.min(2,              /* increase step size for next    */
                            0.98 * s);  /* step properly, at most by      */
                                                /* factor two. Value `0.98*s' is  */
                                                /* used in order to avoid that    */
                                                /* the theoretical value s is     */
                                                /* exceeded by accidental         */
                                                /* rounding errors.               */
                    if (xka + hk1 > xend_h)      /* nearly reached xend?           */
                    {
                        hhilf  = hk1;              /* => One further step with       */
                        hk1     = xend - xka;      /*    reduced step size might be  */
                        amEnde = girboolean.TRUE;            /*    enough.                     */

                        if (hk1 < mach_1 * Math.abs(xend))    /* very close to xend ?    */
                            fertig = girboolean.TRUE;       /* finish iteration        */
                    }
                }
            }

            else                                /* step unsuccessful?               */
            {                                   /* before repeating this step:      */
                hk1 *= Math.max(0.5,                /* reduce step size properly, at    */
                        0.98 * s);              /* most by factor 1/2 (for factor   */
                amEnde = girboolean.FALSE;        /* 0.98: see above)                 */
            }

        }
        while ( fertig == girboolean.FALSE );


        //vmfree(vmblock);
        hk1 = hhilf;            /* return the latest step size computed by step    */
                                    /* size control and                                */
        return fehler;        /* and error code to the caller                    */
    }

    private double norm_max     /* Find the maximum norm of a REAL vector .........*/
    (
            double[] vektor,        /* vector .................*/
            int  n                  /* length of vector .......*/
    )                           /* Maximum norm ...........*/
    {
        double norm = 0;        /* local max */
        double betrag;          /* magnitude of a component */
        for (int i = 0; i < n; ++i)
        {
            if ((betrag = Math.abs(vektor[i])) > norm)
            {
                norm = betrag;
            }
        }
        return norm;
    }

    private void engl45             /* Einbettungsforml von England 4. und 5. Ord. */
    (
            double      x,              /* Anfangspunkt der Integration .....*/
            double[]    y,              /* DGLS-Loesung bei x ...............*/
            int         n,              /* Anzahl der DGLen .................*/
            double      h,              /* Schrittweite .....................*/
            double[]    y4,             /* DGLS-Loesung 4. Ordnung bei x+h ..*/
            double[]    y5              /* DGLS-Loesung 5. Ordnung bei x+h ..*/
    )
    {
        int i;                      /* loop variable */

        ProbeFunctionPrognoz(x, y, k1);
        for (i = 0; i < n; ++i)
            yhilf[i] = y[i] + 0.5 * h * k1[i];
        ProbeFunctionPrognoz(x + 0.5 * h, yhilf, k2);

        for (i = 0; i < n; ++i)
            yhilf[i] = y[i] + 0.25 * h * (k1[i] + k2[i]);
        ProbeFunctionPrognoz(x + 0.5 * h, yhilf, k3);

        for (i = 0; i < n; ++i)
            yhilf[i] = y[i] + h * (-k2[i] + 2 * k3[i]);
        ProbeFunctionPrognoz(x + h, yhilf, k4);

        for (i = 0; i < n; ++i)
            yhilf[i] = y[i] + h / 27.0 * (7.0 * k1[i] +
                    10 * k2[i] + k4[i]);
        ProbeFunctionPrognoz(x + 2.0 / 3.0 * h, yhilf, k5);

        for (i = 0; i < n; ++i)
            yhilf[i] = y[i] + h / 625.0 *
                    (28.0 * k1[i] - 125.0 * k2[i] +
                            546.0 * k3[i] + 54.0 * k4[i] -
                            378.0 * k5[i]);
        ProbeFunctionPrognoz(x + h / 5, yhilf, k6);

        for (i = 0; i < n; ++i)
        {
            y4[i] = y[i] + h / 6.0 * (k1[i] + 4.0 * k3[i] + k4[i]);
            y5[i] = y[i] + h / 336.0 *
                    (14.0 * k1[i] + 35.0 * k4[i] +
                            162.0 * k5[i] + 125.0 * k6[i]);
        }
    }
    private void ProbeFunctionPrognoz(double pX, double[] pY, double[] pF)
    {
        //int t = 0;
        if (Constraints(pX + delta_x_for_prognoz,pY) == 0)
            Probe(pX + delta_x_for_prognoz, pY, pF);
        else
            res_prognoz = false;
    }
    private double dist_max(double[] vec1,double[] vec2, int n)
    {
        double abstand, hilf;

        for (--n, abstand = 0; n >= 0; --n)
            if ((hilf= Math.abs(vec1[n] - vec2[n])) > abstand)
                abstand = hilf;

        return abstand;
    }
    private void Stepcorrect(double halt,double[][]zj,int amEnde)
    {
        //  --- Óìåíüøàåì øàã â 2 ðàçà
        halt  = h;
        h    = 0.5 * h;
        double quot1 = h / halt;
        double quot2 = Math.sqrt(quot1);
        double quot3 = quot2 * quot1;
        double quot4 = quot3 * quot1;
        for (int i = 0; i < n; ++i)
        {
            //zj[0][i] = result[i];
            zj[1][i] = quot1 * zj[1][i];
            zj[2][i] = quot2 * zj[2][i];
            zj[3][i] = quot3 * zj[3][i];
            zj[4][i] = quot4 * zj[4][i];
        }
        amEnde = 0;
    }

   private boolean SolveProceed(double[]py,double[][]Z) throws IOException {
        return SolveProceed(py, Z, h);
    }


    private int gauss            /* Gauss algorithm for solving linear equations .*/
    (
            int mod,           // Modus: 0, 1, 2, 3
            int n,             // Dimension of matrix
            double[][] mat,         // Input matrix
            double[][] lumat,       // LU decomposition
            int[] perm,        // row remutation vector
            double[] b,           // right hand side
            double[] x,           // solution of the system
            int signd          // sign of the permutation
    )
    {
        switch (mod)
        {

            case 1: /* Find factorization only ...............................*/
                return (gaudec (n, mat, lumat, perm, signd));

            case 2: /* Solve only ............................................*/
                return (gausol (n, lumat, perm, b, x));

            case 3:
                //System.Windows.Forms.MessageBox.Show(" fgauss: gausoli not implemented.\n");
                return 0;
        }

        return (5);                                           /* Wrong call */
    }

    int gaudec              // Gauss decomposition
    (
            int     n,            // size of matrix
            double[][]  mat,        // Input matrix
            double[][]  lumat,      // matrix decomposition
            int[]     perm,       // row interchanges
            int   signd         // sign of perm
    )
    {
        int m, j, i, j0;
        double piv, tmp, zmax;
        double[] d;

        //if (n < 1) return (1);                   /*  Invalid parameters     */

        if (mat == null || lumat == null)
        {
            return (1);
        }
        if (perm == null)
        {
            return (1);
        }

        for (i = 0; i < n; i++)
        {
            if (mat[i] == null || lumat[i] == null)
            {
                return (1);
            }
        }

        d = new double[n];

        if (lumat != mat)                     /* If  lumat and  mat are     */
        {
            CopyMat(n, n, mat, lumat);         /* distinct, copy mat to lumat*/
        }

        for (i = 0; i < n; ++i)
        {
            perm[i] = i;                        /* Initialize perm            */
            zmax = 0;
            for (j = 0; j < n; ++j)             /* find row maxima            */
            {
                tmp = Math.abs(lumat[i][j]);
                if (tmp > zmax) zmax = tmp;
            }

            if (zmax == 0)                   /* mat is singular            */
            {
                return (3);
            }
            d[i] = 1 / zmax;
        }

        signd = 1;                         /* initialize sign of perm      */

        for (i = 0; i < n; ++i)
        {
            piv = Math.abs(lumat[i][i]) * d[i];
            j0 = i;                           /* Search for pivot element     */
            for (j = i + 1; j < n; ++j)
            {
                tmp = Math.abs(lumat[j][i]) * d[j];
                if (piv < tmp)
                {
                    piv = tmp;                    /* Mark pivot element and       */
                    j0 = j;                       /* its location                 */
                }
            }

            if (piv < 1E-15)               /* If piv is small, mat is      */
            {                                 /* nearly singular              */
                signd = 0;
                return (4);
            }

            if (j0 != i)
            {
                signd = - signd;              /* update signd                 */

                Swap (perm[j0], perm[i]);  /* swap pivotentries            */
                Swap(d[j0], d[i]);        /* swap skaling vector          */

                Swap (lumat[j0], lumat[i]);    /* swap j0-th and i-th   */
                                                         /* row of  lumat         */
            }

            for (j = i + 1; j < n; ++j)       /* Gauss elimination            */
            {
                if (lumat[j][i] != 0)
                {
                    lumat[j][i] /= lumat[i][i];
                    tmp = lumat[j][i];
                    for (m = i + 1; m < n; ++m)
                        lumat[j][m] -= tmp * lumat[i][m];
                }
            }
        } /* end i */

        return (0);
    }
    static <T> void Swap(T lhs, T rhs) {
        T temp;
        temp = lhs;
        lhs = rhs;
        rhs = temp;
    }
    void CopyMat (int m, int n, double[][] source, double[][] dest) //Copy the m x n matrix source to the  m x n matrix dest.
    {
        int i, j;
        for (i = 0; i < m; i++)
        {
            for (j = 0; j < n; j++)
            {
                dest[i][j] = source[i][j];
            }
        }
    }
    int gausol              // Gauss solution
    (
            int n,            // size of matrix
            double[][] lumat,      // decomposed matrix (LU)
            int[] perm,       // row permutation vector
            double[] b,          // Right hand side
            double[] x           // solution
    )
    {
        int j, k;
        double sum;

        //if (n < 1) return (1);                   // Invalid input parameter

        if (lumat == null || b == null || perm == null)
        {
            return (1);
        }

        for (j = 0; j < n; j++)
        {
            if (lumat[j] == null)
            {
                return (1);
            }
        }

        for (k = 0; k < n; ++k)                              // update b
        {
            x[k] = b[perm[k]];
            for (j = 0; j < k; j++)
                x[k] -= lumat[k][j] * x[j];
        }

        for (k = n - 1; k >= 0; --k)                    // back substitute
        {
            sum = 0;
            for (j = k + 1; j < n; ++j)
            {
                sum += lumat[k][j] * x[j];
            }

            if (lumat[k][k] == 0)
            {
                return (3);
            }
            x[k] = (x[k] - sum) / lumat[k][k];
        }

        return (0);
    }
    public  double[] GetSolvePoint()
    {
        return result;
    }
    public  double GetStep()
    {
        return h;
    }

    //_-------------------------------------------cDislocProblem-------------------

    public void Probe(double pX, double[] y, double[] f) {
        double y0 = y[0];
        double y1 = y[1];

        double d = y0 / e0;
        double per = d * d + 2 * d;
        //if( (per + 1.) == 1. ){ //Óñëîâíûé îïåðàòîð íåîáõîäèì äëÿ ïîâûøåíèÿ òî÷íîñòè
        //    f[1] = ct * sqrt( 1 - per*per );
        //}
        //else
        f[1] = ct * Math.sqrt(per / (per + 1));

        switch (m_rastSgatie) {
            case 2:
                f[0] =
                        (
                                tau - taur -
                                        (m_variant[1] ? ((e0 + y0) / y1) : 0) -
                                        (m_variant[0] ? Socr2 * y1 : 0) -
                                        Socr1_id_1 / (dim - y1) -
                                        (m_variant[3] ? bb * f[1] : 0)
                        ) * f[1];
                break;
            case 3:
                f[0] =
                        (
                                -tau - taur+
                                        (m_variant[1] ? ((e0 + y0) / y1) : 0) -
                                        (m_variant[0] ? Socr2 * y1 : 0) +
                                        Socr1_id_1 / (dim - y1) -
                                        (m_variant[3] ? bb * f[1] : 0)
                        ) * f[1];
                f[1] *= -1;
                break;
        }
    }

    public void Insert(double[] y) throws IOException {
         if (!m_testCalculationComplete)
         {
             if (y[0] > m_epsCalculationComplete)
             {
                 m_testCalculationComplete = true;
                 m_epsCalculationComplete = 1E-24;
             }
         }
         m_countPointsRasheta++;
         if (m_SaveInsertPoints)//проверка интервалов
         {
             if (m_insertPoint.Insert(x, y))
             {
                 return;
             }
             else
             {
                 if (InsertPoint())
                 {
                     m_insertPoint.FindInterval(x, y);
                 }
                 else
                 {
                     m_insertPoint.NextSubInterval(x, y);
                     if (!m_insertPoint.Insert(x, y))
                     {
                         m_insertPoint.FindInterval(x, y);
                     }
                 }
             }
         }
         else
         {
             if (rend_cDisloc < y[1])
             {
                 rend_cDisloc = y[1];
             }
         }
    }

    //--------------------------------------------------------------
    public int Constraints(double x, double[] y) {
        if (m_OgranichDim) {
            if (y[1] >= dim && dim != 0) {
                return 1;
            }
        }
        if (x > xend) {
            return 2;
        }
        if (m_testCalculationComplete) {
            if (y[0] < m_epsCalculationComplete) {
                return 1;
            }
            if (m_countPointsRasheta > m_CountPointsRashetaCritical) {
                double d = (y[0] + e0) / e0;
                d *= d;
                double speed_ct_sqrt = 1 - 1 / d;
                if (speed_ct_sqrt < m_epsCalcComplete) {
                    return 2;
                }
                if (y[1] < r0_cDisloc) {
                    return 2;
                }
            }
            return 0;
        } else {
            if (m_countPointsRasheta > m_CountPointsRashetaCritical) {
                if (y[0] < E0_cDisloc) {
                    return 1;
                }
                double d = (y[0] + e0) / e0;
                d *= d;
                double speed_ct_sqrt = 1 - 1 / d;
                if (speed_ct_sqrt < m_epsCalcComplete) {
                    return 2;
                }
                return 0;
            }
            if (y[0] >= E0_cDisloc) {
                return 0;
            }
            return 1;
        }
    }

    //--------------------------------------------------------------
    public boolean SolveProceed(double[] py, double[][] pZ, double ph) throws IOException {
        if (py[0] < 0.0 /*|| m_disloc.GetId() == 1*/) {
            return false;
        }
        if (py[1] > dim) {
            return false;
        }
        if (m_rastSgatie == 3) {
            return false;
        }
        if (m_countRastSgatie != 0) {
            //m_insertPoint.Clear();
            return false;
        } else m_countRastSgatie--;
        if (m_rastSgatie == 2) {
            return false;
        } else {
            m_rastSgatie = 2;
        }
        Insert(py);
        double newx = x - m_save.Disloc.GetDislocData(m_save.Disloc.GetCountDislocData() - 1).GetParam(0);
        double halt = ph;
        ph += newx;
        pZ[0][0] = m_save.Disloc.GetDislocData(m_save.Disloc.GetCountDislocData() - 1).GetParam(1);
        pZ[0][1] = m_save.Disloc.GetDislocData(m_save.Disloc.GetCountDislocData() - 1).GetParam(2);
        DerivativeFistOrder(py, pZ[1]);
        for (int i = 0; i < 2; i++) {
            pZ[1][i] = 0;//*ph;
            pZ[2][i] = 0;//-(*ph/halt)*(*ph/halt);
            pZ[3][i] = 0;//-(*ph/halt)*(*ph/halt)*(*ph/halt);
            pZ[4][i] = 0;//-(*ph/halt)*(*ph/halt)*(*ph/halt)*(*ph/halt);
        }
        return true;
    }

    public int GetVariablesCount() {
        return 2;
    }

    {
    }

    public boolean Solve() throws IOException {
        m_save.GetDislocID();
        m_insertPoint.GetFlagInitialize();
        if (m_save.GetDislocID() != 1 && (dim == 0 || m_insertPoint.GetFlagInitialize() == false))
        {
            // ----- Рассчитаем первую дислокацию для того,
            // ----- чтобы получить диаметр зоны сдвига (dim)
            cDisloc disloc_tmp = m_save.getDisloc();
            m_save.CreateDisloc(0, 1, false);

           /* cDislocProblem problem = new cDislocProblem(m_method, m_minCountCicle,
                    m_countSubintervals, m_save, xend, m_rastSgatie, tau,
                    t0, E0, r0, bb, ct, e0, Socr1, Socr2, taur, dim, m_variant);
            Solve();*/


            m_save.setDisloc(disloc_tmp);
            m_testCalculationComplete = false;
        }

        if (m_rastSgatieStatic == 1)
            m_rastSgatie = 2;
        else
            m_rastSgatie = m_rastSgatieStatic;

        m_dislocId = m_save.GetDislocID();
        Socr1_id_1 = Socr1 * (m_dislocId - 1);
        // ----- Зададим названия переменных
        String[] str = { "Время, нс",
                "Кинетическая энергия, 1E-9 Жд/м",
                "Радиус, мкм",
                "Скорость, м/c"};
        m_save.SaveDislocAxisName(str);
        // ----- Зададим начальные значения
        m_countRastSgatie = 2 * m_minCountCicle - 1;
        m_flag = true;
        m_testCalculationComplete = false;

        this.x = t0_cDisloc;
        this.m_functions.set(0, E0_cDisloc);
        this.m_functions.set(1, r0_cDisloc);

        m_countPointsRasheta = 0;
        m_insertPoint.Clear();


        if (m_dislocId == 1)
        {
            double tmp_xend = xend;
            m_SaveInsertPoints = false;
            m_insertPoint.Clear();
            m_insertPoint.SetInterval(0, 1E-4, 100, 1);
            h = 1E-23;
            SetTN(xend);
            SetStep(h);
            SetStatistic(null);
            x_max = 0;
            rend_cDisloc = 0;
            if (!SolveGIR())
            {
                m_SaveInsertPoints = true;
                return false;
            }
            m_SaveInsertPoints = true;
        }
        // ----- Сохраним первую точку
        double d = E0_cDisloc / e0;
        double per = d * d + 2 * d;
        double speed;
        int tmp_rastSgatie = m_rastSgatie;
        int tmp_countRastSgatie = m_countRastSgatie;
        if (m_rastSgatie == 3)
            speed = -ct * Math.sqrt((per) / (per + 1));
        else
            speed = ct * Math.sqrt((per) / (per + 1));
        m_save.SaveDisloc(t0_cDisloc, E0_cDisloc, r0_cDisloc, speed);

        m_insertPoint.Clear();
        m_insertPoint.SetInterval(0,rend_cDisloc , m_countSubintervals, 1); //забить ренд rend = 5.071313836492597E-5
        h = 1E-23;
        //SetTN(xend_cDisloc);
        //SetStep(h_cDisloc);
        this.x = t0_cDisloc;
        this.m_functions.set(0, E0_cDisloc);
        this.m_functions.set(1, r0_cDisloc);
        m_countPointsRasheta = 0;
        m_testCalculationComplete = false;
        m_rastSgatie = tmp_rastSgatie;
        m_countRastSgatie = tmp_countRastSgatie;

       //et.setText("dgdh");

        boolean rez = SolveGIR();

        if (rez == false) {
            return rez;
        }
        InsertEndPoint2();

        m_countRastSgatie = tmp_countRastSgatie;
        double rend_tmp = m_save.GetParam(m_dislocId, 2); //double rend_tmp = m_save.Disloc.GetDislocData(m_save.Disloc.GetCountDislocData() - 1).GetParam(2);
        if (rend_cDisloc > rend_tmp)
        {
            rend_cDisloc = rend_tmp;
        }

        if (m_dislocId == 1)
        {
            if (dim == 0)
            {
                //et.setText(et.getText() + " qq ");
                dim = m_save.GetDiametr(et);//Ошибки исправлены
                //et.setText(et.getText() + String.valueOf(dim));
            }
        }
        /*
        while (m_rastSgatieStatic == 1 && m_dislocId != 1)
        {
            if (m_countRastSgatie == 0)
            {
                if (m_solveSrednZnach)
                {
                    m_save.SaveDislocSrednZnach(ct,e0);
                }
                x_max = 0;
                return rez;
            }
            else m_countRastSgatie--;

            m_flag = true;
            if (m_rastSgatie == 2)
            {
                m_rastSgatie = 3;
                if (m_variant[0] == false && m_variant[3] == false)
                {
                    if (m_variant[1] == false) {
                        this.m_functions.set(0, m_save.getDisloc().GetDislocData(m_save.getDisloc().GetCountDislocData() - 3).GetParam(1));
                        this.m_functions.set(1, m_save.getDisloc().GetDislocData(m_save.getDisloc().GetCountDislocData() - 3).GetParam(2));
                    } else {
                        this.m_functions.set(0, m_save.getDisloc().GetDislocData(m_save.getDisloc().GetCountDislocData() - 4).GetParam(1));
                        this.m_functions.set(1, m_save.getDisloc().GetDislocData(m_save.getDisloc().GetCountDislocData() - 4).GetParam(2));
                    }
                } else {
                    this.m_functions.set(0, m_save.getDisloc().GetDislocData(m_save.getDisloc().GetCountDislocData() - 1).GetParam(1));
                    this.m_functions.set(1, m_save.getDisloc().GetDislocData(m_save.getDisloc().GetCountDislocData() - 1).GetParam(2));
                }
            }
            else {
                m_rastSgatie = 2;
                this.m_functions.set(0, m_save.getDisloc().GetDislocData(m_save.getDisloc().GetCountDislocData() - 1).GetParam(1));
                this.m_functions.set(1, m_save.getDisloc().GetDislocData(m_save.getDisloc().GetCountDislocData() - 1).GetParam(2));
            }

            m_insertPoint.Clear();
            if (m_rastSgatie == 2)
            {
                m_insertPoint.SetInterval(0, rend_cDisloc, m_countSubintervals, 1);
            }
            else
            {
                m_insertPoint.SetInterval(0, rend_cDisloc, m_countSubintervals, 1, true);
            }
            //SetTN(xend_cDisloc);
            this.x = t0_cDisloc;
            m_countPointsRasheta = 0;
            m_testCalculationComplete = false;
            SetStep(h);
            x_max += xend;//.getDisloc().GetDislocData(m_save.getDisloc().GetCountDislocData() - 1).GetParam(0);
            et.setText(et.getText() + " dd");
            if (SolveGIR())
            {
                et.setText(et.getText() + " 99");
                InsertEndPoint2();
                et.setText(et.getText() + " aa");
            }
        }
        */
        if (m_solveSrednZnach)
        {
            //et.setText(et.getText()+" ee");
            m_save.SaveDislocSrednZnach(ct,e0);
        }
        m_insertPoint.Clear();
        //et.setText(et.getText()+" 111");
        return rez;
    }

    //--------------------------------------------------------------
   public cDisloc GetDisloc() {
        return m_save.getDisloc();
    }


    //--------------------------------------------------------------
    protected void InsertEndPoint2() throws IOException {
       InsertPoint();
        m_insertPoint.ClearThisInterval();
        double[] Y = GetSolvePoint();
        double d1 = Y[0];
        double d2 = Y[1];
        if (Y[0] < E0 && m_dislocId == 1)
        {
            InterpolationEndPoint(Y, 1);
            Insert(Y);
        }
        else
        {
            if (Y[1] >= dim && !m_variant[0])
            {
                InterpolationEndPoint(Y, 2);
                Insert(Y);
            }
            else
            {
                if (Y[0] < E0)
                {
                    InterpolationEndPoint(Y, 3);
                    Insert(Y);
                }
            }
        }
        InsertPoint();
   }
    protected void InterpolationEndPoint(double[] y, int pFlag) throws IOException {
        double yii = y[0];
        double rii = y[1];
        double xi, yi, ri;
        if (m_save.getDisloc() != null)
        {
            xi = m_save.Disloc.GetDislocData(m_save.Disloc.GetCountDislocData() - 1).GetParam(0);
            yi = m_save.Disloc.GetDislocData(m_save.Disloc.GetCountDislocData() - 1).GetParam(1);
            ri = m_save.Disloc.GetDislocData(m_save.Disloc.GetCountDislocData() - 1).GetParam(2);
        }
        else
        {
            xi = m_save.GetParam(m_dislocId,0);
            yi = m_save.GetParam(m_dislocId, 1);
            ri = m_save.GetParam(m_dislocId, 2);
        }

        double xii_xi = GetStep();

        double x_xi;

        if (xii_xi != 0)
        {
            if (pFlag == 1)
            {
                double yii_yi = yii - yi;
                if (Math.abs(yii_yi) < 1E-300)
                    yii_yi = Math.signum(yii_yi) * 1E-300;
                x_xi = (E0 - yi) * (xii_xi) / (yii_yi);
                x = x_xi + xi;
                y[0] = E0;
                y[1] = (x_xi) * (rii - ri) / (xii_xi) + ri;
            }
            else if (pFlag == 2)
            {
                double rii_ri = rii - ri;
                if (Math.abs(rii_ri) < 1E-300)
                    rii_ri = Math.signum(rii_ri) * 1E-300;
                x_xi = (dim - ri) * (xii_xi) / (rii_ri);
                x = x_xi + xi;
                y[0] = (x_xi) * (yii - yi) / (xii_xi) + yi;
                y[1] = dim;
            }
            else if (pFlag == 3)
            {
                double yii_yi = yii - yi;
                if (Math.abs(yii_yi) < 1E-300)
                    yii_yi = Math.signum(yii_yi) * 1E-300;
                x_xi = (E0 - yi) * (xii_xi) / (yii_yi);
                x = x_xi + xi;
                y[0] = E0;
                y[1] = (x_xi) * (rii - ri) / (xii_xi) + ri;
            }
            else
            {
                double yii_yi = yii - yi;
                if (Math.abs(yii_yi) < 1E-300)
                    yii_yi = Math.signum(yii_yi) * 1E-300;
                double rii_ri = rii - ri;
                if (Math.abs(rii_ri) < 1E-300)
                    rii_ri = Math.signum(rii_ri) * 1E-300;
                if ((-yi) / (yii_yi) < (dim - ri) / (rii_ri))
                {
                    x_xi = (-yi) * (xii_xi) / (yii_yi);
                    x = x_xi + xi;
                    y[0] = E0;
                    y[1] = (x_xi) * (rii_ri) / (xii_xi) + ri;
                }
                else
                {
                    x_xi = (dim - ri) * (xii_xi) / (rii_ri);
                    x = x_xi + xi;
                    y[0] = (x_xi) * (yii_yi) / (xii_xi) + yi;
                    y[1] = (x_xi) * (rii - ri) / (xii_xi) + ri;
                }
            }
        }
        else
        {
            x = xi;
            y[0] = yi;
            y[1] = ri;
        }
    }
    protected boolean InsertPoint() throws IOException {
        double[] ptrPoint;
        double d;
        double per;
        double speed;
        boolean flag = true;
        for (m_insertPoint.Open(); !m_insertPoint.Eof(); m_insertPoint.Next())
        {
            ptrPoint = m_insertPoint.GetPoint();
            d = ptrPoint[0] / e0;
            per = d * d + 2 * d;
            if (m_rastSgatie == 3)
                speed = -ct * Math.sqrt((per) / (per + 1));
            else
                speed = ct * Math.sqrt((per) / (per + 1));
            m_save.SaveDisloc(ptrPoint[2] + x_max, ptrPoint[0], ptrPoint[1], speed);
            flag = false;
        }
        return flag;
    }
    protected void DerivativeFistOrder(double[] y, double[] f)
    {
        double y0 = y[0];
        double y1 = y[1];
        double e_2 = y0 * y0;
        double e_e0 = y0 * e0;
        double Sv = Math.sqrt((e_2 + 2 * e_e0) / (e_2 + 2 * e_e0 + e0_2));
        double SvIv = Sv * (e_2 * y0 + 3 * e_2 * e0 + 3 * y0 * e0_2 + e0_2 * e0) / e0_2;
        double dim_r = dim - y1;
        if (dim_r == 0)
        {
            double eps = 1E+20;
            int first = (int)(dim * eps);
            int second = (int)(y1 * eps);
            dim_r = (first - second) / eps;
            while (dim_r == 0)
            {
                eps *= 10;
                first = (int)(dim * eps);
                second = (int)(y1 * eps);
                dim_r = (first - second) / eps;
            }
        }

        switch (m_rastSgatie)
        {
            case 2:
                f[0] =
                        (
                                (
                                        (-1 / y1) -
                                                (m_variant[3] ? ((bb * ct) / SvIv) : 0)
                                ) * ct * Sv
                                        +
                                        (
                                                tau - taur -
                                                        ((m_variant[1] ? e0 : 0 + y0) / y1) -
                                                        (m_variant[0] ? Socr2 * y1 : 0) -
                                                        Socr1_id_1 / (dim_r) -
                                                        (m_variant[3] ? bb * ct * Sv : 0)
                                        ) * ct / SvIv
                        )
                                +
                                (
                                        ((m_variant[1] ? e0 : 0 + y0) / (y1 * y1)) -
                                                (m_variant[0] ? Socr2 : 0) -
                                                Socr1_id_1 / (dim_r * dim_r)
                                ) * ct * Sv;
                break;

            case 3:
                f[0] =
                        (
                                (
                                        (1 / y1) -
                                                (m_variant[3] ? ((bb * ct) / SvIv) : 0)
                                ) * ct * Sv
                                        +
                                        (
                                                -tau - taur +
                                                        ((m_variant[1] ? e0 : 0 + y0) / y1) +
                                                        (m_variant[0] ? Socr2 * y1 : 0) +
                                                        Socr1_id_1 / (dim_r) -
                                                        (m_variant[3] ? bb * ct * Sv : 0)
                                        ) * ct / SvIv
                        )
                                +
                                (
                                        ((-(m_variant[1] ? e0 : 0 + y0) / (y1 * y1))) +
                                                (m_variant[0] ? Socr2 : 0) +
                                                Socr1_id_1 / (dim_r * dim_r)
                                ) * ct * Sv;
                break;
        }
        f[1] = ct / SvIv;
    }
}
