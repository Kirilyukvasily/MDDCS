package ru.dip.ddcs;

import android.app.Application;

/**
 * Created by В on 20.03.2016.
 */
public class cDislocObserver {
    //private int iter;
    //private int iterMax;
    private void Update() { }
    private cZona m_zona;
    private cDisloc m_disloc = null;
    //private GraficForm m_graficForm = new GraficForm();
    private boolean m_reshow = false;

    public cDislocObserver()
    {
        m_zona = null;
     //   m_graficForm.ClearGraphic();
    }
    public cDislocObserver(cZona zona)
    {
        m_zona = zona;
      //  m_graficForm.ClearGraphic();
    }
    public void Dispose()
    {
       System.gc(); /*GC.SuppressFinalize(this);*/
    }

    {
        Dispose();
    }

    public void SetZona(cZona zona)
    {
        m_zona = zona;//new cZona(zona);
      /* *//* if (m_graficForm.IsClosing)
        {
            if (m_reshow)
            {
                m_graficForm = new GraficForm();
                m_graficForm.SetZona(m_zona);
                m_graficForm.IsClosing = false;
                Show();
                m_reshow = false;
            }
            return;*//*
        }
        m_graficForm.SetZona(m_zona);*/
    }

    public void SetDisloc(cDisloc disloc)
    {
        m_disloc = disloc;
        /*if (m_graficForm.IsClosing)
        {
            if (m_reshow)
            {
                m_graficForm = new GraficForm();
                m_graficForm.SetZona(m_zona);
                m_graficForm.SetDisloc(disloc);
                m_graficForm.IsClosing = false;
                Show();
                m_reshow = false;
            }
            return;
        }
        m_graficForm.SetDisloc(disloc);*/
    }

    public void Paint(cDislocData dislocData)
    {
       /* if (m_graficForm.IsClosing)
        {
            if (m_reshow)
            {
                m_graficForm = new GraficForm();
                m_graficForm.SetZona(m_zona);
                m_graficForm.IsClosing = false;
                Show();
                m_graficForm.SetDisloc(m_disloc);
                m_graficForm.PaintGraphic(m_disloc);
                m_reshow = false;
            }
            return;
        }
        m_graficForm.PaintGraphic();
        Application.DoEvents();*/
    }

    public void Paint(cDislocData pDislocData, int pNum)
    {/*
        if (m_graficForm.IsClosing)
        {
            Paint();
            //m_graficForm.PaintGraphic();
        }
        else
        {
            if( pNum < 0 )
                m_graficForm.PaintGraphic(pDislocData);
            else
                m_graficForm.PaintGraphic(pDislocData,pNum);
            if (m_graficForm.IsClosing == false)
            {
                m_graficForm.Show();
            }
            Application.DoEvents();
        }*/
    }


    public void Show()
    {
       /* if (m_graficForm.IsClosing)
        {
            if (m_reshow)
            {
                m_graficForm = new GraficForm();
                m_graficForm.SetZona(m_zona);
                m_graficForm.IsClosing = false;
                Show();
                m_graficForm.SetDisloc(m_disloc);
                m_graficForm.PaintGraphic(m_disloc);
                m_reshow = false;
            }
            return;
        }
        m_graficForm.Show();*/
    }

    public void Clear()
    {
       /* m_graficForm.ClearGraphic();*/
    }

  /*  public GraficForm GetGraficForm()
    {
        return m_graficForm;
    }*/

    public void ReShow()
    {
       /* if (m_graficForm.IsClosing)
        {
            m_graficForm.IsClosing = true;
            lock ((Object)m_reshow);
            {
                m_reshow = true;
            }
        }*/
    }
}
