package com.otavio.alert;

import com.otavio.alert.browser.FielTorcedorBrowser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorinthiansAlertApplication {

    public static void main(String[] args) {

        SpringApplication.run(CorinthiansAlertApplication.class, args);

        FielTorcedorBrowser browser =
                new FielTorcedorBrowser();

        browser.abrirSite();
    }
}