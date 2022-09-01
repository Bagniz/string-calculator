import exceptions.InvalidNumberStringException;
import exceptions.NegativesNotAllowedException;
import exceptions.NumberStringNullException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("String Calculator Tests")
public class StringCalculatorTest {

    StringCalculator stringCalculator = new StringCalculator();

    @Nested
    @DisplayName("Step 01")
    class StepOneTest {
        @Test
        void shouldThrowIfStringIsNull() {
            assertThatThrownBy(() -> stringCalculator.add(null)).isInstanceOf(NumberStringNullException.class);
        }

        @Test
        void shouldReturnZeroIfEmpty() {
            assertThat(stringCalculator.add("")).isEqualTo(0);
        }

        @Test
        void shouldReturnThenNumberIfOnlyOne() {
            assertThat(stringCalculator.add("1")).isEqualTo(1);
        }

        @Test
        void shouldAddTwoNumbers() {
            assertThat(stringCalculator.add("8,60")).isEqualTo(68);
        }
    }

    @Nested
    @DisplayName("Step 02")
    class StepTwoTest {
        @Test
        void shouldAddMultipleNumbers() {
            assertThat(stringCalculator.add("1,5,8,6,4,3,2,5,1,8")).isEqualTo(43);
        }
    }

    @Nested
    @DisplayName("Step 03")
    class StepThreeTest {
        @Test
        void shouldThrowWhenStringIsInvalid() {
            assertThatThrownBy(() -> stringCalculator.add("1,\n"))
                    .isInstanceOf(InvalidNumberStringException.class);
        }

        @Test
        void shouldAddNumberWhenSomeNewLines() {
            assertThat(stringCalculator.add("1,5\n8,6,4\n3,2,5\n1,8")).isEqualTo(43);
        }

        @Test
        void shouldAddNumberWhenOnlyNewLines() {
            assertThat(stringCalculator.add("1\n5\n8\n6\n4\n3\n2\n5\n1\n8")).isEqualTo(43);
        }
    }

    @Nested
    @DisplayName("Step 04")
    class StepFourTest {
        @Test
        void shouldAddNumbersWithDifferentDelimiter() {
            assertThat(stringCalculator.add("//+\n1+3+5+8")).isEqualTo(17);
        }
    }

    @Nested
    @DisplayName("Step 05")
    class StepFiveTest {
        @Test
        void shouldThrowNegativesNotAllowedException() {
            List<Integer> expectedNegativeNumbers = Arrays.asList(-5, -8, -4, -5);
            assertThatThrownBy(() -> stringCalculator.add("1,-5\n-8,6,-4\n3,2,-5\n1,8"))
                    .isInstanceOf(NegativesNotAllowedException.class)
                    .hasMessageContaining(expectedNegativeNumbers.toString());

        }
    }

    @Nested
    @DisplayName("Step 06")
    class StepSixTest {
        @Test
        void shouldIgnoreInvalidNumbers() {
            assertThat(stringCalculator.add("//;\n1;4665;8;6;1002;3;2;999;1;8")).isEqualTo(1028);
        }
    }

    @Nested
    @DisplayName("Step 07")
    class StepSevenTest {
        @Test
        void shouldAcceptDelimitersWithAnyLength() {
            assertThat(stringCalculator.add("//[test]\n1test1546test5test8465")).isEqualTo(6);
        }

        @Test
        void shouldAcceptDelimitersWithAnyLengthAndSpecialCharacters() {
            assertThat(stringCalculator.add("//[*+*]\n1*+*1546*+*5*+*8465")).isEqualTo(6);
        }
    }
}
