/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Alexandre
 */
public class DateUtils {
    
     public static String formatToDate(Date date){
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           return sdf.format(date);
    }
     
      public static String formatToDateToTime(Date date){
           SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
           return sdf.format(date);
    }
     
    public static String formatToDateTime(Date date){
       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy - HH:mm:ss");
       return sdf.format(date);
}
 
      public static Date getDateFromString(String date) throws ParseException{
            
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           return sdf.parse(date);
    }
      
        public static Date getDateTimeFromString(String date) throws ParseException{
            
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           return sdf.parse(date);
    }
        
        public static Date getDateTimeLocal(String date) throws ParseException{
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        	return formatter.parse(date);
     }
        
        public static String formatDateTimeLocal(Date date){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return sdf.format(date);
     }
        public static String excelFormat(Date date){
            SimpleDateFormat sdf = new SimpleDateFormat("d/m/yy h:mm");
            return sdf.format(date);
     }
         
        
        
    
}
