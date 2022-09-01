package exceptions;

public class InvalidNumberStringException extends RuntimeException {
    public InvalidNumberStringException() {
        super("Invalid numbers string");
    }
}
