package encryption;

import static org.junit.Assert.*;
import io.github.jcodeforge.licensing.encryption.KeyFileUtils;
import io.github.jcodeforge.licensing.encryption.PasswordProvider;
import io.github.jcodeforge.licensing.encryption.PrivateKeyPasswordProvider;
import io.github.jcodeforge.licensing.encryption.RSAKeyPairGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Test class for RSAKeyPairGenerator.
 */
@SuppressWarnings("EmptyMethod")
public class RSAKeyPairGeneratorUnitTest {

    private RSAKeyPairGenerator mRSAKeyPairGenerator;
    private PasswordProvider mPasswordProvider;

    @Before
    public void setUp() {
        mRSAKeyPairGenerator = new RSAKeyPairGenerator();
        mPasswordProvider = new PrivateKeyPasswordProvider();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testSaveKeyPairToFiles() throws IOException {
        String privateKeyName = "id_rsa.private";
        String publicKeyName = "id_rsa.public";
        char[] password = mPasswordProvider.getPassword();

        File privateKeyFile = new File(privateKeyName);
        File publicKeyFile = new File(publicKeyName);

        if(privateKeyFile.exists()) {
            FileUtils.forceDelete(privateKeyFile);
        }

        if(publicKeyFile.exists()) {
            FileUtils.forceDelete(publicKeyFile);
        }

        mRSAKeyPairGenerator.saveKeyPairToFiles(privateKeyName, publicKeyName, password);

        assertTrue("Public key should exist.", privateKeyFile.exists());
        assertTrue("Private key should exist.", publicKeyFile.exists());

        PrivateKey privateKey = KeyFileUtils.readEncryptedPrivateKey(privateKeyFile, password);
        PublicKey publicKey = KeyFileUtils.readEncryptedPublicKey(publicKeyFile, password);

        assertNotNull("The private key should not be null.", privateKey);
        assertNotNull("The public key should not be null.", publicKey);
    }
}
