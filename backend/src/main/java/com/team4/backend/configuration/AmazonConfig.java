package com.team4.backend.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
    @Bean
    public AmazonS3 s3() {
        AWSCredentialsProvider credentials = new ProfileCredentialsProvider("ProjetIntegreTeam4");
        return AmazonS3ClientBuilder.standard().withRegion("us-east-1").withCredentials(credentials).build();
    }

}
