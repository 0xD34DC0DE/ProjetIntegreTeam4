package com.team4.backend.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.backend.model.Notification;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Sinks;

import java.util.Collections;

@Log4j2
@Configuration
public class WebSocketConfiguration {

    private final Sinks.Many<Notification> sinks;
    private final ObjectMapper objectMapper;

    public WebSocketConfiguration(Sinks.Many<Notification> sinks, ObjectMapper objectMapper) {
        this.sinks = sinks;
        this.objectMapper = objectMapper;
    }

    @Bean
    public HandlerMapping webSocketHandlerMapping(WebSocketHandler webSocketHandler) {
        return new SimpleUrlHandlerMapping() {
            {
                setUrlMap(Collections.singletonMap("/ws", webSocketHandler));
                setOrder(10);
            }
        };
    }

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return webSocketSession -> webSocketSession
                .send(
                        sinks.asFlux()
                                .map(notification -> {
                                    try {
                                        return webSocketSession.textMessage(objectMapper.writeValueAsString(notification));
                                    } catch (JsonProcessingException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                )
                .and(webSocketSession.receive()
                        .map(webSocketMessage -> {
                            log.info("Payload: " + webSocketMessage.getPayloadAsText());
                            return webSocketMessage;
                        })

                );
    }

}
