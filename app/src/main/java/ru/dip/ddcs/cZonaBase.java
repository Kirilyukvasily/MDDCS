package ru.dip.ddcs;

/**
 * Created by В on 19.03.2016.
 */
public abstract class cZonaBase {
    public enum State
    {
        Saved,      //Çîíà ñîõðàíåíà â áàçå äàííûõ è èñïîëüçóåòñÿ â DDCS
        Solvered,   //Çîíà èñïîëüçóåòñÿ â DDCS, íî åùå íå ñîõðàíåíà â áàçå äàííûõ
        Deleted,    //Çîíà ñîõðàíåíà â áàçå äàííûõ, íî óäàëåíà èç DDCS
        SolveredAndDeleted //Çîíà áûëà âðåìåííî ðàñ÷èòàíà è óäàëåíà
    };

    //~cZonaBase();

    public abstract void AddDisloc(cDisloc disloc, int idDisloc, boolean Solve);
    public abstract void AddDisloc(cDisloc disloc, int idDisloc);
    public abstract void AddDisloc(cDisloc disloc, boolean Solve);
    public abstract void AddDisloc(cDisloc disloc);

    public abstract void SetSrednZnach(double pT, double pE, double pR, double pV, boolean notdisloc);
    public abstract void SetSrednZnach(double pT, double pE, double pR, double pV);
    public abstract void SetSrednZnach(cSrednZnach csz);
    public abstract void SetParameters(cParametersZona cpz);
    public abstract void SetState(State st);

    public abstract cSrednZnach GetSrednZnach();
    public abstract cParametersZona GetParameters();
    public abstract int GetId();
    public abstract State GetState();
    public abstract boolean GetNameRewrite();
    public abstract void SetName(String str);
    public abstract String GetName();

    public abstract cDisloc GetDisloc(int pDislocId);
    public abstract int GetCountDisloc();
    public abstract String GetFullZagolovoc();
    public abstract int GetMaxDislocId();

    public abstract void Initialize();
    public abstract void Delete();

    public abstract void SetStatistic(cStatistic statistic);
    public abstract cStatistic GetStatistic();

    //protected cZonaBase();
}

