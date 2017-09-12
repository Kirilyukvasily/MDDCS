package ru.dip.ddcs;

import android.widget.EditText;

import java.io.IOException;

/**
 * Created by В on 12.03.2016.
 */
public class cSave {
    // ----- Сохранение в текстовые файлы
    private cEksport m_export = null;

    // ----- Сохранение в базу данных
    private cDataBaseProxyBuilder m_database = null;

    // ----- Сохранение в оперативную память
    // --------- текущая серия зон сдвига
    private cSerii m_serii = null;
    // ------------- = true когда серия расчитывается
    private boolean m_seriiBool = false;
    // --------- текущая зона сдвига
    private cZona m_zona = null;
    // ------------- Средние значения зоны сдвига
    private cSrednZnach m_zonaSrednZnach = null;
    private int m_zonaId = 0;
    // --------- текущая дислокация
    private cDisloc m_disloc = null;
    // ------------- Имена переменных
    private String[] m_axisName = null;
    private int m_dislocId = 0;
    private cSrednZnach m_dislocSrednZnach = null;

    /// <summary>
    /// Сохранять в оперативную память или нет
    /// Для экспорта в текстовые файлы и базы данных
    /// проверка осуществляется через равенство
    /// нулю m_export и m_database соответственно
    /// </summary>
    private boolean m_saveToOperationsMemory = false;

    // ----- On-line отображение графика
    protected cDislocObserver m_dislocObserver;

    public cSave() {
    }

   public cSave(cDataBaseProxyBuilder pDataBaseBuilder, String pKatalogExporta,
                 boolean[] pExportList, cDislocObserver pDislocObserver) {
        /*if (pTypeSaveResults.OperationMemory == EditorOptionsBuilder.eType.SelectCheckBox) {
            // ----- Сохранять в оперативную память
            m_saveToOperationsMemory = true;
        }
        if (pTypeSaveResults.DataBase == EditorOptionsBuilder.eType.SelectCheckBox) {
            // ----- Сохранять в базу данных
            m_database = pDataBaseBuilder;
        }
        if (pTypeSaveResults.TextFiles == EditorOptionsBuilder.eType.SelectCheckBox) {
            // ----- Сохранять в текстовые файлы*/
            m_export = new cEksport(pKatalogExporta, pExportList);
      //  }
        m_dislocObserver = pDislocObserver;
        if (m_dislocObserver != null) {
            if (m_zona != null) {
                m_dislocObserver.SetZona(m_zona);
            }
            if (m_disloc != null) {
                m_dislocObserver.SetDisloc(m_disloc);
            }
        }
    }

    public boolean getSaveToOperationsMemory() {
        return m_saveToOperationsMemory;
    }

    public void setSaveToOperationsMemory(boolean value) {
        m_saveToOperationsMemory = value;
    }


    public void Dispose() {
       System.gc(); /*GC.SuppressFinalize(this);*/
    }

    {
        Dispose();
    }

    public cSerii getSerii() {

        return m_serii;
    }

    public void setSerii(cSerii value) {
        if (m_export != null) {
            m_export.CreateDirectory(value);
        }
        m_seriiBool = true;
        m_serii = value;
    }

    public cZona getZona() {

        return m_zona;
    }

    public void setZona(cZona value) {
        if (m_export != null) {
            m_export.CreateDirectory(value);
        }
        if (m_dislocObserver != null) {
            m_dislocObserver.SetZona(value);
        }
        m_zona = value;

    }

    public cDisloc Disloc;
    public cDisloc getDisloc() {
        {
            return m_disloc;
        }
    }

    public void setDisloc(cDisloc value) {
        if (m_export != null && value != null) {
            m_export.CreateDirectory(value);
        }
        if (m_dislocObserver != null) {
            m_dislocObserver.SetDisloc(value);
        }
        m_disloc = value;

    }

    public void SaveDisloc(double time, double Ek, double R, double V) throws IOException {
        if (m_saveToOperationsMemory) {
            cDislocData dislocData = new cDislocData(time, Ek, R, V);
            // ----- Сохранение в оперативную память
            m_disloc.Add(dislocData);
            // ----- Сохранение в базу данных
            if (m_database != null) {
                //
            }
            // ----- Сохранение в текстовые файлы
            if (m_export != null) {
                m_export.PrintDislocData(dislocData);
            }
            // ----- Он-лайн отображение
            if (m_dislocObserver != null) {
                m_dislocObserver.Paint(dislocData);
            }
        } else {
            // ----- Сохранение средних значений
            m_dislocSrednZnach.Add(time, Ek, R, V);
            // ----- Сохранение в базу данных
            if (m_database != null) {
                //
            }
            // ----- Сохранение в текстовые файлы
            if (m_export != null) {
                m_export.PrintDislocData(time, Ek, R, V);
            }
        }

    }


    public void SaveDisloc(double[] mas) throws IOException {
        if (m_disloc != null) {
            cDislocData dislocData = new cDislocData(mas);
            // ----- Сохранение в оперативную память
            m_disloc.Add(dislocData);
            // ----- Сохранение в базу данных
            if (m_database != null) {
                //
            }
            // ----- Сохранение в текстовые файлы
            if (m_export != null) {
                m_export.PrintDislocData(dislocData, m_dislocId);
            }
        } else {
            cDislocData dislocData = new cDislocData(mas);
            // ----- Сохранение средних значений
            //m_dislocSrednZnach.Add(mas);
            // ----- Сохранение в базу данных
            if (m_database != null) {
                //
            }
            // ----- Сохранение в текстовые файлы
            if (m_export != null) {
                m_export.PrintDislocData(dislocData, m_dislocId);
                //m_export.PrintDislocData(mas);
            }
        }
    }

    public String GetFileName() {
        return m_export.GetFileName();
    }

    public void SaveDislocAxisName(String[] axisName) {
        if (m_saveToOperationsMemory) {
            m_disloc.SetAxisName( axisName);
        } else {
            m_axisName = axisName;
        }
    }

    public void SaveZonaSrednZnach() throws IOException {
        if (m_seriiBool) {
            if (m_export != null) {
                if (m_saveToOperationsMemory) {
                    m_export.PrintZonaSrednZnach(m_zona);
                } else {
                    m_export.PrintZonaSrednZnach(m_zonaId, m_zonaSrednZnach);
                }
            }
        }
    }

    public void SaveDislocSrednZnach(double c, double e0) throws IOException {
        SaveDislocSrednZnach(m_disloc, c, e0);
    }

    public void SaveDislocWithOrientationSrednZnach(double c, double e0) throws IOException {
        if (m_saveToOperationsMemory) {
            m_disloc.SolveSrednZnach(c, e0);
        } else {
            if (m_export != null) {
                m_dislocSrednZnach = m_export.SolveDislocWithOrientationSrednZnach();
            }
        }
    }

    public void SaveDislocSrednZnach(cDisloc disloc, double c, double e0) throws IOException {
        if (m_saveToOperationsMemory) {
            disloc.SolveSrednZnach(c, e0);
            if (m_export != null) {
                m_export.PrintDislocSrednZnach(disloc);
            }
        } else {
            if (m_export != null) {
                m_export.PrintDislocSrednZnach(m_dislocId, m_dislocSrednZnach);
            }
        }
    }

    public void SaveDislocSrednZnach(cDisloc disloc, double c, double[] e0) throws IOException {
        if (m_saveToOperationsMemory) {
            disloc.SolveSrednZnach(c, e0);
            if (m_export != null) {
                m_export.PrintDislocSrednZnach(disloc);
            }
        } else {
            if (m_export != null) {
                m_export.PrintDislocSrednZnach(m_dislocId, m_dislocSrednZnach);
            }
        }
    }

    public void SetStateZona(cZonaBase.State pState) {
        if (m_saveToOperationsMemory) {
            m_zona.SetState(pState);
        }
    }

    public void CreateDisloc(int type, int dislocId) {
        boolean flag=true;
        int countAngle=1;
        if (m_saveToOperationsMemory) {
            if (type == -1) {
                m_disloc = new cDislocItself(dislocId);
                m_disloc.SetAngle(90.0 / countAngle);
            } else if (type == 0) {
                m_disloc = new cDisloc(dislocId);
            } else if (type == 1) {
                m_disloc = (cDisloc) new cDislocWithOrientation(dislocId);
            } else {
                m_disloc = new cDisloc(dislocId, 2);
            }
            m_dislocId = dislocId;
            if (flag) {
                if (m_export != null) {
                    m_export.CreateDirectory(m_disloc);
                }
            }
            if (m_dislocObserver != null) {
                m_dislocObserver.SetDisloc(m_disloc);
            }
        } else {
            m_dislocSrednZnach = new cSrednZnach();
            m_dislocId = dislocId;
            if (m_export != null) {
                m_export.CreateDirectory(dislocId);
            }
        }
    }
    public void CreateDisloc(int type, int dislocId, boolean flag) {
        int countAngle=1;
        if (m_saveToOperationsMemory) {
            if (type == -1) {
                m_disloc = new cDislocItself(dislocId);
                m_disloc.SetAngle(90.0 / countAngle);
            } else if (type == 0) {
                m_disloc = new cDisloc(dislocId);
            } else if (type == 1) {
                m_disloc = (cDisloc) new cDislocWithOrientation(dislocId);
            } else {
                m_disloc = new cDisloc(dislocId, 2);
            }
            m_dislocId = dislocId;
            if (flag) {
                if (m_export != null) {
                    m_export.CreateDirectory(m_disloc);
                }
            }
            if (m_dislocObserver != null) {
                m_dislocObserver.SetDisloc(m_disloc);
            }
        } else {
            m_dislocSrednZnach = new cSrednZnach();
            m_dislocId = dislocId;
            if (m_export != null) {
                m_export.CreateDirectory(dislocId);
            }
        }
    }

    public void CreateZona(int numberZona, cParametersZona pParametersZona) throws IOException {
        if (m_saveToOperationsMemory) {
            m_zona = new cZona(numberZona);
            m_zona.SetState(cZonaBase.State.Solvered);
            m_zona.SetParameters(new cParametersZona(pParametersZona));
            if (m_dislocObserver != null) {
                m_dislocObserver.SetZona(m_zona);
            }
        } else {
            m_zona = null;
            m_zonaSrednZnach = new cSrednZnach(true);
            m_zonaId = numberZona;
        }
        if (m_export != null) {
            m_export.CreateDirectory(numberZona, pParametersZona);
            m_export.PrintZonaParameters(pParametersZona);
        }
    }

    public void CreateSerii(int numberSerii, cParametersSerii pParametersSerii) {
        if (m_saveToOperationsMemory) {
            m_serii = new cSerii(numberSerii);
            m_serii.SetState(cSeriiBase.State.Solvered);
            m_serii.SetParameters(new cParametersSerii(pParametersSerii));
        }
        if (m_export != null) {
            m_serii = null;
            m_seriiBool = true;
            m_export.CreateDirectory(numberSerii, pParametersSerii);
        }
    }

    public void AddDislocForZona() {
        if (m_saveToOperationsMemory) {
            m_zona.AddDisloc(m_disloc);
        } else {
            m_zonaSrednZnach.Add(m_dislocSrednZnach);
        }
    }

    public void AddZonaForSerii() {
        if (m_saveToOperationsMemory) {
            m_serii.AddZona(m_zona);
        }
    }

    public void SetStatistic(cStatistic stat) {
        if (m_saveToOperationsMemory) {
            m_zona.SetStatistic(stat);
        }
    }

    public int GetDislocID() {
        if (m_saveToOperationsMemory) {
            return m_disloc.GetId();
        } else {
            return m_dislocId;
        }
    }

    public void FileRename(double pOrientationEngle) {
        if (m_export != null) {
            String OldFileName = m_export.GetFileName();
            String NewFileName = "";
            int len = OldFileName.length();
            for (int i = len - 1; i >= 0; --i) {
                if (OldFileName.charAt(i) == '.') {
                    NewFileName = OldFileName.substring(0, i) + "_" +String.valueOf( pOrientationEngle) + OldFileName.substring(i, len - i);
                    break;
                }
            }
            m_export.FileRename(OldFileName, NewFileName);
        }
    }

    // ----- Используется для модели cDislocProblemWithOrientation
    public void ObrezanieAfterTime(double pTimeProbega, double pOrientationEngle) throws IOException {
        if (m_saveToOperationsMemory) {
            m_disloc.ObrezanieAfterTime(pTimeProbega);
            if (m_export != null) {
                m_export.FileDelete();
                int count = m_disloc.GetCountDislocData();
                for (int i = 0; i < count; ++i) {
                    cDislocData data = m_disloc.GetDislocData(i);
                    m_export.PrintDislocData(data);
                }
            }
        } else {
            if (m_export != null) {
                m_export.FileDeleteAfterTime(pTimeProbega);
                m_dislocSrednZnach.Dispose();
                m_dislocSrednZnach = m_export.SolveSrednZnach();
            }
        }
        FileRename(pOrientationEngle);
    }

    public double GetMinMaxX() throws IOException {
        if (m_saveToOperationsMemory) {
            int count = m_disloc.GetCountDislocData();
            return m_disloc.GetDislocData(count - 1).GetT();
        } else //if ( m_export != null )
        {
            double res = m_export.GetMinMaxX();
            return res;
        }
    }

    public double GetDiametr(EditText et3) throws IOException {
        if (m_saveToOperationsMemory) {
            int count = m_disloc.GetCountDislocData();
            return m_disloc.GetDislocData(count - 1).GetR();
        } else //if (m_export != null)
        {
            double res = m_export.GetDiametr(et3);
            return res;
        }
    }

    public double GetParam(int dislocId, int param, EditText et3) throws IOException {

        if (m_saveToOperationsMemory) {

            int id = m_disloc.GetId();
            if (dislocId == id) {
                int count = m_disloc.GetCountDislocData();
                return m_disloc.GetDislocData(count - 1).GetParam(param);
            } else {
                int countDisloc = m_zona.GetCountDisloc();
                for (int i = 0; i < countDisloc; ++i) {
                    cDisloc disloc = m_zona.GetDisloc(i);
                    id = disloc.GetId();
                    if (dislocId == id) {
                        int count = disloc.GetCountDislocData();
                        return disloc.GetDislocData(count - 1).GetParam(param);
                    }
                }
            }
        } else if (m_export != null) {
            // Смена нужна потому что в DislocData 1-й и 2-й параметры поменены
            if (param == 1)
                param = 2;
            else if (param == 2)
                param = 1;
            double res = m_export.GetParam(dislocId, param,et3);
            return res;
        }
        return 0;
    }

    public double GetParam(int dislocId, int param) throws IOException {
        if (m_saveToOperationsMemory) {
            int id = m_disloc.GetId();
            if (dislocId == id) {
                int count = m_disloc.GetCountDislocData();
                return m_disloc.GetDislocData(count - 1).GetParam(param);
            } else {
                int countDisloc = m_zona.GetCountDisloc();
                for (int i = 0; i < countDisloc; ++i) {
                    cDisloc disloc = m_zona.GetDisloc(i);
                    id = disloc.GetId();
                    if (dislocId == id) {
                        int count = disloc.GetCountDislocData();
                        return disloc.GetDislocData(count - 1).GetParam(param);
                    }
                }
            }
        } else if (m_export != null) {
            // Смена нужна потому что в DislocData 1-й и 2-й параметры поменены
            if (param == 1)
                param = 2;
            else if (param == 2)
                param = 1;
            double res = m_export.GetParam(dislocId, param);
            return res;
        }
        return 0;
    }

    public double GetCountParam(int dislocId) {
        if (m_saveToOperationsMemory) {
            int id = m_disloc.GetId();
            if (dislocId == id) {
                return m_disloc.GetCountDislocData();
            } else {
                int countDisloc = m_zona.GetCountDisloc();
                for (int i = 0; i < countDisloc; ++i) {
                    cDisloc disloc = m_zona.GetDisloc(i);
                    id = disloc.GetId();
                    if (dislocId == id) {
                        return disloc.GetCountDislocData();
                    }
                }
            }
        }
        return 0;
    }
    public void SaveDisloc(int dislocId, double time, double Ek, double R, double V) throws IOException {
        if (m_export != null)
    {
        m_export.PrintDislocData(time, Ek, R, V, dislocId);
    }

    }
}