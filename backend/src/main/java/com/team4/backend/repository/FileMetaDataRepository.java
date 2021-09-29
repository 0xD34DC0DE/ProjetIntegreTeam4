package com.team4.backend.repository;

import com.team4.backend.model.FileMetaData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetaDataRepository extends ReactiveMongoRepository<FileMetaData,String> {
}
