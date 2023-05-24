package com.hoanghoa.userpurchased.service;

import com.hoanghoa.userpurchased.constant.UserType;

import java.io.File;

public interface IS3Service {

    public String uploadFile(File file);
    public String generateQRCode(String phoneNumber, UserType userType);
}
