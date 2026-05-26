package com.otavio.alert.browser;

import com.microsoft.playwright.*;

public class FielTorcedorBrowser {

    private static final String URL =
            "https://www.fieltorcedor.com.br/";

    public String buscarProximosJogosPublicos() {

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false)
            );

            Page page = browser.newPage();

            page.navigate(URL);
            page.waitForLoadState();

            String pageText = page.locator("body").innerText();

            browser.close();

            return extrairProximosJogos(pageText);

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao buscar próximos jogos no Fiel Torcedor.";
        }
    }

    private String extrairProximosJogos(String texto) {

        int inicio = texto.indexOf("PRÓXIMOS JOGOS");

        if (inicio == -1) {
            return "Nenhuma seção PRÓXIMOS JOGOS encontrada.\n\nFonte: " + URL;
        }

        int fim = texto.indexOf("Clube social do Corinthians", inicio);

        if (fim == -1) {
            fim = texto.indexOf("Fale conosco", inicio);
        }

        if (fim == -1) {
            fim = texto.length();
        }

        String proximosJogos =
                texto.substring(inicio, fim).trim();

        return proximosJogos + "\n\n🎟️ Fonte: " + URL;
    }
}