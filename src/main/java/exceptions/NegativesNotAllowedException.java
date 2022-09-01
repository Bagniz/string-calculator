package exceptions;

import java.util.List;

public class NegativesNotAllowedException extends RuntimeException {
    public NegativesNotAllowedException(List<Integer> negativeNumbers) {
        super("Negatives not allowed: " + negativeNumbers);
    }
}
