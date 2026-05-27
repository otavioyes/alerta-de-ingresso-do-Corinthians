package com.otavio.alert.scheduler;

import com.otavio.alert.service.TicketMonitorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 * Executa o robô automaticamente em intervalos definidos.
 */
@Component
public class TicketScheduler {

    private final TicketMonitorService service =
            new TicketMonitorService();

    /*
     * Executa a cada 5 minutos.
     * 30000 ms = 5 minutos.
     */
    @Scheduled(fixedRate = 300000)
    public void verificarIngressos() {

        System.out.println("Verificando próximos jogos...");

        service.executar();
    }
}