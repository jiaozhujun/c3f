package com.youcan.test;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DTest {
    
  Key key;

  public DTest(String str) {
    setKey(str);//生成密匙
  }

  public DTest() {
    setKey("siyue_qi");
  }

  /**
   * 根据参数生成KEY
   */
  public void setKey(String strKey) {
      try {
        KeyGenerator _generator = KeyGenerator.getInstance("DES");
        _generator.init(new SecureRandom(strKey.getBytes()));
        this.key = _generator.generateKey();
        _generator = null;
      } catch (Exception e) {
        throw new RuntimeException(
            "Error initializing SqlMap class. Cause: " + e);
      }
  }

  /**
   * 加密String明文输入,String密文输出
   */
  public String getEncString(String strMing) {
      byte[] byteMi = null;
      byte[] byteMing = null;
      String strMi = "";
      BASE64Encoder base64en = new BASE64Encoder();
      try {
        byteMing = strMing.getBytes("UTF8");
        byteMi = this.getEncCode(byteMing);
        strMi = base64en.encode(byteMi);
      } catch (Exception e) {
        throw new RuntimeException(
            "Error initializing SqlMap class. Cause: " + e);
      } finally {
        base64en = null;
        byteMing = null;
        byteMi = null;
      }
      return strMi;
  }

  /**
   * 解密 以String密文输入,String明文输出
   * @param strMi
   * @return
   */
  public String getDesString(String strMi) {
      BASE64Decoder base64De = new BASE64Decoder();
      byte[] byteMing = null;
      byte[] byteMi = null;
      String strMing = "";
      try {
        byteMi = base64De.decodeBuffer(strMi);
        byteMing = this.getDesCode(byteMi);
        strMing = new String(byteMing, "UTF8");
      } catch (Exception e) {
        throw new RuntimeException(
            "Error initializing SqlMap class. Cause: " + e);
      } finally {
        base64De = null;
        byteMing = null;
        byteMi = null;
      }
      return strMing;
  }

  /**
   * 加密以byte[]明文输入,byte[]密文输出
   * @param byteS
   * @return
   */
  private byte[] getEncCode(byte[] byteS) {
      byte[] byteFina = null;
      Cipher cipher;
      try {
        cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byteFina = cipher.doFinal(byteS);
      } catch (Exception e) {
        throw new RuntimeException(
            "Error initializing SqlMap class. Cause: " + e);
      } finally {
        cipher = null;
      }
      return byteFina;
  }

  /**
   * 解密以byte[]密文输入,以byte[]明文输出
   * @param byteD
   * @return
   */
  private byte[] getDesCode(byte[] byteD) {
      Cipher cipher;
      byte[] byteFina = null;
      try {
        cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byteFina = cipher.doFinal(byteD);
      } catch (Exception e) {
        throw new RuntimeException(
            "Error initializing SqlMap class. Cause: " + e);
      } finally {
        cipher = null;
      }
      return byteFina;
  }

  public static void main(String args[]) {
		String key = "j iaozhu jun-zZ2";
	  DTest des = new DTest(key);
		byte[] b = {(byte)0xf1,(byte)0xad,(byte)0xc2,(byte)0xa1,(byte)0x91,(byte)0xfa,(byte)0xdf,(byte)0x4c,(byte)0x2b,(byte)0x20,(byte)0x87,(byte)0xfc,(byte)0x4c,(byte)0x51,(byte)0x35,(byte)0xe1};
		byte[] pImei = des.getDesCode(b);
		System.out.println("pImei: " + new String(pImei));
  }
}