package com.otavio.alert.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CorinthiansPartidasScraper {

    private static final String URL =
            "https://www.corinthians.com.br/partidas";

    public String buscarJogosEmSaoPaulo() {

        try {
            Document document = Jsoup.connect(URL)
                    .userAgent("Mozilla/5.0")
                    .timeout(15000)
                    .get();

            String texto = document.body().text();

            StringBuilder resultado = new StringBuilder();

            String[] blocos = texto.split("(?=\\d{2}/\\d{2}/\\d{4})");

            for (String bloco : blocos) {

                if (bloco.toLowerCase().contains("corinthians") &&
                        ehEmSaoPaulo(bloco)) {

                    resultado.append(bloco.trim())
                            .append("\n\n--------------------\n\n");
                }
            }

            if (resultado.isEmpty()) {
                return "Nenhum jogo oficial em São Paulo encontrado no momento.\n\nFonte: " + URL;
            }

            resultado.append("Fonte: ").append(URL);

            return resultado.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao buscar jogos no site oficial do Corinthians.";
        }
    }

    private boolean ehEmSaoPaulo(String texto) {

        String text = texto.toLowerCase();

        return text.contains("são paulo") ||
                text.contains("sao paulo") ||
                text.contains("neo química arena") ||
                text.contains("neo quimica arena") ||
                text.contains("pacaembu") ||
                text.contains("morumbi") ||
                text.contains("allianz parque") ||
                text.contains("canindé") ||
                text.contains("caninde") ||
                text.contains("dr. oswaldo teixeira duarte") ||
                text.contains("arena barueri");
    }
}