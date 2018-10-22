package cn.zjt.iot.oncar.server.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apaches.commons.codec.DecoderException;
import org.apaches.commons.codec.binary.Hex;

/**
 * @author Mr Dk.
 * @since 2018.5.26
 * @version 2018.6.12
 */

public class SecurityUtil {

	private static Cipher cipher;
	private static SecretKey generateKey;
	private static byte[] KEY = {-49, -122, -91, 45, -17, 69, -88, -126, -47, -24, 37, 46, -42, 97, 27, -44};
	private static String Algorithm = "AES";

	private static void InitKey() {
		generateKey = new SecretKeySpec(KEY, Algorithm);
	}

	public static String Encode(String src) {
		try {
			if (generateKey == null) {
				InitKey();
			}
			if (cipher == null) {
				cipher = Cipher.getInstance(Algorithm);
			}

			cipher.init(Cipher.ENCRYPT_MODE, generateKey);
			byte[] resultBytes = cipher.doFinal(src.getBytes());

			return Hex.encodeHexString(resultBytes);

		} catch (InvalidKeyException e) {
			// Catch Exception
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// Catch Exception
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// Catch Exception
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// Catch Exception
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// Catch Exception
			e.printStackTrace();
		}

		return null;
	}

	public static String Decode(String secret) {

		try {
			if (generateKey == null) {
				InitKey();
			}
			if (cipher == null) {
				cipher = Cipher.getInstance(Algorithm);
			}

			cipher.init(Cipher.DECRYPT_MODE, generateKey);
			//System.out.println(secret.toCharArray());
			byte[] result = Hex.decodeHex(secret.toCharArray());

			return new String(cipher.doFinal(result));

		} catch (InvalidKeyException e) {
			// Catch Exception
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// Catch Exception
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// Catch Exception
			e.printStackTrace();
		} catch (DecoderException e) {
			// Catch Exception
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// Catch Exception
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// Catch Exception
			e.printStackTrace();
		}

		return null;
	}
}
