package com.example.progettowebtest.EmailSender;
import com.google.api.services.gmail.model.Message;
import java.io.*;
import java.security.GeneralSecurityException;
import javax.mail.MessagingException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static com.example.progettowebtest.EmailSender.EmailService.*;


@RestController
@CrossOrigin(origins = "*")
public class SendEmailContrattoController {


    @PostMapping("/sendEmailWithAttachment")
    public void sendEmail(@RequestBody EmailDataWithAttachment emailDataWithAttachment) {

        String htmlContent = "<h2>Benvenuto nella banca Caesar Magnus!</h2>\n"+
                "<p>&nbsp;</p>"+
                "<h3>Hai confermato correttamente la tua email.</h4>"+
                "<h3>Di seguito trovi le tue credenziali di accesso e il documento di apertura del conto:</h4>"+
                "<p>&nbsp;</p>"+
                "<h3><strong>Email: "+emailDataWithAttachment.getTo()+" </h3>"+
                "<h3><strong>Password:</strong></h3>"+
                "<p>&nbsp;</p>"+
                "<p>&nbsp;</p>"+
                "<div style=\"max-width: 800px; max-height: 900px; margin: 0 auto; padding: 0 20px; background-color: #e9e9e9;\">"+
                "<h2 style=\"text-align: center;\">Documento di apertura del conto</h2\n>" +
                "<p>&nbsp;</p>" +
                " <div id=\"datiDocumento\">\n" +
                " <p><strong>Io sottoscritto/a: "+emailDataWithAttachment.getNomeCognome()+" <span id=\"nome\"></strong></span></p>\n"+
                " <p><strong>Data di nascita: "+emailDataWithAttachment.getDataDiNascita()+" <span id=\"data_nascita\"></span></strong></p>\n"+
                " <p><strong>Residente a: "+emailDataWithAttachment.getIndirizzo()+"<span id=\"indirizzo\"></span></strong></p>\n"+
                " <p><strong>Numero di telefono: "+emailDataWithAttachment.getNumeroTelefono()+" <span id=\"telefono\"></span></strong></p>\n"+
                " <p><strong>Indirizzo email: "+emailDataWithAttachment.getTo()+"<span id=\"email\"></span></strong></p>\n"+
                " <p>&nbsp;</p>"+
                " <p>&nbsp;</p>"+
                " </div>\n"+
                " <div id=\"testoDocumento\">\n"+
                " <p>Con la presente, dichiaro di voler aprire un conto presso la Banca Caesar Magnus e accetto i termini e le condizioni relative all'apertura e all'utilizzo del suddetto conto.</p>\n"+
                " <p>Dichiaro inoltre di fornire informazioni accurate e veritiere in merito ai dati personali sopra indicati.</p>\n"+
                " <p>Autorizzo la Banca Caesar Magnus a utilizzare i dati forniti per finalit&agrave; connesse all'apertura e alla gestione del mio conto bancario.</p>\n"+
                " <p>Dichiaro di aver ricevuto, letto e compreso il regolamento relativo al conto e accetto di conformarmi alle disposizioni in esso contenute.</p>\n"+
                " </div>\n"+
                " <p>&nbsp;</p>"+
                " <p>&nbsp;</p>"+
                " <div id=\"dataEfirma\">\n"+
                " <div id=\"data\">\n"+
                " <p style=\"text-align: left;\"><strong>Data: "+emailDataWithAttachment.getDataFirma()+"</strong></p>\n"+
                " </div>\n"+
                " </div>\n"+
                " </div>\n"+
                " <p>&nbsp;</p>";
        try {

            PDDocument pdfFile = CreaPDFConfermaConto.creaPDFconto(emailDataWithAttachment.getNomeCognome(), emailDataWithAttachment.getDataDiNascita(), emailDataWithAttachment.getIndirizzo(), emailDataWithAttachment.getNumeroTelefono(), emailDataWithAttachment.getTo(), emailDataWithAttachment.getDataFirma());

            Message message = EmailService.createMessageWithAttachment(htmlContent, emailDataWithAttachment.getSender(), emailDataWithAttachment.getTo(), emailDataWithAttachment.getSubject(), pdfFile);
            sendMessage(getService(), emailDataWithAttachment.getUserId(), message);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }






}