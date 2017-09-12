package ru.dip.ddcs;

/**
 * Created by В on 19.03.2016.
 */
public class cDislocData
    {
        //private double m_e;
        //private double m_r;
        //private double m_v;
        //private double m_t;
        private double[] m_mas;

        public cDislocData(double pT,double pE,double pR,double pV)
        {
            m_mas = new double[4];
            m_mas[0] = pT;
            m_mas[1] = pE;
            m_mas[2] = pR;
            m_mas[3] = pV;
        }

        public cDislocData(double[] mas)
        {
            m_mas = mas;
        }

        public double GetT() { return m_mas[0]; }
        public double GetE() { return m_mas[1]; }
        public double GetR() { return m_mas[2]; }
        public double GetV() { return m_mas[3]; }

        public double GetParam(int pId)
        {
            int count = m_mas.length;
            if (pId < count)
            {
                return m_mas[pId];
            }
            else
            {
                return 0;
            }
        }

        public int GetCount()
        {
            return m_mas.length;
        }

    }

