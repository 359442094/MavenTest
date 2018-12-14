package sk.util.rsa;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import sk.util.aes.AES2Util;
import sk.util.hex.HEXUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSA使用X509EncodedKeySpec、PKCS8EncodedKeySpec生成公钥和私钥
 * 加密数据大小不能超过127 bytes
 * @Description：加签、验签、加密、解密、生成公钥、私钥
 */
public class RSA2Util
{
    /**
     * 生成公私钥对
     * @param filePath 生成文件路径
     */
    @SuppressWarnings("static-access")
    public static void getKeyPair(String filePath)
    {
        KeyPairGenerator keyPairGenerator = null;
        try
        {
            keyPairGenerator = keyPairGenerator.getInstance("RSA");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encode(publicKey.getEncoded());
        String privateKeyString = Base64.encode(privateKey.getEncoded());
        try
        {
            BufferedWriter publicbw = new BufferedWriter(new FileWriter(new File(filePath+"/publicKey.keystore")));
            BufferedWriter privatebw = new BufferedWriter(new FileWriter(new File(filePath+"/privateKey.keystore")));
            publicbw.write(publicKeyString);
            privatebw.write(privateKeyString);
            publicbw.flush();
            publicbw.close();
            privatebw.flush();
            privatebw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 从文件中读取公钥或私钥
     * @param filePath 文件路径
     * @return 公钥或私钥
     */
    public static String readKeyFromFile(String filePath)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while((readLine = br.readLine()) != null)
            {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从字符串中加载公钥
     * @return 公钥
     */
    public static RSAPublicKey readPublicKeyFromString(String publicKeyStr)
    {
        try
        {
            byte[] bt = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bt);
            return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (InvalidKeySpecException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从字符串中加载私钥
     * @return 私钥
     */
    public static RSAPrivateKey readPrivateKeyFromString(String privateKeyStr)
    {
        try
        {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (InvalidKeySpecException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥加签
     * @param content 报文
     * @param privateKey 私钥
     * @return 签名值
     */
    public static String signByPrivateKey(String content,String privateKey)
    {
        try
        {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("SHA256withRSA");//MD5withRSA
            signature.initSign(priKey);
            signature.update(content.getBytes());
            byte[] sign = signature.sign();
            //return Base64.encode(sign);
            //return HEXUtil.encodeHexStr(20,new String(sign));
            return HEXUtil.byteToHexString(sign);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥验签
     * @param content 报文
     * @param publicKey 公钥
     * @param sign 签名值
     * @return 验签是否通过
     */
    public static boolean verifySignByPublicKey(String content,String publicKey,String sign)
    {
        try
        {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(publicKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Signature signature = Signature.getInstance("SHA256withRSA");//MD5withRSA
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            return signature.verify(HEXUtil.hexStringToByte(sign)); //Base64.decode(sign)
        }
        catch (Exception e)
        {
            return false;
            //e.printStackTrace();
        }
    }

    /**
     * 公钥加密
     * @param plainText 明文
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception
     */
    public static String encryptByPublicKey(String plainText,RSAPublicKey publicKey) throws Exception
    {
        if(publicKey == null)
        {
            throw new Exception("公钥为空！");
        }
        Cipher cipher = null;
        try
        {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainText.getBytes());
            //return Base64.encode(output);
            //return HEXUtil.encodeHexStr(20,new String(output));
            return HEXUtil.byteToHexString(output);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密
     * @param cipherText 密文
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception
     */
    public static String decryptByPrivateKey(String cipherText,RSAPrivateKey privateKey) throws Exception
    {
        if(privateKey == null)
        {
            throw new Exception("私钥为空！");
        }
        Cipher cipher = null;
        try
        {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(HEXUtil.hexStringToByte(cipherText)); //Base64.decode(cipherText)
            return new String(output);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

