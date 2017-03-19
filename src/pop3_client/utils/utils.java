/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3_client.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yoannlathuiliere
 */
public abstract class utils {
    public static String getTimestamp(String response) {
        String[] words = response.split(" ");
        
        if(words[5] != null) {
            return words[5];
        }
        
        return "";
    }
    
    public static boolean isError(String response) {
        String[] words = response.split(" ");
        
        if(words[0] == "-ERR") {
            return true;
        }
        
        return false;
    }
    
    public static String getEncodedPassword(String ts, String pass) {
        String to_encode = String.format("%s%s", ts, pass);
        byte[] encoded_pass_bytes;
        
        try {
            encoded_pass_bytes = MessageDigest.getInstance("MD5").digest(to_encode.getBytes());
            String pass_encoded = GetHexStringFromByteArray(encoded_pass_bytes);
            return pass_encoded;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }
    
    public static String GetHexStringFromByteArray(byte[] byte_array)
    {
        StringBuilder hexString = new StringBuilder();
            
        for (int i = 0; i < byte_array.length; i++) 
        {
            if ((0xff & byte_array[i]) < 0x10) 
            {
                hexString.append("0" + Integer.toHexString((0xFF & byte_array[i])));
            } 
            else 
            {
                hexString.append(Integer.toHexString(0xFF & byte_array[i]));
            }
        }

        return hexString.toString();
    }
    
    public static int getNbMessages(String response) {
        String[] words = response.split(" ");
        int nbMessages = Integer.parseInt(words[1]);
        return nbMessages;
    }
    
    public static int getNbBytes(String response) {
        String[] words = response.split(" ");
        int nbBytes = Integer.parseInt(words[2]);
        return nbBytes;
    }
}
