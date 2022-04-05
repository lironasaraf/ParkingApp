package Exceptions;

public class NotValidPhoneNumberException extends RuntimeException  {

    public NotValidPhoneNumberException(){
        super("phone number is not valid!");
    }
    public NotValidPhoneNumberException(String errMsg){
        super(errMsg);
    }
}
