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
    // Variables
    private Socket socket;
    private String ip;
    private int port;
    private static class ConnexionHolder{
        private static final Connexion instance = new Connexion();
    }
    
    // Methods
    private Connexion() {}
    
    public static Connexion getInstance(){
            return ConnexionHolder.instance;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public void setPort(String port) {
        this.port = Integer.parseInt(port);
    }
    
    public Socket connect() {
        try {
            socket = new Socket(this.ip, this.port);
            return socket;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
        return null;
    }
}
