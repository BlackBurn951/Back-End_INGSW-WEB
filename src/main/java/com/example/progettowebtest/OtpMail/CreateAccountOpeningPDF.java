package com.example.progettowebtest.OtpMail;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;

public class CreateAccountOpeningPDF {
    public static PDDocument createAccountOpeningPDF(String nomeCognome,
                                                     String dataNascita,
                                                     String residenteA,
                                                     String numeroTelefono,
                                                     String indirizzoEmail,
                                                     String dataFirma) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 24);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, PDRectangle.A4.getHeight() - 50); // Sposta il testo vicino al margine superiore
            contentStream.showText("Documento di apertura del conto");
            contentStream.newLineAtOffset(0, -100);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
            contentStream.showText("Io sottoscritto/a: " + nomeCognome);
            contentStream.newLineAtOffset(0, -30);
            contentStream.showText("Data di nascita: " + dataNascita);
            contentStream.newLineAtOffset(0, -30);
            contentStream.showText("Residente a: " + residenteA);
            contentStream.newLineAtOffset(0, -30);
            contentStream.showText("Numero di telefono: " + numeroTelefono);
            contentStream.newLineAtOffset(0, -30);
            contentStream.showText("Indirizzo email: " + indirizzoEmail);
            contentStream.newLineAtOffset(0, -50);

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
            contentStream.showText("Con la presente, dichiaro di voler aprire un conto presso la Banca Caesar Magnus e");
            contentStream.newLineAtOffset(0, -25);
            contentStream.showText("accetto i termini e le condizioni relative all'apertura e all'utilizzo del suddetto conto.");
            contentStream.newLineAtOffset(0, -25);
            contentStream.showText("Dichiaro inoltre di fornire informazioni accurate e veritiere");
            contentStream.newLineAtOffset(0, -25);
            contentStream.showText ("in merito ai dati personali sopra indicati.");
            contentStream.newLineAtOffset(0, -25);
            contentStream.showText("Autorizzo la Banca Caesar Magnus a utilizzare i dati forniti per finalità connesse");
            contentStream.newLineAtOffset(0, -25);
            contentStream.showText ("all'apertura e alla gestione del mio conto bancario.");
            contentStream.newLineAtOffset(0, -25);
            contentStream.showText("Dichiaro di aver ricevuto, letto e compreso il regolamento relativo al conto e");
            contentStream.newLineAtOffset(0, -25);
            contentStream.showText("accetto di conformarmi alle disposizioni in esso contenute.");
            contentStream.newLineAtOffset(0, -100);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
            contentStream.showText("Data: " + dataFirma);

            contentStream.newLineAtOffset(0, -200);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 20);
            contentStream.showText("Banca Caesar Magnus");
            contentStream.endText();



        }

        return document;
    }
}