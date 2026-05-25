package com.otavio.alert.notifier;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TelegramNotifier {

    private static final String BOT_TOKEN =
            System.getenv("TELEGRAM_BOT_TOKEN");

    private static final String CHAT_ID =
            System.getenv("TELEGRAM_CHAT_ID");

    public void enviarMensagem(String mensagem) {

        if (BOT_TOKEN == null || BOT_TOKEN.isBlank()) {
            System.out.println("Erro: variável TELEGRAM_BOT_TOKEN não configurada.");
            return;
        }

        if (CHAT_ID == null || CHAT_ID.isBlank()) {
            System.out.println("Erro: variável TELEGRAM_CHAT_ID não configurada.");
            return;
        }

        try {
            String texto = URLEncoder.encode(
                    mensagem,
                    StandardCharsets.UTF_8
            );

            String urlString =
                    "https://api.telegram.org/bot"
                            + BOT_TOKEN
                            + "/sendMessage?chat_id="
                            + CHAT_ID
                            + "&text="
                            + texto;

            URL url = URI.create(urlString).toURL();

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            System.out.println("Mensagem enviada! Código: " + responseCode);

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}