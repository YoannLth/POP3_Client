/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3_client.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yannick
 */
public class Context
{
    private Socket socket;
    private int port;
    private String ip;
    private static Context instance;
    
    public static Context getInstance()
    {
        if(Context.instance == null)
        {
            Context.instance = new Context();
        }
        return Context.instance;
    }
    
    public Context()
    {
        
    }
    
    public void setPort(int port)
    {
        this.port = port;
    }
    
    public void setIp(String ip)
    {
        this.ip = ip;
    }
    
    public void connect()
    {
        try {
            this.socket = new Socket(this.ip, this.port);
            this.socket.setKeepAlive(true);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
    
    public void sendCommand(String cmd)
    {
        try
        {
            byte[] message = new byte[cmd.getBytes().length];
            System.arraycopy(cmd.getBytes(), 0, message, 0, cmd.getBytes().length);
            this.socket.getOutputStream().write(message);
            System.out.println("Command sent");
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String receiveCommand()
    {
        String rep = "";
        
        try {           
            InputStream is = this.socket.getInputStream(); // Récupère la requete du client
            InputStreamReader r = new InputStreamReader(is);  // Création d'un buffer à partir du la requête
            BufferedReader br = new BufferedReader(r); // Création d'un buffer à partir du la requête
            rep = br.readLine(); // Lit la première ligne de la requête
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return rep;
    }
    
    public void close()
    {
        try
        {
            this.socket.close();
        } catch (IOException ex)
        {
            Logger.getLogger(Context.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}
