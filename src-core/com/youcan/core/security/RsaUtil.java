package com.youcan.core.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import com.youcan.core.security.EncryptException;

public class RsaUtil {
	RsaUtil() {
		//do nothing
	}
	/**
	 * 把成生的一对密钥保存到RSAKey.xml文件中
	 */
	public static void saveRSAKey(String keyFile) {
		try {
			SecureRandom sr = new SecureRandom();
			KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			// 注意密钥大小最好为1024,否则解密会有乱码情况.
			kg.initialize(1024, sr);
			FileOutputStream fos = new FileOutputStream(keyFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			// 生成密钥
			oos.writeObject(kg.generateKeyPair());
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得RSA加密的密钥。
	 * 
	 * @return KeyPair返回对称密钥
	 */
	public static KeyPair getKeyPair(String keyFile) {
		// 产生新密钥对
		KeyPair kp = null;
		try {
			FileInputStream is = new FileInputStream(keyFile);
			ObjectInputStream oos = new ObjectInputStream(is);
			kp = (KeyPair) oos.readObject();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw new EncryptException(e.getMessage());
			} catch (EncryptException e1) {
				e1.printStackTrace();
			}
		}
		return kp;
	}

	/**
	 * 文件file进行加密并保存目标文件destFile中
	 * 
	 * @param srcFileName
	 *            要加密的文件 如c:/test/srcFile.txt
	 * @param destFileName
	 *            加密后存放的文件名 如c:/加密后文件.txt
	 */
	public static void encryptFile(String keyFile, String srcFileName, String destFileName)
			throws Exception {
		OutputStream outputWriter = null;
		InputStream inputReader = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			byte[] buf = new byte[100];
			int bufl;
			cipher.init(Cipher.ENCRYPT_MODE, getKeyPair(keyFile).getPublic());
			outputWriter = new FileOutputStream(destFileName);
			inputReader = new FileInputStream(srcFileName);
			while ((bufl = inputReader.read(buf)) != -1) {
				byte[] encText = null;
				byte[] newArr = null;
				if (buf.length == bufl) {
					newArr = buf;
				} else {
					newArr = new byte[bufl];
					for (int i = 0; i < bufl; i++) {
						newArr[i] = buf[i];
					}
				}
				encText = cipher.doFinal(newArr);
				outputWriter.write(encText);
			}
			outputWriter.flush();

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (outputWriter != null) {
					outputWriter.close();
				}
				if (inputReader != null) {
					inputReader.close();
				}
			} catch (Exception e) {
				//do nothing
			}
		}
	}

	/**
	 * 文件file进行加密并保存目标文件destFile中
	 * 
	 * @param srcFileName
	 *            已加密的文件 如c:/加密后文件.txt
	 * @param destFileName
	 *            解密后存放的文件名 如c:/ test/解密后文件.txt
	 */
	public static void decryptFile(String keyFile, String srcFileName, String destFileName)
			throws Exception {
		OutputStream outputWriter = null;
		InputStream inputReader = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			byte[] buf = new byte[128];
			int bufl;
			cipher.init(Cipher.DECRYPT_MODE, getKeyPair(keyFile).getPrivate());

			outputWriter = new FileOutputStream(destFileName);
			inputReader = new FileInputStream(srcFileName);
			while ((bufl = inputReader.read(buf)) != -1) {
				byte[] encText = null;
				byte[] newArr = null;
				if (buf.length == bufl) {
					newArr = buf;
				} else {
					newArr = new byte[bufl];
					for (int i = 0; i < bufl; i++) {
						newArr[i] = buf[i];
					}
				}
				encText = cipher.doFinal(newArr);
				outputWriter.write(encText);
			}
			outputWriter.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (outputWriter != null) {
					outputWriter.close();
				}
				if (inputReader != null) {
					inputReader.close();
				}
			} catch (Exception e) {
				//do nothing
			}
		}
	}
}
