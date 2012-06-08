/*	Synchrotron Soleil 
 *  
 *   File          :  StringFormater.java
 *  
 *   Project       :  apiDev
 *  
 *   Description   :  
 *  
 *   Author        :  SOLEIL
 *  
 *   Original      :  28 févr. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: StringFormater.java,v 
 *
 */
package fr.soleil.TangoArchiving.ArchivingTools.Tools;

public class StringFormater {

    //Special characters
    private final static String[] protect = {
        "[",
        "]",
        ",",
        ";",
        "'",
        "\"",
        "~",
        "\n"
    };

    //Associated code : in fact, the corresponding xml code number (ISO 8859/1)
    private final static String[] code = {
        "91",
        "93",
        "44",
        "59",
        "39",
        "34",
        "126",
        "10"
    };

    //transforms #code# -> corresponding character
    public static String formatStringToRead(String toFormat)
    {
        String result;
        if (toFormat != null)
        {
            result = toFormat;
            for (int i = 0; i < code.length; i++)
            {
                result = result.replaceAll("#"+code[i]+"#", protect[i]);
            }
        }
        else
        {
            result = "";
        }
        return result;
    }

    //transforms character -> corresponding #code#
    public static String formatStringToWrite(String toFormat)
    {
        String result;
        if (toFormat != null)
        {
            result = toFormat;
            for (int i = 0; i < protect.length; i++)
            {
                result = result.replaceAll( "\\" + protect[i], "#"+code[i]+"#" );
            }
        }
        else
        {
            result = "";
        }
        return result;
    }

    public static void main (String[] args)
    {
        String toto1 = "toto \n toto";
        String toto2 = formatStringToWrite(toto1);
        String toto3 = formatStringToRead(toto2);
        System.out.println("toto1 = |"+toto1+"|");
        System.out.println("toto2 = |"+toto2+"|");
        System.out.println("toto3 = |"+toto3+"|");
    }
}
