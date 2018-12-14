package sk.controller;
import sk.util.aes.AES2Util;
import sk.util.hex.HEXUtil;
import sk.util.rsa.RSA2Util;
import java.util.HashMap;
import java.util.Map;
public class Test {

    public static void main(String[]args) throws Exception {
        //生成公私钥文件
        //RSA2Util.getKeyPair("C:/");
        //读取公、私钥、
        String publicKey= RSA2Util.readKeyFromFile("C://publicKey.keystore");
        String privateKey = RSA2Util.readKeyFromFile("C://privateKey.keystore");

        System.out.println("publicKey:"+publicKey);
        System.out.println("privateKey:"+privateKey);
        System.out.println("-----------------------加密后-----------------------");
        Map<String, String> cipherMap = encode(publicKey, privateKey);
        System.out.println("-----------------------解密后-----------------------");
        decode(publicKey,privateKey,cipherMap);
    }
    /**
     * 加密过程
     * */
    public static Map<String,String> encode(String publicKey,String privateKey) throws Exception {
        String plainText="测试明文内容";
        //1.签名
        String signByPrivateKey = RSA2Util.signByPrivateKey(plainText, privateKey);
        System.out.println("签名信息:"+signByPrivateKey);
        String sign = HEXUtil.encodeHexStr(20, signByPrivateKey);

        //2.加密明文
        String AESPrivateKey = AES2Util.getA221();
        System.out.println("生成的秘钥:"+AESPrivateKey);
        String encode = AES2Util.encode(AESPrivateKey, plainText);
        System.out.println("密文信息:"+plainText);
        String json_enc = HEXUtil.encodeHexStr(20, encode);

        //3.加密AES秘钥
        String encrypt = RSA2Util.encryptByPublicKey(AESPrivateKey, RSA2Util.readPublicKeyFromString(publicKey));
        System.out.println("RSA加密后AES秘钥:"+encrypt);
        String key_enc = HEXUtil.encodeHexStr(20, encrypt);

        Map<String,String> maps=new HashMap<>();
        maps.put("sign",sign);
        maps.put("json_enc",json_enc);
        maps.put("key_enc",key_enc);

        return maps;
    }

    /**
     * 解密过程
     * */
    public static void decode(String publicKey,String privateKey,Map<String,String> maps) throws Exception {
        String key_enc = HEXUtil.decodeHexStr(20,maps.get("key_enc"));
        //1.解密RSA加密后的AES秘钥
        String decryptByPrivateKey = RSA2Util.decryptByPrivateKey(key_enc, RSA2Util.readPrivateKeyFromString(privateKey));
        System.out.println("解密后的秘钥:"+decryptByPrivateKey);

        String json_enc = HEXUtil.decodeHexStr(20, maps.get("json_enc"));
        //2.解密密文信息
        String plainText = AES2Util.decode(decryptByPrivateKey, json_enc);
        System.out.println("解密密文信息:"+plainText);

        //3.验证签名信息
        String sign = HEXUtil.decodeHexStr(15, maps.get("sign"));
        boolean flag = RSA2Util.verifySignByPublicKey(plainText,publicKey,sign);
        System.out.println("签名验签:"+flag);
    }
}
