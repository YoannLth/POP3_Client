/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3_client.Model;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author yoannlathuiliere
 */
public class Connexion {
    private Socket socket;
    
    private static class ConnexionHolder{
        private static final Connexion instance = new Connexion();
    }

    public static Connexion getInstance(){
            return ConnexionHolder.instance;
    }
        
    private Connexion() {}
    
    public Socket connect(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            return socket;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
        return null;
    }
}
