package sk.test;
import sk.util.aes.AES2Util;
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
        String sign = RSA2Util.signByPrivateKey(plainText, privateKey);

        //2.加密明文
        String AESPrivateKey = AES2Util.getA221();
        String json_enc = AES2Util.encode(AESPrivateKey, plainText);

        //3.加密AES秘钥
        String key_enc = RSA2Util.encryptByPublicKey(AESPrivateKey, RSA2Util.readPublicKeyFromString(publicKey));

        System.out.println("签名信息:"+sign);
        System.out.println("生成的秘钥:"+AESPrivateKey);
        System.out.println("密文信息:"+json_enc);
        System.out.println("RSA加密后AES秘钥:"+key_enc);

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
        //1.解密RSA加密后的AES秘钥
        String AESPrivateKey = RSA2Util.decryptByPrivateKey(maps.get("key_enc"), RSA2Util.readPrivateKeyFromString(privateKey));

        //2.解密密文信息
        String json_enc = AES2Util.decode(AESPrivateKey, maps.get("json_enc"));

        //3.验证签名信息
        boolean sign = RSA2Util.verifySignByPublicKey(json_enc, publicKey, maps.get("sign"));

        System.out.println("AES秘钥:"+AESPrivateKey);
        System.out.println("明文内容:"+json_enc);
        System.out.println("验签结果:"+sign);
    }
}
