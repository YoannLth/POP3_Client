/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3_client.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
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
        String firstLine = words[0];
        String[] flWords = firstLine.split(" ");
        
        if(flWords[0].equals("-ERR")) {
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
    
    public static HashMap<String,String> parseRetr(Integer id, String mess) {
        HashMap ret = new HashMap();
        String lines[] = mess.split("\\r?\\n");
        String[] exp = lines[1].split(" ");
        
        String dateFormated = lines[3].replace("Date: ","");
        String object = lines[4].replace("Subject: ","");
        String[] obj = object.split("\\(");
        String objectFormated = obj[0];
        
        String body = "";
        
        for(int i = 6; i<lines.length; i++) {
            body += lines[i];
        }
        
        ret.put("Expeditor", exp[1]);
        ret.put("Date", dateFormated);
        ret.put("Object", objectFormated);
        ret.put("Body", body);
        ret.put("ID", "" + id);
        
        return ret;
    }
}
