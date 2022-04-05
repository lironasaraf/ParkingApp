package Exceptions;

public class NotValidEmailException extends RuntimeException {

    public NotValidEmailException(){
        super("Email is not valid!");
    }
    public NotValidEmailException(String errMsg){
        super(errMsg);
    }
}
