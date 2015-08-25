package com.lokesh.weatherinfo;

import android.util.Log;


public class Utils {

    //get Color as per temp value.

    public static String getColorCodeForTemperature(double temp){
        int value = (int)(temp/5);
        String color;
        switch(value){
            case 0: color = "#FF8080"; break;
            case 1: color = "#FFE6E6"; break;
            case 2: color = "#CC99FF"; break;
            case 3: color = "#FFCCFF"; break;
            case 4: color = "#CC99FF"; break;
            case 5: color = "#CCCCFF"; break;
            case 6: color = "#DBDBFF"; break;
            case 7: color = "#CCFFFF"; break;
            case 8: color = "#99CCFF"; break;
            case 9: color = "#CCEBFF"; break;
            case 10: color = "#66C2FF"; break;
            default: color = "#FF8080";
        }
        return color;
    }
}
