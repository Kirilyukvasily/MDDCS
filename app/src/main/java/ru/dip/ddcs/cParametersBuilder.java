package ru.dip.ddcs;

/**
 * Created by В on 03.04.2016.
 */
public class cParametersBuilder {/*
    cParametersZona m_parametersZona;
    cParametersSerii m_parametersSerii;
    //cModelParameters* m_modelParameters;
    //int m_rashirenie;
    double m_T;
    double m_plotnost;
    double m_G;
    double m_B;
    double m_nu;
    double m_b;
    // ----- Номера расчитываемых дислокаций
    String m_num = "";

    private cParametersBuilder() { }

    public cParametersBuilder(String pMetall, int pRastSgatie)
    {
        OleDbConnection m_connection = Connector.getConnector().getConnection();
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "select * from MenedjerParameters_SvIv,Metall_SvIv,data_serii_SvIv where (Metall=Metall_id)and(SaveOrStandart=true)and(Metall_SvIv.Name=\""+pMetall.ToString()+"\")and(data_serii_id=TypeSerii)";
        OleDbDataReader reader = query.ExecuteReader();
        m_parametersZona = new cParametersZona();
        if( reader.Read() )
        {
            m_parametersZona.SetMetall( pMetall.toString() );
            m_parametersZona.SetNapr( reader.getDouble(3) );
            m_parametersZona.SetT0( reader.GetDouble(4) );
            m_parametersZona.SetE0( reader.GetDouble(5) );
            m_parametersZona.SetR0( reader.GetDouble(6) );
            //m_parametersZona->GetPlotnost() =
            //    Query->FieldByName("Plotnost")->AsString.ToDouble();

                *//*
                // ----- Êîä ïåðåíåñåí â EditorOptionsBuilder
                m_modelParameters = new cModelParameters();
                m_modelParameters->GetTNBool() =
                    ( Query->FieldByName("tNBool")->AsInteger ==
                    ModelsTree::eType::SelectCheckBox ? true : false );
                m_modelParameters->GetTN() =
                    Query->FieldByName("tN")->AsString.ToDouble();
                m_modelParameters->GetMinStepBool() =
                    ( Query->FieldByName("minStepBool")->AsInteger ==
                    ModelsTree::eType::SelectCheckBox ? true : false );
                m_modelParameters->GetMaxStepBool() =
                    ( Query->FieldByName("maxStepBool")->AsInteger ==
                    ModelsTree::eType::SelectCheckBox ? true : false );
                m_modelParameters->GetMinStep() =
                    Query->FieldByName("minStep")->AsString.ToDouble();
                m_modelParameters->GetMaxStep() =
                    Query->FieldByName("maxStep")->AsString.ToDouble();
                m_modelParameters->GetTOL() =
                    Query->FieldByName("TOL")->AsString.ToDouble();
                *//*

            m_parametersZona.SetPj( reader.GetDouble(14) );
            m_parametersZona.SetPs( reader.GetDouble(15) );
            m_parametersZona.SetKsi( reader.GetDouble(16) );
            m_parametersZona.SetNu( reader.GetDouble(17) );
            m_parametersZona.SetAlfa( reader.GetDouble(18) );
            m_parametersZona.SetT( reader.GetDouble(19) );
            m_parametersZona.SetTauf( reader.GetDouble(20) );
            m_parametersZona.SetRo( reader.GetDouble(21) );
            m_num = Convert.ToString(reader.GetValue(22));
            //m_parametersZona.SetN( Convert.ToInt32(reader.GetValue(22)) );
            m_parametersZona.SetLamdaSmall( reader.GetDouble(23) );
            m_parametersZona.SetLamdaBig( reader.GetDouble(24) );
            m_parametersZona.SetF( reader.GetDouble(25) );
            int tmp = Convert.ToInt32(reader.GetValue(33));
            m_parametersZona.SetMetallBool(
                    (tmp == (int) ModelsTree.Type.SelectCheckBox));
            tmp = Convert.ToInt32(reader.GetValue(34));
            m_parametersZona.SetNumberBool(
                    (tmp == (int)ModelsTree.Type.SelectCheckBox ? true : false));
            tmp = Convert.ToInt32(reader.GetValue(35));
            m_parametersZona.SetTBool(
                    (tmp == (int)ModelsTree.Type.SelectCheckBox ? true : false));
            tmp = Convert.ToInt32(reader.GetValue(36));
            m_parametersZona.SetPlotnostBool(
                    (tmp == (int)ModelsTree.Type.SelectCheckBox ? true : false));
            tmp = Convert.ToInt32(reader.GetValue(37));
            m_parametersZona.SetTextBool(
                    (tmp == (int)ModelsTree.Type.SelectCheckBox ? true : false));
            m_parametersZona.SetText( reader.GetString(38) );

            tmp = Convert.ToInt32(reader.GetValue(46));
            m_parametersZona.SetMinCountCicle( tmp );
            double tmp_d = Convert.ToDouble(reader.GetValue(47));
            m_parametersZona.SetEpsilonCicle(tmp_d);

            m_parametersSerii = new cParametersSerii();
            m_parametersSerii.SetDataSeriiId( pRastSgatie );
            tmp = Convert.ToInt32(reader.GetValue(26));
            m_parametersSerii.SetSolveSerii(
                    (tmp == (int)ModelsTree.Type.SelectCheckBox ? true : false));
            //bool flag = m_parametersSerii->GetSolveSerii();
            m_parametersSerii.SetTypeSerii( reader.GetString(52) );
            tmp = Convert.ToInt32(reader.GetValue(42));
            m_parametersSerii.SetGr(
                    (tmp == (int)ModelsTree.Type.SelectCheckBox ? true : false));
            tmp_d = Convert.ToDouble(reader.GetValue(28));
            m_parametersSerii.SetLeftGran(tmp_d);
            tmp_d = Convert.ToDouble(reader.GetValue(29));
            m_parametersSerii.SetRightGran(tmp_d);
            tmp = Convert.ToInt32(reader.GetValue(36));
            m_parametersSerii.SetStepBool(
                    ( tmp == (int)ModelsTree.Type.SelectCheckBox ? true : false ) );
            tmp_d = Convert.ToDouble(reader.GetValue(30));
            m_parametersSerii.SetStep(tmp_d);
            tmp = Convert.ToInt32(reader.GetValue(39));
            m_parametersSerii.SetMetallBool(
                    ( tmp == (int)ModelsTree.Type.SelectCheckBox ? true : false ) );
            m_parametersSerii.SetMetall( reader.GetString(49) );
            tmp = Convert.ToInt32(reader.GetValue(40));
            m_parametersSerii.SetNBool(
                    ( tmp == (int)ModelsTree.Type.SelectCheckBox ? true : false ) );
            tmp = Convert.ToInt32(reader.GetValue(41));
            m_parametersSerii.SetTBool(
                    ( tmp == (int)ModelsTree.Type.SelectCheckBox ? true : false ) );

                *//*
                //Óáðàòü
                m_parametersSerii->GetT() =
                    Query->FieldByName("TSerii")->AsString;
                *//*
            tmp = Convert.ToInt32(reader.GetValue(44));
            m_parametersSerii.SetTextBool(
                    (tmp == (int)ModelsTree.Type.SelectCheckBox ? true : false));

            m_parametersSerii.SetText( reader.GetString(45) );

            tmp = Convert.ToInt32(reader.GetValue(31));
            m_parametersSerii.SetArifmProgres(
                    (tmp == (int)ModelsTree.Type.SelectRadioButton ? true : false));

            tmp = Convert.ToInt32(reader.GetValue(32));
            m_parametersSerii.SetGeomProgres(
                    (tmp == (int)ModelsTree.Type.SelectRadioButton ? true : false));
        }

        //m_plotnost = Query->FieldByName("Plotnost")->AsString.ToDouble();
            *//*
            Query->SQL->Text = "select dM.G,dM.B from Metall_SvIv M, data_Metall_SvIv dM where (M.Metall_id=dM.Metall_id)and(M.Name=:name)and(dM.T=:t)";
            Query->Parameters->ParamByName("name")->Value = pMetall;
            Query->Parameters->ParamByName("t")->Value = m_parametersZona->GetT();
            Query->Open();
                m_G = Query->FieldByName("G")->AsString.ToDouble();
                m_B = Query->FieldByName("B")->AsString.ToDouble();
            Query->Close();

            Query->SQL->Text = "select alpha*100000000 as alpha, d0, T0 from Metall_SvIv M, plotnost_Metall_SvIv pM where (M.Metall_id=pM.Metall_id)and(M.Name=:name)";
            Query->Parameters->ParamByName("name")->Value = pMetall;
            Query->Open();
                double alpha = Query->FieldByName("alpha")->AsString.ToDouble()/1e8;
                double d0 = Query->FieldByName("B")->AsString.ToDouble();
                double T0 = Query->FieldByName("B")->AsString.ToDouble();
                m_plotnost =  d0 / ( 1 + 3 * alpha * ( m_T.ToDouble() - T0 ) );
            Query->Close();
            *//*
    }

    public string GetN()
    {
        return m_num;
    }

    public double GetT()
    {
        return m_T;
    }

    public double GetPlotnost()
    {
        return m_plotnost;
    }

    public double GetG()
    {
        return m_G;
    }

    public double GetB()
    {
        return m_B;
    }

    public double GetCoeffPuassona()
    {
        return m_nu;
    }

    public double GetVectorBurgersa()
    {
        return m_b;
    }

    public void SetT(double pTemperatura, string metall)
    {
        OleDbConnection m_connection = Connector.getConnector().getConnection();
        OleDbCommand query = m_connection.CreateCommand();


        m_T = pTemperatura;

        //Ðàíåå èñïîëüçîâàëñÿ çàïðîñ: Query->SQL->Text = "select dM.G as G,dM.B*10000000000 as B from Metall_SvIv M, data_Metall_SvIv dM where (M.Metall_id=dM.Metall_id)and(M.Name=:name)and(dM.T=:t)";
        query.CommandText = "select value from MetallPropety mp, Metall_SvIv m, MetallParametr mpar, SelectedType st where mpar.id=mp.parametr_id and mp.metall_id=m.Metall_id and mpar.name_abbreviated = " +
                "\"G\" and m.Name=\"" +
                metall.ToString() + "\" and mp.T=" +
                pTemperatura.ToString() + " and st.type_id=mp.selected and st.data=\"SelectCheckBox\"";
        OleDbDataReader reader = query.ExecuteReader();
        if( reader.Read() ){
            m_G = reader.GetDouble(0);
        }
        else{
            Interpolate( metall, "G" );
        }
        reader.Close();

        //Query->SQL->Text = "select value*10000000000 as B from MetallPropety mp, Metall_SvIv m, MetallParametr mpar, SelectedType st where mpar.id=mp.parametr_id and mp.metall_id=m.Metall_id and mpar.name_abbreviated=:parametr and m.Name=:name and mp.T=:t and st.type_id=mp.selected and st.data=:type";
        query.CommandText = "SELECT value*10000000000 AS B FROM MetallPropety AS mp, Metall_SvIv AS m, MetallParametr AS mpar, SelectedType st " +
                "WHERE mpar.id=mp.parametr_id and mp.metall_id=m.Metall_id and mpar.name_abbreviated=" +
                "\"B\" and m.Name=\"" +
                metall.ToString() + "\" and mp.T=" +
                pTemperatura.ToString() + " and st.type_id=mp.selected and st.data=\"SelectCheckBox\";";
        reader = query.ExecuteReader();
        if ( reader.Read() )
        {
            m_B = reader.GetDouble(0) / 1e10; //Query->FieldByName("B")->AsString.ToDouble() / 1e10;
        }
        else{
            Interpolate( metall, "B" );
        }
        reader.Close();

        query.CommandText = "select value from MetallPropety mp, Metall_SvIv m, MetallParametr mpar, SelectedType st " +
                "where mpar.id=mp.parametr_id and mp.metall_id=m.Metall_id and mpar.name_abbreviated=" +
                "\"d\" and m.Name=\"" +
                metall.ToString() + "\" and mp.T=" +
                pTemperatura.ToString() + " and st.type_id=mp.selected and st.data=\"SelectCheckBox\"";
        reader = query.ExecuteReader();
        if ( reader.Read() )
        {
            m_plotnost = reader.GetDouble(0);
        }
        else{
            Interpolate( metall, "d" );
        }
        reader.Close();

        query.CommandText = "select value from MetallPropety mp, Metall_SvIv m, MetallParametr mpar, SelectedType st " +
                "where mpar.id=mp.parametr_id and mp.metall_id=m.Metall_id and mpar.name_abbreviated=" +
                "\"nu\" and m.Name=\"" +
                metall.ToString() + "\" and mp.T=" +
                pTemperatura.ToString() + " and st.type_id=mp.selected and st.data=\"SelectCheckBox\"";
        reader = query.ExecuteReader();
        if ( reader.Read() )
        {
            m_nu = reader.GetDouble(0);
        }
        else{
            Interpolate( metall, "nu" );
        }
        reader.Close();

        query.CommandText = "select value*10000000000 as b from MetallPropety mp, Metall_SvIv m, MetallParametr mpar, SelectedType st " +
                "where mpar.id=mp.parametr_id and mp.metall_id=m.Metall_id and mpar.name_abbreviated=" +
                "\"vector b\" and m.Name=\"" +
                metall.ToString() + "\" and mp.T=" +
                pTemperatura.ToString() + " and st.type_id=mp.selected and st.data=\"SelectCheckBox\"";
        reader = query.ExecuteReader();
        if ( reader.Read() )
        {
            m_b = reader.GetDouble(0)/1e10;
        }
        else{
            Interpolate( metall, "vector b" );
        }
        reader.Close();
        query.Cancel();
    }

    public void SaveParameters(string N)
    {
        m_num = N;
        OleDbConnection m_connection = Connector.getConnector().getConnection();
        OleDbCommand query = m_connection.CreateCommand();
        OleDbDataReader reader;

        query.CommandText = "select data_serii_id from data_serii_SvIv where data=\"" +
                m_parametersSerii.GetTypeSerii().ToString() + "\";";
        reader = query.ExecuteReader();
        reader.Read();
        int typeSerii = Convert.ToInt32(reader.GetValue(0));
        reader.Close();

        query.CommandText = "select MenedjerParametersId from MenedjerParameters_SvIv,Metall_SvIv " +
                "where (Metall=Metall_id)and(SaveOrStandart=true)and(Name=\"" +
                m_parametersZona.GetMetall().ToString() + "\");";
        reader = query.ExecuteReader();
        reader.Read();
        int id = Convert.ToInt32(reader.GetValue(0));
        reader.Close();

        string SolveSerii;
        if( m_parametersSerii.GetSolveSerii() )
            SolveSerii = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            SolveSerii = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        string ArifmProgStep;
        if( m_parametersSerii.GetArifmProgres() )
            ArifmProgStep = ((int)ModelsTree.Type.SelectRadioButton).ToString();
        else
            ArifmProgStep = ((int)ModelsTree.Type.NoSelectRadioButton).ToString();

        string GeomProgStep;
        if( m_parametersSerii.GetGeomProgres() )
            GeomProgStep = ((int)ModelsTree.Type.SelectRadioButton).ToString();
        else
            GeomProgStep = ((int)ModelsTree.Type.NoSelectRadioButton).ToString();

        string MetallZonaBool;
        if( m_parametersZona.GetMetallBool() )
            MetallZonaBool = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            MetallZonaBool = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        string NumberZonaBool;
        if( m_parametersZona.GetNumberBool() )
            NumberZonaBool = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            NumberZonaBool = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        string TemperaturaZonaBool;
        if( m_parametersZona.GetTBool() )
            TemperaturaZonaBool = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            TemperaturaZonaBool = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        string PlotnostZonaBool;
        if( m_parametersZona.GetPlotnostBool() )
            PlotnostZonaBool = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            PlotnostZonaBool = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        string TextZonaBool;
        if( m_parametersZona.GetTextBool() )
            TextZonaBool = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            TextZonaBool = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        string MetallSeriiBool;
        if( m_parametersSerii.GetMetallBool() )
            MetallSeriiBool = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            MetallSeriiBool = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        string NumberSeriiBool;
        if( m_parametersSerii.GetNBool() )
            NumberSeriiBool = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            NumberSeriiBool = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        string TemperaturaSeriiBool;
        if( m_parametersSerii.GetTBool() )
            TemperaturaSeriiBool = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            TemperaturaSeriiBool = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        string GRParameterBool;
        if (m_parametersSerii.GetGrBool())
            GRParameterBool = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            GRParameterBool = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        string StepParameterBool;
        if( m_parametersSerii.GetStepBool() )
            StepParameterBool = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            StepParameterBool = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        string TextSeriiBool;
        if( m_parametersSerii.GetTextBool() )
            TextSeriiBool = ((int)ModelsTree.Type.SelectCheckBox).ToString();
        else
            TextSeriiBool = ((int)ModelsTree.Type.NoSelectCheckBox).ToString();

        query.CommandText = "update MenedjerParameters_SvIv set " +
                "napr=" + ZamenaZapitNaDot(m_parametersZona.GetNapr()) + ", " +
                "t0=" + ZamenaZapitNaDot(m_parametersZona.GetT0()) + ", " +
                "e0=" + ZamenaZapitNaDot(m_parametersZona.GetE0()) + ", " +
                "r0=" + ZamenaZapitNaDot(m_parametersZona.GetR0()) + ", " +
                "pj=" + ZamenaZapitNaDot(m_parametersZona.GetPj()) + ", " +
                "ps=" + ZamenaZapitNaDot(m_parametersZona.GetPs()) + ", " +
                "ksi=" + ZamenaZapitNaDot(m_parametersZona.GetKsi()) + ", " +
                "nu=" + ZamenaZapitNaDot(m_parametersZona.GetNu()) + ", " +
                "alfa=" + ZamenaZapitNaDot(m_parametersZona.GetAlfa()) + ", " +
                "T=" + ZamenaZapitNaDot(m_parametersZona.GetT()) + ", " +
                "tauf=" + ZamenaZapitNaDot(m_parametersZona.GetTauf()) + ", " +
                "ro=" + ZamenaZapitNaDot(m_parametersZona.GetRo()) + ", " +
                "N=\"" + N + "\", " +
                //"LamdaSmall=" + m_parametersZona.GetLamdaSmall().ToString() + "," +
                //"LamdaBig=" + m_parametersZona.GetLamdaBig().ToString() + "," +
                //"f=" + m_parametersZona.GetF().ToString() + "," +
                "SolveSerii=" + SolveSerii + ", " +
                "TypeSerii=" + typeSerii.ToString() + ", " +
                "LeftGran=" + ZamenaZapitNaDot(m_parametersSerii.GetLeftGran()) + ", " +
                "RightGran=" + ZamenaZapitNaDot(m_parametersSerii.GetRightGran()) + ", " +
                "Step=" + ZamenaZapitNaDot(m_parametersSerii.GetStep()) + ", " +
                "ArifmProgStep=" + ArifmProgStep + ", " +
                "GeomProgStep=" + GeomProgStep + ", " +
                "MetallZonaBool=" + MetallZonaBool + ", " +
                "NumberZonaBool=" + NumberZonaBool + ", " +
                "TemperaturaZonaBool=" + TemperaturaZonaBool + ", " +
                "PlotnostZonaBool=" + PlotnostZonaBool + ", " +
                "TextZonaBool=" + TextZonaBool + ", " +
                "TextZona=\"" + m_parametersZona.GetText().ToString() + "\", " +
                "MetallSeriiBool=" + MetallSeriiBool + ", " +
                "NumberSeriiBool=" + NumberSeriiBool + ", " +
                "TemperaturaSeriiBool=" + TemperaturaSeriiBool + ", " +
                "GRParameterBool=" + GRParameterBool + ", " +
                "StepParameterBool=" + StepParameterBool + ", " +
                "TextSeriiBool=" + TextSeriiBool + ", " +
                "TextSerii=\"" + m_parametersSerii.GetText().ToString() + "\" " +
                //"MinCountCicle=" + m_parametersZona.GetMinCountCicle().ToString() + "," +
                //"EpsilonCicle=" + m_parametersZona.GetEpsilonCicle().ToString() + " " +
                "where MenedjerParametersId=" + id.ToString() + ";";
        query.ExecuteNonQuery();
    }

    private string ZamenaZapitNaDot(double pStr)
    {
        string rez = pStr.ToString();
        int index = rez.IndexOf(',');
        if (index >= 0)
        {
            int count = rez.Length;
            rez = rez.Substring(0, index) + "." + rez.Substring(index+1, count-index-1 );
        }
        return rez;
    }

    private void Interpolate( string pMetall )
    {
        OleDbConnection m_connection = Connector.getConnector().getConnection();
        OleDbCommand query = m_connection.CreateCommand();
        OleDbDataReader reader;

        double Gi=0,Gii,Bi=0,Bii,Ti=0,Tii;

        query.CommandText = "select dM.T as T,dM.G as G,dM.B*10000000000 as B from Metall_SvIv M, data_Metall_SvIv dM where (M.Metall_id=dM.Metall_id)and(M.Name=" +
                pMetall.ToString() + ")";
        reader = query.ExecuteReader();
        if ( reader.Read() )
        {
            Tii = reader.GetInt32(0);
            Gii = reader.GetInt32(1);
            Bii = reader.GetInt32(2) / 1e10;
            while ( reader.Read() )
            {
                Ti = Tii;
                Gi = Gii;
                Bi = Bii;
                Tii = reader.GetInt32(0);
                Gii = reader.GetInt32(1);
                Bii = reader.GetInt32(2) / 1e10;
                if ( Tii > m_T )
                {
                    break;
                }
            }
            m_G = (m_T - Ti) * (Gii - Gi) / (Tii - Ti) + Gi;
            m_B = (m_T - Ti) * (Bii - Bi) / (Tii - Ti) + Bi;
        }
        reader.Close();
    }
    private void Interpolate(string pMetall, string pParametr)
    {
        OleDbConnection m_connection = Connector.getConnector().getConnection();
        OleDbCommand query = m_connection.CreateCommand();
        OleDbDataReader reader;

        //h-øàã ñåòêè, Ti=T_i, Tii=T_(i+1)
        double Ti=0,Tii=0;

        if( pParametr == "d" ||
                pParametr == "G" ||
                pParametr == "nu" )
        {
            //value_i=G_i, value_ii=G_(i+1) èëè value_i=d_i, value_ii=d_(i+1) èëè value_i=nu_i, value_ii=nu_(i+1)
            double value_i=0,value_ii=0;
            query.CommandText = "select distinct T, value " +
                    "from MetallPropety mp, Metall_SvIv m, MetallParametr mpar, SelectedType st where " +
                    "mpar.id=mp.parametr_id and mp.metall_id=m.Metall_id and " +
                    "mpar.name_abbreviated=\"" + pParametr.ToString() + "\" and " +
                    "m.Name=\"" + pMetall.ToString() + "\" and st.type_id=mp.selected and " +
                    "st.data=\"SelectCheckBox\"";
            reader = query.ExecuteReader();
            if ( reader.Read() )
            {
                Tii = reader.GetDouble(0);
                value_ii = reader.GetDouble(1);
                Ti = Tii - 1;
                value_i = value_ii;
                while ( reader.Read() )
                {
                    Ti = Tii;
                    value_i = value_ii;
                    Tii = reader.GetDouble(0);
                    value_ii = reader.GetDouble(1);
                    if (Tii > m_T)
                    {
                        break;
                    }
                }
            }
            reader.Close();

            //Ëèíåéíàÿ èíòåðïîëÿöèÿ (âûâåäåíà èç óðàâíåíèÿ ïðÿìîé)
            if( pParametr == "d" )
                m_plotnost = (m_T-Ti)*(value_ii-value_i)/(Tii-Ti) + value_i;
            else if( pParametr == "G" )
                m_G = (m_T-Ti)*(value_ii-value_i)/(Tii-Ti) + value_i;
            else if( pParametr == "nu" )
                m_nu = (m_T-Ti)*(value_ii-value_i)/(Tii-Ti) + value_i;
            else if( pParametr == "b" )
                m_b = (m_T-Ti)*(value_ii-value_i)/(Tii-Ti) + value_i;
        }
        else if( pParametr == "B" ||
                pParametr == "vector b" )
        {
            double Bi,Bii;
            query.CommandText = "SELECT distinct T, value*10000000000 AS B " +
                    "FROM MetallPropety AS mp, Metall_SvIv AS m, MetallParametr AS mpar, SelectedType st WHERE " +
                    "mpar.id=mp.parametr_id and mp.metall_id=m.Metall_id and " +
                    "mpar.name_abbreviated=\"" + pParametr.ToString() + "\" and " +
                    "m.Name=\"" + pMetall.ToString() + "\" and st.type_id=mp.selected and " +
                    "st.data=\"SelectCheckBox\"";
            reader = query.ExecuteReader();
            if ( reader.Read() )
            {
                Tii = reader.GetDouble(0);
                Bii = reader.GetDouble(1) / 1e10;
                Ti = Tii - 1;
                Bi = Bii;
                while ( reader.Read() )
                {
                    Ti = Tii;
                    Bi = Bii;
                    Tii = reader.GetDouble(0);
                    Bii = reader.GetDouble(1) / 1e10;
                    if ( Tii > m_T )
                    {
                        break;
                    }
                }
                //Ëèíåéíàÿ èíòåðïîëÿöèÿ (âûâåäåíà èç óðàâíåíèÿ ïðÿìîé)
                if ( pParametr == "B" )
                    m_B = (m_T - Ti) * (Bii - Bi) / (Tii - Ti) + Bi;
                else if ( pParametr == "vector b" )
                    m_b = (m_T - Ti) * (Bii - Bi) / (Tii - Ti) + Bi;
            }
            reader.Close();
        }
    }

    public cParametersZona GetParametersZona()
    {
        return m_parametersZona;
    }

    public cParametersSerii GetParametersSerii()
    {
        return m_parametersSerii;
    }

    public void Dispose()
    {
        GC.SuppressFinalize(this);
    }

    {
        Dispose();
    }*/
}

