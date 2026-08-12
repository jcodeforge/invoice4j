import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import java.io.File;

public final class ExampleGenerator {

    public static void main(String[] args) {
        Invoice minimalInvoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createMinimalInvoice());
        Invoice completeInvoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        ZugferdInvoiceWriter writer = ZugferdInvoiceWriter.builder()
                .profile(ZugferdProfile.EXTENDED)
                .prettyPrint(true)
                .build();

        writer.writeToFile(minimalInvoice, new File("examples/minimal-zugferd-extended-invoice.xml"));
        writer.writeToFile(completeInvoice, new File("examples/complete-zugferd-extended-invoice.xml"));
    }
}
