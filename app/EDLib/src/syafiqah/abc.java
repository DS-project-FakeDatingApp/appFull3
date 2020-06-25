/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syafiqah;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author HP
 */
public class abc {
    
    //the methods below is substitution encrpyt
    public String message;

    public abc(String message) {
        this.message = message;
    }
    
     public abc() {
        message = "";
    }
    
    public int setKey(String w) {
        Random r = new Random();
        String key= w;
        int secret = Integer.parseInt(key);
        return secret;
   
    }
    
    public char[] setEncrypt(String s, String g) {
        abc secretKey = new abc();
        int key = secretKey.setKey(g);
        String st = s;
        System.out.println("Cipher text:");
        char a[] = st.toCharArray();
        for (int i=0; i<a.length;i++) {
                a[i] = (char) ((int)a[i] +key);  
                System.out.print(a[i]);
        }
        return a;
    }
    
   public char[] setDecrypt(char[] b, String h) {
        abc secretKey = new abc();
        int key = secretKey.setKey(h);
        System.out.println("Decrypt text:");
        char a[] = b;
        for (int i=0; i<a.length;i++) {
                a[i] = (char) ((int)a[i] - key);  
                System.out.print(a[i]);
        }
        return a;
    }
   
   
   //the methods below is java.crypto
    private static SecretKeySpec secretKey;
    private static byte[] key;
   
   public static void setKey1(String email) {
       MessageDigest sha = null;
       try {
          key = email.getBytes("UTF-8");
          sha = MessageDigest.getInstance("SHA-1");
          key = sha.digest(key);
          key = Arrays.copyOf(key,16);
          secretKey = new SecretKeySpec(key,"AES");
          
       }  catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }  catch (UnsupportedEncodingException ex) {
           ex.printStackTrace();
       }
       
   }
   
   
   public static String encryptData(String plaintext, String secret) {
   try {
       setKey1(secret);
       byte[] plaintTextByteArray = plaintext.getBytes("UTF-8");

       Cipher cipher = Cipher.getInstance("AES");
       cipher.init(Cipher.ENCRYPT_MODE, secretKey);
       byte[] cipherText = cipher.doFinal(plaintTextByteArray);
       return Base64.getEncoder().encodeToString(cipherText);
       
     /*  System.out.println("Original data: " + plaintext);
       System.out.println("Encrypted data:");
       for (int i = 0; i < cipherText.length; i++) {
           System.out.print(cipherText[i] + " ");

       }*/
   }
       catch(Exception ex){
           ex.printStackTrace();
       }
    return null;
   }

   public static String decryptData(String plaintext, String secret) {
   try {
       setKey1(secret);

       Cipher cipher = Cipher.getInstance("AES");
       cipher.init(Cipher.DECRYPT_MODE, secretKey);
       byte[] decryptText = cipher.doFinal(Base64.getDecoder().decode(plaintext));
       return new String(decryptText);
       
   }
       catch(Exception ex){
           ex.printStackTrace();
       }
       return null;
   }
}
