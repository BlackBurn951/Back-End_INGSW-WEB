package com.example.progettowebtest.EmailSender;
import com.google.api.services.gmail.model.Message;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import static com.example.progettowebtest.EmailSender.EmailService.*;
import static com.example.progettowebtest.EmailSender.OTPGenerator.generateOTP;


@RestController
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = {"Session-ID","Attivita"})
public class SendEmailController {
    public static String generatedOTP;

    @PostMapping("/sendEmail")
    public void sendEmail(HttpServletRequest request, HttpServletResponse response, @RequestParam("IDSession") String idSession, @RequestBody EmailData emailData) {
        String nomeCognome = emailData.getNomeCognome();
        if(Objects.equals(nomeCognome," ")){
            nomeCognome = "Cliente";
        }else{
            nomeCognome = emailData.getNomeCognome();
        }

        try {
            HttpSession session;
            System.out.println("PRIMA DELL'EMPTY ID SESSION PASSATO DAL FRONT QUANDO INVIO EMAIL NELLA REGISTRAZIONE: " + idSession);

            if(idSession.isEmpty()) {
                session = request.getSession(true);
                request.getServletContext().setAttribute(session.getId(), session);
                response.setHeader("Session-ID", session.getId());
                System.out.println("Response: " + response.getHeader("Session-ID"));
            }
            else
                session= (HttpSession) request.getServletContext().getAttribute(idSession);

            System.out.println("DOPO EMPTY ID SESSION PASSATO DAL FRONT QUANDO INVIO EMAIL NELLA REGISTRAZIONE: " + idSession);

            if(session==null) {
                response.setHeader("Attivita", "Scaduta");
                return;
            }

            long minuti= TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());

            String generatedOTP = generateOTP();
            session.setAttribute("control", generatedOTP);
            session.setAttribute("TempoInvioOTP", minuti);

            System.out.println("Response dopo creazione sessione: " + response.getHeader("Session-ID"));


            String emailTemplate = EmailTemplateLoader.loadEmailTemplate("/email_otp_template.html");
            String htm_otp = emailTemplate
                    .replace("$NOME_COGNOME$", nomeCognome)
                    .replace("$GENERATED_OTP$", generatedOTP);
            Message message = createMessage(emailData.getSender(), emailData.getTo(), emailData.getSubject(), htm_otp);
            sendMessage(getService(), emailData.getUserId(), message);

            System.out.println("Response alla fine del metodo: " + response.getHeader("Session-ID"));


        } catch (IOException | GeneralSecurityException | MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
