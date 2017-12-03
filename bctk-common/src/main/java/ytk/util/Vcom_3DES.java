package ytk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Vcom_3DES {

	final static String FileName = "3des.key"; //密钥存放位置

	private int isEncrypt = -1;// 1为加密，0为解密
	// private String keyStr;// 加密/解密密钥，长度为16byte或者24byte。
	private String message; // 要加密/解密信息（解密时需为十六进制显示的字符串）
	
	private String keyStr;//密钥
	
	public Vcom_3DES(int isEncrypt, String message,String keyStr) {
		this.isEncrypt = isEncrypt;
		this.message = message;
		this.keyStr = keyStr;
	}

	public Vcom_3DES() {
	}

	public String Vcom3DESChiper() {

		Security.addProvider(new BouncyCastleProvider());
		URL url = this.getClass().getResource(FileName);
		
		//File myFile = new File();
		//if (!myFile.exists()) {
		//	System.err.println("Can't Find " + FileName);
		//}
		/*String keyStr = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((keyStr = in.readLine()) == null) {
				System.out.println("读取密钥失败！");
				return null;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		SecretKey key = new SecretKeySpec(keyStr.getBytes(), "DESede");

		byte[] text = null;
		byte[] bmessage = null;
		String returnStr = null;
		try {
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding", "BC");
			AlgorithmParameterSpec algorithmparameterspec = new IvParameterSpec(
					"12345678".getBytes());
			if (isEncrypt == 1) {
				bmessage = message.getBytes();
				cipher.init(Cipher.ENCRYPT_MODE, key, algorithmparameterspec);
			} else if (isEncrypt == 0) {
				bmessage = decodeHex(message);
				cipher.init(Cipher.DECRYPT_MODE, key, algorithmparameterspec);
			} else {
				System.out.println("加解密设置错误，请确认输入：1为加密；0为解密");
				return null;
			}
			text = cipher.doFinal(bmessage);
//			System.out.println(encodeHex(text));
			if (isEncrypt == 1) {
				returnStr = encodeHex(text);
			} else if (isEncrypt == 0) {
				returnStr = new String(text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	public int getIsEncrypt() {
		return isEncrypt;
	}

	public void setIsEncrypt(int isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static final String encodeHex(byte bytes[]) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 16)
				buf.append("0");
			buf.append(Long.toString(bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}

	public static final byte[] decodeHex(String hex) {
		char chars[] = hex.toCharArray();
		byte bytes[] = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			int newByte = 0;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = (byte) newByte;
			byteCount++;
		}
		return bytes;
	}

	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case 48: // '0'
			return 0;

		case 49: // '1'
			return 1;

		case 50: // '2'
			return 2;

		case 51: // '3'
			return 3;

		case 52: // '4'
			return 4;

		case 53: // '5'
			return 5;

		case 54: // '6'
			return 6;

		case 55: // '7'
			return 7;

		case 56: // '8'
			return 8;

		case 57: // '9'
			return 9;

		case 97: // 'a'
			return 10;

		case 98: // 'b'
			return 11;

		case 99: // 'c'
			return 12;

		case 100: // 'd'
			return 13;

		case 101: // 'e'
			return 14;

		case 102: // 'f'
			return 15;

		case 58: // ':'
		case 59: // ';'
		case 60: // '<'
		case 61: // '='
		case 62: // '>'
		case 63: // '?'
		case 64: // '@'
		case 65: // 'A'
		case 66: // 'B'
		case 67: // 'C'
		case 68: // 'D'
		case 69: // 'E'
		case 70: // 'F'
		case 71: // 'G'
		case 72: // 'H'
		case 73: // 'I'
		case 74: // 'J'
		case 75: // 'K'
		case 76: // 'L'
		case 77: // 'M'
		case 78: // 'N'
		case 79: // 'O'
		case 80: // 'P'
		case 81: // 'Q'
		case 82: // 'R'
		case 83: // 'S'
		case 84: // 'T'
		case 85: // 'U'
		case 86: // 'V'
		case 87: // 'W'
		case 88: // 'X'
		case 89: // 'Y'
		case 90: // 'Z'
		case 91: // '['
		case 92: // '\\'
		case 93: // ']'
		case 94: // '^'
		case 95: // '_'
		case 96: // '`'
		default:
			return 0;
		}
	}
	
	public static void main(String[] args) {
		//最多24个字节，最少？
		String key= "yycg12345678901234567890";//密钥
		//待加密串
		String oldstring = "test" + "#" + "test" + "#" + System.currentTimeMillis();
		System.out.println("原始串="+oldstring);
		//进行3des加密，1表示加密，key：密钥
		Vcom_3DES tempDesEn = new Vcom_3DES(1, oldstring,
				key);
		//加密后的串
		String strTemp = tempDesEn.Vcom3DESChiper();
		System.out.println("加密后："+strTemp);
		
		//strTemp为加密后串，0表示解密，key：密钥
		Vcom_3DES tempDe= new Vcom_3DES(0,strTemp,key);
		//解密后的串
		String strTempDe = tempDe.Vcom3DESChiper();
		System.out.println("解密后："+strTempDe);
	}
}
