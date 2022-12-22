package factorial;

import java.math.BigInteger;

public class RegularFactorial {

    public static BigInteger calc(BigInteger factorial) {
        BigInteger res = BigInteger.ONE;
        for (BigInteger i = BigInteger.ONE; i.compareTo(factorial) <= 0; i = i.add(BigInteger.ONE)) {
            res = res.multiply(i);
        }
        return res;
    }
}
