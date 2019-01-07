package rsa;

import javax.crypto.Cipher;

import java.io.*;
import java.security.*;
import java.util.Base64;

public class RSA {
	private static int KEYSIZE = 1024;	//key size
	private static String PUBLIC_KEY_FILE = "public.key";		//public key file
	private static String PRIVATE_KEY_FILE = "private.key";	//private key file

	//Generate public key file and private key file
	public static void generateKeyPair() {
		//Generate key pair
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		kpg.initialize(KEYSIZE, new SecureRandom());
		KeyPair kp = kpg.generateKeyPair();

		//Get public key and private key
		Key publicKey = kp.getPublic();
		Key privateKey = kp.getPrivate();

		ObjectOutputStream output1 = null;
		ObjectOutputStream output2 = null;
		try {
			output1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
			output2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
			output1.writeObject(publicKey);
			output2.writeObject(privateKey);
			output1.close();
			output2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//RSA encryption
	public static String encrypt(String source, String publicKeyFile) throws Exception {
		//Get public key
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(publicKeyFile));
		Key key = (Key) ois.readObject();
		ois.close();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] b = source.getBytes();
		String cryptograph = encoder.encodeToString(b);
		return cryptograph;
	}

	//RSA decryption
	public static String decrypt(String cryptograph, String privateKeyFile) throws Exception {
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(privateKeyFile));
		Key key = (Key) input.readObject();
		input.close();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] b1 = decoder.decode(cryptograph);
		String source = new String(b1);
		return source;
	}
}