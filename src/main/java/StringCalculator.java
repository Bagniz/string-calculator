import exceptions.NegativesNotAllowedException;

import java.util.List;
import java.util.stream.Collectors;

public class StringCalculator {

    int add(String input) {
        StringUtils.assertNotNull(input);
        if (input.trim().isEmpty()) {
            return 0;
        }
        String delimiters = StringUtils.parseDelimiters(input);
        StringUtils.validateInput(input, delimiters);
        input = StringUtils.removeDelimitersDefinition(input);
        List<Integer> numbers = StringUtils.parseNumbers(input, delimiters);
        assertNoNegativeNumbers(numbers);
        return numbers.stream().reduce(0, Integer::sum);
    }

    private void assertNoNegativeNumbers(List<Integer> numbers) {
        List<Integer> negativeNumbers = numbers.stream()
                .filter(number -> number < 0)
                .collect(Collectors.toList());
        if (!negativeNumbers.isEmpty()) {
            throw new NegativesNotAllowedException(negativeNumbers);
        }
    }
}
