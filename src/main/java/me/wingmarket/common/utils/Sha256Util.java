package me.wingmarket.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Util {

	public static String encode(String str) {
		String encoded;
		try {
			/*
			 * MessageDigest : Thread-safe 하지 않다 모든 쓰레드에 대해 새 인스턴스를 사용해야한다
			 * 해시 알고리즘에는 MD5, SHA-1, SHA-256 있다
			 * MessageDigest.getInstance(String algorithm) 메소드의 인수에 알고리즘 이름을 지정
			 */

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
			encoded = bytesToHex(encodedhash);
		} catch (NoSuchAlgorithmException exception) {
			// NoSuchAlgorithmException : 어느 암호 알고리즘이 요구되었음에도 불구하고,
			// 현재의 환경에서는 사용 가능하지 않은 경우에 예외를 발생
			throw new IllegalArgumentException(exception.getMessage());
		}
		return encoded;
	}

	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (byte b : hash) {
			/*
			 * b 가 음수일 수 있다.
			 * Integer.toHexString()음수 값을 호출 하면 결과에 2 ^ 32가 추가된다.
			 * 16 진법에서 정수 인수의 문자열 표현을 부호없는 정수로 리턴
			 * 부호없는 정수 값 인수 플러스 2 32 인자가 음수이면 ; 그렇지 않으면 인수와 같다
			 * 결과가 toHexString() 0과 FF 사이에 있도록 하려면 하위 8비트를 제외한 모든 비트를 제거해야한다( 즉 , 0으로 설정)
			 *  이는 비트 AND 0xff로 수행 할 떄 얻는다
			 */
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
