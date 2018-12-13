package sk.test;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sk.keyGenerator.KeyGeneralUtil;
import sk.sha256.SHAUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Controller
public class SHAController {
    /**
     * 发送JSON数据
     * */
    @RequestMapping(path = "/sendJSON",method = RequestMethod.POST)
    public void sendJSON() throws UnsupportedEncodingException {
        //JSON报文原文
        String content=new String("DSADSA爱迪生");
        //JSON报文原文使用SHA256安全哈希算法进行处理
        System.out.println("加密前:"+content);
        String sha256StrJava = SHAUtil.getSHA256StrJava(content);
        System.out.println("加密后:"+sha256StrJava);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //JSON报文原文
        String content=new String("DSADSA爱迪生");
        //JSON报文原文使用SHA256安全哈希算法进行处理
        System.out.println("加密前:"+content);
        String sha256StrJava = SHAUtil.getSHA256StrJava(content);
        System.out.println("加密后:"+sha256StrJava);
    }
}
