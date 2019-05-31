package com.lf.shoppingmall.http_utils;

import com.lf.shoppingmall.common.Constans;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class DES {
    private static DES inst = null;
    private static final String DES_KEY = Constans.DES_KEY;
    private static final byte[] DES_IV = {18, 52, 86, 120, -112, -85, -51, -17};
    private AlgorithmParameterSpec iv = null;
    private Key key = null;
    private Cipher cipher = null;

    private DES()
            throws Exception {
        DESKeySpec keySpec = new DESKeySpec(DES_KEY.getBytes("UTF-8"));
        this.iv = new IvParameterSpec(DES_IV);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        this.key = keyFactory.generateSecret(keySpec);
        this.cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    }

    private DES(String desKey)
            throws Exception {
        DESKeySpec keySpec = new DESKeySpec(desKey.getBytes("UTF-8"));
        this.iv = new IvParameterSpec(DES_IV);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        this.key = keyFactory.generateSecret(keySpec);
        this.cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    }

    public synchronized byte[] encode(byte[] data)
            throws Exception {
        this.cipher.init(1, this.key, this.iv);
        byte[] pasByte = this.cipher.doFinal(data);
        return Base64.encodeBase64(pasByte);
    }

    public synchronized byte[] decode(byte[] data)
            throws Exception {
        this.cipher.init(2, this.key, this.iv);
        byte[] pasByte = this.cipher.doFinal(Base64.decodeBase64(data));
        return pasByte;
    }

    public static DES getInstance()
            throws Exception {
        if (inst == null) {
            inst = new DES();
        }
        return inst;
    }

    public static DES getInstance(String desKey)
            throws Exception {
        if (inst == null) {
            inst = new DES(desKey);
        }
        return inst;
    }

    public static String encryptDES(String src) {
        String text = null;
        try {
            text = new String(getInstance("scguoshu").encode(src.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static String decryptDES(String src) {
        String text = null;
        try {
            byte[] b = getInstance("scguoshu").decode(src.getBytes());
            text = new String(b, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
