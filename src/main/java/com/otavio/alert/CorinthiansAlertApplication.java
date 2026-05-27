package com.otavio.alert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 * Classe principal da aplicação.
 *
 * Responsável por:
 * 1. Inicializar o Spring Boot.
 * 2. Ativar o sistema de agendamentos automáticos.
 * 3. Manter o robô rodando continuamente.
 */
@SpringBootApplication

/*
 * Habilita o scheduler do Spring Boot.
 *
 * Isso permite utilizar:
 * @Scheduled
 *
 * para executar tarefas automáticas
 * em intervalos definidos.
 */
@EnableScheduling
public class CorinthiansAlertApplication {

    /*
     * Metodo principal da aplicação.
     *
     * Fluxo:
     * 1. Inicializa o contexto Spring Boot.
     * 2. Carrega componentes da aplicação.
     * 3. Inicia o scheduler automático.
     * 4. Mantém o robô em execução contínua.
     */
    public static void main(String[] args) {

        /*
         * Inicializa a aplicação Spring Boot.
         */
        SpringApplication.run(
                CorinthiansAlertApplication.class,
                args
        );
    }
}