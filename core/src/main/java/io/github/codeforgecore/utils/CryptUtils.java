package io.github.codeforgecore.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class CryptUtils {

    public static String sha512(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        return bytesToHex(md.digest(input.getBytes(StandardCharsets.UTF_8)));
    }

    public static String sha256(File file) {
        MessageDigest md;
        try (InputStream is = new FileInputStream(file)) {
            md = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[8192];
            int read;

            while ((read = is.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return bytesToHex(md.digest());
    }

    public static String sha256(String input, String salt) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        // Passing the salt to the digest for the computation
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(md.digest(input.getBytes(StandardCharsets.UTF_8)));
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
