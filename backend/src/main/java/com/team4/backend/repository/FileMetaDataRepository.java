package com.team4.backend.repository;

import com.team4.backend.model.FileMetaData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

<<<<<<< HEAD
import java.io.File;
=======
import java.time.LocalDateTime;
>>>>>>> b08952a42262e211490815b59452ecdb09ec8706

@Repository
public interface FileMetaDataRepository extends ReactiveMongoRepository<FileMetaData, String> {

    Flux<FileMetaData> findFileMetaDataById(String id);

    Mono<Long> countAllByIsValidFalseAndIsSeenFalse();

    Flux<FileMetaData> findAllByIsValidFalseAndIsSeenFalse(Pageable pageable);

<<<<<<< HEAD
    Flux<FileMetaData> findAllByUserEmail(String userEmail);

=======
    Flux<FileMetaData> findAllByUserEmailAndIsValidTrueOrderByUploadDate(String userEmail);
    
>>>>>>> b08952a42262e211490815b59452ecdb09ec8706
}
