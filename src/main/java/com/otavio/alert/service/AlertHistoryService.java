package com.otavio.alert.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class AlertHistoryService {

    private static final Path HISTORY_FILE =
            Path.of("sent-alerts.txt");


    public boolean jaFoiEnviado(String id) {
        try {
            if (!Files.exists(HISTORY_FILE)) {
                return false;
            }

            return Files.readAllLines(HISTORY_FILE).contains(id);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void salvarComoEnviado(String id) {
        try {
            Files.writeString(
                    HISTORY_FILE,
                    id + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}