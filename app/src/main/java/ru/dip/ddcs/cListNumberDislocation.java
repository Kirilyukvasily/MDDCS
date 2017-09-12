package ru.dip.ddcs;/*
package ru.dip.ddcs;

import java.util.List;

*/

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by В on 23.11.2016.
 */


public class cListNumberDislocation
{
    public boolean Eof;
    private ArrayList<Integer> m_vector = new ArrayList<Integer>();
    private int m_selectedMas;

    private cListNumberDislocation() { }
    //cListNumberDislocation(int);
    public cListNumberDislocation(String pString)
    {
        Eof = ( pString == "" );
        if( !Eof )
            Definition( pString );
        m_selectedMas = 0;
    }

    public void Dispose()
    {
       System.gc(); // GC.SuppressFinalize(this);
    }

    //cListNumberDislocation()
    {

        Dispose();
    }

    public void Open()
    {
        m_selectedMas = 0;
        Eof = !( m_vector.size() > m_selectedMas );
    }

    public void Next()
    {
        m_selectedMas++;
        Eof = !( m_vector.size() > m_selectedMas );
    }

    public void Next(int pDelta)
    {
        m_selectedMas += pDelta;
        Eof = !( m_vector.size() > m_selectedMas );
    }

    public int Size()
    {
        return m_vector.size();
    }

    public int GetId()
    {
        return m_vector.get(m_selectedMas);
    }

    private void Definition(String pString)
    {
        //AnsiString str;
        int zapit, tire;

        zapit = pString.indexOf(",");
        tire = pString.indexOf("-");

        if( zapit == -1 && tire == -1 )
        {
            if( pString != "" )
            {
                m_vector.add(Integer.valueOf(pString));
            }
        }
        else
        {
            if( tire == -1 )
            {
                Zapit( pString, zapit );
            }
            else
            {
                if( zapit == -1 )
                {
                    Tire( pString, tire );
                }
                else
                {
                    if( zapit < tire )
                    {
                        Zapit( pString, zapit );
                    }
                    else
                    {
                        Tire( pString, tire );
                    }
                }
            }
        }
    }

    private void Tire(String pString,int pTire)
    {
        String str;
        int number1, number2;

        number1 = Integer.valueOf(pString.substring(0, pTire));
        //m_vector.push_back( number1 );

        //str = pString.Remove( 1, pTire );
        int lenght = pString.length();
        str = pString.substring(pTire + 1, lenght);
        int zapit = str.indexOf(",");
        if( zapit == -1 ){
            number2 = Integer.valueOf(str);
            str = "";
        }
        else{
            number2 =Integer.valueOf(str.substring(0, zapit));
            lenght = str.length();
            str = str.substring(zapit + 1, lenght); //pString.Remove(1, zapit);
        }

        if( number1 < number2 )
        {
            for(;number1<=number2 ;number1++)
                m_vector.add(number1);
            Definition( str );
        }
        else
        {
            if( number1 == number2 )
            {
                    m_vector.add(number1);
                    Definition(str);
            }
            else
            {
                //number1 > number2
                //âûäàòü ñîîáùåíèå îá îøèáêå!
            }
        }
    }

    private void Zapit(String pString,int pZapit)
    {
        String str = pString.substring(0, pZapit);
        m_vector.add(Integer.valueOf(str));
        int lenght = pString.length();
        str = pString.substring(pZapit + 1, lenght); //pString.Remove(1, pZapit);
        if( str != "" )
        {
            Definition( str );
        }
    }
}



