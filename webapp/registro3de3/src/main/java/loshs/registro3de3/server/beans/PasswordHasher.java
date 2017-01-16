package loshs.registro3de3.server.beans;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Singleton;
import org.apache.commons.codec.binary.Base64;

@Singleton
public class PasswordHasher {

    private static final int ITERATIONS = 1 * 1000;
    private static final int SALT_LENGTH = 32;
    private static final int DESIRED_LENGTH = 256;

    private static SecureRandom RANDOM;

    static final Logger LOGGER = Logger.getLogger(PasswordHasher.class.getName());

    static {
        try {
            RANDOM = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public PasswordHasher() {

    }

    public String getSecureHash(char[] password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = new byte[SALT_LENGTH];
        if (RANDOM != null) {
            RANDOM.nextBytes(salt);
        } else {
            throw new NullPointerException("Secure random is not instanciated.");
        }
        //byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(SALT_LENGTH);
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    public boolean compare(char[] password, String stored) throws Exception {
        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException("The stored password does not have the form 'salt$hash'");
        }
        String hashInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return hashInput.equals(saltAndPass[1]);
    }

    private String hash(char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (password == null || password.length == 0) {
            throw new IllegalArgumentException("Empty passwords are not supported.");
        }
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(password, salt, ITERATIONS, DESIRED_LENGTH));
        return Base64.encodeBase64String(key.getEncoded());
    }
}
