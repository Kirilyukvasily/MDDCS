
//package ru.dip.ddcs;
/*


*/
/**
 * Created by В on 20.03.2016.
 *//*


public class EditorOptionsBuilder {
    public enum eType
    {
        SelectCheckBox = 1,
        NoSelectCheckBox = 2
    };

    public enum AlgProreg
    {
        SaveKrainihPoints,
        SavePointsWithMinAndMaxValues,
        SaveKrainihPointsAndPointsWithMinAndMaxValues
    };

    public class TypeSaveResults
    {
        public eType OperationMemory;
        public eType DataBase;
        public eType TextFiles;
    }

    private OleDbConnection m_connection = Connector.getConnector().getConnection();
    private int m_countSubintervals;
    private eType m_statisticWindowVisible;
    private eType m_graphicWindowVisible;
    //private eType m_saveOperationMemoryVisible;
    //private eType m_saveDataBaseVisible;
    //private eType m_saveTextFilesVisible;
    //eType::TypeRasheta m_typeRasheta;
    private String m_katalog;
    private boolean[] m_eksportList = new boolean[12];
    private String[] m_eksportListName = new String[12];
    private TypeSaveResults m_typeSaveResultsList = new TypeSaveResults();
    private LiteratureTree m_literature;
    private LiteratureTree m_literatureFull;
    private cMethodParameters m_methodParameters;
    private AlgProreg m_algProreg;

    public EditorOptionsBuilder()
    {

*/
/*//*
*/
/*
/string tmpstring;
        OleDbCommand query = m_connection.CreateCommand();

        //Ñ÷èòûâàåì íàñòðîéêè
        m_methodParameters = new cMethodParameters();
        query.CommandText = "select * from EditorOptions where id = 1";
        OleDbDataReader reader = query.ExecuteReader();
        reader.Read();
        m_countSubintervals = reader.GetInt(1);
        m_statisticWindowVisible = (eType)Convert.ToInt32( reader.GetValue(2) );
        m_graphicWindowVisible = (eType)Convert.ToInt32( reader.GetValue(3) );
        //m_typeRasheta = (eType::TypeRasheta)query->FieldByName("TypeRasheta")->AsInteger;
        m_katalog = reader.GetString(5);
        // ----- Ñ÷èòûâàåì ìåòîä
        m_methodParameters.SetMethod((cMethodParameters.Method) Convert.ToInt32(reader.GetValue(6)));
        // ----- Ñ÷èòûâàåì íàñòðîéêè ìåòîäà
        m_methodParameters.SetTNBool(
                ( Convert.ToInt32(reader.GetValue(7)) == (int)ModelsTree.Type.SelectCheckBox ? true : false ));
        m_methodParameters.SetTN( reader.GetDouble(8) );
        m_methodParameters.SetMinStepBool(
                ( Convert.ToInt32(reader.GetValue(9)) == (int)ModelsTree.Type.SelectCheckBox ? true : false ));
        m_methodParameters.SetMaxStepBool(
                ( Convert.ToInt32(reader.GetValue(11)) == (int)ModelsTree.Type.SelectCheckBox ? true : false ));
        m_methodParameters.SetMinStep( reader.GetDouble(10) );
        m_methodParameters.SetMaxStep( reader.GetDouble(12) );
        m_methodParameters.SetTOL( reader.GetDouble(13) );
        m_typeSaveResultsList.OperationMemory = (eType)Convert.ToInt32(reader.GetValue(15));
        m_typeSaveResultsList.DataBase = (eType)Convert.ToInt32(reader.GetValue(16));
        m_typeSaveResultsList.TextFiles = (eType)Convert.ToInt32(reader.GetValue(17));
        reader.Close();

        m_literature = new LiteratureTree();
        m_literatureFull = new LiteratureTree();

        int key;
        LiteratureTree.eType type;
        string name;
        LiteratureTree tree;
        int countParametrs;

        //Ñ÷èòûâàåì èç ÁÄ ñóùåñòâóþùèå ïàðàìåòðû
        query.CommandText = "select * from MetallParametr";
        reader = query.ExecuteReader();
        while( reader.Read() )
        {
            key = reader.GetInt32(0);
            type = LiteratureTree.eType.None;
            name = reader.GetString(2);
            tree = new LiteratureTree( key, type, name );
            m_literatureFull.SetNode( tree );
        }
        reader.Close();
        countParametrs = m_literatureFull.GetCount();

            *//*
*/
/*
*//*

*/
/*
            //Ñ÷èòûâàåì ëèòåðàòóðíûå èñòî÷íèêè ýêñïåðèìåíòàëüíûõ
            //äàííûõ äëÿ ðàçíûõ ìàòåðèàëîâ
            query->SQL->Clear();
            query->SQL->Add("select * from Metall_SvIv");
            for( query->Open(); !query->Eof; query->Next() ) {
                key = query->FieldByName("Metall_id")->AsInteger;
                type = query->FieldByName("selected")->AsInteger;
                name = query->FieldByName("Name")->AsString;
                for( unsigned i = 0; i < countParametrs; ++i ){
                    m_literature = m_literatureFull->GetNodeByIndex(i);
                    tree = new LiteratureTree( key, type, name );
                    m_literature->SetNode( tree );
                    ReadNodeTree( tree, m_literature->GetName() );
                }
            }
            m_literature = m_literatureFull->GetNodeByIndex(0);
            query->Close();
            *//*
*/
/*
*//*

*/
/*
            *//*
*/
/*
*//*

*/
/*
            // ----- Ñ÷èòûâàåì ëèòåðàòóðíûå èñòî÷íèêè ýêñïåðèìåíòàëüíûõ
            // ----- äàííûõ äëÿ âûáðàííîãî â ãëàâíîì ìåíþ ìàòåðèàëå
            name = DataForms::getDataForms()->getMaterial();
            query->SQL->Clear();
            query->SQL->Add("select * from Metall_SvIv where Name:=name");
            query->Parameters->ParamByName("name")->Value = name;
            query->Open();
            key = query->FieldByName("Metall_id")->AsInteger;
            type = 1;
            //type = query->FieldByName("selected")->AsInteger;
            query->Close();
            for( unsigned i = 0; i < countParametrs; ++i ){
                m_literature = m_literatureFull->GetNodeByIndex(i);
                tree = new LiteratureTree( key, type, name );
                m_literature->SetNode( tree );
                ReadNodeTree( tree, m_literature->GetName() );
            }
            *//*
*/
/*
*//*

*/
/*

        // ----- Ñ÷èòûâàåì íàñòðîéêè ýêñïîðòà è òèïà ñîõðàíåíèÿ ðåçóëüòàòîâ âû÷èñëåíèÿ.
        // ----- Íåîáõîäèìî äëÿ òîãî, ÷òîáû "çíàòü" î íàñòðîéêàõ, åñëè ïîëüçîâàòåëü
        // ----- íå âõîäèò â äèàëîãîâîå îêíî Íàñòðîéêè âû÷èñëåíèé
        EditorOptionsEksport();

            *//*
*/
/*
*//*

*/
/*
            try{
                delete query;
            }
            catch(...){
                // ----- Ïî÷åìó-òî ïðè óäàëåíèè query âûçûâàåòñÿ îøèáêà
                // ----- åñëè EditorOptionsEksport íå âûçûâàåòñÿ, òî
                // ----- îøèáêà íå âûäàåòñÿ.
            }
            *//*
*/
/*

    }
    public void Dispose()
    {
        GC.SuppressFinalize(this);
    }

    {
        Dispose();
    }

    //void SaveOptions(long int,eType::Type,*//*

*/
/*eType::Type,eType::TypeRasheta,*//*
*/
/*
AnsiString,TTreeView* tree);
    public void SaveOptions(int pCountSubintervals,
                            eType pStatisticWindowVisible, eType pGraphicWindowVisible,*//*

*/
/*eType::TypeRasheta pTypeRasheta,*//*
*/
/*

                            String pKatalogEksporta, TreeView tree, cMethodParameters.Method pMethod,
                            eType pTNBool, double pTN, eType pMinStepBool,
                            double pMinStep, eType pMaxStepBool, double pMaxStep,
                            double pTOL, AlgProreg pAlgProreg, eType pSaveOperationMemoryVisible,
                            eType pSaveDataBaseVisible, eType pSaveTextFilesVisible)
    {
        m_countSubintervals = pCountSubintervals;
        m_statisticWindowVisible = pStatisticWindowVisible;
        m_graphicWindowVisible = pGraphicWindowVisible;
        // ----- Способ сохранения результатов
        m_typeSaveResultsList.OperationMemory = pSaveOperationMemoryVisible;
        m_typeSaveResultsList.DataBase = pSaveDataBaseVisible;
        m_typeSaveResultsList.TextFiles = pSaveTextFilesVisible;
        //m_typeRasheta = pTypeRasheta;
        m_katalog = pKatalogEksporta;

        // ----- Ñîõðàíåíèå âûáîðà ìåòîäà èíòåãðèðîâàíèÿ
        m_methodParameters.SetMethod( pMethod );
        // ----- Ñîõðàíåíèå íàñòðîåê ìåòîäà èíòåãðèðîâàíèÿ
        m_methodParameters.SetTNBool(pTNBool == eType.SelectCheckBox ? true : false);
        m_methodParameters.SetTN(pTN);
        m_methodParameters.SetMinStepBool(pMinStepBool == eType.SelectCheckBox ? true : false);
        m_methodParameters.SetMinStep(pMinStep);
        m_methodParameters.SetMaxStepBool(pMaxStepBool == eType.SelectCheckBox ? true : false);
        m_methodParameters.SetMaxStep(pMaxStep);
        m_methodParameters.SetTOL(pTOL);
        // ----- Ñîõðàíåíèå âûáîðà àëãîðèòìà ïðîðåæèâàíèÿ
        m_algProreg = pAlgProreg;

        int graphicWindowVisible = (m_graphicWindowVisible == eType.SelectCheckBox ? 1 : 2);
        int statisticWindowVisible = (m_statisticWindowVisible == eType.SelectCheckBox ? 1 : 2);
        int saveOperationMemoryVisible = (m_typeSaveResultsList.OperationMemory == eType.SelectCheckBox ? 1 : 2);
        int saveDataBaseVisible = (m_typeSaveResultsList.DataBase == eType.SelectCheckBox ? 1 : 2);
        int saveTextFilesVisible = (m_typeSaveResultsList.TextFiles == eType.SelectCheckBox ? 1 : 2);

        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "update EditorOptions set " +
                "CountSubinervals = " + m_countSubintervals.ToString() + ", " +
                "StatisticWindowVisible = " + statisticWindowVisible + ", " +
                "GraphicWindowVisible = " + graphicWindowVisible + ", " +
                "KatalogEksporta = \"" + m_katalog.ToString() + "\", " +
                "Method = " + ((int)pMethod).ToString() + ", " +
                "tNBool = " + ((int)pTNBool).ToString() + ", " +
                "tN = " + ZamenaZapNaDot(pTN.ToString()) + ", " +
                "MinStepBool = " + ((int)pMinStepBool).ToString() + ", " +
                "MinStep = " + ZamenaZapNaDot(pMinStep.ToString()) + ", " +
                "MaxStepBool = " + ((int)pMaxStepBool).ToString() + ", " +
                "MaxStep = " + ZamenaZapNaDot(pMaxStep.ToString()) + ", " +
                "TOL = " + ZamenaZapNaDot(pTOL.ToString()) + ", " +
                "AlgProreg = " + ((int)pAlgProreg).ToString() + ", " +
                "SaveOperationMemoryBool = " + saveOperationMemoryVisible + ", " +
                "SaveDataBaseBool = " + saveDataBaseVisible + ", " +
                "SaveTextFilesBool = " + saveTextFilesVisible +
                " where id = 1;";
        query.ExecuteNonQuery();
        query.Cancel();
    }

    private String ZamenaZapNaDot(String text)
    {
        int count = text.length();
        for ( int i = 0; i < count; i++ )
        {
            if (text[i] == ',')
            {
                String tmp = text.Substring(0, i) + "." + text.Substring(i + 1, count - i - 1);
                text = tmp;
                break;
            }
        }
        return text;
    }

    public void SaveOptionsEksport(DataGridView table)
    {
        OleDbCommand query = m_connection.CreateCommand();
        int count = m_eksportList.Length;
        bool tmp;
        for (int i = 0; i < count; i++ )
        {
            tmp = Convert.ToBoolean(table[0, i].Value);
            if (tmp != m_eksportList[i])
            {
                m_eksportList[i] = tmp;
                query.CommandText = "update Eksport_SvIv set bool = " +
                        tmp.ToString() + " where Eksport_id = " +
                        (i+1).ToString() + ";";
                query.ExecuteNonQuery();
            }
        }
        query.Cancel();
    }

    public void EditorOptionsEksport()
    {
        OleDbCommand query = m_connection.CreateCommand();
        query.CommandText = "select * from Eksport_SvIv where Eksport_id <= 12";
        OleDbDataReader reader = query.ExecuteReader();
        for (int i = 0; reader.Read(); ++i )
        {
            m_eksportList[i] = reader.GetBoolean(1);
            m_eksportListName[i] = reader.GetString(2);
        }
        reader.Close();
        query.Cancel();

            *//*

*/
/*
            m_typeSaveResultsList = new bool[3];
            query.CommandText = "select bool from TypeSaveResults";
            reader = query.ExecuteReader();
            for (int i = 0; reader.Read(); ++i)
            {
                m_typeSaveResultsList[i] = reader.GetBoolean(0);
            }
            reader.Close();
            query.Cancel();
            *//*
*/
/*

    }

    public void EditorOptionsEksport(OleDbCommand pQueryEksport, OleDbCommand pQueryTypeSaveResults)
    {
        EditorOptionsEksport();
        // ----- Äåëàåì àêòèâíûì ñîåäèíåíèå òàáëèöû ýêñïîðòà ñ àíàëîãè÷íîé òàáëèöåé â ÁÄ
        pQueryEksport.Connection.ConnectionString = m_connection.ConnectionString;
        pQueryEksport.Connection.Open();
        // ----- Äåëàåì àêòèâíûì ñîåäèíåíèå òàáëèöû òèïà ñîõðàíåíèÿ ðàñ÷åòíûõ äàííûõ ñ àíàëîãè÷íîé òàáëèöåé â ÁÄ
        pQueryTypeSaveResults.Connection.ConnectionString = m_connection.ConnectionString;
        pQueryTypeSaveResults.Connection.Open();
    }

    public void EditorOptionsEksportDontSave()
    {
        OleDbCommand query = m_connection.CreateCommand();

        for (int i = 0; i < 12; ++i)
        {
            bool name;
            if ( m_eksportList[i] == true )
                name = true;
            else
                name = false;
            query.CommandText = "update Eksport_SvIv set bool = " + name.ToString() + "where Eksport_id = " + (i + 13).ToString() + ";";
            query.ExecuteNonQuery();
        }
        query.Cancel();

            *//*

*/
/*
            for (int i = 0; i < 3; ++i)
            {
                bool name;
                if ( m_typeSaveResultsList[i] == true )
                    name = true;
                else
                    name = false;
                query.CommandText = "update TypeSaveResults set bool = " + name.ToString() + " where id = "+i.ToString()+";";
                query.ExecuteNonQuery();
            }
            query.Cancel();
            *//*
*/
/*

    }

    public String GetKatalogEksporta()
    {
        return m_katalog;
    }

    public int GetCountSubintervals()
    {
        return m_countSubintervals;
    }
    public boolean GetStatisticWindowVisible()
    {
        if( m_statisticWindowVisible == eType.SelectCheckBox )
            return true;
        return false;
    }

    public boolean GetGraphicWindowVisible()
    {
        if( m_graphicWindowVisible == eType.SelectCheckBox )
            return true;
        return false;
    }

    public boolean GetSaveOperationMemoryVisible()
    {
        if (m_typeSaveResultsList.OperationMemory == eType.SelectCheckBox)
            return true;
        return false;
    }

    public boolean GetSaveDataBaseVisible()
    {
        if (m_typeSaveResultsList.DataBase == eType.SelectCheckBox)
            return true;
        return false;
    }

    public boolean GetSaveTextFilesVisible()
    {
        if (m_typeSaveResultsList.TextFiles == eType.SelectCheckBox)
            return true;
        return false;
    }

    public boolean[] GetEksportList()
    {
        return m_eksportList;
    }
    public String[] GetEksportListName()
    {
        return m_eksportListName;
    }
    //bool* GetTypeRasheta();
    public TypeSaveResults GetTypeSaveResultsList()
    {
        return m_typeSaveResultsList;
    }
    //void ReadMaterials(TTreeView* treeMaterials);
    //void ReadMaterials(TComboBox* materials);
    //void ReadLiterature(AnsiString parametr, TTreeView* treeMaterials, TTreeView* treeLiterature);
    //void ReadLiterature(AnsiString parametr,TComboBox* pMaterials,TTreeView* treeLiterature);
    public void BuildLiterature(String parametr,TreeView treeLiterature)
    {
        OleDbCommand query = m_connection.CreateCommand();
        int key;
        LiteratureTree.eType type_t;
        string name;
        LiteratureTree tree;
        int countParametrs;

        // ----- Ñ÷èòûâàåì ëèòåðàòóðíûå èñòî÷íèêè ýêñïåðèìåíòàëüíûõ
        // ----- äàííûõ äëÿ âûáðàííîãî â ãëàâíîì ìåíþ ìàòåðèàëå
        countParametrs = m_literatureFull.GetCount();
        name = "Cu";//DataForms::getDataForms()->getMaterial();
        query.CommandText = "select * from Metall_SvIv where Name=\""+name.ToString()+"\";";
        OleDbDataReader reader = query.ExecuteReader();
        reader.Read();
        key = Convert.ToInt32(reader.GetValue(0));
        type_t = LiteratureTree.eType.SelectCheckBox;
        //type = query->FieldByName("selected")->AsInteger;
        reader.Close();

        for( int i = 0; i < countParametrs; ++i ){
            m_literature = m_literatureFull.GetNodeByIndex(i);
            tree = new LiteratureTree( key, type_t, name );
            m_literature.Clear();
            m_literature.SetNode( tree );
            ReadNodeTree( tree, m_literature.GetName() );
        }

        //Ïî÷èñòèì äåðåâî Ëèòåðàòóðû
        treeLiterature.Nodes.Clear();

        LiteratureTree literature;
        countParametrs = m_literatureFull.GetCount();
        for( int i = 0; i < countParametrs; ++i ){
            literature = m_literatureFull.GetNodeByIndex(i);
            if( literature.GetName() == parametr ){
                m_literature = literature;
                break;
            }
        }

        literature = m_literature.GetNodeByIndex(0);
        if( literature != null )
        {
            int index = 0;
            int type;
            LiteratureTree literature2 = literature.GetNodeByIndex(0);
            treeLiterature.Nodes.Add(literature2.GetName());
            String tempAS = literature2.GetName();
            treeLiterature.Nodes[index].ImageIndex = (int)literature2.GetType();
            int tmp = (int)literature2.GetType();
            if( literature2.GetType() == LiteratureTree.eType.None )
                treeLiterature.Nodes[index].SelectedImageIndex = 5;
            else
                treeLiterature.Nodes[index].SelectedImageIndex = (int)literature2.GetType();
            //BuildNodeTree( treeLiterature, index, literature );

            literature2 = literature.GetNodeByIndex(1);
            for( int i = 2; literature2 != null; i++)
            {

                treeLiterature.Nodes.Add(literature2.GetName());

                index++; //= treeLiterature->Items->Count - 1;
                type = (int)literature2.GetType();

                treeLiterature.Nodes[index].StateImageIndex = type;
                treeLiterature.Nodes[index].ImageIndex = type;
                treeLiterature.Nodes[index].SelectedImageIndex = type;

                //BuildNodeTree( treeLiterature, index, literature );

                literature2 = literature.GetNodeByIndex(i);
            }
        }
    }

    public void BuildParameters(ComboBox pParametrs)
    {
        //Ïî÷èñòèì ñïèñîê ïàðàìåòðîâ
        pParametrs.Items.Clear();
        //Îïðåäåëèì êîëè÷åñòâî ïàðàìåòðîâ
        int countParametrs = m_literatureFull.GetCount();
        for( int i = 0; i < countParametrs; ++i ){
            pParametrs.Items.Add( m_literatureFull.GetNodeByIndex(i).GetName() );
        }
        pParametrs.Text = pParametrs.Items[0].ToString();
    }

    public void BuildMethod(ComboBox pMethod)
    {
        OleDbCommand query = m_connection.CreateCommand();
        //Ïî÷èñòèì ñïèñîê äîñòóïíûõ ìåòîäîâ
        pMethod.Items.Clear();
        // ----- Ñ÷èòûâàåì ñïèñîê äîñòóïíûõ ìåòîäîâ
        query.CommandText = "select * from Method";
        OleDbDataReader reader = query.ExecuteReader();
        while( reader.Read() )
        {
            pMethod.Items.Add( reader.GetString(1) );
        }
        reader.Close();
        // ----- Ñ÷èòûâàåì âûáðàííûé ìåòîä
        query.CommandText = "select * from EditorOptions where id = 1";
        reader = query.ExecuteReader();
        reader.Read();
        int method = Convert.ToInt32(reader.GetValue(6));
        reader.Close();
        pMethod.SelectedIndex = method;
        m_methodParameters.SetMethod((cMethodParameters.Method)method);
        //pMethod->Text = pMethod->Items->operator [](method);
        query.Cancel();
    }

    public void BuildAlgProreg(ComboBox pAlgProreg)
    {
        OleDbCommand query = m_connection.CreateCommand();
        //Ïî÷èñòèì ñïèñîê äîñòóïíûõ ìåòîäîâ
        pAlgProreg.Items.Clear();
        // ----- Ñ÷èòûâàåì ñïèñîê äîñòóïíûõ ìåòîäîâ
        query.CommandText = "select * from AlgProreg";
        OleDbDataReader reader = query.ExecuteReader();
        while (reader.Read())
        {
            pAlgProreg.Items.Add(reader.GetString(1));
        }
        reader.Close();
        // ----- Ñ÷èòûâàåì âûáðàííûé ìåòîä
        OleDbCommand query2 = m_connection.CreateCommand();
        query2.CommandText = "select * from EditorOptions where id = 1";
        reader = query2.ExecuteReader();
        reader.Read();
        m_algProreg = (AlgProreg)Convert.ToInt32(reader.GetValue(14));
        reader.Close();
        pAlgProreg.SelectedIndex = (int)m_algProreg;
        query2.Cancel();
    }

    public void SaveLiterature(TreeView treeLiterature)
    {
        SaveLiteratureTree(treeLiterature);
        SaveLiteratureInDB();
    }

    //void SelectedRadioButton(TTreeView* tree);
    //LiteratureTree* GetModelTree();
    public void PaintParameterValue(Chart pChart, Form EditorOptionsForm)
    {

        OleDbCommand query = m_connection.CreateCommand();
        double x, y;
        Color[] color={Color.Red,Color.Blue,Color.Olive,Color.Fuchsia,Color.Yellow,
                Color.Purple,Color.Green,Color.Navy,Color.Maroon,Color.Teal,
                Color.Aqua,Color.Brown,Color.Goldenrod,Color.Gray,
                Color.SkyBlue,Color.Lime,Color.Silver,Color.MistyRose};
        LiteratureTree literature;
        int colorIndex = 0;
        int countMaterial = m_literature.GetCount();
        pChart.Series.Clear();
        //int indexSeries = 0;
        //double maxAxis, minAxis;
        // ----- flagMinMax = false - max и min значения не заданы
        // ----- flagMinMax = екгу - max и min значения заданы
        boolean flagMinMax = false;
        double x_min = 0;
        double x_max = 0;
        double y_min = 0;
        double y_max = 0;
        for( int i = 0; i < countMaterial; ++i )
        {
            literature = m_literature.GetNodeByIndex(i);
            int countLink = literature.GetCount();
            for( int j = 0; j < countLink; ++j )
            {
                if( literature.GetNodeByIndex(j).GetType() == LiteratureTree.eType.SelectCheckBox )
                {
                    //series.LegendText = string( literature.GetName() );
                    //pChart.Series.Add(literature.GetName());
                    Series series = new Series();
                    //series.Name = literature.GetName();
                    series.BorderColor = color[colorIndex];
                    //series->Pointer->Visible = true;
                    //series->Pointer->VertSize = 2;
                    //series->Pointer->HorizSize = 2;

                    query.CommandText = "select * from MetallPropety where " +
                            "parametr_id = " + m_literature.GetKey().ToString() + " and " +
                            "metall_id = " + literature.GetKey().ToString() + " and " +
                            "link = " + literature.GetNodeByIndex(j).GetKey().ToString() + " ORDER BY T;";
                    OleDbDataReader reader = query.ExecuteReader();
                    int countpoint = 0;
                    reader.Read();
                    if (!flagMinMax)
                    {
                        x_max = x_min = reader.GetDouble(3);
                    }
                    do
                    {
                        x = reader.GetDouble(3);
                        if (x_min > x)
                        {
                            x_min = x;
                        }
                        if (x_max < x)
                        {
                            x_max = x;
                        }
                        y = reader.GetDouble(4);
                        series.Points.AddXY(x, y);
                        ++countpoint;
                    } while (reader.Read());
                    reader.Close();

                    if (flagMinMax)
                    {
                        y = series.Points.FindMinByValue().YValues.Min();
                        if (y_min > y)
                        {
                            y_min = y;
                        }
                        y = series.Points.FindMaxByValue().YValues.Max();
                        if (y_max < y)
                        {
                            y_max = y;
                        }
                    }
                    else
                    {
                        y_min = series.Points.FindMinByValue().YValues.Min();
                        y_max = series.Points.FindMaxByValue().YValues.Max();
                        flagMinMax = true;
                    }

                    if (countpoint == 1)
                    {
                        series.ChartType = SeriesChartType.Point;
                        series.MarkerStyle = MarkerStyle.Square;
                        series.MarkerSize = 8;
                    }
                    else
                    {
                        series.ChartType = SeriesChartType.Line;
                    }
                    pChart.Series.Add(series);
                    ++colorIndex;
                }
            }
        }

        if (x_min == x_max)
        {
            if (x_min == 0)
            {
                x_min = -1;
                x_max = 1;
            }
            else
            {
                x_min = x_min - 0.1 * x_min;
                x_max = x_max + 0.1 * x_max;
            }
        }
        pChart.ChartAreas[0].AxisX.Minimum = x_min;
        pChart.ChartAreas[0].AxisX.Maximum = x_max;

        if (y_min == y_max)
        {
            if (y_min == 0)
            {
                y_min = -1;
                y_max = 1;
            }
            else
            {
                y_min = y_min - 0.1 * y_min;
                y_max = y_max + 0.1 * y_max;
            }
        }
        pChart.ChartAreas[0].AxisY.Minimum = y_min;
        pChart.ChartAreas[0].AxisY.Maximum = y_max;

        pChart.ChartAreas[0].AxisX.Title = "Температура";
        pChart.ChartAreas[0].AxisX.LabelStyle.Format = "{F0}";
        if (y_max < 1)
        {
            y = y_max;
            int i = 2;
            for (; y > 0 && y < 1; ++i )
            {
                y *= 10;
            }
            pChart.ChartAreas[0].AxisY.LabelStyle.Format = "{F" + i + "}";
        }
        else
        {
            pChart.ChartAreas[0].AxisY.LabelStyle.Format = "{F0}";
        }
    }

    public cMethodParameters GetMethodParameters()
    {
        return m_methodParameters;
    }

    private void ReadNodeTree(LiteratureTree node, String pParametr)
    {
        int key = node.GetKey();
        LiteratureTree.eType type;
        string name;
        LiteratureTree tree;

        OleDbCommand query = m_connection.CreateCommand();

        query.CommandText = "SELECT DISTINCT Link.link AS lin, Link.id AS id, mpr.selected AS selected FROM MetallPropety AS mpr, MetallParametr AS mpa, Metall_SvIv AS m, Link WHERE (mpr.parametr_id=mpa.id) And (mpr.metall_id=m.Metall_id) And (Link.id=mpr.link) And (m.Metall_id="+
                node.GetKey().ToString() + ") And (mpa.name_full=\"" +
                pParametr.ToString()  + "\");";
        OleDbDataReader reader = query.ExecuteReader();
        while( reader.Read() )
        {
            key = reader.GetInt32(1);
            type = (LiteratureTree.eType)Convert.ToInt32(reader.GetValue(2));
            name = reader.GetString(0);
            tree = new LiteratureTree( key, type, name );
            node.SetNode( tree );
        }
        reader.Close();
    }

    private void BuildNodeTree(TreeNode treeLiterature, LiteratureTree pLiterature)
    {
        int type;
        LiteratureTree literature = pLiterature.GetNodeByIndex(0);
        for (int i = 0; literature != null; )
        {
            treeLiterature.Nodes.Add(literature.GetName());
            type = (int)literature.GetType();
            treeLiterature.Nodes[i].StateImageIndex = type;
            treeLiterature.Nodes[i].ImageIndex = type;
            treeLiterature.Nodes[i].SelectedImageIndex = type;
            i++;
            literature = pLiterature.GetNodeByIndex(i);
        }
    }

    private void SaveNodeLiterature(TreeView treeLiterature,int index,LiteratureTree node)
    {
        LiteratureTree literature = node.GetNodeByIndex(0);
        for( int i = 1; literature != null; ++i, ++index )
        {
            literature.SetType(treeLiterature.Nodes[index].ImageIndex);
            literature = node.GetNodeByIndex(i);
        }
    }

    private void SaveLiteratureTree(TreeView treeLiterature)
    {
        LiteratureTree literature = m_literature.GetNodeByIndex(0);
        if ( literature == null)
            return;
        literature = literature.GetNodeByIndex(0);
        for (int i = 1; literature != null; i++)
        {
            literature.SetType(treeLiterature.Nodes[i-1].ImageIndex);
            literature = (m_literature.GetNodeByIndex(0)).GetNodeByIndex(i);
        }
    }

    private void SaveLiteratureInDB()
    {
        OleDbCommand query = m_connection.CreateCommand();
        //int tmp;
        LiteratureTree literature = m_literature.GetNodeByIndex(0);
        for (int i = 1; literature!=null; i++)
        {
            query.CommandText = "update Metall_SvIv set selected=" +
                    literature.GetKey().ToString() + " where Metall_id=" +
                    ((int)literature.GetType()).ToString() + ";";
            query.ExecuteNonQuery();
            //SaveNodeTreeInBD( literature );
            literature = m_literature.GetNodeByIndex(i);
        }
        query.Cancel();
        LiteratureTree literaturefull;
        int countParamets = m_literatureFull.GetCount();
        for (int i = 0; i < countParamets; i++)
        {
            literaturefull = m_literatureFull.GetNodeByIndex(i);
            // ----- Ñåé÷àñ âûâîäèòñÿ èíôîðìàöèÿ òîëüêî îá îäíîì ìàòåðèàëå
            // ----- (âûáðàííîì â ãëàâíîì îêíå), ïîýòîìó countMaterials=1
            int countMaterials = literaturefull.GetCount();
            for (int j = 0; j < countMaterials; j++)
            {
                literature = literaturefull.GetNodeByIndex(j);
                SaveNodeTreeInDB(literature, literaturefull.GetKey());
            }
        }
    }

    private void SaveNodeTreeInDB(LiteratureTree node,int key)
    {
        OleDbCommand query = m_connection.CreateCommand();
        LiteratureTree literature = node.GetNodeByIndex(0);
        for (int i = 1; literature != null; i++)
        {
            query.CommandText = "update MetallPropety set selected=" +
                    ((int)literature.GetType()).ToString() + " where link=" +
                    literature.GetKey().ToString() + " and metall_id=" +
                    node.GetKey().ToString() + " and parametr_id=" +
                    key.ToString() + ";";
            query.ExecuteNonQuery();
            literature = node.GetNodeByIndex(i);
        }
        query.Cancel();
    }
    //void SelectedRadioButton(TTreeView* tree,unsigned& index,LiteratureTree* node);
}

*/

