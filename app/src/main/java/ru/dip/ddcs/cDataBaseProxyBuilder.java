package ru.dip.ddcs;

/**
 * Created by В on 20.03.2016.
 */
public class cDataBaseProxyBuilder {
  /*  private OleDbConnection m_connection = Connector.getConnector().getConnection();
    //private OleDbCommand m_query = Connector.getConnector().getQuery();
    private System.Windows.Forms.ToolStripProgressBar m_progressBar;
    private cDataBase m_dataBase;

    public cDataBaseProxyBuilder(cDataBase pDataBase,System.Windows.Forms.ToolStripProgressBar pProgressBar)
    {
        m_dataBase = pDataBase;
        m_progressBar = pProgressBar;

        m_connection = Connector.getConnector().getConnection();

        OleDbCommand query = m_connection.CreateCommand();

        query.CommandText = "select count(zona_id) from zona_SvIv";
        OleDbDataReader reader = query.ExecuteReader();
        reader.Read();
        int count = reader.GetInt32(0);
        reader.Close();

        m_progressBar.Maximum = count + 1;
        //Следующая строка должна быть такой
        //m_progressBar->Position = 1;
        m_progressBar.Value = 1;
        query.Cancel();

        cSeriiProxy serii;
        cZonaProxy zona;
        int id;

        query.CommandText = "select distinct serii_id from serii_SvIv where serii_id > 0";
        reader = query.ExecuteReader();
        while( reader.Read() )
        {
            id = reader.GetInt32(0);
            serii = new cSeriiProxy(id);
            ReadSerii(id,serii);
            pDataBase.AddSerii(serii);
        }
        reader.Close();

        query.CommandText = "select distinct zona_SvIv.zona_id from zona_SvIv where serii_id = 0";
        reader = query.ExecuteReader();
        while( reader.Read() )
        {
            id = reader.GetInt32(0);
            zona = new cZonaProxy(id);
            ReadZona(id,zona);
            zona.SetConnection(m_connection);
            pDataBase.AddZona(zona);
        }
        reader.Close();
        m_dataBase = pDataBase;

        query.Cancel();

        m_progressBar.Value = m_progressBar.Maximum;
    }

    public int GetMaxZonaId()
    {
        int res = m_dataBase.GetMaxZonaId();
        return res;
    }

    public int GetMaxSeriiId()
    {
        int res = m_dataBase.GetMaxSeriiId();
        return res;
    }
    public int GetMaxDislocId()
    {
        int res = m_dataBase.GetMaxDislocId();
        return res;
    }

    public void SetProgressBar(ToolStripProgressBar pProgressBar)
    {
        m_progressBar = pProgressBar;
    }

    public void SaveDataBuilder()
    {
        int countSerii = m_dataBase.GetCountSerii();
        int countZona = m_dataBase.GetCountZona();
        int countAllZona = countZona;
        for(int idSerii = 0; idSerii < countSerii; idSerii++)
        {
            countAllZona += m_dataBase.GetSerii(idSerii).GetCountZona();
        }

        m_progressBar.ProgressBar.Invoke((MethodInvoker)delegate {m_progressBar.Maximum = countAllZona + 1; });
        //m_progressBar.Maximum = countAllZona + 1;
        m_progressBar.ProgressBar.Invoke((MethodInvoker)delegate { m_progressBar.Value = 0; });
        //m_progressBar.Value = 0;

        cSeriiBase serii;
        cZonaBase zona;
        // ----- Проверим серии на необходимость сохранения, изменения имени или удаления
        for(int idSerii = 0; idSerii < countSerii; idSerii++)
        {
            serii = m_dataBase.GetSerii(idSerii);
            if(serii.GetState() == cSeriiBase.State.Solvered)
            {
                SaveSerii(serii);
            }
            else if(serii.GetState() == cSeriiBase.State.Deleted)
            {
                DeleteSerii(serii);
            }
            else if (serii.NameRewrite())
            {
                UpdateParametersSerii(serii);
            }
            else
            {
                // ----- Проверим зоны внутри серий на необходимость
                // ----- сохранения, изменения имени или удаления
                int count = serii.GetCountZona();
                bool flagDelZona = false;
                for (int id = 0; id < count; id++)
                {
                    zona = serii.GetZona(id);
                    if (zona.GetState() == cZonaBase.State.Deleted)
                    {
                        // ----- Удаляем зону из сериии
                        DeleteZona(zona);
                        serii.GetParameters().SetN(serii.GetParameters().GetN()-1);
                        flagDelZona = true;
                    }
                    else if (zona.GetNameRewrite())
                    {
                        if (flagDelZona == false)
                        {
                            UpdateParametersZona(zona);
                        }
                    }
                }
                // ------ Сохраняем обновленные параметры серии,
                // ------ если из нее была удалена хотя бы одна зона
                if (flagDelZona == true)
                {
                    UpdateParametersSerii(serii);
                }
            }
        }

        // ----- Проверим зоны на необходимость сохранения, изменения имени или удаления
        for(int idZona = 0; idZona < countZona; idZona++)
        {
            zona = m_dataBase.GetZona(idZona);
            if(zona.GetState() == cZonaBase.State.Solvered)
            {
                SaveZona(0,zona);
            }
            else if(zona.GetState() == cZonaBase.State.Deleted)
            {
                DeleteZona(zona);
            }
            else if (zona.GetNameRewrite())
            {
                UpdateParametersZona( zona );
            }
        }

        m_progressBar.ProgressBar.Invoke((MethodInvoker)delegate { m_progressBar.Value = m_progressBar.Maximum; });
        //m_progressBar.Value = m_progressBar.Maximum;
        m_progressBar.ProgressBar.Invoke((MethodInvoker)delegate { m_progressBar.Value = 0; });
    }

    public cDataBase GetDataBase()
    {
        return m_dataBase;
    }

    private void ReadSerii(double pId,cSeriiProxy pSerii)
    {
        OleDbCommand query = m_connection.CreateCommand();

        ReadSrednZnachSerii(pId,pSerii);
        ReadParametersSerii(pId,pSerii);

        cZonaProxy zona;
        int id;

        query.CommandText = "select distinct zona_SvIv.zona_id from zona_SvIv where (serii_id="+pId.ToString()+")and(zona_id>0)";
        OleDbDataReader reader = query.ExecuteReader();
        while( reader.Read() )
        {
            id = reader.GetInt32(0);
            zona = new cZonaProxy(id);
            ReadZona(id,zona);
            zona.SetConnection(m_connection);
            pSerii.AddZona(zona);
        }
        reader.Close();

        pSerii.GetParameters().SetMetall(pSerii.GetZona(0).GetParameters().GetMetall());

        query.Cancel();
    }

    private void ReadZona(int pId,cZonaProxy pZona)
    {
        OleDbCommand query = m_connection.CreateCommand();

        ReadSrednZnachZona(pId,pZona);
        ReadParametersZona(pId,pZona);

        cDisloc disloc;
        int idDislocInZona;
        int idDisloc;

        query.CommandText = "select distinct disloc_id, N from disloc_SvIv where (zona_id="+pId.ToString()+")and(disloc_id>0)";
        OleDbDataReader reader = query.ExecuteReader();
        while( reader.Read() )
        {
            idDisloc = reader.GetInt32(0);
            idDislocInZona = reader.GetInt32(1);
            disloc = new cDisloc(idDislocInZona);
            ReadSrednZnachDisloc(idDisloc,disloc);
            pZona.AddDisloc(disloc, idDisloc,false);
        }
        reader.Close();
        query.Cancel();

        ReadPeremennaya(pZona);

        m_progressBar.ProgressBar.Invoke((MethodInvoker)delegate { m_progressBar.Value++; });
        //m_progressBar.Value++;
    }
    //void ReadDisloc(double,cDisloc*);

    private void ReadSrednZnachSerii(double pId,cSeriiBase pSerii)
    {
        OleDbCommand query = m_connection.CreateCommand();

        int N;
        double v,time,KinEnerg;

        query.CommandText = "select N,v_sr,srzns.T*1e15 as t,E_sr*1e15 as E_sr from serii_SvIv s,sredn_zn_serii_SvIv srzns where (s.serii_id=srzns.sredn_zn_serii_id)and(s.serii_id="+pId.ToString()+")";
        OleDbDataReader reader = query.ExecuteReader();
        reader.Read();
        N = Convert.ToInt32(reader.GetValue(0));
        v = reader.GetDouble(1);
        time = reader.GetDouble(2)/1e15;
        KinEnerg = reader.GetDouble(3)/1e15;
        reader.Close();
        query.Cancel();

        pSerii.SetSrednZnach(time,KinEnerg,(double)N,v);
    }

    private void ReadSrednZnachZona(double pId,cZonaBase pZona)
    {
        OleDbCommand query = m_connection.CreateCommand();

        double time,radius,v,KinEnerg;

        query.CommandText = "select t*1e15 as t,r*1e10 as r,v_sr,E_sr*1e15 as E_sr from zona_SvIv,sredn_zn_zona_SvIv where (zona_id=sredn_zn_zona_id)and(zona_id="+pId.ToString()+")and(zona_id>0)";
        OleDbDataReader reader = query.ExecuteReader();
        reader.Read();
        time = reader.GetDouble(0)/1e15;
        radius = reader.GetDouble(1)/1e10;
        v = reader.GetDouble(2);
        KinEnerg = reader.GetDouble(3)/1e15;
        reader.Close();
        query.Cancel();

        pZona.SetSrednZnach(time,KinEnerg,radius,v,true);
    }

    private void ReadSrednZnachDisloc(double pId,cDisloc pDisloc)
    {
        OleDbCommand query = m_connection.CreateCommand();

        double time,radius,v,KinEnerg;

        query.CommandText = "select t*1e15 as t,r*1e10 as r,v_sr,E_sr*1e15 as E_sr from disloc_SvIv,sredn_zn_disloc_SvIv where (disloc_id=sredn_zn_disloc_id)and(disloc_id="+pId.ToString()+")and(disloc_id>0)";
        OleDbDataReader reader = query.ExecuteReader();
        reader.Read();
        time = reader.GetDouble(0)/1e15;
        radius = reader.GetDouble(1)/1e10;
        v = reader.GetDouble(2);
        KinEnerg = reader.GetDouble(3)/1e15;
        reader.Close();
        query.Cancel();

        pDisloc.SetSrednZnach(time,KinEnerg,radius,v);
    }

    private void ReadParametersZona(int pId,cZonaBase pZona)
    {
        OleDbCommand query = m_connection.CreateCommand();

        cParametersZona parameters = new cParametersZona();

        query.CommandText = "select * from parametersZona_SvIv,Metall_SvIv where (parameters_id="+pId.ToString()+")and(Metall_SvIv.Metall_id=parametersZona_SvIv.Metall_id)";
        OleDbDataReader reader = query.ExecuteReader();
        reader.Read();
        parameters.SetMetall(reader.GetString(24));
        parameters.SetNapr(reader.GetDouble(2));
        parameters.SetT0(reader.GetDouble(3));
        parameters.SetE0(reader.GetDouble(4));
        parameters.SetR0(reader.GetDouble(5));
        //parameters->GetMinStep() = query->FieldByName("MinStep")->AsString.ToDouble();
        //parameters->GetMaxStep() = query->FieldByName("MaxStep")->AsString.ToDouble();
        parameters.SetPj(reader.GetDouble(6));
        parameters.SetPs(reader.GetDouble(7));
        parameters.SetKsi(reader.GetDouble(8));
        parameters.SetNu(reader.GetDouble(9));
        parameters.SetAlfa(reader.GetDouble(10));
        parameters.SetT(reader.GetDouble(11));
        parameters.SetTauf(reader.GetDouble(12));
        parameters.SetRo(reader.GetDouble(13));
        parameters.SetN(Convert.ToInt32(reader.GetValue(14)));
        parameters.SetLamdaSmall(reader.GetDouble(15));
        parameters.SetLamdaBig(reader.GetDouble(16));
        parameters.SetF(reader.GetDouble(17));
        parameters.SetMetallBool(reader.GetBoolean(18));
        parameters.SetNumberBool(reader.GetBoolean(19));
        parameters.SetTBool(reader.GetBoolean(20));
        parameters.SetPlotnostBool(reader.GetBoolean(21));
        parameters.SetTextBool(true);
        parameters.SetText(reader.GetString(22));
        reader.Close();
        query.Cancel();

        pZona.SetParameters(parameters);
    }

    private void ReadParametersSerii(double pId,cSeriiBase pSerii)
    {
        OleDbCommand query = m_connection.CreateCommand();

        cParametersSerii parameters = new cParametersSerii();

        query.CommandText = "select * from parametersSerii_SvIv,data_serii_SvIv where (parameters_id="+pId.ToString()+")and(data_serii_SvIv.data_serii_id=parametersSerii_SvIv.data_serii_id)";
        OleDbDataReader reader = query.ExecuteReader();
        reader.Read();
        parameters.SetTypeSerii(reader.GetString(12));
        parameters.SetLeftGran(reader.GetDouble(2));
        parameters.SetRightGran(reader.GetDouble(3));
        parameters.SetStep(reader.GetDouble(4));
        parameters.SetMetallBool(reader.GetBoolean(5));
        //parameters->GetMetall() = query->FieldByName("Name")->AsString;
        parameters.SetNBool(reader.GetBoolean(6));
        parameters.SetN((int)pSerii.GetSrednZnach().GetR());
        //parameters->GetT() = query->FieldByName("T")->AsString;
        parameters.SetGr(reader.GetBoolean(8));
        parameters.SetStepBool(reader.GetBoolean(9));
        parameters.SetTextBool(true);
        parameters.SetText(reader.GetString(10));
        reader.Close();
        query.Cancel();

        pSerii.SetParameters(parameters);
    }

    private void ReadPeremennaya(cZonaBase pZona)
    {
        OleDbCommand query = m_connection.CreateCommand();
        cDisloc disloc = pZona.GetDisloc(0);
        query.CommandText = "select * from Peremennaya where zona_id = " + pZona.GetId() + ";";
        OleDbDataReader reader = query.ExecuteReader();
        List<string> list_str = new List<string>(0);
        while (reader.Read())
        {
            list_str.Add( reader.GetString(1) );
        }
        reader.Close();
        query.Cancel();

        int len = list_str.Count;
        string[] str = new string[len];
        for (int i = 0; i < len; ++i)
        {
            str[i] = list_str[i];
        }
        disloc.AxisName = str;
    }

    //void InsertDataDislocOpen();
    private void InsertDataDisloc(double t, double E, double r, double v, int disloc_id, int data_disl_id)
    {
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "insert into data_disloc_SvIv values(" +
                data_disl_id.ToString() + "," +
                t.ToString().Replace(',', '.') + "," +
                E.ToString().Replace(',', '.') + "," +
                r.ToString().Replace(',', '.') + "," +
                v.ToString().Replace(',', '.') + "," +
                disloc_id.ToString() + ");";
        query.ExecuteNonQuery();
    }
    //void InsertDataDislocClose();

    private void InsertDisloc(int pIdDisloc, int pIdDislocInZona, int pIdZona)
    {
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "insert into disloc_SvIv values(" + pIdDisloc + "," + pIdDislocInZona + "," + pIdZona + ");";
        query.ExecuteNonQuery();
    }

    private void InsertZona(int pZonaId, int pSeriiId, int pRashId)
    {
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "insert into zona_SvIv values("+pZonaId.ToString()+","+pSeriiId.ToString()+","+pRashId.ToString()+");";
        query.ExecuteNonQuery();
    }

    private void InsertSerii(int pSeriiId)
    {
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "insert into serii_SvIv values ("+pSeriiId.ToString()+","+pSeriiId.ToString()+");";
        query.ExecuteNonQuery();
    }

    private void InsertPeremennaya(cZonaBase pZona)
    {
        string[] str = pZona.GetDisloc(0).AxisName;

        if (str==null)
        {
            return;
        }

        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "select * from Peremennaya;";
        OleDbDataReader reader = query.ExecuteReader();
        int id = 0;
        if (reader.Read())
        {
            reader.Close();
            query.CommandText = "select max(per_id) from Peremennaya;";
            reader = query.ExecuteReader();
            reader.Read();
            id = reader.GetInt32(0) + 1;
        }
        reader.Close();

        int length = str.Length;
        int zona_id = pZona.GetId();
        // ----- Сохраним список осей
        for (int i = 0; i < length; ++i, ++id)
        {
            query.CommandText = "insert into Peremennaya values (" + id + ",\"" + str[i] + "\"," + zona_id + ");";
            query.ExecuteNonQuery();
        }
    }

    private void InsertParametersZona(cZonaBase pZona)
    {
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "select Metall_id from Metall_SvIv where Name=\""+pZona.GetParameters().GetMetall().ToString()+"\";";
        OleDbDataReader reader = query.ExecuteReader();
        reader.Read();
        double Metall_id = (double)reader.GetInt32(0);
        reader.Close();

        query.CommandText = "insert into parametersZona_SvIv values("+
                pZona.GetId().ToString().Replace(',', '.') + "," +
                Metall_id.ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetNapr().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetT0().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetE0().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetR0().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetPj().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetPs().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetKsi().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetNu().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetAlfa().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetT().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetTauf().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetRo().ToString().Replace(',', '.') + ",\"" +
                pZona.GetParameters().GetN().ToString() + "\"," +
                pZona.GetParameters().GetLamdaSmall().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetLamdaBig().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetF().ToString().Replace(',', '.') + "," +
                pZona.GetParameters().GetMetallBool().ToString() + "," +
                pZona.GetParameters().GetNumberBool().ToString() + "," +
                pZona.GetParameters().GetTBool().ToString() + "," +
                pZona.GetParameters().GetPlotnostBool().ToString() + ",\"" +
                pZona.GetParameters().GetText().ToString() + "\");";
        query.ExecuteNonQuery();

    }

    private void InsertParametersSerii(int pParamId,cSeriiBase pSerii)
    {
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "select data_serii_id from data_serii_SvIv where data=\""+pSerii.GetParameters().GetTypeSerii().ToString()+"\";";
        OleDbDataReader reader = query.ExecuteReader();
        reader.Read();
        int data_serii_id = Convert.ToInt32(reader.GetValue(0));
        reader.Close();

        query.CommandText = "insert into parametersSerii_SvIv values(" +
                pParamId.ToString() + "," +
                data_serii_id.ToString() + "," +
                pSerii.GetParameters().GetLeftGran().ToString().Replace(',', '.') + "," +
                pSerii.GetParameters().GetRightGran().ToString().Replace(',', '.') + "," +
                pSerii.GetParameters().GetStep().ToString().Replace(',', '.') + "," +
                pSerii.GetParameters().GetMetallBool() + "," +
                pSerii.GetParameters().GetNBool().ToString() + "," +
                pSerii.GetParameters().GetTBool().ToString() + "," +
                pSerii.GetParameters().GetGrBool().ToString() + "," +
                pSerii.GetParameters().GetStepBool().ToString() + ",\"" +
                pSerii.GetParameters().GetText().ToString() + "\");";
        query.ExecuteNonQuery();
    }

    private void InsertSrednZnDisloc(int sredn_zn_disloc_id, double t, double r, double v, double E)
    {
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "insert into sredn_zn_disloc_SvIv values(" +
                sredn_zn_disloc_id.ToString() + "," +
                t.ToString().Replace(',', '.') + "," +
                r.ToString().Replace(',', '.') + "," +
                v.ToString().Replace(',', '.') + "," +
                E.ToString().Replace(',', '.') + ");";
        query.ExecuteNonQuery();
    }

    private void InsertSrednZnZona(int sredn_zn_zona_id, double t, double r, double v, double E)
    {
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "insert into sredn_zn_zona_SvIv values(" +
                sredn_zn_zona_id.ToString() + "," +
                t.ToString().Replace(',', '.') + "," +
                r.ToString().Replace(',', '.') + "," +
                v.ToString().Replace(',', '.') + "," +
                E.ToString().Replace(',', '.') + ");";
        query.ExecuteNonQuery();
    }

    private void InsertSrednZnSerii(int sredn_zn_serii_id, int N, double v, double t, double E)
    {
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "insert into sredn_zn_serii_SvIv values(" +
                sredn_zn_serii_id.ToString() + "," +
                N.ToString() + "," +
                v.ToString().Replace(',', '.') + "," +
                t.ToString().Replace(',', '.') + "," +
                E.ToString().Replace(',', '.') + ");";
        query.ExecuteNonQuery();
    }

    private void SaveSerii(cSeriiBase pSerii)
    {
        int count = pSerii.GetCountZona();
        int idSerii = pSerii.GetId();

        InsertParametersSerii(idSerii,pSerii);

        InsertSerii(idSerii);

        cSrednZnach srednZnach = pSerii.GetSrednZnach();
        InsertSrednZnSerii(idSerii,
                pSerii.GetParameters().GetN(),
                srednZnach.GetV() / srednZnach.GetCount(),
                srednZnach.GetT(),
                srednZnach.GetE() / srednZnach.GetCount());

        cZonaBase zona;
        for(int id = 0; id < count; id++)
        {
            zona = pSerii.GetZona(id);
            if(zona.GetState() == cZonaBase.State.Solvered)
            {
                SaveZona(idSerii, zona);
            }
            else if(zona.GetState() == cZonaBase.State.Deleted)
            {
                DeleteZona(zona);
            }
        }
    }

    private void SaveZona(int pIdSerii,cZonaBase pZona)
    {
        m_progressBar.ProgressBar.Invoke((MethodInvoker)delegate { m_progressBar.Value++; });
        //m_progressBar.Value++;
        int count = pZona.GetCountDisloc();
        int zona_id = pZona.GetId();

        InsertParametersZona(pZona);
        InsertZona(zona_id, pIdSerii, 1);
        InsertPeremennaya(pZona);

        cSrednZnach srednZnach = pZona.GetSrednZnach();
        InsertSrednZnZona(pZona.GetId(),
                srednZnach.GetT(),
                srednZnach.GetR(),
                srednZnach.GetV() / srednZnach.GetCount(),
                srednZnach.GetE() / srednZnach.GetCount());

        int max_disloc_id = GetMaxDisloc();
        int max_disloc_data_id = GetMaxDislocData();

        cDisloc disloc;
        for(int id = 0; id < count; ++id)
        {
            disloc = pZona.GetDisloc(id);
            SaveDisloc(zona_id, disloc, max_disloc_id, max_disloc_data_id);
            max_disloc_data_id += disloc.GetCountDislocData();
            ++max_disloc_id;
        }
    }

    private void UpdateParametersZona(cZonaBase pZona)
    {
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "update parametersZona_SvIv set " +
                "MetallBool = " + pZona.GetParameters().GetMetallBool().ToString() + ", " +
                "NumberBool = " + pZona.GetParameters().GetNumberBool().ToString() + ", " +
                "TBool = " + pZona.GetParameters().GetTBool().ToString() + ", " +
                "PlotnostBool = " + pZona.GetParameters().GetPlotnostBool().ToString() + ", " +
                "Name = \"" + pZona.GetParameters().GetText().ToString() + "\" where "+
                "parameters_id = " + pZona.GetId().ToString() + ";";
        query.ExecuteNonQuery();
    }

    private void UpdateParametersSerii(cSeriiBase pSerii)
    {
        int idSerii = pSerii.GetId();
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "update parametersSerii_SvIv set " +
                "M = " + pSerii.GetParameters().GetMetallBool().ToString() + ", " +
                "N = " + pSerii.GetParameters().GetNBool().ToString() + ", " +
                "T = " + pSerii.GetParameters().GetTBool().ToString() + ", " +
                "grBool = " + pSerii.GetParameters().GetGrBool().ToString() + ", " +
                "hBool = " + pSerii.GetParameters().GetStepBool().ToString() + ", " +
                "Name = \"" + pSerii.GetParameters().GetText().ToString() + "\" where " +
                "parameters_id = " + idSerii.ToString() + ";";
        query.ExecuteNonQuery();

        cZonaBase zona;
        int count = pSerii.GetCountZona();
        for(int id = 0; id < count; id++)
        {
            zona = pSerii.GetZona(id);
            if(zona.GetState() != cZonaBase.State.Solvered &&
                    zona.GetState() != cZonaBase.State.Deleted)
            {
                UpdateParametersZona(zona);
            }
        }
    }

    private void SaveDisloc(int pIdZona, cDisloc pDisloc, int pDislocId, int pDislocDataId)
    {
        int dislocId = pDisloc.GetId();
        InsertDisloc(pDislocId, dislocId, pIdZona);

        cSrednZnach srednZnach = pDisloc.GetSrednZnach();
        InsertSrednZnDisloc(pDislocId,
                srednZnach.GetT(),
                srednZnach.GetR(),
                srednZnach.GetV(),
                srednZnach.GetE());

        cDislocData dataDisloc;
        int count = pDisloc.GetCountDislocData();
        //InsertDataDislocOpen();
        try
        {
            for (int id = 0; id < count; ++id)
            {
                dataDisloc = pDisloc.GetDislocData(id);
                InsertDataDisloc(dataDisloc.GetT(), dataDisloc.GetE(),
                        dataDisloc.GetR(), dataDisloc.GetV(), pDislocId, pDislocDataId);
                ++pDislocDataId;
            }
        }
        catch
        {
            int t = 0;
        }
        //InsertDataDislocClose();
    }

    private void DeleteDataBuilder()
    {
        OleDbCommand query = m_connection.CreateCommand();

        try
        {
            query.CommandText = "delete from sredn_zn_disloc_SvIv";
            query.ExecuteNonQuery();
        }
        catch{};
        try
        {
            query.CommandText = "delete from data_disloc_SvIv";
            query.ExecuteNonQuery();
        }
        catch{};
        try
        {
            query.CommandText = "delete from disloc_SvIv";
            query.ExecuteNonQuery();
        }
        catch{};
        try
        {
            query.CommandText = "delete from sredn_zn_zona_SvIv";
            query.ExecuteNonQuery();
        }
        catch{};
        try
        {
            query.CommandText = "delete from zona_SvIv";
            query.ExecuteNonQuery();
        }
        catch{};
        try
        {
            query.CommandText = "delete from parametersZona_SvIv";
            query.ExecuteNonQuery();
        }
        catch{};
        try
        {
            query.CommandText = "delete from parametersSerii_SvIv where parameters_id > 0";
            query.ExecuteNonQuery();
        }
        catch{};
        try
        {
            query.CommandText = "delete from sredn_zn_serii_SvIv";
            query.ExecuteNonQuery();
        }
        catch{};
        try
        {
            query.CommandText = "delete from serii_SvIv where serii_id > 0";
            query.ExecuteNonQuery();
        }
        catch{};
        try
        {
            query.CommandText = "delete from Peremennaya where per_id > 0";
            query.ExecuteNonQuery();
        }
        catch { };
    }

    private void DeleteSerii(cSeriiBase pSerii)
    {
        int count = pSerii.GetCountZona();
        for(int i = 0; i < count; i++)
        {
            DeleteZona(pSerii.GetZona(i));
        }

        OleDbCommand query = m_connection.CreateCommand();

        string serii_id = pSerii.GetId().ToString();
        try
        {
            query.CommandText = "delete from sredn_zn_serii_SvIv where sredn_zn_serii_id=" + serii_id + ";";
            query.ExecuteNonQuery();
        }
        catch{};

        try
        {
            query.CommandText = "delete from serii_SvIv where serii_id=" + serii_id + ";";
            query.ExecuteNonQuery();
        }
        catch{};

        try
        {
            query.CommandText = "delete from parametersSerii_SvIv where parameters_id=" + serii_id + ";";
            query.ExecuteNonQuery();
        }
        catch{};
    }

    private void DeleteZona(cZonaBase pZona)
    {
        OleDbCommand query = m_connection.CreateCommand();
        string zona_id = pZona.GetId().ToString();
        try
        {
            int id;
            query.CommandText = "select disloc_id from disloc_SvIv,zona_SvIv where (disloc_SvIv.zona_id=" + zona_id + ")and(disloc_SvIv.zona_id=zona_SvIv.zona_id)";
            OleDbDataReader reader = query.ExecuteReader();
            while( reader.Read() )
            {
                id = reader.GetInt32(0);
                DeleteDisloc(id);
            }
            reader.Close();
        }
        catch{};

        try
        {
            query.CommandText = "delete from sredn_zn_zona_SvIv where sredn_zn_zona_id=" + zona_id + ";";
            query.ExecuteNonQuery();
        }
        catch{};

        try
        {
            query.CommandText = "delete from Peremennaya where zona_id=" + zona_id + ";";
            query.ExecuteNonQuery();
        }
        catch { };

        try
        {
            query.CommandText = "delete from zona_SvIv where zona_id=" + zona_id + ";";
            query.ExecuteNonQuery();
        }
        catch{};

        try
        {
            query.CommandText = "delete from parametersZona_SvIv where parameters_id=" + zona_id + ";";
            query.ExecuteNonQuery();
        }
        catch{};

    }

    private void DeleteDisloc(int pDislocId)
    {
        OleDbCommand query = m_connection.CreateCommand();
        try
        {
            query.CommandText = "delete from data_disloc_SvIv where disloc_id="+pDislocId.ToString()+";";
            query.ExecuteNonQuery();
        }
        catch{};
        try
        {
            query.CommandText = "delete from sredn_zn_disloc_SvIv where sredn_zn_disloc_id="+pDislocId.ToString()+";";
            query.ExecuteNonQuery();
        }
        catch{};
        try
        {
            query.CommandText = "delete from disloc_SvIv where disloc_id=" + pDislocId.ToString() + ";";
            query.ExecuteNonQuery();
        }
        catch{};
    }

    private int GetMaxDisloc()
    {
        //int id = m_dataBase.GetMaxDislocId();
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "select * from disloc_SvIv;";
        OleDbDataReader reader = query.ExecuteReader();
        int id = 0;
        if (reader.Read())
        {
            reader.Close();
            query.CommandText = "select max(per_id) from Peremennaya;";
            reader = query.ExecuteReader();
            reader.Read();
            id = reader.GetInt32(0);
        }
        reader.Close();
        return id + 1;
    }

    private int GetMaxDislocData()
    {
        OleDbCommand query = m_connection.CreateCommand();

        int count = 0;
        query.CommandText = "select data_disloc_id from data_disloc_SvIv";
        OleDbDataReader reader = query.ExecuteReader();
        // ----- Достаточно проверить, что есть хотябы одна запись
        if (reader.Read())
        {
            count++;
        }
        reader.Close();

        int max_disloc_data = 0;
        // ----- Если есть хотя бы одна запись
        if (count > 0)
        {
            query.CommandText = "select Max(data_disloc_id) as max_data_disloc_id from data_disloc_SvIv";
            reader = query.ExecuteReader();
            if (reader.Read())
            {
                max_disloc_data = Convert.ToInt32(reader.GetValue(0));
            }
            reader.Close();
        }

        return max_disloc_data + 1;
    }

    public void Dispose()
    {
        GC.SuppressFinalize(this);
    }


    {
        Dispose();
    }*/
}

