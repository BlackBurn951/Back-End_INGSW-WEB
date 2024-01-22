package com.example.progettowebtest.ClassiEmail;

public class InvioOTP {
    private String nomeCognome;
    private String otp;

    public InvioOTP(String nomeCognome, String otp) {
        this.nomeCognome = nomeCognome;
        this.otp = otp;
    }

    public String getNomeCognome() {
        return nomeCognome;
    }

    public String getOtp() {
        return otp;
    }
}
