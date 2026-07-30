package io.github.jcodeforge.core.utils;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipal;
import java.security.KeyStore;
import java.util.Base64;

/**
 * This class contains static helper methods related to security
 */
public abstract class SecurityUtils {

    private static final String TAG = "SecurityUtils";

    public static String encodeStringAsCredential(String stringToEncode) {
        if (stringToEncode != null && !stringToEncode.isEmpty()) {
            byte[] encodedStringBytes;
            encodedStringBytes = ("byebyehackers:" + stringToEncode).getBytes(StandardCharsets.UTF_8);
            return CryptUtils.bytesToHex(Base64.getEncoder().encode(encodedStringBytes));
        }
        return "";
    }

    public static void restrictUserFilePermissions(String filepath) {
        Path path = Paths.get(filepath);
        try {
            // Set owner
            UserPrincipal owner = FileSystems.getDefault()
                    .getUserPrincipalLookupService()
                    .lookupPrincipalByName(System.getProperty("user.name"));
            Files.setOwner(path, owner);
            // Set permissions
            ProcessBuilder pb = new ProcessBuilder(
                    "icacls",
                    filepath,
                    "/grant",
                    System.getProperty("user.name") + ":F"
            );
            pb.inheritIO().start().waitFor();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean isPkcs12File(String filePath, String password) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, password != null ? password.toCharArray() : null);

            return ks.size() > 0;

        } catch (Exception e) {
            return false;
        }
    }
}
