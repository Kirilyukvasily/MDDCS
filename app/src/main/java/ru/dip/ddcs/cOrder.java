package ru.dip.ddcs;

/**
 * Created by В on 19.03.2016.
 */
public class cOrder {
    private int order;
    private double time;

    public cOrder(int pOrder, double pTime)
    {
        order = pOrder;
        time = pTime;
    }

    public int GetOrder()
    {
        return order;
    }

    public double GetTime()
    {
        return time;
    }

    public void SetOrderAndTime(int pOrder, double pTime)
    {
        order = pOrder;
        time = pTime;
    }
}

