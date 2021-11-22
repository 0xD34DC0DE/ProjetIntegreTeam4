package com.team4.backend.repository;

import com.team4.backend.model.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Component
public class CustomNotificationRepositoryImpl implements CustomNotificationRepository {

    private final ReactiveMongoOperations mongoOperations;

    public CustomNotificationRepositoryImpl(ReactiveMongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Flux<Notification> findAllByReceiverId(String id, Pageable pageable) {
        Query query = Query.query(Criteria.where("receiverIds").is(id)).with(pageable).with(Sort.by(Sort.Direction.DESC, "creationDate"));
        return mongoOperations.find(query, Notification.class);
    }

    @Override
    public Mono<Notification> deleteUserNotification(String notificationId, String userId) {
        Query query = Query.query(Criteria.where("id").is(notificationId));
        Update update = new Update().pull("receiverIds", userId);
        return mongoOperations.findAndModify(query, update, FindAndModifyOptions.options().upsert(true).returnNew(true), Notification.class);
    }

    @Override
    public Mono<Notification> addUserToSeenNotification(String userId, String notificationId) {
        Query query = Query.query(Criteria.where("id").is(notificationId));
        Update update = new Update().push("seenIds", userId);
        return mongoOperations.findAndModify(query, update, FindAndModifyOptions.options().upsert(true).returnNew(true), Notification.class);
    }

}
