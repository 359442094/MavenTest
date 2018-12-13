package sk.rsa;

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
        //加签后Data
        System.out.println("sign："+sign);
        //验签
        System.out.println("验签："+Rsa2Util.verifySignByPublicKey("测试加签", publicKey, sign));

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
