package com.team4.backend.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

@AllArgsConstructor
@Repository
public class FileAssetRepository {
    private final AmazonS3 amazonS3;

    public void create(String location, InputStream fileStream, Map<String, String> options) throws FileNotFoundException {
        ObjectMetadata metadata = new ObjectMetadata();
        options.forEach(metadata::addUserMetadata);
        amazonS3.putObject("projetintegreteam4", location, fileStream, metadata);
    }

}
