import com.google.common.base.Preconditions;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.Apint;
import org.apfloat.ApintMath;

/**
 * Created by novy on 10.03.15.
 */

public class PiDigitGenerator {

    private final long cutoffThreshold;

    public PiDigitGenerator(long cutoffThreshold) {
        Preconditions.checkArgument(cutoffThreshold >= 1, "Cannot create generator with zero or negative threshold");
        this.cutoffThreshold = cutoffThreshold + 1;
    }

    public int generate(long nth) {
        Preconditions.checkArgument(nth > 0, "Cannot calculate nth digit given zero or negative value!");
        Preconditions.checkArgument(nth < cutoffThreshold, "Trying to calculate digit above cutoff threshold!");

        Apfloat sum = new Apfloat(0);

        for (int i = 0; i < nth; i++) {

            final Apfloat numerator = calculateNumerator(i);
            final Apfloat denominator = calculateDenominator(i);

            sum = sum.add(
                    numerator.divide(denominator.precision(cutoffThreshold))
            );
        }

        Apfloat result = inverse(sum.multiply(constant(cutoffThreshold)));

        return getNthDigitFrom(result, nth);


    }

    private Apfloat inverse(Apfloat result) {
        return ApfloatMath.pow(
                result, -1
        );
    }

    private int getNthDigitFrom(Apfloat result, long nth) {
        Apfloat fraction = result
                .divide(
                        new Apfloat(10)
                ).frac();


        long fractionalPrecision = nth;

        while (fractionalPrecision > 0) {
            fraction = fraction.multiply(new Apfloat(10));
            fractionalPrecision--;
        }

        return Integer.valueOf(
                fraction
                        .truncate()
                        .mod(
                                new Apfloat(10)
                        ).toString()
        );
    }

    private Apfloat constant(long precision) {
        return ApfloatMath.sqrt(
                new Apfloat(10005, precision)
        ).divide(
                new Apfloat(42709344 * 100L, precision)
        );
    }

    private Apfloat calculateDenominator(int i) {
        return ApintMath.factorial(3 * i)
                .multiply(
                        ApfloatMath.pow(
                                ApintMath.factorial(i), 3
                        )
                )
                .multiply(
                        ApfloatMath.pow(
                                new Apfloat(640320), 3 * i
                        )
                );
    }

    private Apfloat calculateNumerator(int i) {
        return ApintMath.factorial(6 * i)
                .multiply(ApintMath.pow(new Apint(-1), i))
                .multiply(
                        new Apint(545140134)
                                .multiply(new Apfloat(i))
                                .add(new Apfloat(13591409))

                );


    }
}
