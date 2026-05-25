package com.otavio.alert.browser;

import com.microsoft.playwright.*;
import com.otavio.alert.notifier.TelegramNotifier;
import com.otavio.alert.service.AlertHistoryService;

import java.nio.file.Paths;

public class FielTorcedorBrowser {

    public void abrirSite() {

        try (Playwright playwright = Playwright.create()) {

            BrowserContext context = playwright.chromium()
                    .launchPersistentContext(
                            Paths.get("browser-data"),
                            new BrowserType.LaunchPersistentContextOptions()
                                    .setHeadless(false)
                    );

            Page page = context.newPage();

            page.navigate("https://www.fieltorcedor.com.br/jogos/");

            /*TEMPO QUE O SITE FICA ABERTO: 0min*/
            page.waitForLoadState();

            System.out.println("Página aberta!");
            System.out.println("URL atual:");
            System.out.println(page.url());

            String pageText = page.locator("body").innerText();

            String[] jogos = pageText.split("X\\nCORINTHIANS");

            TelegramNotifier notifier = new TelegramNotifier();
            AlertHistoryService historyService = new AlertHistoryService();

            for (String jogo : jogos) {

                String bloco = "CORINTHIANS" + jogo;

                boolean temCompra =
                        bloco.contains("COMPRE AGORA");

                boolean emSaoPaulo =
                        bloco.contains("NEO QUIMICA ARENA") ||
                                bloco.contains("NEO QUÍMICA ARENA");

                if (temCompra && emSaoPaulo) {

                    if (!historyService.jaFoiEnviado(bloco)) {

                        System.out.println("🚨 Jogo em São Paulo encontrado:");
                        System.out.println("--------------------------------");
                        System.out.println(bloco);

                        String linkCompra = page.url();

                        notifier.enviarMensagem(
                                "🚨 Ingresso Corinthians disponível em São Paulo!\n\n"
                                        + bloco
                                        + "\n\n🎟️ Comprar ingresso:\n"
                                        + linkCompra
                        );

                        historyService.salvarComoEnviado(bloco);

                        System.out.println("--------------------------------");

                    } else {
                        System.out.println("Alerta já enviado anteriormente.");
                    }
                }
            }

            context.close();
        }
    }
}