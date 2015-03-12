import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JUnitParamsRunner.class)
public class PiDigitGeneratorTest {

    @Test(expected = IllegalArgumentException.class)
    public void oneShouldNotBeAbleToCreateWithZeroThreshold() throws Exception {

        new PiDigitGenerator(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void oneShouldNotBeAbleToCreateWithNegativeThreshold() throws Exception {

        new PiDigitGenerator(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionGivenZero() throws Exception {

        final PiDigitGenerator objectUnderTest = new PiDigitGenerator(32);

        objectUnderTest.generate(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionGivenNegativeValue() throws Exception {

        final PiDigitGenerator objectUnderTest = new PiDigitGenerator(32);

        objectUnderTest.generate(-666);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionTryingToCalculateDigitThatExceedThreshold() throws Exception {

        final PiDigitGenerator objectUnderTest = new PiDigitGenerator(64);

        objectUnderTest.generate(65);

    }

    @Test
    @Parameters({
            "1    |  3",
            "2    |  1",
            "4    |  1",
            "8    |  6",
            "16   |  3",
            "32   |  5",
            "64   |  2",
            "128  |  6",
            "256  |  8",
            "512  |  4"
    })
    public void shouldReturnProperPiDigitBelowThreshold(long n, int expectedResult) throws Exception {

        final PiDigitGenerator objectUnderTest = new PiDigitGenerator(512);

        assertThat(objectUnderTest.generate(n)).isEqualTo(expectedResult);

    }
}