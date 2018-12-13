package sk.aes;

public class Aes2Test {
    public static void main(String[] args) throws Exception {
        //生成秘钥
        String AesPrivateKey = Aes2Util.getA221();
        System.out.println("生成器生成的秘钥:"+AesPrivateKey);

        //加密的明文数据[密文]
        String cipherText = Aes2Util.encode(AesPrivateKey,"明文数据");
        System.out.println("cipherText:"+cipherText);

        //解密的密文数据[明文]
        String plainText = Aes2Util.decode(AesPrivateKey,cipherText);
        System.out.println("plainText:"+plainText);
    }
}
