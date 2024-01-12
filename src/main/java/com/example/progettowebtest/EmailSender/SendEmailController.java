package com.example.progettowebtest.EmailSender;
import com.google.api.services.gmail.model.Message;
import java.io.*;
import java.security.GeneralSecurityException;
import javax.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.example.progettowebtest.EmailSender.EmailService.*;
import static com.example.progettowebtest.EmailSender.OTPGenerator.generateOTP;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SendEmailController{
    public static String generatedOTP;


    @PostMapping("/sendEmail")
    public void sendEmail(HttpSession session, @RequestBody EmailData emailData) {
        String nomeCognome = emailData.getNomeCognome();


        try {
            if(emailData.isAllegato()){
                String emailTemplate = EmailTemplateLoader.loadEmailTemplate("/email_pdf_template.html");
                String pdf_html = emailTemplate
                        .replace("EMAIL_DESTINATARIO", emailData.getTo())
                        .replace("NOME_COGNOME", emailData.getNomeCognome())
                        .replace("DATA_NASCITA", emailData.getDataDiNascita())
                        .replace("INDIRIZZO", emailData.getIndirizzo())
                        .replace("NUMERO_TELEFONO", emailData.getNumeroTelefono())
                        .replace("DATA_FIRMA", emailData.getDataFirma());

                PDDocument pdfFile = CreaPDFConfermaConto.creaPDFconto(emailData.getNomeCognome(), emailData.getDataDiNascita(), emailData.getIndirizzo(), emailData.getNumeroTelefono(),emailData.getTo(), emailData.getDataFirma());
                Message message = EmailService.createMessageWithAttachment(pdf_html, emailData.getSender(), emailData.getTo(), emailData.getSubject(), pdfFile);
                sendMessage(getService(), emailData.getUserId(), message);


            }else{
                //Generazione e ripristino sessione
                if(session!=null)
                    session.invalidate();

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                session = request.getSession(true);


                //Generazione otp e assegnamento alla sessione
                String generatedOTP = generateOTP();;
                session.setAttribute("control", BCrypt.hashpw(generatedOTP, BCrypt.gensalt(5)));

                String emailTemplate = EmailTemplateLoader.loadEmailTemplate("/email_otp_template.html");
                String htm_otp = emailTemplate
                        .replace("$NOME_COGNOME$", nomeCognome)
                        .replace("$GENERATED_OTP$", generatedOTP);
                Message message = createMessage(emailData.getSender(), emailData.getTo(), emailData.getSubject(), htm_otp);
                sendMessage(getService(), emailData.getUserId(), message);


            }

        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}