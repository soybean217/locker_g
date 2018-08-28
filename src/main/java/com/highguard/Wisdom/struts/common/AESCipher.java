package com.highguard.Wisdom.struts.common;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.highguard.Wisdom.logging.WisdomLogger;

public class AESCipher {
	public static void aesEncrypt(String inFileName, String outFileName, String password) throws Exception {
		byte[] encodeFormat = getKey(password).getEncoded();
		SecretKeySpec key = new SecretKeySpec(encodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");		
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		InputStream in = new FileInputStream(inFileName);
		DataOutputStream out = new DataOutputStream(new FileOutputStream(outFileName));
		
		crypt(in, out, cipher);
		
		in.close();
		out.close();
	}
	
	public static void aesDecrypt(String inFileName, String outFileName, String password) throws Exception {
		byte[] encodeFormat = getKey(password).getEncoded();
		SecretKeySpec key = new SecretKeySpec(encodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");		
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		InputStream in = new FileInputStream(inFileName);
		DataOutputStream out = new DataOutputStream(new FileOutputStream(outFileName));
		
		crypt(in, out, cipher);
		
		in.close();
		out.close();
		
	}
	private static void crypt(InputStream in, OutputStream out, Cipher cipher) throws Exception {
		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(blockSize);
		
		byte[] inBytes = new byte[blockSize];
		byte[] outBytes = new byte[outputSize];
		
		int inLength = 0;
		boolean flag = true;
		while(flag) {
			inLength = in.read(inBytes);
			if(inLength == blockSize) {
				int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
				out.write(outBytes, 0, outLength);
			} else {
				flag = false;
			}
		}
		if(inLength > 0) {
			outBytes = cipher.doFinal(inBytes, 0, inLength);
		} else {
			outBytes = cipher.doFinal();
		}
		out.write(outBytes);
	}
	
	private static SecretKey getKey (String key) {
		SecretKey secretKey = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());
			kgen.init(128, secureRandom);
			secretKey = kgen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			WisdomLogger.logError(e, "AESCipher.getKey");
		}
		return secretKey;
	}
}
