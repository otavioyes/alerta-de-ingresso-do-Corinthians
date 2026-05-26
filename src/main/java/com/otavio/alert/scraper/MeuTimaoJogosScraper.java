package com.otavio.alert.scraper;

import com.microsoft.playwright.*;
import com.otavio.alert.notifier.TelegramNotifier;
import com.otavio.alert.service.AlertHistoryService;

public class MeuTimaoJogosScraper {

    private static final String URL =
            "https://www.meutimao.com.br/proximos-jogos-do-corinthians";

    private final TelegramNotifier notifier =
            new TelegramNotifier();

    private final AlertHistoryService historyService =
            new AlertHistoryService();

    public void verificarJogosEmSaoPaulo() {

        try (Playwright playwright = Playwright.create()) {

            Browser browser =
                    playwright.chromium().launch(

                            new BrowserType.LaunchOptions()
                                    .setHeadless(false)
                    );

            Page page = browser.newPage();

            page.navigate(URL);

            page.waitForLoadState();

            String texto =
                    page.locator("body").innerText();

            browser.close();

            String[] jogos =
                    texto.split("(?=Corinthians)");

            for (String jogo : jogos) {

                if (!ehJogoEmSaoPaulo(jogo)) {
                    continue;
                }

                String mensagem =
                        formatarMensagem(jogo);

                String id =
                        mensagem.replaceAll(
                                "[^a-zA-Z0-9]",
                                ""
                        ).toLowerCase();

                if (historyService.jaFoiEnviado(id)) {

                    System.out.println(
                            "Alerta já enviado anteriormente."
                    );

                    continue;
                }

                notifier.enviarMensagem(mensagem);

                historyService.salvarComoEnviado(id);

                System.out.println(
                        "Alerta enviado!"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private boolean ehJogoEmSaoPaulo(String jogo) {

        String text = jogo.toLowerCase();

        return text.contains("neo química arena") ||
                text.contains("neo quimica arena") ||
                text.contains("são paulo") ||
                text.contains("sao paulo");
    }

    private String formatarMensagem(String jogo) {

        return "🚨 Jogo do Corinthians em São Paulo!\n\n"
                + jogo.trim()
                + "\n\n📅 Fonte:\n"
                + URL;
    }
}