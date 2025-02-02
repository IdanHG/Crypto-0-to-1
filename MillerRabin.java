import java.math.BigInteger;

/**
* Implementation of the Miller-Rabin primality test algorithm.
* This test is a probabilistic primality test that determines whether a given number 
* is likely to be prime.
*/
public class MillerRabin {

   /**
    * Performs one round of the Miller-Rabin primality test.
    * The test is based on the fact that for a prime n, either:
    * 1) a^u ≡ 1 (mod n), or
    * 2) a^(u*2^i) ≡ -1 (mod n) for some i in [0, r-1]
    * where n-1 = u * 2^r and u is odd.
    *
    * @param a The base number to test with, should be in range [2, n-2]
    * @param r The number of times n-1 was divided by 2 to get u
    * @param u The odd number such that n-1 = u * 2^r
    * @param n The number being tested for primality
    * @return true if the number passes this round of testing, false if n is definitely composite
    */
   public static boolean millerRabinTest(BigInteger a, int r, BigInteger u, BigInteger n) {
       // Compute x = a^u mod n
       BigInteger x = a.modPow(u, n);
       
       // First check: if x ≡ ±1 (mod n), n passes this round
       if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
           return true;
       }

       // Additional r-1 checks: repeatedly square x
       // If we ever get -1 mod n, n passes this round
       for (int i = 0; i < r - 1; i++) {
           // x = x^2 mod n
           x = x.modPow(BigInteger.TWO, n);
           
           // If x ≡ -1 (mod n), n passes this round
           if (x.equals(n.subtract(BigInteger.ONE))) {
               return true;
           }
       }

       // If we reach here, n failed this round of testing
       // Therefore n is definitely composite
       return false;
   }
}