package studio.secretingredients.consult4me.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SecurityUtil class for MD5 based checksum calculation
 *
 * @author corbandalas - created 18.05.2020
 * @since 0.1.0
 *
 */

public class SecurityUtil {

    private static final Base64 theBase64Codec = new Base64();

    public static String generateKeyFromArray(String... params) {
        try {
            byte byteHash[];
            final MessageDigest md5 = MessageDigest.getInstance("SHA-256");

            final StringBuilder buf = new StringBuilder();
            for (final String item : params) {
                buf.append(item);
            }

            md5.update(buf.toString().getBytes());
            byteHash = md5.digest();
            md5.reset();

            return toHexString(byteHash);
        } catch (final NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String generateKeyFromArrayMD5(String... params) {
        try {
            byte byteHash[];
            final MessageDigest md5 = MessageDigest.getInstance("md5");

            final StringBuffer buf = new StringBuffer();
            for (final String item : params) {
                buf.append(item);
            }

            md5.update(buf.toString().getBytes());
            byteHash = md5.digest();
            md5.reset();

            return toHexString(byteHash);
        } catch (final NoSuchAlgorithmException e) {
        }
        return null;
    }

    private static String toHexString(final byte[] array) {
        final StringBuilder sb = new StringBuilder();

        for (final byte element : array) {
            final String hex = "0" + Integer.toHexString(element);
            final String end = hex.substring(hex.length() - 2);
            sb.append(end.toUpperCase());
        }
        return sb.toString();
    }

    public static String encodeString(String str) {
        return new String(theBase64Codec.encode(str.getBytes())).trim();
    }

    public static String decodeString(String str) {
        return new String(theBase64Codec.decode(str.getBytes()));
    }
}





