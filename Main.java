import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        
        //karatsuba checker
        System.out.println("Karatsuba Checker");
        Random rndX = new Random();
        BigInteger x = new BigInteger(500, rndX);
        Random rndY = new Random();
        BigInteger y = new BigInteger(500, rndY);

        System.out.println("First num is" + x);
        System.out.println("Second num is" + y);

        System.out.println("Product is" + Arithmetic.karatsuba(x, y));


        //division checker
        System.out.println("Division Checker");
        Random random = new Random();

        // 8-bit number divide by 23
        BigInteger a = new BigInteger(8, random);
        BigInteger b = BigInteger.valueOf(23);
        BigInteger[] res1 = Arithmetic.computeDivision(a, b);

        // 512-bit number divide by 12345
        BigInteger c = new BigInteger(512, random);
        BigInteger d = BigInteger.valueOf(12345);
        BigInteger[] res2 = Arithmetic.computeDivision(c, d);
        System.out.println("8-bit number- " + a);
        System.out.println("divide by 23: q = " + res1[0] + "; r = " + res1[1] + ";");
        System.out.println("512-bit number- " + c);
        System.out.println("divide by 12345: q = " + res2[0] + "; r = " + res2[1] + ";");

        //Euclidean checker
        System.out.println("Extended Euclidean Checker");
        BigInteger modulus = BigInteger.valueOf(999331);
        BigInteger number = BigInteger.valueOf(296);

        // Use Extended Euclidean Algorithm to calculate the modular inverse of 'number' in Z-999331
        BigInteger[] exeuclid1 = Arithmetic.ExtendedEuclid(modulus, number);
        if (exeuclid1[0].equals(BigInteger.ONE)) {
            System.out.println("The modular inverse of " + number + " in Z-999331 is " + exeuclid1[2].mod(modulus));
        } else {
            System.out.println(number + " has no inverse in Z-999331");
        }

        // Generate a random number in the range [0, 999330]
        Random randomGenerator = new Random();
        BigInteger randomNum = new BigInteger(modulus.subtract(BigInteger.ONE).bitLength(), randomGenerator);

        // Ensure the generated number is within the valid range
        if (randomNum.compareTo(modulus.subtract(BigInteger.ONE)) > 0 || randomNum.compareTo(BigInteger.ZERO) < 0) {
            randomNum = randomNum.mod(modulus);
        }

        // Calculate the modular inverse of the random number in Z-999331 using Extended Euclidean Algorithm
        BigInteger[] exeuclid2 = Arithmetic.ExtendedEuclid(modulus, randomNum);
        if (exeuclid2[0].equals(BigInteger.ONE)) {
            System.out.println("The modular inverse of " + randomNum + " in Z-999331 is " + exeuclid2[2].mod(modulus));
        } else {
            System.out.println(randomNum + " has no inverse in Z-999331");
        }
        
        
        int bitLength = 512;
        int accuracy = 40;

        // Sample 10 random primes and count attempts
        int totalAttempts = 0;
        System.out.println("------------------------- \n HERE IS A PRIME SAMPLING RUNNING EXAMPLE :) \n -------------------------");
        for (int i = 0; i < 10; i++) {
            int attempts = 0;
            BigInteger prime = new BigInteger(bitLength, new Random()).setBit(bitLength - 1).setBit(0); // Ensure odd and correct length
            while (!Primes.isProbablePrime(prime, accuracy)) {
                prime = new BigInteger(bitLength, new Random()).setBit(bitLength - 1).setBit(0); // Ensure odd and correct length
                attempts++;
            }
            System.out.println("Prime " + (i + 1) + ": " + prime);
            System.out.println("Attempts: " + attempts);
            totalAttempts += attempts;
        }

        // Calculate and display the average number of attempts
        double averageAttempts = totalAttempts / 10.0;
        System.out.println("Average attempts: " + averageAttempts);

        // Calculate expected number of attempts based on Prime Number Theorem
        double expectedAttempts = Math.log(2) * bitLength;
        System.out.println("Expected attempts (PNT): " + expectedAttempts);
        System.out.println("The difference between the Average number of attempts is " + (expectedAttempts - averageAttempts));

        
        // Example for Diffie-Hellman Key Exchange
        //int bitLength = 512;
        int k = 40;

        // Generate the parameters
        BigInteger q1 = Primes.samplePrime(bitLength - 1, k);
        BigInteger p1 = q1.multiply(BigInteger.TWO).add(BigInteger.ONE);
        while (!Primes.isProbablePrime(p1, k)) {
            q1 = Primes.samplePrime(bitLength - 1, k);
            p1 = q1.multiply(BigInteger.TWO).add(BigInteger.ONE);
        }
        BigInteger g = DiffieHelman.findGenerator(q1, p1);

        // Alice's keys
        BigInteger a1 = DiffieHelman.generatePrivateKey(q1);
        BigInteger hA = DiffieHelman.computePublicKey(g, a1, p1);

        // Bob's keys
        BigInteger b1 = DiffieHelman.generatePrivateKey(q1);
        BigInteger hB = DiffieHelman.computePublicKey(g, b1, p1);

        // Shared secret keys
        BigInteger sharedKeyAlice = DiffieHelman.computeSharedKey(hB, a1, p1);
        BigInteger sharedKeyBob = DiffieHelman.computeSharedKey(hA, b1, p1);

        // Output results
        System.out.println("------------------------- \n HERE IS Diffie-Hellman RUNNING EXAMPLE :) \n -------------------------");
        System.out.println("Prime q: " + q1);
        System.out.println("Prime p: " + p1);
        System.out.println("Generator g: " + g);

        System.out.println("Alice's private key: " + a1);
        System.out.println("Alice's public key (hA): " + hA);

        System.out.println("Bob's private key: " + b1);
        System.out.println("Bob's public key (hB): " + hB);

        System.out.println("Shared key (Alice): " + sharedKeyAlice);
        System.out.println("Shared key (Bob): " + sharedKeyBob);

        System.out.println("Shared keys match: " + sharedKeyAlice.equals(sharedKeyBob));


        // Example call to the RSA algorithm
        System.out.println("--- RSA Algorithm Execution ---");
        BigInteger[] keyPair = RSA.generateRSAKeys(512, 40);

        BigInteger N = keyPair[0];
        BigInteger e = keyPair[1];
        BigInteger d1 = keyPair[2];
        BigInteger p = keyPair[3];
        BigInteger q = keyPair[4];

        System.out.println("Public key (N, e): (" + N + ", " + e + ")");
        System.out.println("Private key components: d = " + d1 + ", p = " + p + ", q = " + q);

        BigInteger plaintext = Arithmetic.getRandomBigInteger(N);
        System.out.println("Plaintext: " + plaintext);

        BigInteger ciphertext = RSA.encryptRSA(plaintext, e, N);
        System.out.println("Ciphertext: " + ciphertext);

        BigInteger decryptedText = RSA.CRTdecryptRSA(ciphertext, d1, p, q);
        System.out.println("Decrypted text: " + decryptedText);

        System.out.println("Decryption successful: " + plaintext.equals(decryptedText));
        }


    }



