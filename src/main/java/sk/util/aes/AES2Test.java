package sk.util.aes;

public class AES2Test {
    public static void main(String[] args) throws Exception {
        //生成秘钥
        String AesPrivateKey = AES2Util.getA221();
        System.out.println("生成器生成的秘钥:"+AesPrivateKey);

        //加密的明文数据[密文]
        String cipherText = AES2Util.encode(AesPrivateKey,"明文数据");
        System.out.println("cipherText:"+cipherText);

        //解密的密文数据[明文]
        String plainText = AES2Util.decode(AesPrivateKey,cipherText);
        System.out.println("plainText:"+plainText);
    }
}
