import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHelman {
    /**
         * Finds a generator for the subgroup of order q in Zp*.
         * A valid generator g satisfies certain conditions for the group structure.
         *
         * @param q The order of the subgroup.
         * @param p The prime modulus.
         * @return A generator g for the subgroup.
         */
        public static BigInteger findGenerator(BigInteger q, BigInteger p) {
            BigInteger g = BigInteger.TWO;
            while (true) {
                if (!g.modPow(BigInteger.TWO, p).equals(BigInteger.ONE) && !g.modPow(q, p).equals(BigInteger.ONE)) {
                    break;
                }
                g = g.add(BigInteger.ONE);
            }
            return g;
        }

        /**
         * Generates a private key randomly within the range [1, q-1].
         *
         * @param q The order of the subgroup.
         * @return A randomly generated private key.
         */
        public static BigInteger generatePrivateKey(BigInteger q) {
            return Arithmetic.randomBetween(BigInteger.ONE, q.subtract(BigInteger.ONE), new SecureRandom());
        }

        /**
         * Computes the public key based on the generator, private key, and prime modulus.
         * The public key is calculated as g^privateKey mod p.
         *
         * @param g The generator.
         * @param privateKey The private key.
         * @param p The prime modulus.
         * @return The computed public key.
         */
        public static BigInteger computePublicKey(BigInteger g, BigInteger privateKey, BigInteger p) {
            return g.modPow(privateKey, p);
        }

        /**
         * Computes the shared secret key based on the other party's public key,
         * the private key, and the prime modulus.
         * The shared key is calculated as publicKey^privateKey mod p.
         *
         * @param publicKey The public key of the other party.
         * @param privateKey The private key of the current party.
         * @param p The prime modulus.
         * @return The computed shared secret key.
         */
        public static BigInteger computeSharedKey(BigInteger publicKey, BigInteger privateKey, BigInteger p) {
            return publicKey.modPow(privateKey, p);
        }
}
