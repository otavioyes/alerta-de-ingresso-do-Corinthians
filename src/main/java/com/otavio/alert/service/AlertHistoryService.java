package com.otavio.alert.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/*
 * Classe responsável por controlar o histórico
 * de alertas já enviados.
 *
 * Objetivo:
 * evitar envio duplicado da mesma mensagem
 * para o Telegram.
 */
public class AlertHistoryService {

    /*
     * Arquivo utilizado para armazenar os IDs
     * dos alertas já enviados.
     */
    private static final Path HISTORY_FILE =
            Path.of("sent-alerts.txt");

    /*
     * Verifica se um alerta já foi enviado anteriormente.
     *
     * Retorna:
     * true  -> alerta já enviado
     * false -> alerta ainda não enviado
     */
    public boolean jaFoiEnviado(String id) {

        try {

            /*
             * Caso o arquivo ainda não exista,
             * significa que nenhum alerta foi enviado.
             */
            if (!Files.exists(HISTORY_FILE)) {

                return false;
            }

            /*
             * Lê todas as linhas do arquivo
             * e verifica se o ID informado já existe.
             */
            return Files.readAllLines(HISTORY_FILE)
                    .contains(id);

        } catch (IOException e) {

            /*
             * Exibe erro caso ocorra falha na leitura do arquivo.
             */
            e.printStackTrace();

            return false;
        }
    }

    /*
     * Salva um novo alerta como enviado.
     *
     * O ID é armazenado no arquivo
     * sent-alerts.txt.
     */
    public void salvarComoEnviado(String id) {

        try {

            /*
             * Escreve o ID no final do arquivo.
             *
             * CREATE:
             * cria o arquivo caso ele não exista.
             *
             * APPEND:
             * adiciona conteúdo sem apagar o anterior.
             */
            Files.writeString(
                    HISTORY_FILE,
                    id + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {

            /*
             * Exibe erro caso ocorra falha
             * ao salvar o histórico.
             */
            e.printStackTrace();
        }
    }
}