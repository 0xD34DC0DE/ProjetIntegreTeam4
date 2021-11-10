package com.team4.backend.publisher;

import com.team4.backend.event.NotificationCreatedEvent;
import org.springframework.context.ApplicationListener;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;


public class NotificationCreatedPublisher implements ApplicationListener<NotificationCreatedEvent>, Consumer<FluxSink<NotificationCreatedEvent>> {

    private final Executor executor;
    private final BlockingQueue<NotificationCreatedEvent> queue = new LinkedBlockingQueue<>();

    public NotificationCreatedPublisher(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void onApplicationEvent(NotificationCreatedEvent notificationCreatedEvent) {
        queue.offer(notificationCreatedEvent);
    }

    @Override
    public void accept(FluxSink<NotificationCreatedEvent> notificationCreatedEventFluxSink) {
        executor.execute(() -> {
            while (true) {
                try {
                    NotificationCreatedEvent notificationCreatedEvent = queue.take();
                    notificationCreatedEventFluxSink.next(notificationCreatedEvent);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
