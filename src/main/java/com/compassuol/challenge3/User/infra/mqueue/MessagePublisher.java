package com.compassuol.challenge3.User.infra.mqueue;

import com.compassuol.challenge3.User.model.EmissaoNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagePublisher {


    private final RabbitTemplate rabbitTemplate;
    private final Queue queueEmissaoNotification;

    public void solicitarNotification(EmissaoNotification emissaoNotification) throws JsonProcessingException {
        var json = convertIntoJson(emissaoNotification);
        rabbitTemplate.convertAndSend(queueEmissaoNotification.getName(), json);
    }

    private String convertIntoJson(EmissaoNotification emissaoNotification) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(emissaoNotification);
        return json;
    }
}