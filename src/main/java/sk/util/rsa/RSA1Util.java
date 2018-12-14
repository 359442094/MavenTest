package sk.util.rsa;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
/**
 * 工具类
 */
public class RSA1Util {
    /*
        读取秘钥数据
     */
    public static byte[] readKeyDatas(String keyFilePath){
        BufferedReader bufferedReader=null;
        try{
            bufferedReader = new BufferedReader(new FileReader(keyFilePath));
            String str=null;
            StringBuilder stringBuilder=new StringBuilder();
            while ((str=bufferedReader.readLine())!=null){
                if(str.contains("---")){
                    continue;
                }
                stringBuilder.append(str);
            }
            return stringBuilder.toString().getBytes();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /*
        生成公钥
     */
    public static PublicKey getPublicKey(String publicKeyPath){
        //1.读取公钥文件,获取公钥数据
        byte[] bytesPublicBase64 = readKeyDatas(publicKeyPath);
        //2.对读取回来的数据进行Base64解码
        byte[] bytesPublic = Base64.getDecoder().decode(bytesPublicBase64);
        //3.把解码后的数据,重新封装成一个PublicKey对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytesPublic);
        KeyFactory keyFactory=null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
        生成私钥
     */
    public static PrivateKey getPrivateKey(String privateKeyPath){
        //1.读取私钥文件,获取私钥数据
        byte[] bytesPrivateBase64 = readKeyDatas(privateKeyPath);
        //2.对读取回来的数据进行Base64解码
        byte[] bytesPrivate = Base64.getDecoder().decode(bytesPrivateBase64);
        //3.把解码后的数据,重新封装成一个PrivateKey对象
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytesPrivate);
        KeyFactory keyFactory=null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
        加密数据
     */
    public static String encodeData(PublicKey publicKey,String originData){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            byte[] bytesEncrypt = cipher.doFinal(originData.getBytes());
            //Base64编码
            byte[] bytesEncryptBase64 = Base64.getEncoder().encode(bytesEncrypt);
            return new String(bytesEncryptBase64);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
        解密数据
     */
    public static String decodeData(PrivateKey privateKey,String encodeData){
        try {
            //Base64解码
            byte[] bytesEncrypt = Base64.getDecoder().decode(encodeData);
            //加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            byte[] bytesDecrypt = cipher.doFinal(bytesEncrypt);
            return new String(bytesDecrypt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}