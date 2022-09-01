package exceptions;

public class NumberStringNullException extends RuntimeException {
    public NumberStringNullException() {
        super("Numbers string must not be null");
    }
}
