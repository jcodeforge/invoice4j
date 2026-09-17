package validation.genericode;

import io.github.jcodeforge.invoice4jxr.validation.genericode.GenericodeCodeList;
import io.github.jcodeforge.invoice4jxr.validation.genericode.GenericodeRegistry;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenericodeRegistryTest {

    private final GenericodeRegistry SUT = GenericodeRegistry.fromResource("/genericode/currency-codes_3.gc");

    @Test
    public void shouldAcceptValidCode() {
        assertTrue(SUT.contains("EUR"));
        assertTrue(SUT.contains("USD"));
    }

    @Test
    public void shouldRejectInvalidCode() {
        assertFalse(SUT.contains("INVALID"));
    }

    @Test
    public void shouldRejectNullCode() {
        assertFalse(SUT.contains(null));
    }

    @Test
    public void shouldRejectBlankCode() {
        assertFalse(SUT.contains(""));
        assertFalse(SUT.contains(" "));
    }

    @Test
    public void shouldExposeCodeList() {
        GenericodeCodeList codeList = SUT.getCodeList();

        assertNotNull(codeList);
        assertEquals("Currency-Codes", codeList.getShortName());
        assertEquals("3", codeList.getVersion());
        assertEquals("AlphabeticCode", codeList.getCodeColumn());
        assertTrue(codeList.contains("EUR"));
        assertTrue(codeList.contains("USD"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMissingResource() {
        GenericodeRegistry.fromResource("/genericode/does-not-exist.gc");
    }
}