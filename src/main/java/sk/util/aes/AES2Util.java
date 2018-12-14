package sk.util.aes;

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
            return byteToHexString(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("没有此算法。");
            return "";
        }
    }

    /**
     * 二进制byte[]转十六进制string
     */
    public static String byteToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex=Integer.toHexString(bytes[i]);
            if(strHex.length() > 3){
                sb.append(strHex.substring(6));
            } else {
                if(strHex.length() < 2){
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return  sb.toString();
    }

    /**
     * 十六进制string转二进制byte[]
     */
    public static byte[] hexStringToByte(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                System.out.println("十六进制转byte发生错误！！！");
                e.printStackTrace();
            }
        }
        return baKeyword;
    }

    /**
     * 使用AES对称密钥进行加密
     * @param privateKey 密钥
     * @param plainText 密文
     * @return  加密后数据
     */
    public static String encode(String privateKey,String plainText) throws Exception{
        byte[] keyb = hexStringToByte(privateKey);
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
        byte[] bjiamihou = cipher.doFinal(plainText.getBytes());
        return byteToHexString(bjiamihou);		//加密后数据为ecf0a6bc80dbaf657eac9b06ecd92962
    }

    /**
     * 使用AES对称密钥进行解密
     * @param privateKey 密钥
     * @param cipherText 密文
     * @rturn 解密后数据
     */
    public static String decode(String privateKey,String cipherText) throws Exception{
        byte[] keys = hexStringToByte(privateKey);
        byte[] miwen = hexStringToByte(cipherText);
        SecretKeySpec sKeySpec = new SecretKeySpec(keys, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
        byte[] bjiemihou = cipher.doFinal(miwen);
        return new String(bjiemihou);
    }

}
