package com.example.progettowebtest.EmailSender;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
public class CreaPDFConfermaConto {

    private static final float VERTICAL_OFFSET = 25f;

    private static void setFont(PDPageContentStream contentStream, Standard14Fonts.FontName fontName, float fontSize) throws IOException {
        contentStream.setFont(new PDType1Font(fontName), fontSize);
    }

    public static PDDocument creaPDFconto(String... campi) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            setFont(contentStream, Standard14Fonts.FontName.HELVETICA_BOLD, 24);
            contentStream.newLineAtOffset(50, PDRectangle.A4.getHeight() - 50);
            contentStream.showText("Documento di apertura del conto");
            contentStream.newLineAtOffset(0, -100);

            setFont(contentStream, Standard14Fonts.FontName.HELVETICA_BOLD, 16);
            String[] labels = {
                    "Io sottoscritto/a: ",
                    "Data di nascita: ",
                    "Residente a: ",
                    "Numero di telefono: ",
                    "Indirizzo email: "
            };

            for (int i = 0; i < labels.length; i++) {
                contentStream.showText(labels[i] + campi[i]);
                contentStream.newLineAtOffset(0, -VERTICAL_OFFSET);
            }
            contentStream.newLineAtOffset(0, -50);


            setFont(contentStream, Standard14Fonts.FontName.HELVETICA, 14);
            String[] statements = {
                    "Con la presente, dichiaro di voler aprire un conto presso la Banca Caesar Magnus e",
                    "accetto i termini e le condizioni relative all'apertura e all'utilizzo del suddetto conto.",
                    "Dichiaro inoltre di fornire informazioni accurate e veritiere",
                    "in merito ai dati personali sopra indicati.",
                    "Autorizzo la Banca Caesar Magnus a utilizzare i dati forniti per finalità connesse",
                    "all'apertura e alla gestione del mio conto bancario.",
                    "Dichiaro di aver ricevuto, letto e compreso il regolamento relativo al conto e",
                    "accetto di conformarmi alle disposizioni in esso contenute.",
            };

            for (String statement : statements) {
                contentStream.showText(statement);
                contentStream.newLineAtOffset(0, -VERTICAL_OFFSET);
            }
            contentStream.newLineAtOffset(0, -100);


            setFont(contentStream, Standard14Fonts.FontName.HELVETICA_BOLD, 16);
            contentStream.showText("Data: " + campi[5]);

            contentStream.newLineAtOffset(0, -150);

            setFont(contentStream, Standard14Fonts.FontName.HELVETICA_BOLD, 20);
            contentStream.showText("Banca Caesar Magnus");
            contentStream.endText();
        }

        return document;
    }
}

