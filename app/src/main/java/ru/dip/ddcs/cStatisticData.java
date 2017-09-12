package ru.dip.ddcs;

import java.util.ArrayList;

/**
 * Created by В on 19.03.2016.
 */
public class cStatisticData {
    private ArrayList<cStep> m_step = new ArrayList<cStep>();
    private ArrayList<cOrder> m_order = new ArrayList<cOrder>();
    //public cStatisticData();

    public void SetStep(cStep pStep)
    {
        m_step.add(pStep);
    }

    public void SetOrder(cOrder pOrder)
    {
        m_order.add(pOrder);
    }

    public void SetStep(double step, double x)
    {
        m_step.add(new cStep(Math.log10(step), x * 1E9));
    }

    public void SetOrder(int order, double x)
    {
        m_order.add(new cOrder(order, x * 1E9));
    }

    public ArrayList<cStep> GetStep()
    {
        return m_step;
    }

    public cStep GetStep(int index)
    {
        return m_step.get(index);
    }

    public ArrayList<cOrder> GetOrder()
    {
        return m_order;
    }

    public cOrder GetOrder(int index)
    {
        return m_order.get(index);
    }

    public int GetCountStep()
    {
        return m_step.size();
    }

    public int GetCountOrder()
    {
        return m_order.size();
    }

    public void RewriteLastStep(double step, double x)
    {
        int count = m_step.size();
        m_step.get(count - 1).SetStepAndTime(step, x);
    }

    public void RewriteLastOrder(int order, double x)
    {
        int count = m_order.size();
        m_order.get(count - 1).SetOrderAndTime(order, x);
    }
}

