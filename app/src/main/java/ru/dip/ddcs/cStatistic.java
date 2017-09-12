package ru.dip.ddcs;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by В on 19.03.2016.
 */
public class cStatistic {
    private ArrayList<cStatisticData> m_data = new ArrayList<cStatisticData>();
    private cStatisticData m_statistic;
  //  StatisticForm m_statForm = StatForm.getForm();
    // ----- Индекс шага, который нужно вывести на экран
    private int m_countStep = 0;
    // ----- Индекс порядка, который нужно вывести на экран
    private int m_countOrder = 0;
    // ----- Изменено ли значение последнего сохраненного шага
    private boolean m_countStepMinus = false;
    // ----- Изменено ли значение последнего сохраненного порядка
    private boolean m_countOrderMinus = false;
    private int iter;
    private int iterMax;
    private boolean m_reshow = false;


    private void Update() {
        /*// ----- Возьмем форму статистики
        //StatisticForm statForm = StatForm.getForm();

        // ----- Сохраняем статистику изменения шага
        ArrayList<cStep> steps = m_statistic.GetStep();
        if (m_countStepMinus) {
            --m_countStep;
            m_countStepMinus = false;
        }
        int size = steps.size();
        for (; m_countStep < size; ++m_countStep) {
            if (m_statForm.IsClosing) {
                if (m_reshow) {
                    StatForm.CreateForm();
                    m_statForm = StatForm.getForm();
                    m_statForm.IsClosing = false;
                    m_statForm.Show();
                    m_countStep = 0;
                    m_countOrder = 0;
                    m_countStepMinus = false;
                    m_countOrderMinus = false;
                    m_reshow = false;
                }
                return;
            }
            m_statForm.SetStep(steps[m_countStep].GetStep(), steps[m_countStep].GetTime());
        }
        AxisMetca(steps[size - 1].GetTime(), m_statForm.GetChartStep());
        Application.DoEvents();

        // ----- Сохраняем статистику изменения порядка
        ArrayList<cOrder> orders = m_statistic.GetOrder();
        if (m_countOrderMinus) {
            --m_countOrder;
        }
        size = orders.size();
        for (; m_countOrder < size; ++m_countOrder) {
            if (m_statForm.IsClosing) {
                if (m_reshow) {
                    StatForm.CreateForm();
                    m_statForm = StatForm.getForm();
                    m_statForm.IsClosing = false;
                    m_statForm.Show();
                    m_countStep = 0;
                    m_countOrder = 0;
                    m_countStepMinus = false;
                    m_countOrderMinus = false;
                    m_reshow = false;
                }
                return;
            }
            m_statForm.SetOrder(orders[m_countOrder].GetOrder(), orders[m_countOrder].GetTime());
        }
        AxisMetca(orders[size - 1].GetTime(), m_statForm.GetChartOrder());
        Application.DoEvents();

        ShowStatisticWindow();
        //statForm.ShowDialog();*/
    }

    private void AxisMetca(double x_max, char pChart) {
        /*// ----- Зададим метки на оси X
        double x_metka = 1;
        double x_tmp = x_max;
        if (x_tmp < 1 && x_tmp > 0) {
            while (x_tmp < 1) {
                x_metka *= 0.1;
                x_tmp *= 10;
            }
            x_tmp *= 10;
            int x_tmp2 = (int) x_tmp + 1;
            x_max = x_tmp2 * 0.1 * x_metka;
        } else {
            int x_tmp2 = (int) x_tmp + 1;
            x_max = x_tmp2;
        }
        // ----- Зададим максимальные и минимальные значения на осях
        if (m_statForm.IsClosing) {
            if (m_reshow) {
                StatForm.CreateForm();
                m_statForm = StatForm.getForm();
                m_statForm.IsClosing = false;
                m_statForm.Show();
                m_countStep = 0;
                m_countOrder = 0;
                m_countStepMinus = false;
                m_countOrderMinus = false;
                m_reshow = false;
            }
            return;
        }
        pChart.ChartAreas[0].AxisX.Maximum = x_max;*/
    }

    public cStatistic() {
       /* //sensor =  new Sensor( new cStatisticObserver(&m_step,&m_order) );
        //sensor->Start();
        iterMax = 800;
        iter = 0;*/
    }

    public cStatistic(int count_iter) {
        /*iterMax = count_iter;
        iter = 0;*/
    }

    public void Dispose() {/*
        m_data.removeAll(DeleteStatisticData);
       System.gc(); *//*GC.SuppressFinalize(this);*/
    }

    private static boolean DeleteStatisticData(cStatisticData s) {
        return true;
    }

    {
/*
        Dispose();
*/
    }

    public void CreateNewStatistics() {
       /* //if (m_statistic)
        //{
        //    m_statistic.Dispose();
        //}
        m_statistic = new cStatisticData();
        m_data.add(m_statistic);*/
    }

    public void SetStep(double step, double x) {
        /*m_statistic.SetStep(new cStep(Math.log10(step), x * 1E9));
        if (iter > iterMax) {
            if (m_statForm.IsClosing) {
                if (m_reshow) {
                    StatForm.CreateForm();
                    m_statForm = StatForm.getForm();
                    m_statForm.IsClosing = false;
                    m_statForm.Show();
                    m_countStep = 0;
                    m_countOrder = 0;
                    m_countStepMinus = false;
                    m_countOrderMinus = false;
                    m_reshow = false;
                } else {
                    iter = 0;
                    return;
                }
            }
            Update();
            iter = 0;
        }
        ++iter;*/
    }

    public void SetOrder(int order, double x) {/*
        m_statistic.SetOrder(new cOrder(order, x * 1E9));
        if (iter > iterMax) {
            if (m_statForm.IsClosing) {
                if (m_reshow) {
                    StatForm.CreateForm();
                    m_statForm = StatForm.getForm();
                    m_statForm.IsClosing = false;
                    m_statForm.Show();
                    m_countStep = 0;
                    m_countOrder = 0;
                    m_countStepMinus = false;
                    m_countOrderMinus = false;
                    m_reshow = false;
                } else {
                    iter = 0;
                    return;
                }
            }
            Update();
            iter = 0;
        }
        ++iter;*/
    }

    public void SetStepAndOrder(double step, int order, double x) {
       /* //double x1E9 = x * 1E9;

        // ----- Добавляем инфомацию о шаге
        step = Math.log10(step);
        int count = m_statistic.GetCountStep();
        // ----- Если количество сохраненных точек больше 1, то
        // ----- проверяем равенство последнего сохраненного
        // ----- шага текушему. Если они равны, то изменяем
        // ----- последний сохраненный шаг на текущий.
        if (count > 1) {
            double step_last = m_statistic.GetStep(count - 1).GetStep();
            if (step_last == step) {
                if (m_countStep == count) {
                    m_countStepMinus = true;
                }
                m_statistic.RewriteLastStep(step, x);
            } else {
                m_statistic.SetStep(new cStep(step, x));
            }
        }
        // ----- Первые две точки сохраняем без их анализа.
        else {
            m_statistic.SetStep(new cStep(step, x));
        }

        // ----- Добавляем инфомацию о порядке.
        count = m_statistic.GetCountOrder();
        // ----- Если количество сохраненных точек больше 1, то
        // ----- проверяем равенство последнего сохраненного
        // ----- порядка текушему. Если они равны, то изменяем
        // ----- последний сохраненный порядок на текущий.
        if (count > 1) {
            int order_last = m_statistic.GetOrder(count - 1).GetOrder();
            if (order_last == order) {
                if (m_countOrder == count) {
                    m_countOrderMinus = true;
                }
                m_statistic.RewriteLastOrder(order, x);
            } else {
                m_statistic.SetOrder(new cOrder(order, x));
            }
        }
        // ----- Первые две точки сохраняем без их анализа.
        else {
            m_statistic.SetOrder(new cOrder(order, x));
        }

        // ----- Вывод изменений на экран выполним
        // ----- через определенное число раз
        if (iter > iterMax) {
            if (m_statForm.IsClosing) {
                if (m_reshow) {
                    StatForm.CreateForm();
                    m_statForm = StatForm.getForm();
                    m_statForm.IsClosing = false;
                    m_statForm.Show();
                    m_countStep = 0;
                    m_countOrder = 0;
                    m_countStepMinus = false;
                    m_countOrderMinus = false;
                    m_reshow = false;
                } else {
                    iter = 0;
                    return;
                }
            }
            Update();
            iter = 0;
        }
        ++iter;*/
    }

    public void ShowStatisticWindow() {/*
        if (m_statForm.IsClosing) {
            if (m_reshow) {
                StatForm.CreateForm();
                m_statForm = StatForm.getForm();
                m_statForm.IsClosing = false;
                m_statForm.Show();
                m_countStep = 0;
                m_countOrder = 0;
                m_countStepMinus = false;
                m_countOrderMinus = false;
                m_reshow = false;
            }
            return;
        }
        StatForm.getForm().Show();
        //Application->ProcessMessages();*/
    }

    public void PaintStatisticWindow(int num) {/*
        cStatisticData statistic;
        if (num >= m_data.size())
            return;
        //âðåìåííî ñîõðàíÿåì çíà÷åíèå m_statistic
        statistic = m_statistic;
        //ïåðåïðèñâàåâàåì, ÷òîáû èñïîëüçîâàòü ôóíêöèþ Update()
        m_statistic = m_data.get(num);
        Update();
        m_statistic = statistic;
        //Application->ProcessMessages();*/
    }

    public void CloseStatisticWindow() {/*
        if (m_statForm.IsClosing) {
            if (m_reshow) {
                StatForm.CreateForm();
                m_statForm = StatForm.getForm();
                m_statForm.IsClosing = false;
                m_statForm.Show();
                m_countStep = 0;
                m_countOrder = 0;
                m_countStepMinus = false;
                m_countOrderMinus = false;
                m_reshow = false;
            }
            return;
        }
        StatisticForm statForm = StatForm.getForm();
        statForm.Close();
        //Application.ProcessMessages();*/
    }

    public void ClearStatisticWindow() {/*
        if (m_statForm.IsClosing) {
            if (m_reshow) {
                StatForm.CreateForm();
                m_statForm = StatForm.getForm();
                m_statForm.IsClosing = false;
                m_statForm.Show();
                m_countStep = 0;
                m_countOrder = 0;
                m_countStepMinus = false;
                m_countOrderMinus = false;
                m_reshow = false;
            }
            return;
        }
        StatisticForm statForm = StatForm.getForm();
        statForm.Clear();
        //Application->ProcessMessages();*/
    }

    public void ReShow() {/*
        if (m_statForm.IsClosing) {
            m_statForm.IsClosing = true;
            lock((Object) m_reshow);
            {
                m_reshow = true;
            }
        }*/
    }
}
