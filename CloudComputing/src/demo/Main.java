package demo;

import rsa.RSA;
import signature.HashUtil;

public class Main {
	public static void main(String[] args) {
		if (args[0].equals(("0"))) {
			RSA.generateKeyPair();
		}else if (args[0].equals("1")) {
			//生成电子签名
			HashUtil hashUtil = new HashUtil();
			hashUtil.generateSignature(args[1], args[2], args[3]);
		} else if (args[0].equals("2")) {
			System.out.println(new HashUtil().audit(args[1], args[2], Integer.parseInt(args[3])));
		}
	}
}
