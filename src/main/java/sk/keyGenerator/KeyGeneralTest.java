package sk.keyGenerator;

import java.util.Date;

public class KeyGeneralTest {
    //============================controller========================\\
    public static void main(String[] args) {
        System.out.println("加载默认摘要算法为HmacMD5__:"+KeyGeneralUtil.initDefault());

        System.out.println("加载默认配置算法为AES__:"+KeyGeneralUtil.initAESKey(256,new Date().getTime()));

        System.out.println("加载默认配置算法为DES__:"+KeyGeneralUtil.initDESKey(56,new Date().getTime()));
    }
}
