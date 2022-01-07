package com.kma.onleethryy.utils;

import android.util.Base64;
import androidx.annotation.NonNull;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    public static String encrypt(String clearText , String key){
        byte[] encryptedText;
        try {
            byte[] keyData = key.getBytes();
            SecretKey secretKey = new SecretKeySpec(keyData, "AES");
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedText = c.doFinal(clearText.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(encryptedText, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String encryptedText , String key){
        byte[] clearText;
        try {
            byte[] keyData = key.getBytes();
            SecretKey ks = new SecretKeySpec(keyData, "AES");
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, ks);
            clearText = c.doFinal(Base64.decode(encryptedText, Base64.DEFAULT));
            return new String(clearText, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

//    public static void createKey(Context context) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES , "AndroidKeyStore");
//        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
//                AppUtils.getAlias(context) ,
//                KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY
//        ).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
//                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
//                .setRandomizedEncryptionRequired(true)
//                .build();
//        keyGenerator.init(keyGenParameterSpec);
//        keyGenerator.generateKey();
//    }

//    public static SecretKey getKey(Context context) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException {
//        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
//        keyStore.load(null);
//        KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) keyStore.getEntry(AppUtils.getAlias(context) , null);
//        return entry.getSecretKey();
//    }

    public static String genKeyFromId(@NonNull String idUser1 , @NonNull String idUser2){
        String firstId = "";
        String secondId = "";
        //cai nao ngan hon se auto la id1, cai nao dai hon se la id2
        if (idUser1.length() < idUser2.length()){
            //neu id1 ngan hon id2
            firstId = idUser1;
            secondId = idUser2;
        }else if (idUser1.length() > idUser2.length()){
            //neu id2 ngan hon id1
            firstId = idUser2;
            secondId = idUser1;
        }else {
            //neu 2 id bang nhau
            char[] charArray1 = idUser1.toCharArray();
            char[] charArray2 = idUser2.toCharArray();
            for (int i = 0 ; i < charArray2.length ; i++){
                //convert sang ascii roi so sanh
                int char1 = (int) charArray1[i];
                int char2 = (int) charArray2[i];
                if (char1 < char2){
                    firstId = idUser1;
                    secondId = idUser2;
                    break;
                }else if (char1 > char2){
                    firstId = idUser2;
                    secondId = idUser1;
                    break;
                }else {
                    //do notthing
                }
            }
        }
        //băm hai id ra rồi concat lại
        String concatString = AppUtils.hashString(firstId) + AppUtils.hashString(secondId);
        //băm luôn chuỗi vừa concat tạo ra khoá
        String hashString = AppUtils.hashString(concatString);
        return hashString;
    }





}
