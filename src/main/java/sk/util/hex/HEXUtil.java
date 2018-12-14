package sk.util.hex;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Hex;

/**
 * Hex 转换工具类
 */
public class HEXUtil {

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

    /*
     * 将普通字符串转换成Hex编码字符串
     *
     * @param dataCoding 编码格式，15表示GBK编码，8表示UnicodeBigUnmarked编码，0表示ISO8859-1编码
     * @param realStr 普通字符串
     * @return Hex编码字符串
     * @throws UnsupportedEncodingException
     */

    public static String encodeHexStr(String realStr) {
        String hexStr = null;
        if (realStr != null) {
            try {
                hexStr = new String(Hex.encodeHex(realStr.getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.toString());
            }
        }
        return hexStr;
    }

    /*
     * 将Hex编码字符串转换成普通字符串
     *
     * @param dataCoding 反编码格式，15表示GBK编码，8表示UnicodeBigUnmarked编码，0表示ISO8859-1编码
     * @param hexStr Hex编码字符串
     * @return 普通字符串
     */

    public static String decodeHexStr(String hexStr) {
        String realStr = null;
        try {
            realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "UTF-8");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return realStr;
    }

}
