package edu.unifi.model.util.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides methods for password hashing and authentication.
 * <p>
 * Usage:
 * <pre>
 *     // Create a password
 *     char[] password = "myPassword".toCharArray();
 *
 *     // Hash the password
 *    String hashedPassword = PasswordManager.hash(password);
 *
 *    // Authenticate the password
 *    boolean isAuthenticated = PasswordManager.authenticate(password, hashedPassword);
 *
 *    // Print the result
 *    System.out.println("Is authenticated: " + isAuthenticated);
 * </pre>
 */
public final class PasswordManager {
    public static final String ID = "$31$";
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int SIZE = 128;
    private static final Pattern layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");
    private static final SecureRandom random = new SecureRandom();
    private static final int cost = 16; // Cost of each iteration from 0 to 30

    /**
     * Calculates the number of iterations for the given cost.
     *
     * @param cost the cost of each iteration
     * @return the number of iterations
     * @throws IllegalArgumentException if the cost is not between 0 and 30
     */
    private static int iterations(int cost) {
        if ((cost < 0) || (cost > 30))
            throw new IllegalArgumentException("cost: " + cost);
        return 1 << cost;
    }

    /**
     * Hashes the given password.
     *
     * @param password the password to be hashed
     * @return the hashed password
     */
    public static String hash(char[] password) {
        byte[] salt = new byte[SIZE / 8];
        random.nextBytes(salt);
        byte[] dk = pbkdf2(password, salt, 1 << cost);
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        return ID + cost + '$' + enc.encodeToString(hash);
    }

    /**
     * Authenticates the given password with the given token.
     *
     * @param password the password to be authenticated
     * @param token    the token to be used for authentication
     * @return true if the password is authenticated, false otherwise
     * @throws IllegalArgumentException if the token format is invalid
     */
    public static boolean authenticate(char[] password, String token) {
        Matcher m = layout.matcher(token);
        if (!m.matches())
            throw new IllegalArgumentException("Invalid token format");
        int iterations = iterations(Integer.parseInt(m.group(1)));
        byte[] hash = Base64.getUrlDecoder().decode(m.group(2));
        byte[] salt = Arrays.copyOfRange(hash, 0, SIZE / 8);
        byte[] check = pbkdf2(password, salt, iterations);
        int zero = 0;
        for (int idx = 0; idx < check.length; ++idx)
            zero |= hash[salt.length + idx] ^ check[idx];
        return zero == 0;
    }

    /**
     * Generates a secret key based on the given password, salt, and iterations.
     *
     * @param password   the password
     * @param salt       the salt
     * @param iterations the number of iterations
     * @return the secret key
     * @throws IllegalStateException if the algorithm is missing or the SecretKeyFactory is invalid
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
        KeySpec spec = new PBEKeySpec(password, salt, iterations, SIZE);
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
        } catch (InvalidKeySpecException ex) {
            throw new IllegalStateException("Invalid SecretKeyFactory", ex);
        }
    }
}
