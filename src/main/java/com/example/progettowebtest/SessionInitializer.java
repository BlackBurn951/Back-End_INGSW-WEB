package com.example.progettowebtest;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

public class SessionInitializer extends AbstractHttpSessionApplicationInitializer {
    protected CookieSerializer httpSessionIdResolver() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setUseSecureCookie(false); // Imposta a true per abilitare solo cookie sicuri (HTTPS)
        cookieSerializer.setUseHttpOnlyCookie(false); // Imposta a true per abilitare il flag HttpOnly
        //cookieSerializer.setCookiePath("/"); // Imposta il percorso del cookie, se necessario
        return cookieSerializer;
    }
}
