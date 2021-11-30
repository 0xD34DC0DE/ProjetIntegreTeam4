package com.team4.backend.repository;

import com.team4.backend.model.ProfileImage;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Set;

@Component
public class CustomProfileImageRepositoryImpl implements CustomProfileImageRepository {

    private final ReactiveMongoOperations mongoOperations;

    public CustomProfileImageRepositoryImpl(ReactiveMongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Flux<ProfileImage> findByUploaderEmails(Set<String> uploaderEmails) {
        Query query = Query.query(Criteria.where("uploaderEmail").in(uploaderEmails));
        return mongoOperations.find(query, ProfileImage.class);
    }

}
