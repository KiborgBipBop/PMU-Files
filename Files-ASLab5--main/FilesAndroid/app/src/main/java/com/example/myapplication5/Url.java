package com.example.myapplication5;

import android.util.Patterns;
import android.webkit.URLUtil;

public class Url
{

    public static String get(String str, int offset) throws Exception
    {
        if (str.length() == offset)
        {
            offset--;
        }

        if (str.charAt(offset) == ' ')
        {
            offset--;
        }
        int startIndex = offset;
        int endIndex = offset;

        try
        {
            while (str.charAt(startIndex) != ' ' && str.charAt(startIndex) != '\n')
            {
                startIndex--;
            }
            startIndex++;
        } catch (StringIndexOutOfBoundsException e)
        {
            startIndex = 0;
        }

        try
        {
            while (str.charAt(endIndex) != ' ' && str.charAt(endIndex) != '\n')
            {
                endIndex++;
            }
        } catch (StringIndexOutOfBoundsException e)
        {
            endIndex = str.length();
        }

        char last = str.charAt(endIndex - 1);
        if (last == ',' || last == '.' ||
                last == '!' || last == '?' ||
                last == ':' || last == ';')
        {
            endIndex--;
        }

        str = str.substring(startIndex, endIndex);
        /*if(str.charAt(startIndex)==' ')
            str=str.substring(startIndex+1, endIndex);
        else
            str=str.substring(startIndex, endIndex);*/
        //System.out.println("["+str+"]");
        if (URLUtil.isValidUrl(str) || Patterns.WEB_URL.matcher(str).matches())
            return str;
        else
            throw new Exception();
    }
}

