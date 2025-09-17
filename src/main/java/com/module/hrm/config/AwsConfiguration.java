package com.module.hrm.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Slf4j
@Configuration
@AllArgsConstructor
public class AwsConfiguration {

    private static final Region REGION = Region.AP_SOUTHEAST_1;

    /**
     * Create basic credentials.
     */
    private AwsCredentialsProvider awsCredentialsProvider() {
        return AwsCredentialsProviderChain.builder()
            .addCredentialsProvider(ProfileCredentialsProvider.create())
            .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
            .addCredentialsProvider(DefaultCredentialsProvider.create())
            .build();
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder().region(REGION).credentialsProvider(this.awsCredentialsProvider()).build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder().region(REGION).credentialsProvider(this.awsCredentialsProvider()).build();
    }

    @Bean
    public LambdaClient lambdaClient() {
        return LambdaClient.builder().region(REGION).credentialsProvider(this.awsCredentialsProvider()).build();
    }
}
