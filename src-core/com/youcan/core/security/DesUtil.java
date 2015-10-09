package com.youcan.core.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.youcan.core.util.Text;

public class DesUtil {
	public DesUtil() {
		//do nothing
	}

	/**
	 * 把成生的一对密钥保存到DesKey.xml文件中
	 */
	public static void genDesKey(String keyFile) {
		try {
			SecureRandom sr = new SecureRandom();
			// 为我们选择的DES算法生成一个KeyGenerator对象
			KeyGenerator kg = KeyGenerator.getInstance("DES");
			kg.init(sr);
			FileOutputStream fos = new FileOutputStream(keyFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			// 生成密钥
			Key key = kg.generateKey();
			oos.writeObject(key);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得DES加密的密钥。在交易处理的过程中应该定时更 换密钥。需要JCE的支持，如果jdk版本低于1.4，则需要
	 * 安装jce-1_2_2才能正常使用。
	 * 
	 * @return Key 返回对称密钥
	 */
	public static Key getKeyFromFile(String keyFile) {
		Key kp = null;
		try {
			InputStream is = new FileInputStream(keyFile);
			ObjectInputStream oos = new ObjectInputStream(is);
			kp = (Key) oos.readObject();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kp;
	}

	/**
	 * 文件file进行加密并保存目标文件destFile中
	 * 
	 * @param file
	 *            要加密的文件 如c:/test/srcFile.txt
	 * @param destFile
	 *            加密后存放的文件名 如c:/加密后文件.txt
	 */
	public static void encryptFile(String keyFile, String file, String destFile) throws Exception {
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, getKeyFromFile(keyFile));
		InputStream is = new FileInputStream(file);
		OutputStream out = new FileOutputStream(destFile);
		CipherInputStream cis = new CipherInputStream(is, cipher);
		byte[] buffer = new byte[1024];
		int r;
		while ((r = cis.read(buffer)) > 0) {
			out.write(buffer, 0, r);
		}
		cis.close();
		is.close();
		out.close();
	}

	/**
	 * 文件file进行解密并保存目标文件destFile中
	 * 
	 * @param file
	 *            已加密的文件 如c:/加密后文件.txt
	 * @param destFile
	 *            解密后存放的文件名 如c:/ test/解密后文件.txt
	 */
	public static void decryptFile(String keyFile, String file, String dest) throws Exception {
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, getKeyFromFile(keyFile));
		InputStream is = new FileInputStream(file);
		OutputStream out = new FileOutputStream(dest);
		CipherOutputStream cos = new CipherOutputStream(out, cipher);
		byte[] buffer = new byte[1024];
		int r;
		while ((r = is.read(buffer)) >= 0) {
			cos.write(buffer, 0, r);
		}
		cos.close();
		out.close();
		is.close();
	}
	
    /**
     * 三次文件加密函数
     * 参数：
     * 要加密的文件，输出文件，密码（由0-F组成，共48个字符，表示3个8位的密码）如：
     * AD67EA2F3BE6E5ADD368DFE03120B5DF92A8FD8FEC2F0746
     * 其中：
     * AD67EA2F3BE6E5AD DES密码一
     * D368DFE03120B5DF DES密码二
     * 92A8FD8FEC2F0746 DES密码三
     */
    public static void triEncryptFile(String sKey, String srcFile, String destFile){
        try{
            if(sKey.length() == 48){
                byte[] bytK1 = Text.getBytesFromStr(sKey.substring(0,16));
                byte[] bytK2 = Text.getBytesFromStr(sKey.substring(16,32));
                byte[] bytK3 = Text.getBytesFromStr(sKey.substring(32,48));
                File file = new File(srcFile);
                FileInputStream fis = new FileInputStream(file);
                int fileLen = (int)file.length();
                byte[] bytIn = new byte[fileLen];
                for(int i = 0; i < fileLen; i++) {
                    bytIn[i] = (byte)fis.read();
                }
                fis.close();
                //三次加密
                FileOutputStream fos = new FileOutputStream(destFile);
                fos.write(desEncrypt(desEncrypt(desEncrypt(bytIn,bytK1),bytK2),bytK3));
                fos.flush();
                fos.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 解密函数
     * 输入：
     * 要解密的文件，输出文件，密码（由0-F组成，共48个字符，表示3个8位的密码）如：
     * AD67EA2F3BE6E5ADD368DFE03120B5DF92A8FD8FEC2F0746
     * 其中：
     * AD67EA2F3BE6E5AD DES密码一
     * D368DFE03120B5DF DES密码二
     * 92A8FD8FEC2F0746 DES密码三
     */
    public static void triDecryptFile(String sKey, String srcFile, String destFile){
        try {
            if (sKey.length() == 48) {
                byte[] bytK1 = Text.getBytesFromStr(sKey.substring(0,16));
                byte[] bytK2 = Text.getBytesFromStr(sKey.substring(16,32));
                byte[] bytK3 = Text.getBytesFromStr(sKey.substring(32,48));
                File file = new File(srcFile);
                int fileLen = (int)file.length();
                FileInputStream fis = new FileInputStream(file);
                byte[] bytIn = new byte[fileLen];
                for(int i = 0;i<fileLen;i++){
                    bytIn[i] = (byte)fis.read();
                }
                fis.close();
                //三次DES解密
                byte[] bytOut = desDecrypt(desDecrypt(desDecrypt(bytIn,bytK3),bytK2),bytK1);
                FileOutputStream fos = new FileOutputStream(destFile);
                fos.write(bytOut);
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static byte[] triDecryptFile(String sKey, String srcFile){
        try {
            if (sKey.length() == 48) {
                byte[] bytK1 = Text.getBytesFromStr(sKey.substring(0,16));
                byte[] bytK2 = Text.getBytesFromStr(sKey.substring(16,32));
                byte[] bytK3 = Text.getBytesFromStr(sKey.substring(32,48));
                File file = new File(srcFile);
                int fileLen = (int)file.length();
                FileInputStream fis = new FileInputStream(file);
                byte[] bytIn = new byte[fileLen];
                for(int i = 0;i<fileLen;i++){
                    bytIn[i] = (byte)fis.read();
                }
                fis.close();
                //三次DES解密
                return desDecrypt(desDecrypt(desDecrypt(bytIn,bytK3),bytK2),bytK1);
            }
			return null;
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }

    /**
     * 用DES方法加密输入的字节
     * bytKey需为8字节长，是加密的密码
     */
    static byte[] desEncrypt(byte[] sourceBytes,byte[] keyBytes) throws Exception{
        DESKeySpec desKS = new DESKeySpec(keyBytes);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey sk = skf.generateSecret(desKS);
        Cipher cip = Cipher.getInstance("DES");
        cip.init(Cipher.ENCRYPT_MODE,sk);
        return cip.doFinal(sourceBytes);
    }

    /**
     * 用DES方法解密输入的字节
     * bytKey需为8字节长，是解密的密码
     */
    static byte[] desDecrypt(byte[] sourceBytes,byte[] keyBytes) throws Exception{
        DESKeySpec desKS = new DESKeySpec(keyBytes);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey sk = skf.generateSecret(desKS);
        Cipher cip = Cipher.getInstance("DES");
        cip.init(Cipher.DECRYPT_MODE,sk);
        return cip.doFinal(sourceBytes);
    }
}