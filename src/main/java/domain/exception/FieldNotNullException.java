package domain.exception;

public class FieldNotNullException extends RuntimeException{
    public FieldNotNullException(String msj){
        super(msj);
    }
}
