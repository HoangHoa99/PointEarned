package com.hoanghoa.userpurchased.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hoanghoa.userpurchased.constant.UserType;
import com.hoanghoa.userpurchased.service.IS3Service;
import com.hoanghoa.userpurchased.util.QRUtil;
import com.hoanghoa.userpurchased.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Date;

@Service
public class S3ServiceImpl implements IS3Service {
    private AmazonS3 s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;
    @Value("${aws.accessKeyId}")
    private String accessKey;
    @Value("${aws.accessSecretKey}")
    private String secretKey;
    @Value("${aws.endpointUrl}")
    private String endpointUrl;

    /**
     * Initialize aws credential by key id and secret key
     */
    @PostConstruct
    private void initCredentials() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_SOUTHEAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    /**
     * Upload QR image to S3 bucket
     * @param file File to upload
     * @return QR image URL
     */
    @Override
    public String uploadFile(File file) {
        String fileUrl;
        try {
            String fileName = file.getName();
            fileUrl = endpointUrl + "/" + fileName;

            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            boolean deletedFile = file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            fileUrl = null;
        }
        return fileUrl;
    }

    /**
     * Call QRUtil to generate QR code, then upload it into S3 bucket by S3Service
     * @param phoneNumber phone number
     * @param userType user type
     * @return QR URL
     */
    @Override
    public String generateQRCode(String phoneNumber, UserType userType) {
        try {
            String fileName = StringUtil.USER_PREFIX + new Date().toInstant().getEpochSecond();
            File file = QRUtil.createFilePath(fileName);
            String data = phoneNumber + StringUtil.COMMA + userType.name();
            QRUtil.generateQRCode(data, file);

            return this.uploadFile(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return StringUtil.NULL;
    }
}
