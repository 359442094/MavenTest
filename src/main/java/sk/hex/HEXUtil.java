package sk.hex;
import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Hex;
/**
 * Hex 转换工具类
 * */
public class HEXUtil {
/*
     * 将普通字符串转换成Hex编码字符串
     *
     * @param dataCoding 编码格式，15表示GBK编码，8表示UnicodeBigUnmarked编码，0表示ISO8859-1编码
     * @param realStr 普通字符串
     * @return Hex编码字符串
     * @throws UnsupportedEncodingException
*/

    public static String encodeHexStr(int dataCoding, String realStr) {
        String hexStr = null;
        if (realStr != null) {
            try {
                if (dataCoding == 15) {
                    hexStr = new String(Hex.encodeHex(realStr.getBytes("GBK")));
                }
                else if ((dataCoding & 0x0C) == 0x08) {
                    hexStr = new String(Hex.encodeHex(realStr.getBytes("UnicodeBigUnmarked")));
                } else {
                    hexStr = new String(Hex.encodeHex(realStr.getBytes("UTF-8")));
                }
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

    public static String decodeHexStr(int dataCoding, String hexStr) {
        String realStr = null;
        try {
            if (hexStr != null) {
                if (dataCoding == 15) {
                    realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "GBK");
                }
                else if ((dataCoding & 0x0C) == 0x08) {
                    realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "UnicodeBigUnmarked");
                } else {
                    realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "UTF-8");
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return realStr;
    }

}
