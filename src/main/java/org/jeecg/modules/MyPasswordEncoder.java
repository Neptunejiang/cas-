/*
package org.jeecg.modules;

import org.apache.commons.codec.digest.DigestUtils;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;

public class MyPasswordEncoder implements PasswordEncoder {
*/
/**
     * 定义使用的算法为:PBEWITHMD5andDES算法
     *//*
*/
/*

    public static final String ALGORITHM = "PBEWithMD5AndDES";//加密算法


*//*
*/
/**
     * 定义迭代次数为1000次
     *//*
*/
/*

    private static final int ITERATIONCOUNT = 1000;


    @Override
    public String encode(CharSequence rawPassword) {
        // 使用 PBEWithMD5AndDES 算法对密码进行加密
        // ...
        *//*
*/
/*Key key = getPBEKey((String)rawPassword);
        byte[] encipheredData = null;
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), ITERATIONCOUNT);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            //update-begin-author:sccott date:20180815 for:中文作为用户名时，加密的密码windows和linux会得到不同的结果 gitee/issues/IZUD7
            encipheredData = cipher.doFinal(plaintext.getBytes("utf-8"));
            //update-end-author:sccott date:20180815 for:中文作为用户名时，加密的密码windows和linux会得到不同的结果 gitee/issues/IZUD7
        } catch (Exception e) {
        }
        return bytesToHexString(encipheredData);*//*
*/
/*
        return null;
    }
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 将用户输入的密码进行加密，然后与数据库中的密码进行比较
        // ...
        String encodePassword = encode(rawPassword);
        if(encodePassword.equals(encodedPassword)){
            return true;
        }else {
            return false;
        }
    }
*//*
*/
/**
     * 根据PBE密码生成一把密钥
     *
     * @param password
     *            生成密钥时所使用的密码
     * @return Key PBE算法密钥
     * *//*
*/
/*

    private static Key getPBEKey(String password) {
        // 实例化使用的算法
        SecretKeyFactory keyFactory;
        SecretKey secretKey = null;
        try {
            keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            // 设置PBE密钥参数
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            // 生成密钥
            secretKey = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return secretKey;
    }*//*


    @Override
    public String encode(CharSequence password) {
        return DigestUtils.md5Hex(password.toString()).toUpperCase();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodePassword) {
        // 判断密码是否存在
        if (rawPassword == null) {
            return false;
        }
        //通过md5加密后的密码
        String pass = this.encode(rawPassword.toString());
        //比较密码是否相等的问题
        return pass.equals(encodePassword);
    }

    AbstractPreAndPostProcessingAuthenticationHandler handler;
}
*/
