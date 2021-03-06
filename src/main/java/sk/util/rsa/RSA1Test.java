package sk.util.rsa;
import java.io.UnsupportedEncodingException;
import java.security.*;
/**
 * 加签  验签    签名验证：验证数据的合法来源   即验证数据来源的合法性
 * 加签：私钥
 * 验签：公钥
 */
public class RSA1Test {
    private static String privateKeyPath="储存私钥信息的文件路径";
    private static String publicKeyPath="储存公钥信息的文件路径";

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
        String data="验证该数据是否为合法的服务器发送";
        //加签过程
        PrivateKey privateKey = RSA1Util.getPrivateKey(privateKeyPath);
        Signature signature = Signature.getInstance("Sha1WithRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes("UTF-8"));
        byte[] signed = signature.sign();
        //验签过程
        PublicKey publicKey = RSA1Util.getPublicKey(publicKeyPath);
        Signature signature2 = Signature.getInstance("Sha1WithRSA");
        signature2.initVerify(publicKey);
        signature2.update(data.getBytes("UTF-8"));
        boolean verify = signature2.verify(signed);
        System.out.println("验签结果:"+verify);
    }
}
