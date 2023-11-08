package com.api.transferencia.monetariabasica.services;

import com.api.transferencia.monetariabasica.commons.exceptions.NotificationException;
import com.api.transferencia.monetariabasica.dtos.NotificationDTO;
import com.api.transferencia.monetariabasica.models.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class NotificationService {
    private final RestTemplate restTemplate;

    private final Logger logger;

    @Value("${client.notification-mock.url}")
    private String url;

    public NotificationService(RestTemplate restTemplate, Logger logger) {
        this.restTemplate = restTemplate;
        this.logger = logger;
    }

    public void notify(User user, String message) {
        logger.log(Level.INFO, () -> "Notifying usuário: " + user);
        String email = user.getEmail();
        NotificationDTO notificationDTO = new NotificationDTO(email, message);

        ResponseEntity<String> notificationResponse =
                restTemplate.postForEntity(url, notificationDTO, String.class);

        boolean notificationVerifying =
                notificationResponse.getStatusCode() == HttpStatus.OK;
        if (!notificationVerifying) {
            logger.warning("Erro no serviço de notificação");
            throw new NotificationException("Serviço indiponível");
        }
    }
}
