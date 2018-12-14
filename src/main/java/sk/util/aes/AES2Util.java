package sk.util.aes;

import sk.util.hex.HEXUtil;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class AES2Util {
    /**
     * 自动生成AES128位密钥
     */
    public static String getA221(){
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            return HEXUtil.byteToHexString(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("没有此算法。");
            return "";
        }
    }

    /**
     * 使用AES对称密钥进行加密
     * @param privateKey 密钥
     * @param plainText 密文
     * @return  加密后数据
     */
    public static String encode(String privateKey,String plainText) throws Exception{
        byte[] keyb = HEXUtil.hexStringToByte(privateKey);
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
        byte[] bjiamihou = cipher.doFinal(plainText.getBytes());
        return HEXUtil.byteToHexString(bjiamihou);		//加密后数据为ecf0a6bc80dbaf657eac9b06ecd92962
    }

    /**
     * 使用AES对称密钥进行解密
     * @param privateKey 密钥
     * @param cipherText 密文
     * @rturn 解密后数据
     */
    public static String decode(String privateKey,String cipherText) throws Exception{
        byte[] keys = HEXUtil.hexStringToByte(privateKey);
        byte[] miwen = HEXUtil.hexStringToByte(cipherText);
        SecretKeySpec sKeySpec = new SecretKeySpec(keys, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
        byte[] bjiemihou = cipher.doFinal(miwen);
        return new String(bjiemihou);
    }

}
