package ru.dip.ddcs;

/**
 * Created by В on 20.03.2016.
 */
public abstract class cSeriiBase  {
    public enum State
    {
        Saved,      //Ñåðèÿ ñîõðàíåíà â áàçå äàííûõ è èñïîëüçóåòñÿ â DDCS
        Solvered,   //Ñåðèÿ èñïîëüçóåòñÿ â DDCS, íî åùå íå ñîõðàíåíà â áàçå äàííûõ
        Deleted,    //Ñåðèÿ ñîõðàíåíà â áàçå äàííûõ, íî óäàëåíà èç DDCS
        SolveredAndDeleted //Ñåðèÿ áûëà âðåìåííî ðàñ÷èòàíà è óäàëåíà
    };

    //~cSeriiBase();

    public abstract void AddZona(cZonaBase czb);

    public abstract void SetSrednZnach(double pT, double pE, double pR, double pV);
    public abstract void SetSrednZnach(cSrednZnach pSrednZnach);
    public abstract void SetParameters(cParametersSerii pParameters);
    public abstract void SetState(State st);

    public abstract int GetId();
    public abstract cSrednZnach GetSrednZnach();
    public abstract cParametersSerii GetParameters();
    public abstract int GetMaxZonaId();
    public abstract int GetCountZona();
    public abstract State GetState();
    public abstract boolean NameRewrite();
    public abstract void SetName(String str);
    public abstract String GetName();

    public abstract String GetZagolovoc();
    public abstract cZonaBase GetZonaById(double a);
    public abstract cZonaBase GetZona(int a);

    public abstract void Delete();
    public abstract void DeleteZona(int number);

    public abstract void Initialize();
}
