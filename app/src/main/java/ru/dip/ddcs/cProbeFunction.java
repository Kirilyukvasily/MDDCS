package ru.dip.ddcs;

/**
 * Created by В on 22.02.2016.
 */
public abstract class cProbeFunction {
    public abstract void Dispose();
    cProbeFunction() { }

    //  Methods
    //public abstract virtual void Probe(const Utils::cMatrix<double>* y, Utils::cMatrix<double>* f);
    public abstract void Probe(double x,double[] y,double[] f);
    //public abstract virtual void Jiacobian(const Utils::cMatrix<double>* y,Utils::cMatrix<double>* f);
    //public abstract virtual void Insert(const Utils::cMatrix<double>* y);
    public abstract void Insert(double[] y);
    //public abstract virtual bool Constraints(const Utils::cMatrix<double>* y);
    public abstract int Constraints(double x, double[] y);
    public  boolean SolveProceed(double[] py, double[][] pZ, double ph)
    {
        return false;
    }

    public abstract int GetVariablesCount();
    public abstract void SetCountDisloc(int countDisloc);
    //virtual double GetXEnd() = 0;
    public abstract boolean Solve();
    //public abstract cDisloc GetDisloc();
}

