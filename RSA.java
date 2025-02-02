import java.math.BigInteger;

/**
 * Implementation of the RSA cryptosystem with Chinese Remainder Theorem (CRT) optimization
 * for decryption. This implementation includes key generation, encryption, and CRT-based
 * decryption.
 */
public class RSA {
    
    /**
     * Generates RSA key pairs with specified bit length and primality certainty.
     * 
     * @param bitLength The bit length of the modulus N (should be even)
     * @param certainty The probability of primality: 1 - (1/2)^certainty
     * @return BigInteger array containing [N, e, d, p, q] where:
     *         N: modulus
     *         e: public exponent
     *         d: private exponent
     *         p, q: prime factors of N
     * @throws IllegalArgumentException if bitLength is odd or too small
     */
    public static BigInteger[] generateRSAKeys(int bitLength, int certainty) {
        // Validate input parameters
        if (bitLength < 8 || bitLength % 2 != 0) {
            throw new IllegalArgumentException("Bit length must be even and at least 8");
        }
        if (certainty < 1) {
            throw new IllegalArgumentException("Certainty must be positive");
        }

        // Generate two distinct prime numbers of equal bit length
        BigInteger p, q;
        do {
            p = Primes.samplePrime(bitLength / 2, certainty);
            q = Primes.samplePrime(bitLength / 2, certainty);
        } while (p.equals(q));  // Ensure p and q are different

        // Calculate modulus N and Euler's totient function φ(N)
        BigInteger N = p.multiply(q);
        BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Find public exponent e that is coprime with φ(N)
        // Starting with e = 3 and incrementing by 2 to maintain odd values
        BigInteger e = BigInteger.valueOf(3);
        while (!Arithmetic.ExtendedEuclid(e, phiN)[0].equals(BigInteger.ONE)) {
            e = e.add(BigInteger.TWO);
            if (e.compareTo(phiN) >= 0) {
                // If e becomes too large, we need new primes
                return generateRSAKeys(bitLength, certainty);
            }
        }

        // Calculate private exponent d
        BigInteger d = Arithmetic.computeModInverse(e, phiN);
        
        // Verify that e and d are multiplicative inverses modulo φ(N)
        if (!e.multiply(d).mod(phiN).equals(BigInteger.ONE)) {
            throw new IllegalStateException("Key generation failed: invalid e,d pair");
        }

        return new BigInteger[] {N, e, d, p, q};
    }

    /**
     * Encrypts a message using RSA.
     * 
     * @param message The message to encrypt (must be less than N)
     * @param e Public exponent
     * @param N Modulus
     * @return Encrypted message
     * @throws IllegalArgumentException if message ≥ N
     */
    public static BigInteger encryptRSA(BigInteger message, BigInteger e, BigInteger N) {
        if (message.compareTo(N) >= 0) {
            throw new IllegalArgumentException("Message must be smaller than modulus N");
        }
        if (message.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Message must be non-negative");
        }
        
        return message.modPow(e, N);
    }

    /**
     * Decrypts RSA ciphertext using Chinese Remainder Theorem (CRT) optimization.
     * This method is typically 4 times faster than standard RSA decryption.
     * 
     * @param ciphertext The encrypted message
     * @param d Private exponent
     * @param p First prime factor of N
     * @param q Second prime factor of N
     * @return Decrypted message
     * @throws IllegalArgumentException if ciphertext ≥ p*q or parameters are invalid
     */
    public static BigInteger CRTdecryptRSA(BigInteger ciphertext, BigInteger d, BigInteger p, BigInteger q) {
        // First, validate input
        BigInteger N = p.multiply(q);
        if (ciphertext.compareTo(N) >= 0) {
            throw new IllegalArgumentException("Ciphertext must be smaller than N");
        }
    
        // Calculate CRT exponents
        BigInteger dp = d.mod(p.subtract(BigInteger.ONE));
        BigInteger dq = d.mod(q.subtract(BigInteger.ONE));
    
        // Compute partial decryptions
        BigInteger m1 = ciphertext.modPow(dp, p);
        BigInteger m2 = ciphertext.modPow(dq, q);
    
        // Calculate q^(-1) mod p using modInverse instead of ExtendedEuclid
        BigInteger qInv = q.modInverse(p);
    
        // Calculate h = (m1 - m2) * qInv mod p
        BigInteger h = m1.subtract(m2).multiply(qInv).mod(p);
    
        // Final combination: m = m2 + h * q
        return m2.add(h.multiply(q)).mod(N);
    }
}