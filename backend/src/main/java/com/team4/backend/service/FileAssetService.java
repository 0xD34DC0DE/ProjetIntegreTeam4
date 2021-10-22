package com.team4.backend.service;

import com.team4.backend.repository.FileAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileAssetService {

    private final FileAssetRepository fileAssetRepository;

    public FileAssetService(FileAssetRepository fileAssetRepository) {
        this.fileAssetRepository = fileAssetRepository;
    }

    protected FileInputStream getFileInputStream(String filePath) throws FileNotFoundException {
        return new FileInputStream(filePath);
    }

    public Mono<String> create(String filePath, String userEmail, String mimeType, String assetId) {

        return Mono.fromCallable(() -> {
            FileInputStream file = getFileInputStream(filePath);

            Map<String, String> metadata = new HashMap<>();
            metadata.put(HttpHeaders.CONTENT_TYPE, mimeType);

            String location = userEmail + "/" + assetId;

            fileAssetRepository.create(location, file, metadata);
            return location;
        }).publishOn(Schedulers.boundedElastic());
    }
}
