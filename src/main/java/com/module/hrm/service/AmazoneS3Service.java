package com.module.hrm.service;

import com.module.hrm.web.common.enumeration.FileObjectType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.InvalidObjectStateException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.S3Exception;

public interface AmazoneS3Service {
    void uploadFile(String bucket, String key, byte[] data);

    public byte[] downloadFile(String bucket, String key)
        throws NoSuchKeyException, InvalidObjectStateException, S3Exception, AwsServiceException, SdkClientException, IOException;

    public URL generatePresignedUrl(String bucket, String key, Duration duration);

    String generateSignedUrl(String keyName);
    String generateIRSignedUrl(String keyName);
    String generateEvsSignedUrl(String keyName);
    Collection<String> generateSignedUrls(String smartSalesGuideLineBucketS3Key, String char1);

    void deleteFileOnS3bucket(String keyName);

    String copyFileOnS3bucket(FileObjectType objectType, String uploadedUrl);
    String copyFileOnS3bucket(FileObjectType user, String imageS3, String concat);

    String backupFileOnS3bucket(String keyName);

    String uploadFileToS3bucket(File fileDb, String contentType, String concat);
    String uploadFileToS3bucket(InputStream is, String originalFilename, String mediaTypeApplicationCsv, String tempUploadDir);
    String uploadFileToS3bucket(MultipartFile file, String defaultUploadDir);
}
