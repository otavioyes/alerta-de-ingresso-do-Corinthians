package com.otavio.alert.service;

import com.otavio.alert.browser.FielTorcedorBrowser;

public class TicketMonitorService {

    private final FielTorcedorBrowser browser =
            new FielTorcedorBrowser();

    public void executar() {

        browser.verificarProximosJogos();
    }
}