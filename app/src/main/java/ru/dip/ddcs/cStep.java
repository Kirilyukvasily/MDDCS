package ru.dip.ddcs;

/**
 * Created by В on 19.03.2016.
 */
public class cStep {
    private double step;
    private double time;

    public cStep(double pStep, double pTime)
    {
        step = pStep;
        time = pTime;
    }

    public double GetStep()
    {
        return step;
    }

    public double GetTime()
    {
        return time;
    }

    public void SetStepAndTime(double pStep, double pTime)
    {
        step = pStep;
        time = pTime;
    }
}

