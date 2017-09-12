package ru.dip.ddcs;

/**
 * Created by В on 20.03.2016.
 */
public class cDislocItself <strong> extends cDisloc {
    double angle = 0;
    public cDislocItself(double pId)
    {
        super((int)pId);
        m_modelId = 11;
    }
    public cDislocItself(int pId)
    {
        super(pId);
        m_modelId = 11;
    }

    public void Dispose()
    {
       System.gc(); /*GC.SuppressFinalize(this);*/
    }

    {
        Dispose();
    }

    public int GetCountDisloc()
    {
        if (angle != 0)
        {
            return (int) (90.0 / angle + 1);
        }
        return 0;
    }

    public void SetAngle(double pAngle)
    {
        angle = pAngle;
    }

    public double GetEngle(int index)
    {
        return angle * index;
    }

    public double GetMaxX()
    {
        int countPoint = GetCountDisloc();
        if (countPoint > 0)
        {
            int countData = super.GetCountDislocData();
            double maxX = 0, tmp;
            for (int i = 0; i < countPoint; i++)
            {
                tmp = (super.GetDislocData(countData - 1)).GetParam(0);
                if (maxX < tmp)
                {
                    maxX = tmp;
                }
            }
            return maxX;
        }
        return 0;
    }

    public double GetMinMaxX()
    {
        int countPoint = GetCountDisloc();
        if (countPoint > 0)
        {
            int countData = super.GetCountDislocData();
            double minX = (super.GetDislocData(0)).GetParam(0);
            double tmp;
            for (int i = 0; i < countPoint; i++)
            {
                tmp = (super.GetDislocData(countData - 1)).GetParam(0);
                if ( tmp < minX )
                {
                    minX = tmp;
                }
            }
            return minX;
        }
        return 0;
    }

    public double GetParam(double pX, int pParam, int pNumAngle)
    {
        int countAngle = GetCountDisloc();
        int par;
        //countAngle = 5138;
        if (pParam > 0)
            par = (pParam - 1) * countAngle + pNumAngle + 1;
        else
            par = 0;
        // ----- Ïîãðåøíîñòü ïðè ñðàâíåíèè âåùåñòâåííûõ ÷èñåë
        double eps = pX * 1E-10;
        // ----- Êîëè÷åñòâî ñîõðàíåííûõ òî÷åê
        int count = super.GetCountDislocData();
        // ----- Èíäåêñû ëåâîé, ïðàâîé è öåíòðàëüíîé òî÷êè
        int left_index = 0;
        int right_index = count - 1;
        int center_index = (right_index - left_index) / 2;
        // ----- Çíà÷åíèå X äëÿ ëåâîé, ïðàâîé è öåíòðàëüíîé òî÷êè
        double left = GetDislocData(left_index).GetParam(0);
        double right = GetDislocData(right_index).GetParam(0);
        double center = GetDislocData(center_index).GetParam(0);
        // ----- Åñëè èñêîìîé òî÷êè íåò, òî âîçâðàùàåì çíà÷åíèå ïîñëåäíåé òî÷êè
        if (right < pX)
        {
            return GetDislocData(count - 1).GetParam(par);
        }
        // ----- Åñëè ëåâàÿ òî÷êà ñîâïàëà ñ èñêîìîé, òî âîçâðàùàåì çíà÷åíèå â ýòîé òî÷êå
        if (Math.abs(left - pX) < eps)
        {
            return GetDislocData(left_index).GetParam(par);
        }
        // ----- Åñëè ïðàâàÿ òî÷êà ñîâïàëà ñ èñêîìîé, òî âîçâðàùàåì çíà÷åíèå â ýòîé òî÷êå
        if (Math.abs(right - pX) < eps)
        {
            return GetDislocData(right_index).GetParam(par);
        }
        // ----- Àëãîðèòì ïîëîâèííîãî äåëåíèÿ äëÿ ïîèñêà òî÷åê
        while (true)
        {
            if (center_index == left_index || center_index == right_index)
            {
                break;
            }
            if (center < pX)
            {
                left_index = center_index;
                left = center;
                center_index = (right_index + left_index) / 2;
                center = GetDislocData(center_index).GetParam(0);
                continue;
            }
            if (center > pX)
            {
                right_index = center_index;
                right = center;
                center_index = (right_index + left_index) / 2;
                center = GetDislocData(center_index).GetParam(0);
                continue;
            }
            // ----- Åñëè öåíòðàëüíàÿ òî÷êà ñîâïàëà ñ èñêîìîé, òî âîçâðàùàåì çíà÷åíèå â ýòîé òî÷êå
        if (Math.abs(center - pX) < eps)
            {
                return GetDislocData(center_index).GetParam(par);
            }
        }
        // ----- Èñêîìîé òî÷êè íå íàøëè, íî íàéäåíû ñîñåäíèè òî÷êè. Áóäåì èíòåðïîëèðîâàòü çíà÷åíèå ëèíåéíîé èíòåðïîëÿöèåé
        double y_left = GetDislocData(left_index).GetParam(par);
        double y_right = GetDislocData(right_index).GetParam(par);
        double res = (pX - left) * (y_right - y_left) / (right - left) + y_left;
        return res;
    }
}

