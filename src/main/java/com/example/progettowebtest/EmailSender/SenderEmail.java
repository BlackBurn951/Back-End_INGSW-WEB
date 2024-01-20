package com.example.progettowebtest.EmailSender;

import com.example.progettowebtest.ClassiEmail.InvioCarta;
import com.example.progettowebtest.ClassiEmail.InvioContratto;
import com.example.progettowebtest.ClassiEmail.InvioOTP;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.MessagingException;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.example.progettowebtest.EmailSender.EmailService.*;

public class SenderEmail {
    public static void sendEmails(InvioOTP datiOtp, InvioContratto datiContr, InvioCarta datiCarta, String emailUtente) {
        if(datiOtp!=null) {
            try {
                String emailTemplate;
                String htm_otp;

                emailTemplate = EmailTemplateLoader.loadEmailTemplate("/email_otp_template.html");
                htm_otp = emailTemplate
                        .replace("$NOME_COGNOME$", datiOtp.getNomeCongome())
                        .replace("$GENERATED_OTP$", datiOtp.getOtp());



                Message message = createMessage("caesar.magnus.info@gmail.com", emailUtente, "Verifica identità tramite OTP", htm_otp);
                sendMessage(getService(), "caesar.magnus.info@gmail.com", message);
            } catch (IOException | GeneralSecurityException | MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else if (datiContr!=null) {
            try {
                String emailTemplate = EmailTemplateLoader.loadEmailTemplate("/email_pdf_template.html");
                String pdf_html = emailTemplate
                        .replace("EMAIL_DESTINATARIO", emailUtente)
                        .replace("NOME_COGNOME", datiContr.getNomeCognome())
                        .replace("DATA_NASCITA", datiContr.getDataNascita())
                        .replace("INDIRIZZO", datiContr.getIndirizzo())
                        .replace("NUMERO_TELEFONO", datiContr.getNumTelefono())
                        .replace("DATA_FIRMA", datiContr.getDataFirma())
                        .replace("PINDASERVER", datiContr.getPinConto());
                MagnusDAO.getInstance().getContoCorrenteDAO().setPinChiaro("");

                PDDocument pdfFile = CreaPDFConfermaConto.creaPDFconto(datiContr.getNomeCognome(), datiContr.getDataNascita(), datiContr.getIndirizzo(), datiContr.getNumTelefono(), emailUtente, datiContr.getDataFirma());
                Message message = EmailService.createMessageWithAttachment(pdf_html, "caesar.magnus.info@gmail.com", emailUtente, "Conferma registrazione e dati conto", pdfFile);
                sendMessage(getService(), "caesar.magnus.info@gmail.com", message);
            } catch (IOException | GeneralSecurityException | MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else if (datiCarta!=null) {
            try {
                String emailTemplate;
                String htm_otp;

                String data = datiCarta.getScadenzaCarta();

                String oggettoEmail= "Conferma creazione carta";

                String sender = "caesar.magnus.info@gmail.com";

                emailTemplate = EmailTemplateLoader.loadEmailTemplate("/email_conf_carta_template.html");
                htm_otp = emailTemplate
                        .replace("$TIPO_CARTA$", datiCarta.getTipo())
                        .replace("$NOME_COGNOME$", datiCarta.getNomeCognome())
                        .replace("$PIN_CARTA$", datiCarta.getPinCarta())
                        .replace("$NUMERO_CARTA$", datiCarta.getNumCarta())
                        .replace("$SCADENZA_CARTA$", data)
                        .replace("$CVV_CARTA$", datiCarta.getCvv());

                Message message = createMessage(sender, emailUtente,oggettoEmail, htm_otp);
                sendMessage(getService(), sender, message);

            } catch (IOException | GeneralSecurityException | MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
