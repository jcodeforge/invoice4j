package validation.genericode;

import io.github.jcodeforge.invoice4jxr.validation.genericode.GenericodeCodeList;
import io.github.jcodeforge.invoice4jxr.validation.genericode.GenericodeReader;
import org.junit.Test;
import java.io.InputStream;

import static org.junit.Assert.*;

public class GenericodeReaderTest {

    private final GenericodeReader SUT = new GenericodeReader();

    @Test
    public void shouldReadCurrencyCodeList() {
        try (InputStream input = getClass().getResourceAsStream(
                "/genericode/currency-codes_3.gc")) {

            assertNotNull(input);

            GenericodeCodeList codeList = SUT.read(input);

            assertNotNull(codeList);

            assertEquals("Currency-Codes", codeList.getShortName());
            assertEquals("3", codeList.getVersion());
            assertEquals("AlphabeticCode", codeList.getCodeColumn());

            assertTrue(codeList.contains("EUR"));
            assertTrue(codeList.contains("USD"));
            assertFalse(codeList.contains("INVALID"));
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    public void shouldFindXppUnitCode() {
        String[] resources = {
                "/genericode/rec20_3.gc",
                "/genericode/rec21_3.gc",
                "/genericode/untdid.7161_3.gc",
                "/genericode/untdid.7143_4.gc"
        };

        GenericodeReader reader = new GenericodeReader();

        for (String resource : resources) {
            GenericodeCodeList codeList =
                    reader.readResource(resource);

            if (codeList.contains("XPP")) {
                System.out.println("XPP found in: " + resource + " (" + codeList.getShortName() + ")");
            }
        }
    }
}