package io.github.jcodeforge.invoice4jzugferd;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;

public final class ZugferdXmpMetadata {

    private static final String FACTUR_X_NAMESPACE = "urn:factur-x:pdfa:CrossIndustryDocument:invoice:1p0#";

    public String create(Invoice invoice, ZugferdProfile profile) {
        String invoiceNumber = escapeXml(invoice.getInvoiceNumber());
        String conformanceLevel = escapeXml(getConformanceLevel(profile));

        return """
                <?xpacket begin="﻿" id="W5M0MpCehiHzreSzNTczkc9d"?>
                <x:xmpmeta xmlns:x="adobe:ns:meta/">
                  <rdf:RDF
                      xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                      xmlns:pdfaExtension="http://www.aiim.org/pdfa/ns/extension/"
                      xmlns:pdfaSchema="http://www.aiim.org/pdfa/ns/schema#"
                      xmlns:pdfaProperty="http://www.aiim.org/pdfa/ns/property#">

                    <!-- General PDF metadata -->

                    <rdf:Description
                        rdf:about=""
                        xmlns:pdf="http://ns.adobe.com/pdf/1.3/"
                        xmlns:dc="http://purl.org/dc/elements/1.1/"
                        xmlns:xmp="http://ns.adobe.com/xap/1.0/"
                        xmlns:pdfaid="http://www.aiim.org/pdfa/ns/id/">

                      <pdf:Producer>invoice4j</pdf:Producer>

                      <dc:title>
                        <rdf:Alt>
                          <rdf:li xml:lang="x-default">
                            Invoice %s
                          </rdf:li>
                        </rdf:Alt>
                      </dc:title>

                      <xmp:CreatorTool>invoice4j</xmp:CreatorTool>

                    </rdf:Description>


                    <!-- Factur-X metadata -->

                    <rdf:Description
                        rdf:about=""
                        xmlns:fx="%s">

                      <fx:DocumentType>INVOICE</fx:DocumentType>

                      <fx:DocumentFileName>
                        factur-x.xml
                      </fx:DocumentFileName>

                      <fx:Version>1.0</fx:Version>

                      <fx:ConformanceLevel>
                        %s
                      </fx:ConformanceLevel>

                    </rdf:Description>


                    <!-- PDF/A extension schema -->

                    <rdf:Description rdf:about="">

                      <pdfaExtension:schemas>
                        <rdf:Bag>

                          <rdf:li rdf:parseType="Resource">

                            <pdfaSchema:schema>
                              Factur-X PDFA Extension Schema
                            </pdfaSchema:schema>

                            <pdfaSchema:namespaceURI>
                              %s
                            </pdfaSchema:namespaceURI>

                            <pdfaSchema:prefix>
                              fx
                            </pdfaSchema:prefix>

                            <pdfaSchema:property>
                              <rdf:Seq>

                                <rdf:li rdf:parseType="Resource">
                                  <pdfaProperty:name>
                                    DocumentFileName
                                  </pdfaProperty:name>
                                  <pdfaProperty:valueType>
                                    Text
                                  </pdfaProperty:valueType>
                                  <pdfaProperty:category>
                                    external
                                  </pdfaProperty:category>
                                  <pdfaProperty:description>
                                    name of the embedded XML invoice file
                                  </pdfaProperty:description>
                                </rdf:li>

                                <rdf:li rdf:parseType="Resource">
                                  <pdfaProperty:name>
                                    DocumentType
                                  </pdfaProperty:name>
                                  <pdfaProperty:valueType>
                                    Text
                                  </pdfaProperty:valueType>
                                  <pdfaProperty:category>
                                    external
                                  </pdfaProperty:category>
                                  <pdfaProperty:description>
                                    type of the embedded XML document
                                  </pdfaProperty:description>
                                </rdf:li>

                                <rdf:li rdf:parseType="Resource">
                                  <pdfaProperty:name>
                                    Version
                                  </pdfaProperty:name>
                                  <pdfaProperty:valueType>
                                    Text
                                  </pdfaProperty:valueType>
                                  <pdfaProperty:category>
                                    external
                                  </pdfaProperty:category>
                                  <pdfaProperty:description>
                                    version of the Factur-X XML schema
                                  </pdfaProperty:description>
                                </rdf:li>

                                <rdf:li rdf:parseType="Resource">
                                  <pdfaProperty:name>
                                    ConformanceLevel
                                  </pdfaProperty:name>
                                  <pdfaProperty:valueType>
                                    Text
                                  </pdfaProperty:valueType>
                                  <pdfaProperty:category>
                                    external
                                  </pdfaProperty:category>
                                  <pdfaProperty:description>
                                    conformance level of the Factur-X invoice
                                  </pdfaProperty:description>
                                </rdf:li>

                              </rdf:Seq>
                            </pdfaSchema:property>

                          </rdf:li>

                        </rdf:Bag>
                      </pdfaExtension:schemas>

                    </rdf:Description>

                  </rdf:RDF>
                </x:xmpmeta>
                <?xpacket end="w"?>
                """.formatted(
                invoiceNumber,
                FACTUR_X_NAMESPACE,
                conformanceLevel,
                FACTUR_X_NAMESPACE
        );
    }

    private String getConformanceLevel(ZugferdProfile profile) {
        return switch (profile) {
            case MINIMUM -> "MINIMUM";
            case BASIC_WL -> "BASIC WL";
            case BASIC -> "BASIC";
            case EN16931 -> "EN 16931";
            case EXTENDED -> "EXTENDED";
        };
    }

    private String escapeXml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}