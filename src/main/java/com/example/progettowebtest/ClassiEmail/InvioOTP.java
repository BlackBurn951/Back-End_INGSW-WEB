package com.example.progettowebtest.ClassiEmail;

public class InvioOTP {
    private String nomeCongome;
    private String otp;

    public InvioOTP(String nomeCongome, String otp) {
        this.nomeCongome = nomeCongome;
        this.otp = otp;
    }

    public String getNomeCongome() {
        return nomeCongome;
    }

    public String getOtp() {
        return otp;
    }
}
