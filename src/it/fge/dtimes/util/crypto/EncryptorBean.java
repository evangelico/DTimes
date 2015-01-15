package it.fge.dtimes.util.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptorBean {
	private static Logger logger = LoggerFactory.getLogger(EncryptorBean.class);

	static byte[] keyBytes = new byte[]{0x39, 0x34, 0x23, (byte) 0xbd, 0x12, 0x79, (byte) 0x8a, 0x1b, (byte) 0xc6, 0x2a, (byte) 0xe0, (byte) 0xb2, (byte) 0xf3, (byte) 0xfe, 0x7a, (byte) 0xd8};

	/**
	 * Decrypt String
	 */
	public static  String decryptString(String encryptInput) {
		String decrypt = "";
		try {

			byte[] decodedB64Data = Base64.decodeBase64(encryptInput.getBytes());
			SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);

			byte[] decrypted = cipher.doFinal(decodedB64Data);

			decrypt = new String(decrypted);
			
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
			logger.error(e.getMessage(), e);
		}
		return decrypt;

	}

	/**
	 * Encrypt String
	 */
	public static  String encryptString(String input) {
		String encrypt = "";
		try {
			SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] ecrypted = cipher.doFinal(input.getBytes());
			encrypt = Base64.encodeBase64URLSafeString(ecrypted);

		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
			logger.error(e.getMessage(), e);
		}
		return encrypt;

	}
}