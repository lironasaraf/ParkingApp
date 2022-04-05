package Exceptions;

public class NotValidPasswordException extends RuntimeException {

    public NotValidPasswordException(){
        super("password is not valid!");
    }
    public NotValidPasswordException(String errMsg){
        super(errMsg);
    }
}
