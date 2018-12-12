package sk.aes;

import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import sk.aes.AesUtil;

public class AesTest {
    public static void main(String[] args) {
        //加密的操作
        byte[] bys = AesUtil.encrypt("需要加密的内容", "1234");
        //解密的操作
        byte[] decrypt = AesUtil.decrypt(bys, "1234");

        System.out.println("加密后:"+new String(bys));
        System.out.println("解密后:"+new String(decrypt));

    }
}
