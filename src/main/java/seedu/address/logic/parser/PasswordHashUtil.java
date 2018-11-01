package seedu.address.logic.parser;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import seedu.address.model.login.HashingException;

/**
 * A utility class for hashing passwords.
 * The code is taken from https://www.baeldung.com/sha-256-hashing-java.
 */
public class PasswordHashUtil {

    private static final String SHA_256_ALGORITHM = "SHA-256";

    /**
     * Uses the SHA-256 algorithm to hash a string.
     * @param pw The password supplied by the user
     * @return The SHA-256 hash of the supplied password
     * @throws HashingException
     */
    public static String hash(String pw) throws HashingException {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256_ALGORITHM);
            byte[] encodedHash =
                    digest.digest(pw.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new HashingException();
        }
    }

    /**
     * A helper function to convert a byte array to a hex string.
     * @param hash The hashed password from the algorithm
     * @return The string representation of the byte array
     */
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }
        return hexString.toString();
    }
}
