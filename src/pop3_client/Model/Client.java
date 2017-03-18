/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3_client.Model;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yoannlathuiliere
 */
public class Client {
    private DataOutputStream output;
    private DataInputStream input;

    public Client(Socket socket) {
        try {
            this.output = new DataOutputStream(socket.getOutputStream());
            this.input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommande(Socket s, String cmd) {
        try
        {
            byte[] message = new byte[cmd.getBytes().length];
            System.arraycopy(cmd.getBytes(), 0, message, 0, cmd.getBytes().length);
            s.getOutputStream().write(message);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String readMessage() {
        try {
            InputStreamReader r = new InputStreamReader(input);  // Création d'un buffer à partir du la requête
            BufferedReader br = new BufferedReader(r); // Création d'un buffer à partir du la requête
            
            if (br.ready()) {
                return br.readLine(); // Lit la première ligne de la requête
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}