package com.otavio.alert.service;

import com.otavio.alert.browser.FielTorcedorBrowser;
import com.otavio.alert.notifier.TelegramNotifier;
import com.otavio.alert.scraper.CorinthiansPartidasScraper;

public class TicketMonitorService {

    private final CorinthiansPartidasScraper partidasScraper =
            new CorinthiansPartidasScraper();

    private final FielTorcedorBrowser fielTorcedorBrowser =
            new FielTorcedorBrowser();

    private final TelegramNotifier notifier =
            new TelegramNotifier();

    private final AlertHistoryService historyService =
            new AlertHistoryService();

    public void executar() {

        String jogosCorinthians =
                partidasScraper.buscarJogosEmSaoPaulo();

        enviarSeForNovo(
                "corinthians-site",
                "📅 Jogos oficiais do Corinthians em São Paulo:\n\n" + jogosCorinthians
        );

        String proximosJogosFiel =
                fielTorcedorBrowser.buscarProximosJogosPublicos();

        enviarSeForNovo(
                "fiel-torcedor",
                "🎟️ Próximos jogos no Fiel Torcedor:\n\n" + proximosJogosFiel
        );
    }

    private void enviarSeForNovo(String prefixo, String mensagem) {

        String id = prefixo + "-" + mensagem.hashCode();

        if (historyService.jaFoiEnviado(id)) {
            System.out.println("Alerta já enviado: " + prefixo);
            return;
        }

        notifier.enviarMensagem(mensagem);
        historyService.salvarComoEnviado(id);
    }
}