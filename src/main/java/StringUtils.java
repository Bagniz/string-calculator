import exceptions.InvalidNumberStringException;
import exceptions.NumberStringNullException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {
    private static final int DEFAULT_MAX_NUMBER = 1000;
    private static final String DEFAULT_DELIMITERS_REGEXP = ",|\n";

    // Used to match the defined delimiter between '//' and '\n'
    private static final String SINGLE_CHAR_DELIMITER_DEF = "\\/\\/(.)\\n";
    // Used to match the defined variable length delimiter between '//[' and ']\n'
    private static final String VARIABLE_LENGTH_DELIMITER_DEF = "\\/\\/\\[(.+)\\]\\n";
    private static final String STRING_VALIDATION_FORMAT = "(%s){2,}";
    private static final String DELIMITERS_DEF_STARTER = "//";

    static void assertNotNull(String input) {
        if (input == null) {
            throw new NumberStringNullException();
        }
    }

    private static Optional<String> parseDelimitersDefinition(String stringNumbers, String regexp) {
        Pattern pattern = Pattern.compile(regexp, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(stringNumbers);
        if (matcher.find()) {
            return Optional.of(Pattern.quote(matcher.group(1)));
        }
        return Optional.empty();
    }

    static String parseDelimiters(String stringNumbers) {
        return parseDelimitersDefinition(stringNumbers, SINGLE_CHAR_DELIMITER_DEF).orElseGet(
                () -> parseDelimitersDefinition(stringNumbers, VARIABLE_LENGTH_DELIMITER_DEF).orElse(DEFAULT_DELIMITERS_REGEXP)
        );
    }

    static String removeDelimitersDefinition(String input) {
        if (input.startsWith(DELIMITERS_DEF_STARTER)) {
            return input.substring(input.indexOf('\n') + 1);
        }
        return input;
    }

    static void validateInput(String input, String delimiter) {
        if (input.startsWith(DELIMITERS_DEF_STARTER) && input.indexOf('\n') + 1 == input.length()) {
            throw new InvalidNumberStringException();
        }

        Pattern pattern = Pattern.compile(String.format(STRING_VALIDATION_FORMAT, delimiter), Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            throw new InvalidNumberStringException();
        }
    }

    static List<Integer> parseNumbers(String input, String delimiters) {
        return Arrays.stream(input.split(delimiters))
                .map(Integer::parseInt)
                .filter(number -> number <= DEFAULT_MAX_NUMBER)
                .collect(Collectors.toList());
    }
}
