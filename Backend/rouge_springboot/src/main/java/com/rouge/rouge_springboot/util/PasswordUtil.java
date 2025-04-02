package com.rouge.rouge_springboot.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码加密工具类
 */
public class PasswordUtil {
    private static final String SALT = "password_salt";

    /**
     * 加密密码
     * @param password 原密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(SALT.getBytes());
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    /**
     * 验证密码
     * @param password 原始密码
     * @param storedHash 存储的加密密码
     * @return 验证结果
     */
    public static boolean checkPassword(String password, String storedHash) {
        String hashedPassword = encryptPassword(password);
        return hashedPassword.equals(storedHash);
    }
}