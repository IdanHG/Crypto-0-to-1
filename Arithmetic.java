import java.math.BigInteger;
import java.util.Random;

/**
* A utility class providing various arithmetic operations for BigInteger values.
* Includes implementations of Karatsuba multiplication, extended Euclidean algorithm,
* modular arithmetic, and random number generation.
*/
public class Arithmetic {
   
   /**
    * Implements the Karatsuba algorithm for fast multiplication of large numbers.
    * This is an efficient divide-and-conquer algorithm that reduces the number of 
    * single-digit multiplications.
    *
    * @param x First BigInteger to multiply
    * @param y Second BigInteger to multiply
    * @return The product x * y
    */
   public static BigInteger karatsuba(BigInteger x, BigInteger y) {
       // Base case: for small numbers, use regular multiplication
       if (x.intValue() <= 1 && y.intValue() <= 1) {
           return x.multiply(y);
       }

       // Find the maximum bit length of the numbers
       int n = Math.max(x.bitLength(), y.bitLength());

       // Round up n/2 to handle odd lengths
       n = n / 2 + n % 2;

       // Split numbers into high and low parts:
       // x = a + b*2^n
       // y = c + d*2^n
       BigInteger b = x.shiftRight(n);                    // b = high bits of x
       BigInteger a = x.subtract(b.shiftLeft(n));         // a = low bits of x
       BigInteger d = y.shiftRight(n);                    // d = high bits of y
       BigInteger c = y.subtract(d.shiftLeft(n));         // c = low bits of y

       // Recursively compute the three products needed
       BigInteger A1 = karatsuba(a, c);      // ac
       BigInteger A2 = karatsuba(b, d);      // bd
       BigInteger A3 = karatsuba(a.add(b), c.add(d));  // (a+b)(c+d)

       // Combine results using the Karatsuba formula:
       // result = A1 + (A3 - A1 - A2)*2^n + A2*2^(2n)
       return A1.shiftLeft(n * 2)
               .add((A3.subtract(A1)).subtract(A2).shiftLeft(n))
               .add(A2);
   }
   
   /**
    * Computes quotient and remainder for division using recursive bit operations.
    *
    * @param x Dividend
    * @param y Divisor
    * @return Array containing [quotient, remainder]
    */
   public static BigInteger[] computeDivision(BigInteger x, BigInteger y) {
       BigInteger[] output = { BigInteger.ZERO, BigInteger.ZERO };
       // Base case: if dividend is 0
       if (x.equals(BigInteger.ZERO)) {
           return output;
       }

       // Recursive case: divide x/2 first
       output = computeDivision(x.divide(BigInteger.TWO), y);
       output[0] = output[0].multiply(BigInteger.TWO);    // Double quotient
       output[1] = output[1].multiply(BigInteger.TWO);    // Double remainder

       // If x is odd, add 1 to remainder
       if (x.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
           output[1] = output[1].add(BigInteger.ONE);
       }

       // If remainder ≥ divisor, subtract divisor and increment quotient
       if (output[1].compareTo(y) >= 0) {
           output[1] = output[1].subtract(y);
           output[0] = output[0].add(BigInteger.ONE);
       }

       return output;
   }
   
   /**
    * Implements the Extended Euclidean Algorithm.
    * For given a and b, finds gcd(a,b) and coefficients x,y such that:
    * ax + by = gcd(a,b)
    *
    * @param a First number
    * @param b Second number
    * @return Array containing [gcd, x, y]
    */
   public static BigInteger[] ExtendedEuclid(BigInteger a, BigInteger b) {
       BigInteger[] result = new BigInteger[3];
       
       // Base case: if b = 0, gcd = a, coefficients are 1,0
       if (b.equals(BigInteger.ZERO)) {
           result[0] = a;                  // gcd
           result[1] = BigInteger.ONE;     // x coefficient
           result[2] = BigInteger.ZERO;    // y coefficient
       } else {
           // Recursive case
           BigInteger[] quotientAndRemainder = computeDivision(a, b);
           BigInteger[] recursiveSolution = ExtendedEuclid(b, quotientAndRemainder[1]);
           
           // Build solution from recursive call
           result[0] = recursiveSolution[0];    // gcd stays the same
           result[1] = recursiveSolution[2];    // x = previous y
           // y = previous x - quotient * previous y
           result[2] = recursiveSolution[1].subtract(quotientAndRemainder[0].multiply(recursiveSolution[2]));
       }
       return result;
   }

   /**
    * Computes the modular multiplicative inverse using the Extended Euclidean Algorithm.
    *
    * @param a Number to find inverse for
    * @param m Modulus
    * @return The modular multiplicative inverse of a modulo m
    * @throws ArithmeticException if the inverse doesn't exist
    */
   public static BigInteger computeModInverse(BigInteger a, BigInteger m) {
       BigInteger[] result = ExtendedEuclid(a, m);
       BigInteger gcd = result[0];
       BigInteger x = result[1];

       if (!gcd.equals(BigInteger.ONE)) {
           throw new ArithmeticException("Modular inverse does not exist");
       }

       return x.mod(m);
   }

   /**
    * Generates a random BigInteger in the range [min, max).
    *
    * @param min Lower bound (inclusive)
    * @param max Upper bound (exclusive)
    * @param random Random number generator
    * @return Random BigInteger in the specified range
    */
   public static BigInteger randomBetween(BigInteger min, BigInteger max, Random random) {
       BigInteger range = max.subtract(min);
       BigInteger result = new BigInteger(range.bitLength(), random);
       while (result.compareTo(range) >= 0) {
           result = new BigInteger(range.bitLength(), random);
       }
       return result.add(min);
   }

   /**
    * Generates a random BigInteger less than the specified upper limit.
    *
    * @param upperLimit Upper bound (exclusive)
    * @return Random BigInteger less than upperLimit
    */
   public static BigInteger getRandomBigInteger(BigInteger upperLimit) {
       Random random = new Random();
       BigInteger result;
       do {
           result = new BigInteger(upperLimit.bitLength(), random);
       } while (result.compareTo(upperLimit) >= 0);
       return result;
   }
}