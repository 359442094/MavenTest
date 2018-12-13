package sk.test;

import sk.aes.Aes2Util;
import sk.hex.HEXUtil;
import sk.rsa.Rsa2Util;

public class TestController {
    public static void main(String[] args) throws Exception {
        String plainText="测试明文内容";

        //生成公私钥文件
        //Rsa2Util.getKeyPair("C:/");
        //获取公钥
        String publicKey = Rsa2Util.readKeyFromFile("C:/publicKey.keystore");
        //获取私钥
        String privateKey = Rsa2Util.readKeyFromFile("C:/privateKey.keystore");

        //私钥加签--测试加签
        String sign = Rsa2Util.signByPrivateKey(plainText, privateKey);

        //签名结果转化为HEX字符串 sign域
        String encodeHexStr = HEXUtil.encodeHexStr(20, sign);

        System.out.println("sign域:"+encodeHexStr);

        System.out.println("--------------------------------------");
        //生成秘钥
        String AesPrivateKey = Aes2Util.getA221();
        //System.out.println("生成器生成的秘钥:"+AesPrivateKey);

        //加密的明文数据[密文]
        String AEScipherText = Aes2Util.encode(AesPrivateKey,plainText);
        //System.out.println("AEScipherText:"+AEScipherText);
        //转化为HEX字符串
        String json_enc = HEXUtil.encodeHexStr(20,AEScipherText);

        System.out.println("json_enc:"+json_enc);
        //System.out.println("--------------------------------------");

        //公钥加密AES加密后的秘钥
        String RSAcipherText = Rsa2Util.encryptByPublicKey(AesPrivateKey, Rsa2Util.readPublicKeyFromString(publicKey));
        //密文
        //System.out.println("RSAcipherText："+RSAcipherText);
        //将RSA公钥加密过的AES秘钥转化为HEX字符串
        String key_enc = HEXUtil.encodeHexStr(20, RSAcipherText);
        System.out.println("key_enc:"+key_enc);
        System.out.println("--------------------------------------");



    }
}
