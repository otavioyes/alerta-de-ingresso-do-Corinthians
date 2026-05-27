package com.otavio.alert;

import com.otavio.alert.service.TicketMonitorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Classe principal da aplicação.
 *
 * Responsável por iniciar o Spring Boot
 * e executar o serviço de monitoramento
 * dos jogos/ingressos do Corinthians.
 */
@SpringBootApplication
public class CorinthiansAlertApplication {

    /*
     * Método principal da aplicação.
     *
     * Fluxo:
     * 1. Inicializa o Spring Boot.
     * 2. Cria o serviço principal do robô.
     * 3. Executa a verificação dos jogos.
     */
    public static void main(String[] args) {

        /*
         * Inicializa a aplicação Spring Boot.
         */
        SpringApplication.run(
                CorinthiansAlertApplication.class,
                args
        );

        /*
         * Cria instância do serviço principal
         * responsável pelo monitoramento.
         */
        TicketMonitorService service =
                new TicketMonitorService();

        /*
         * Executa o robô de verificação.
         */
        service.executar();
    }
}