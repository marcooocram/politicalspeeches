package exceptions;

public class CSVParsingException extends RuntimeException {

    public CSVParsingException (String message, Throwable t){
        super(message,t);
    }

}
