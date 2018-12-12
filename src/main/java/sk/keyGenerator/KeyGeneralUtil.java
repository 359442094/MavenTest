package sk.keyGenerator;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.apache.commons.codec.binary.Base64;
/**
 * KeyGenerator 生成器
 * */
public class KeyGeneralUtil {
    /**
     * AES 128,256
     * DEA 56
     */
//===========================加密方式======================\\
    private static final String ALGORITHM_HMACMD5="HmacMD5";
    private static final String ALGORITHM_AES="AES";
    private static final String ALGORITHM_DES="DES";
    //===========================加密类型于算法无关======================\\
    //默认
    public static String initDefault() {
        String key = "";
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM_HMACMD5);
            SecretKey secretKey=generator.generateKey();
            byte[]bytes=encode(secretKey.getEncoded());
            key=new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }
    //安全随机种子
    public static String initAESKey(int keysize,long seed) {
        String key = "";
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM_AES);
            SecureRandom random=new  SecureRandom();
            random.setSeed(seed);
            generator.init(keysize, random);
            SecretKey secretKey=generator.generateKey();
            byte[]bytes=encode(secretKey.getEncoded());
            key=new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }
    //安全随机种子
    public static String initDESKey(int keysize,long seed) {
        String key = "";
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM_DES);
            SecureRandom random=new  SecureRandom();
            random.setSeed(seed);
            generator.init(keysize, random);
            SecretKey secretKey=generator.generateKey();
            byte[]bytes=encode(secretKey.getEncoded());
            key=new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }
    //===========================base64======================\\
    public static byte[] encode(byte[]plainBytes){
        Base64 base64=new Base64();
        return base64.encode(plainBytes);
    }
    public static byte[] decode(byte[]cipherText){
        Base64 base64=new Base64();
        return base64.decode(cipherText);
    }
}

