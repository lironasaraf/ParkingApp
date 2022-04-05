package utils;

import utils.InputChecks;

public class Utils {

    /**
     * compare two strings representing dates, in the format dd/mm/yyyy
     * @param date1
     * @param date2
     * @return
     *      -1 - date2 > date1
     *      1 - date1 > date2
     *      0 - date1 == date2
     *      -2 - not valid input
     */
    public static int CompareDateStrings(String date1, String date2){

        // check valid input
        if(!InputChecks.isValidData(date1) || !InputChecks.isValidData(date2)){ return -2; }

        String[] date1Splited = date1.split("/"), date2Splited = date2.split("/");

        // check for equality
        if(date1.equals(date2)){ return 0; }

        // compare year, then month, then day
        int comp = date1Splited[2].compareTo(date2Splited[2]);
        if(comp != 0){ return comp; }
        comp = date1Splited[1].compareTo(date2Splited[1]);
        if(comp != 0){ return comp; }
        comp = date1Splited[0].compareTo(date2Splited[0]);
        return comp;
    }


    /**
     * compare two strings representing times, in the format hh:mm
     * @param time1
     * @param time2
     * @return
     *      -1 - time2 > time1
     *      1 - time1 > time2
     *      0 - time1 == time2
     *      -2 - not valid input
     */
    public static int CompareTimeStrings(String time1, String time2){

        // check valid input
        if(!InputChecks.isValidTime(time1) || !InputChecks.isValidTime(time2)){ return -2; }

        String[] date1Splited = time1.split(":"), date2Splited = time2.split(":");

        // check for equality
        if(time1.equals(time2)){ return 0; }

        // compare hour, then minute
        int comp = date1Splited[1].compareTo(date2Splited[1]);
        if(comp != 0){ return comp; }
        comp = date1Splited[0].compareTo(date2Splited[0]);
        return comp;
    }




}
