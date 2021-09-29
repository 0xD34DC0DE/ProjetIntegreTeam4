package com.team4.backend.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

@Configuration
public class AmazonConfig {
    @Bean
    public AmazonS3 s3() {
        AWSCredentialsProvider credentials = new ProfileCredentialsProvider("ProjetIntegreTeam4");
        return AmazonS3ClientBuilder.standard().withRegion("us-east-1").withCredentials(credentials).build();
    }

//    @Bean
//    public MultipartResolver multipartResolver() {
//        final CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setMaxUploadSize(-1);
//        return resolver;
//    }
//
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        final MultipartFilter filter = new MultipartFilter();
//        final FilterRegistrationBean bean = new FilterRegistrationBean(filter);
//        bean.addInitParameter("mulitpartResolverBeanName", "multipartResolver");
//        return bean;
//    }

}
