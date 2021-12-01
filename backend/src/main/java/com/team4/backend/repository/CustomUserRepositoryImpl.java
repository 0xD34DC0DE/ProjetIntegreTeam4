package com.team4.backend.repository;

import com.team4.backend.model.Notification;
import com.team4.backend.model.User;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final ReactiveMongoOperations mongoOperations;

    public CustomUserRepositoryImpl(ReactiveMongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Mono<User> findByEmailAndUpdateProfileImageId(String email, String profileImageId) {
        Query query = Query.query(Criteria.where("email").is(email));

        Update update = new Update().set("profileImageId", profileImageId);

        return mongoOperations.findAndModify(query, update, FindAndModifyOptions.options().upsert(true).returnNew(true), User.class);
    }

    @Override
    public Flux<User> findByEmails(Set<String> emails) {
        Query query = Query.query(Criteria.where("email").in(emails));

        return mongoOperations.find(query, User.class);
    }

}
