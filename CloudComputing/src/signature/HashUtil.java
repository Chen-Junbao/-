package signature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import rsa.RSA;

public class HashUtil {
    /**
     * 计算字符串的hash值
     * @param string    明文
     * @param algorithm 算法名
     * @return          字符串的hash值
     */
    public static String hash(String string, String algorithm) {
        if (string.isEmpty()) {
            return "";
        }
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance(algorithm);
            byte[] bytes = hash.digest(string.getBytes("UTF-8"));
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 计算文件的hash值
     * @param file      文件File
     * @param algorithm 算法名
     * @return          文件的hash值
     */
    public static String hash(File file, String algorithm) {
        if (file == null || !file.isFile() || !file.exists()) {
            return "";
        }
        FileInputStream in = null;
        String result = "";
        byte buffer[] = new byte[1024];
        int len;
        try {
            MessageDigest hash = MessageDigest.getInstance(algorithm);
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                hash.update(buffer, 0, len);
            }
            byte[] bytes = hash.digest();

            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null!=in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public void generateSignature(String srcPath, String destPath, String privateKey) {
        RSA r = new RSA();
        Read_file_bytes read_file_bytes = new Read_file_bytes();
        byte[][] bytes = read_file_bytes.readFileBytes(srcPath);
        byte[][] bytes1 = new byte[1024][200];
        for (int i = 0; i < Read_file_bytes.len; i++) {
            String encode = null;
            try {
                encode = r.encrypt(new String(bytes[i]), privateKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String dec = hash(encode,"SHA1");
            bytes1[i] = dec.getBytes();
        }
        read_file_bytes.writeFileBytes(bytes1, destPath);
    }

    public boolean audit(String oriSignature, String newSignature, int blockNum){
        Read_file_bytes read_file_bytes = new Read_file_bytes();
        byte[][] bytes1 = read_file_bytes.readFileBytes(oriSignature);
        byte[][] bytes2 = read_file_bytes.readFileBytes(newSignature);
        if(new String(bytes1[blockNum]).equals(new String(bytes2[blockNum]))){
            return true;
        }
        else {
            return false;
        }
    }
}
