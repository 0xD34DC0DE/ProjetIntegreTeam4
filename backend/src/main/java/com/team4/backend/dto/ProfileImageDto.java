package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

@Data
@NoArgsConstructor
public class ProfileImageDto {

    private String uploaderEmail;
    private byte[] image;

    @Builder(builderMethodName = "profileImageDtoBuilder")
    public ProfileImageDto(String uploaderEmail, byte[] image) {
        this.uploaderEmail = uploaderEmail;
        this.image = image;
    }

}
