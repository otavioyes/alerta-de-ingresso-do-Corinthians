package com.otavio.alert.service;

import com.otavio.alert.browser.FielTorcedorBrowser;

/*
 * Classe responsável por iniciar
 * o monitoramento dos ingressos/jogos.
 *
 * Essa classe funciona como ponto central
 * da automação.
 */
public class TicketMonitorService {

    /*
     * Instância responsável por acessar
     * o site público do Fiel Torcedor.
     */
    private final FielTorcedorBrowser browser =
            new FielTorcedorBrowser();

    /*
     * Executa o fluxo principal do robô.
     *
     * Fluxo:
     * 1. Acessa o site do Fiel Torcedor.
     * 2. Verifica próximos jogos.
     * 3. Filtra jogos em São Paulo.
     * 4. Verifica disponibilidade de ingressos.
     * 5. Envia alerta no Telegram.
     */
    public void executar() {

        browser.verificarProximosJogos();
    }
}