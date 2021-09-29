package com.team4.backend.service;

import com.team4.backend.repository.FileAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileAssetService {
    @Autowired
    FileAssetRepository fileAssetRepository;

    public String create(MultipartFile file, String userId) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalStateException("File empty");
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put(HttpHeaders.CONTENT_TYPE, file.getContentType());
        metadata.put(HttpHeaders.CONTENT_LENGTH, file.getSize() + "");

        UUID assetId = UUID.randomUUID();
        String location = userId + "/" + assetId;

        fileAssetRepository.create(location, file.getInputStream(), metadata);

        return assetId.toString();
    }
}
