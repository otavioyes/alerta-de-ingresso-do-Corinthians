package com.otavio.alert.notifier;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/*
 * Classe responsável por enviar mensagens para o Telegram
 * utilizando a API oficial do Bot Telegram.
 */
public class TelegramNotifier {

    /*
     * Token do bot Telegram.
     *
     * O token é obtido através de variável de ambiente
     * para evitar expor informações sensíveis no código.
     */
    private static final String BOT_TOKEN =
            System.getenv("TELEGRAM_BOT_TOKEN");

    /*
     * ID do grupo/chat que receberá os alertas.
     *
     * Também é carregado via variável de ambiente.
     */
    private static final String CHAT_ID =
            System.getenv("TELEGRAM_CHAT_ID");

    /*
     * Envia uma mensagem para o Telegram.
     *
     * Fluxo:
     * 1. Valida se as variáveis de ambiente existem.
     * 2. Codifica a mensagem para URL.
     * 3. Monta a URL da API Telegram.
     * 4. Realiza requisição HTTP GET.
     * 5. Exibe código de resposta no terminal.
     */
    public void enviarMensagem(String mensagem) {

        /*
         * Verifica se o token do bot foi configurado.
         */
        if (BOT_TOKEN == null || BOT_TOKEN.isBlank()) {

            System.out.println(
                    "Erro: variável TELEGRAM_BOT_TOKEN não configurada."
            );

            return;
        }

        /*
         * Verifica se o ID do chat/grupo foi configurado.
         */
        if (CHAT_ID == null || CHAT_ID.isBlank()) {

            System.out.println(
                    "Erro: variável TELEGRAM_CHAT_ID não configurada."
            );

            return;
        }

        try {

            /*
             * Codifica a mensagem para evitar problemas
             * com espaços, acentos e caracteres especiais.
             */
            String texto = URLEncoder.encode(
                    mensagem,
                    StandardCharsets.UTF_8
            );

            /*
             * Monta a URL da API do Telegram.
             */
            String urlString =
                    "https://api.telegram.org/bot"
                            + BOT_TOKEN
                            + "/sendMessage?chat_id="
                            + CHAT_ID
                            + "&text="
                            + texto;

            /*
             * Converte a string em objeto URL.
             */
            URL url = URI.create(urlString).toURL();

            /*
             * Abre conexão HTTP com a API do Telegram.
             */
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            /*
             * Define o método HTTP utilizado.
             */
            connection.setRequestMethod("GET");

            /*
             * Executa a requisição e captura o código de resposta.
             */
            int responseCode = connection.getResponseCode();

            /*
             * Exibe o resultado da requisição.
             */
            System.out.println(
                    "Mensagem enviada! Código: "
                            + responseCode
            );

            /*
             * Encerra conexão HTTP.
             */
            connection.disconnect();

        } catch (IOException e) {

            /*
             * Exibe erro caso a comunicação com o Telegram falhe.
             */
            e.printStackTrace();
        }
    }
}