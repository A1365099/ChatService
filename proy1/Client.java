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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.DefaultCaret;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class Client implements Runnable{
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;
    private ObjectOutputStream outOb;
    private final int PORTC; //No. del puerto
    private String host = "localhost"; //Host
    
    private String msgs = "";
    JEditorPane jEP;
    
    ThServer thS;
   // private LinkedList<String> names = new LinkedList<String>();
    String name;
    private JFileChooser fc;
    public Client(JEditorPane panel, String name, int PORTC){
        this.jEP = panel;
     
        this.name = name;
        this.PORTC = PORTC;
      
        try {
            //Agregamos el socket
            client = new Socket(host,PORTC);
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(this.name+ " connected....\n"); //Anuncia un usuario nuevo conectado
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    @Override
    public void run() {
        try{
            //Ciclo infinito que escucha por mensajes del servidor y los muestra en el panel
            while(true){
                msgs += in.readUTF();
                jEP.setText(msgs);
                
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //Manda los mensajes
    public void sendMsg(String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
   
}
