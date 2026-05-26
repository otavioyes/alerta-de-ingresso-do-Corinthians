package com.otavio.alert.browser;

import com.microsoft.playwright.*;
import com.otavio.alert.notifier.TelegramNotifier;
import com.otavio.alert.service.AlertHistoryService;

public class FielTorcedorBrowser {

    private static final String URL =
            "https://www.fieltorcedor.com.br/";

    private final TelegramNotifier notifier =
            new TelegramNotifier();

    private final AlertHistoryService historyService =
            new AlertHistoryService();

    public void verificarProximosJogos() {

        try (Playwright playwright = Playwright.create()) {

            Browser browser =
                    playwright.chromium().launch(

                            new BrowserType.LaunchOptions()
                                    .setHeadless(false)
                    );

            Page page = browser.newPage();

            page.navigate(URL);

            page.waitForTimeout(5000);

            String texto =
                    page.locator("body").innerText();

            //System.out.println(texto);

            if (!texto.contains("PRÓXIMOS JOGOS")) {

                System.out.println(
                        "Seção PRÓXIMOS JOGOS não encontrada."
                );

                browser.close();

                return;
            }

            boolean temJogos =
                    texto.contains("COMPRE AGORA");

            if (!temJogos) {

                System.out.println(
                        "PRÓXIMOS JOGOS: sem jogos disponíveis no momento"
                );

                System.out.println(
                        "Não existem jogos públicos ativos agora."
                );

                browser.close();

                return;
            }

            int inicio =
                    texto.indexOf("PRÓXIMOS JOGOS");

            int fim =
                    texto.indexOf(
                            "Clube social do Corinthians"
                    );

            if (fim == -1) {
                fim = texto.length();
            }

            String proximosJogos =
                    texto.substring(inicio, fim);

            String[] blocos =
                    proximosJogos.split("COMPRE AGORA");

            for (String bloco : blocos) {

                String jogo =
                        bloco.trim();

                if (jogo.isBlank()) {
                    continue;
                }

                if (!ehJogoEmSaoPaulo(jogo)) {
                    continue;
                }

                String mensagem =
                        "🚨 Jogo do Corinthians em São Paulo!\n\n"
                                + jogo
                                + "\n\n✅ Ingressos disponíveis"
                                + "\n\n🎟️ Fonte:\n"
                                + URL;

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

            browser.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private boolean ehJogoEmSaoPaulo(String jogo) {

        String text = jogo.toLowerCase();

        return text.contains("neo quimica arena") ||
                text.contains("neo química arena") ||
                text.contains("são paulo") ||
                text.contains("sao paulo") ||
                text.contains("pacaembu") ||
                text.contains("morumbi") ||
                text.contains("allianz parque") ||
                text.contains("canindé") ||
                text.contains("caninde") ||
                text.contains("arena barueri");
    }

    private String formatarJogo(String jogo) {

        String texto =
                jogo.replace("COMPRE AGORA", "")
                        .trim();

        return texto;
    }
}