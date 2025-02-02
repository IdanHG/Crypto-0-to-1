import java.math.BigInteger;
import java.util.Random;

/**
 * A utility class for generating and testing prime numbers.
 * This class implements probabilistic primality testing using the Miller-Rabin algorithm.
 */
public class Primes {

    /**
     * Generates a random prime number of specified bit length.
     * The generated number is tested for primality using the Miller-Rabin test.
     * 
     * @param bitLength The desired length of the prime number in bits
     * @param k The number of rounds for the Miller-Rabin primality test
     *         (higher k means higher accuracy but slower performance)
     * @return A probable prime number of the specified bit length
     */
    public static BigInteger samplePrime(int bitLength, int k) {
        Random random = new Random();
        
        // Generate a random odd number of specified bit length
        // setBit(bitLength - 1) ensures the number has exactly bitLength bits
        // setBit(0) ensures the number is odd
        BigInteger candidate = new BigInteger(bitLength, random)
                             .setBit(bitLength - 1)
                             .setBit(0);
        
        // Keep generating numbers until we find a probable prime
        while (!isProbablePrime(candidate, k)) {
            candidate = new BigInteger(bitLength, random)
                       .setBit(bitLength - 1)
                       .setBit(0);
        }
        
        return candidate;
    }
    
    /**
     * Tests if a number is probably prime using the Miller-Rabin primality test.
     * The probability of a composite number passing the test is at most 4^(-k).
     * 
     * @param n The number to test for primality
     * @param k The number of rounds of testing to perform
     * @return true if n is probably prime, false if n is definitely composite
     */
    public static boolean isProbablePrime(BigInteger n, int k) {
        // Handle small cases
        if (n.equals(BigInteger.TWO)) return true;
        if (n.compareTo(BigInteger.TWO) < 0 || n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) 
            return false;

        // Write n-1 as u * 2^r where u is odd
        BigInteger u = n.subtract(BigInteger.ONE);
        int r = 0;
        while (u.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            u = Arithmetic.computeDivision(u, BigInteger.TWO)[0];
            r++;
        }

        // Perform k rounds of Miller-Rabin testing
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            // Choose a random base a in [2, n-2]
            BigInteger a = Arithmetic.randomBetween(BigInteger.TWO, 
                                                  n.subtract(BigInteger.ONE), 
                                                  random);
            
            // Perform Miller-Rabin test with base a
            if (!MillerRabin.millerRabinTest(a, r, u, n)) {
                return false;  // n is definitely composite
            }
        }

        return true;  // n is probably prime
    }
}