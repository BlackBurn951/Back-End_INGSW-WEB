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
import static com.example.progettowebtest.EmailSender.OTPGenerator.generateOTP;


@RestController
@CrossOrigin(origins = "*")
public class SendEmailController {
    public static String generatedOTP;


    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody ExtendedEmailData extendedEmailData) {
        String nomeCognome = extendedEmailData.getNomeCognome();
        generatedOTP = generateOTP();

        try {
            if(extendedEmailData.isAllegato()){
                String emailTemplate = EmailTemplateLoader.loadEmailTemplate("/email_pdf_template.html");
                String pdf_html = emailTemplate
                        .replace("EMAIL_DESTINATARIO", extendedEmailData.getTo())
                        .replace("NOME_COGNOME", extendedEmailData.getNomeCognome())
                        .replace("DATA_NASCITA", extendedEmailData.getDataDiNascita())
                        .replace("INDIRIZZO", extendedEmailData.getIndirizzo())
                        .replace("NUMERO_TELEFONO", extendedEmailData.getNumeroTelefono())
                        .replace("DATA_FIRMA", extendedEmailData.getDataFirma());

                PDDocument pdfFile = CreaPDFConfermaConto.creaPDFconto(extendedEmailData.getNomeCognome(), extendedEmailData.getDataDiNascita(), extendedEmailData.getIndirizzo(), extendedEmailData.getNumeroTelefono(),extendedEmailData.getTo(), extendedEmailData.getDataFirma());
                Message message = EmailService.createMessageWithAttachment(pdf_html, extendedEmailData.getSender(), extendedEmailData.getTo(), extendedEmailData.getSubject(), pdfFile);
                sendMessage(getService(), extendedEmailData.getUserId(), message);


            }else{
                String emailTemplate = EmailTemplateLoader.loadEmailTemplate("/email_otp_template.html");
                String htm_otp = emailTemplate
                        .replace("$NOME_COGNOME$", nomeCognome)
                        .replace("$GENERATED_OTP$", generatedOTP);
                Message message = createMessage(extendedEmailData.getSender(), extendedEmailData.getTo(), extendedEmailData.getSubject(), htm_otp);
                sendMessage(getService(), extendedEmailData.getUserId(), message);


            }


        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }




}