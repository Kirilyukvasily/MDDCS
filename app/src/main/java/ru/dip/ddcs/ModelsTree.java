package ru.dip.ddcs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by В on 29.03.2016.
 */
public class ModelsTree {
    public enum Type
    {
        None,
        SelectCheckBox,
        NoSelectCheckBox,
        SelectRadioButton,
        NoSelectRadioButton
    };

    private List<ModelsTree> m_node = new ArrayList<ModelsTree>();
    private int m_primaryKey;
    private Type m_type;
    private String m_name;

    public ModelsTree()
    {
        m_primaryKey = 0;
        m_type = Type.None;
        m_name = "";
    }
    public ModelsTree(int key, Type type, String name)
    {
        m_primaryKey = key;
        m_type = type;
        m_name = name;
    }
    public void Dispose()
    {
        //m_node.removeAll(DeleteModelsTree);
       System.gc(); //GC.SuppressFinalize(this);
    }
    private boolean DeleteModelsTree(ModelsTree mt)
    {
        return true;
    }

    {
        Dispose();
    }

    public ModelsTree GetNodeByIndex(int index)
    {
        if( m_node.size() <= index )
            return null;
        return m_node.get(index);
    }
    public ModelsTree GetNodeByName(String name)
    {
        int count = m_node.size();
        for( int i = 0; i < count; i++ )
            if( m_node.get(i).GetName() == name )
                return m_node.get(i);
        return null;
    }

    public void SetNode(ModelsTree node)
    {
        m_node.add(node);
    }
    /*public void SetType(int type)
    {
        m_type = (Type)type;
    }*/
    public void SetType(Type type)
    {
        m_type = type;
    }

    public int GetKey()
    {
        return m_primaryKey;
    }
    public Type GetType()
    {
        return m_type;
    }
    public String GetName()
    {
        return m_name;
    }
    public int GetCount()
    {
        return m_node.size();
    }
}
