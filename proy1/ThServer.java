/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proy1;

/**
 *
 * @author Dann
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class ThServer implements Runnable{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    
    Chat chat;
    
    private LinkedList<Socket> users = new LinkedList<Socket>();
  //  private LinkedList<String> names = new LinkedList<String>();
    
    public ThServer(Socket soc,LinkedList users){
        this.socket = soc;
        this.users = users;
        
    }
   
    @Override
    public void run() {
        try {
            //Inicializamos los canales de comunicacion
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("Welcome to TecChat....\n");
           
            //Ciclo infinito para escuchar  mensajes del cliente
            while(true){
               String recibido = in.readUTF();
             
               //Cuando se recibe un mensaje se envia a todos los usuarios conectados 
                for (int i = 0; i < users.size(); i++) {
                    out = new DataOutputStream(users.get(i).getOutputStream());
                    out.writeUTF(recibido);
                }
               
            }
        } catch (IOException e) {
            //Si ocurre un excepcion lo mas seguro es que sea por que el cliente se desconecto , es removido de la lista de usuarios
            for (int i = 0; i < users.size(); i++) {
                if(users.get(i) == socket){
                    users.remove(i);
                    try {
                        out.writeUTF("User disconnected...");
                    } catch (IOException ex) {
                        //Logger.getLogger(ThServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                } 
            }
            
        }
    }
    
    
}
