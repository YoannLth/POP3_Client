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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author yannick
 */
public class Context
{
    private SSLSocket socket;
    private int port;
    private String ip;
    private String timestamp;
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
    
    public void setTimestamp(String ts)
    {
        this.timestamp = ts;
    }
    
    public String getTimestamp()
    {
        return this.timestamp;
    }
    
    public void connect()
    {
        try {
            this.socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(this.ip, this.port);
            this.socket.setKeepAlive(true);
            this.EnableAnonCipherSuite();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
    
    public void sendCommand(String cmd)
    {
        cmd = cmd + "\n";
        try
        {
            byte[] message = new byte[cmd.getBytes().length];
            System.arraycopy(cmd.getBytes(), 0, message, 0, cmd.getBytes().length);
            this.socket.getOutputStream().write(message);
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
            rep += br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return rep;
    }
    
    public String receiveRep() {
        String rep = "";
        
        try {           
            InputStream is = this.socket.getInputStream(); // Récupère la requete du client
            InputStreamReader r = new InputStreamReader(is);  // Création d'un buffer à partir du la requête
            BufferedReader br = new BufferedReader(r); // Création d'un buffer à partir du la requête
            
            do {
                String msg = br.readLine();
                if(!msg.equals(".")) {
                  rep += msg + "\n";  
                }
            } while(br.ready());
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
    
    public void EnableAnonCipherSuite()
    {
        String[] supported = this.socket.getSupportedCipherSuites();
        List<String> list= new ArrayList<String>();

        for(int i = 0; i < supported.length; i++)
        {
            if(supported[i].indexOf("_anon_") > 0)
            {
                list.add(supported[i]);
            }
        }
        String[] anonCipherSuitesSupported = list.toArray(new String[0]);
        this.socket.setEnabledCipherSuites(anonCipherSuitesSupported);
    }
}
