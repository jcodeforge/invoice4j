package io.github.jcodeforge.licensing.encryption;

import io.github.jcodeforge.licensing.exception.KeyNotFoundException;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class EncryptedKeyDataProvider implements KeyDataProvider {

    private final Path mPrivateKeyPath;

    public EncryptedKeyDataProvider() {
        mPrivateKeyPath = null;
    }

    public EncryptedKeyDataProvider(Path privateKeyPath) {
        mPrivateKeyPath = privateKeyPath;
    }

    @Override
    public byte[] getEncryptedPrivateKeyData() throws KeyNotFoundException {
        if (mPrivateKeyPath == null || !Files.isRegularFile(mPrivateKeyPath)) {
            throw new KeyNotFoundException("Private key for signing not found at: " + mPrivateKeyPath);
        }

        try {
            return Files.readAllBytes(mPrivateKeyPath);
        } catch (IOException e) {
            throw new KeyNotFoundException("Could not read private key for signing", e);
        }
    }

    @Override
    public byte[] getEncryptedPublicKeyData() throws KeyNotFoundException {
        try(final InputStream stream = this.getClass().getClassLoader()
                .getResourceAsStream("id_rsa.public")) {
            if (stream != null) {
                return IOUtils.toByteArray(stream);
            }
        }
        catch(Exception e) {
            throw new KeyNotFoundException("The public key file was not found.", e);
        }

        return null;
    }
}
