package Exceptions;

public class NotValidNameException extends RuntimeException {

    public NotValidNameException(){
        super("name is not valid!");
    }
    public NotValidNameException(String errMsg){
        super(errMsg);
    }
}
