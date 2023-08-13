package com.geekster.BloggingPlatformAPI.service.utility.hashingUtility;

import jakarta.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypter {

    public static String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(password.getBytes());

        byte[] digested = md5.digest();

        return DatatypeConverter.printHexBinary(digested);
    }
}
