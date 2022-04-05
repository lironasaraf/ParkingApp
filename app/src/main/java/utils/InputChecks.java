package utils;

import android.provider.ContactsContract;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class InputChecks {


    /**
     * check if the given Email address is valid
     * @param email
     * @return
     */
    public static boolean CheckValidEMail(String email){
        if(email == null || email.isEmpty()){return false;}

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * check if the given password is valid - a.k.a at list 6 characters long
     * @param password
     * @return
     */
    public static boolean CheckValidPassword(String password){
        if(password == null || password.isEmpty()){return false;}

        return password.length() >=6;
    }

    /**
     * check if the given phone number is valid
     * @param phoneNumber
     * @return
     */
    public static boolean CheckValidPhoneNumber(String phoneNumber){
        if(phoneNumber == null || phoneNumber.isEmpty()){return false;}

        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    /**
     * check if the given name is valid - a.k.a at list 2 characters long and doesn't contain a digit
     * @param name
     * @return
     */
    public static boolean CheckValidName(String name){
        if(name == null || name.isEmpty()){return false;}

        return name.length() >=2 && !name.matches(".*\\d.*");
    }


    public static boolean isValidData(String date){

        if(!date.matches("[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]")){ return false; }

        String[] dmy = date.split("/");

        if(Integer.parseInt(dmy[0]) > 31 || Integer.parseInt(dmy[1]) > 12){return false;}

        return true;
    }
    public static boolean isValidTime(String time){
        if(!time.matches("[0-2][0-9]:[0-5][0-9]")){ return false; }

        String[] dmy = time.split(":");

        if(Integer.parseInt(dmy[0]) > 23 || Integer.parseInt(dmy[1]) > 59){return false;}

        return true;

    }



}
