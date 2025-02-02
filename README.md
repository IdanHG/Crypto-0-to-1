# Crypto-0-to-1
This repository contains implementations of fundamental cryptographic algorithms and mathematical utilities developed as part of an honors program in computer science. The project focuses on implementing core cryptographic concepts from scratch, emphasizing understanding of the underlying mathematical principles.
Implemented Algorithms
1. Textbook RSA 

**Complete RSA implementation with:**

Key generation
Encryption
Decryption using Chinese Remainder Theorem (CRT) optimization
Prime number generation for key components



**2. Diffie-Hellman Key Exchange**

Implementation includes:

Prime number generation for group parameters
Generator finding algorithm
Private and public key generation
Shared secret computation

3. Prime Number Operations

Miller-Rabin primality testing
Random prime number generation
Probabilistic primality testing with configurable accuracy

4. Mathematical Utilities

Karatsuba multiplication algorithm for large numbers
Extended Euclidean Algorithm
Modular arithmetic operations
Custom division implementation
Random number generation utilities


**Features**

All implementations use Java's BigInteger class for arbitrary-precision arithmetic
Comprehensive test cases for each algorithm
Performance optimizations (e.g., CRT for RSA decryption)
Configurable security parameters (bit lengths, test iterations)

**Usage**

Prerequisites

Java Development Kit (JDK) 8 or higher
Java IDE (optional)

**Running the Tests**
javac Main.java
java Main

**The Main class includes test cases for:**

Karatsuba multiplication
Division operations
Extended Euclidean Algorithm
Prime number generation
Diffie-Hellman key exchange
RSA encryption/decryption

**Implementation Notes**

Textbook RSA

Uses CRT (chinese remainder theorem) for faster decryption
Implements secure prime generation
Includes parameter validation

Diffie-Hellman

Generates safe primes (p = 2q + 1 where q is prime)
Includes proper generator finding algorithm
Implements secure private key generation

Prime Number Generation and Testing
Interaction between Primes and Miller-Rabin Classes
The prime number generation system uses two main classes that work together:

  1.Primes Class
  
  -Entry point for prime number generation
  -Manages the prime candidate selection process
  -Implements the overall primality testing strategy
  
  
  2.MillerRabin Class
  
  -Implements the core primality testing algorithm
  -Provides mathematical certainty for prime verification

**Mathematical Background**

The Miller-Rabin test is based on two key theorems:

-Fermat's Little Theorem

If p is prime and a is not divisible by p
Then a^(p-1) ≡ 1 (mod p)

-Square Root Property

In Z_p where p is prime
If x² ≡ 1 (mod p), then x ≡ ±1 (mod p)

**Mathematical Utilities**


-Karatsuba multiplication for improved performance
-Custom division implementation
-Secure random number generation

**Academic Context**

This project was developed as part of a university honors program, focusing on:

-Understanding fundamental cryptographic concepts

-Implementing mathematical algorithms from scratch

-Working with large numbers and modular arithmetic

-Applying optimizations to standard algorithms
