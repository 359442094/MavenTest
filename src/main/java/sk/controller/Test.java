package sk.controller;

import sk.aes.Aes2Util;
import sk.hex.HEXUtil;
import sk.rsa.Rsa2Util;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception {
        String plainText="测试明文内容";
        //1.加签
        //生成公私钥文件
        //Rsa2Util.getKeyPair("C:/");
        //获取公钥
        String publicKey = Rsa2Util.readKeyFromFile("C:/publicKey.keystore");
        //获取私钥
        String privateKey = Rsa2Util.readKeyFromFile("C:/privateKey.keystore");

        //私钥加签--测试加签
        String sign = Rsa2Util.signByPrivateKey(plainText, privateKey);
        System.out.println("签名内容:"+sign);
        //签名结果转化为HEX字符串 sign域
        String encodeHexStr = HEXUtil.encodeHexStr(20, sign);

        System.out.println("sign域:"+encodeHexStr);


        //2.加密明文数据
        //生成秘钥
        String AesPrivateKey = Aes2Util.getA221();
        System.out.println("生成器生成的秘钥:"+AesPrivateKey);

        //加密的明文数据[密文]
        String AEScipherText = Aes2Util.encode(AesPrivateKey,plainText);
        System.out.println("加密的明文数据:"+AEScipherText);
        //转化为HEX字符串
        String json_enc = HEXUtil.encodeHexStr(20,AEScipherText);

        System.out.println("json_enc:"+json_enc);


        //3.RSA加密AES的秘钥
        //公钥加密AES加密后的秘钥
        String RSAcipherText = Rsa2Util.encryptByPublicKey(AesPrivateKey, Rsa2Util.readPublicKeyFromString(publicKey));
        System.out.println("RSA加密后AES的秘钥:"+RSAcipherText);
        //密文
        //将RSA公钥加密过的AES秘钥转化为HEX字符串
        String key_enc = HEXUtil.encodeHexStr(20, RSAcipherText);
        System.out.println("key_enc:"+key_enc);

        System.out.println("------------------------解密操作-------------------------------");

        //1.RSA私钥解密AES秘钥
        String privateStr = HEXUtil.decodeHexStr(20, key_enc);
        String RSApwd = Rsa2Util.decryptByPrivateKey(privateStr, Rsa2Util.readPrivateKeyFromString(privateKey));
        System.out.println("RSA加密前AES的秘钥:"+RSApwd);
        //2.解密JSON明文
        String jsonContent = HEXUtil.decodeHexStr(20, json_enc);
        String decode = Aes2Util.decode(RSApwd, jsonContent);
        System.out.println("JSON明文:"+decode);
        //3.验证签名
        String qian = HEXUtil.decodeHexStr(20, encodeHexStr);
        boolean flag = Rsa2Util.verifySignByPublicKey(decode, publicKey, qian);
        System.out.println("验签结果:"+flag);
    }
}
