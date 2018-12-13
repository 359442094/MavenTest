package sk.rsa;

import sk.hex.HEXUtil;
/**
 * 模拟真实场景
 * */
public class Rsa2Test {
    public static void main(String[] args) throws Exception
    {
        //生成公私钥文件
        //Rsa2Util.getKeyPair("C:/");
        //获取公钥
        String publicKey = Rsa2Util.readKeyFromFile("C:/publicKey.keystore");
        //获取私钥
        String privateKey = Rsa2Util.readKeyFromFile("C:/privateKey.keystore");

        System.out.println("publicKey："+publicKey);
        System.out.println("privateKey："+privateKey);

        System.out.println("---------------------------------------------");
        //私钥加签
        String sign = Rsa2Util.signByPrivateKey("测试加签", privateKey);

        //签名结果
        System.out.println("sign："+sign);
        System.out.println("---------------------------------------------");

        //签名结果转化为HEX字符串
        String encodeHexStr = HEXUtil.encodeHexStr(20, sign);
        System.out.println("签名结果转化为HEX字符串:"+encodeHexStr); //sign
        //HEX字符串转化为签名结果
        System.out.println("HEX字符串转化为签名结果:"+HEXUtil.decodeHexStr(20,encodeHexStr));

        System.out.println("---------------------------------------------");

        //使用公钥验证签名
        System.out.println("验签结果："+Rsa2Util.verifySignByPublicKey("测试加签", publicKey, sign));

        System.out.println("---------------------------------------------");

        //公钥加密明文数据
        String cipherText = Rsa2Util.encryptByPublicKey("测试加密明文数据", Rsa2Util.readPublicKeyFromString(publicKey));
        //密文
        System.out.println("cipherText："+cipherText);
        //明文
        String plainText = Rsa2Util.decryptByPrivateKey(cipherText, Rsa2Util.readPrivateKeyFromString(privateKey));
        System.out.println("plainText："+plainText);
    }
}
