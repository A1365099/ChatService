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

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Server implements Runnable{
    
    private final int PORTS;
    private final int noCon = 20; //Num max de clientes en espera, pueden ser más pero por cuestiones del proyecto lo dejo en 20
   
    //linked list de sockets
    private LinkedList<Socket> users1 = new LinkedList<Socket>();
   
 public Server(int PORTS){
     this.PORTS = PORTS;
 }
    public void run(){
        try{
            //A server socket waits for requests to come in over the network
            //Los parametros que recibe son el no. del puerto y el backlog, el cual indica el numero máximo de clientes que pueden esperar para aceptar la conexion
           ServerSocket server1 = new ServerSocket(PORTS,noCon);
       
            //Ciclo infinito para estar escuchando por nuevos clientes
            while(true){
                System.out.println("Listening....");
                //Cuando un cliente se conecte, se guarda el socket en la lista 
                // to handle multiple clients in parallel you have to call accept() multiple times and handle connections separately. 
                Socket cliente1 = server1.accept();
                users1.add(cliente1);
               
                //El Thread se inicializa estara atendiendo al cliente y escucha constantemente
                Runnable  run1 = new ThServer(cliente1,users1);
                Thread th1 = new Thread(run1);
                th1.start();
          
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        //iniciamos los threads que van a estar en cargo de  cada puerto
        Server server1= new Server(1000);
        Server server2 = new Server(2222);
        Thread th1 = new Thread(server1);
        th1.start();
        Thread th2 = new Thread(server2);
        th2.start();
        
        //Se pueden agregar más puertos pero por cuestiones del proyecto usaremos 2
        
        /*Server server3= new Server(4444);
        Server server4 = new Server(8888);
        Thread th3 = new Thread(server3);
        th3.start();
        Thread th4 = new Thread(server3);
        th4.start();
        */
    }
}
