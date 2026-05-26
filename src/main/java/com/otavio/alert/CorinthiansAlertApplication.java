package com.otavio.alert;

import com.otavio.alert.service.TicketMonitorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorinthiansAlertApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                CorinthiansAlertApplication.class,
                args
        );

        TicketMonitorService service =
                new TicketMonitorService();

        service.executar();
    }
}