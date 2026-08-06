import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiInvoiceWriter;
import java.io.File;

public final class ExampleGenerator {

    public static void main(String[] args) {
        CiiInvoiceWriter writer = CiiInvoiceWriter.builder()
                .prettyPrint(true)
                .build();

        writer.writeToFile(TestInvoiceFactory.createMinimalInvoice(), new File("examples/minimal-invoice.xml"));
        writer.writeToFile(TestInvoiceFactory.createCompleteInvoice(), new File("examples/complete-invoice.xml"));
    }
}
