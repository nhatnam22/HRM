package com.module.hrm.service.impl;

import com.module.hrm.service.AmazoneS3Service;
import com.module.hrm.web.common.enumeration.FileObjectType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.InvalidObjectStateException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
@Slf4j
@AllArgsConstructor
public class AmazonS3ServiceImpl implements AmazoneS3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    public void uploadFile(String bucket, String key, byte[] data) {
        s3Client.putObject(PutObjectRequest.builder().bucket(bucket).key(key).build(), RequestBody.fromBytes(data));
    }

    public byte[] downloadFile(String bucket, String key)
        throws NoSuchKeyException, InvalidObjectStateException, S3Exception, AwsServiceException, SdkClientException, IOException {
        return s3Client.getObject(GetObjectRequest.builder().bucket(bucket).key(key).build()).readAllBytes();
    }

    public URL generatePresignedUrl(String bucket, String key, Duration duration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(duration)
            .getObjectRequest(getObjectRequest)
            .build();

        return s3Presigner.presignGetObject(presignRequest).url();
    }

    @Override
    public String generateSignedUrl(String keyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String generateIRSignedUrl(String keyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String generateEvsSignedUrl(String keyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> generateSignedUrls(String smartSalesGuideLineBucketS3Key, String char1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteFileOnS3bucket(String keyName) {
        // TODO Auto-generated method stub

    }

    @Override
    public String copyFileOnS3bucket(FileObjectType objectType, String uploadedUrl) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String copyFileOnS3bucket(FileObjectType user, String imageS3, String concat) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String backupFileOnS3bucket(String keyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String uploadFileToS3bucket(File fileDb, String contentType, String concat) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String uploadFileToS3bucket(InputStream is, String originalFilename, String mediaTypeApplicationCsv, String tempUploadDir) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String uploadFileToS3bucket(MultipartFile file, String defaultUploadDir) {
        // TODO Auto-generated method stub
        return null;
    }
}
